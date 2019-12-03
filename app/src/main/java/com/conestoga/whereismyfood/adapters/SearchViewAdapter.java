package com.conestoga.whereismyfood.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class SearchViewAdapter extends ArrayAdapter implements Filterable {

    List<String> allCodes;
    List<String> originalCodes;
    StringFilter filter;

    public SearchViewAdapter(@NonNull Context context, @LayoutRes int resource
            , @IdRes int textViewResourceId, @NonNull List<String> objects) {
        super(context, resource, textViewResourceId, objects);
        this.allCodes = objects;
        this.originalCodes = objects;
    }

    public SearchViewAdapter(Context context, int resource, List<String> keys) {
        super(context, resource, keys);
        allCodes = keys;
        originalCodes = keys;
    }

    public int getCount() {
        return allCodes != null ? allCodes.size() : 0;
    }

    public Object getItem(int position) {
        return allCodes != null ? allCodes.get(position) : null;
    }

    public long getItemId(int position) {
        return position;
    }

    private class StringFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();
            final List<String> list = originalCodes;

            int count = list.size();
            final ArrayList<String> nlist = new ArrayList<>(count);
            String filterableString;

            if (constraint != null) {
                String filterString = constraint.toString().toLowerCase();

                for (int i = 0; i < count; i++) {
                    filterableString = list.get(i);
                    if (filterableString.toLowerCase().contains(filterString)) {
                        nlist.add(filterableString);
                    }
                }
            }

            results.values = nlist;
            results.count = nlist.size();
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            allCodes = (ArrayList<String>) results.values;
            notifyDataSetChanged();
        }

    }


    @Override
    public Filter getFilter() {
        return new StringFilter();
    }
}