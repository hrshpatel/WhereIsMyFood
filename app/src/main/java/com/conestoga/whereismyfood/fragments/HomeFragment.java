package com.conestoga.whereismyfood.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.conestoga.whereismyfood.R;
import com.conestoga.whereismyfood.activities.LoginActivity;
import com.conestoga.whereismyfood.adapters.HomeListAdapter;
import com.conestoga.whereismyfood.adapters.SearchViewAdapter;
import com.conestoga.whereismyfood.apiutils.APIClient;
import com.conestoga.whereismyfood.apiutils.APIInterface;
import com.conestoga.whereismyfood.customviews.CustomSearchView;
import com.conestoga.whereismyfood.models.SubscriptionModel;
import com.conestoga.whereismyfood.response.GetSubscriptionResponse;
import com.conestoga.whereismyfood.utils.AppSharedPref;
import com.conestoga.whereismyfood.utils.ProgressDialogUtil;

import org.w3c.dom.Text;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private CustomSearchView mCustomSearchView;
    private ArrayList<SubscriptionModel> mSubscriptionList;
    private ArrayList<SubscriptionModel> mOriginalList;
    private RecyclerView mRecyclerView;
    Call<GetSubscriptionResponse> subscriptionApi;
    private APIInterface mApiInterface;
    private ArrayList<String> mHintsList;
    private AppSharedPref mSharedPref;

    private Callback<GetSubscriptionResponse> subscriptionResponse = new Callback<GetSubscriptionResponse>() {
        @Override
        public void onResponse(Call<GetSubscriptionResponse> call, Response<GetSubscriptionResponse> response) {
            ProgressDialogUtil.dismissProgress();

            GetSubscriptionResponse subscriptionResponse = response.body();

            if (subscriptionResponse.getSuccess().equals("0")) {
                Toast.makeText(getActivity(), "" + subscriptionResponse.getMessage()
                        , Toast.LENGTH_LONG).show();
            } else if (subscriptionResponse.getSuccess().equals("1")) {
                mSubscriptionList = subscriptionResponse.getSubscriptionModelList();
                mOriginalList = new ArrayList<>();
                mOriginalList.addAll(mSubscriptionList);
                if (mSubscriptionList != null && mSubscriptionList.size() > 0) {
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    HomeListAdapter homeListAdapter = new HomeListAdapter(getActivity(), mSubscriptionList);
                    mRecyclerView.setAdapter(homeListAdapter);

                    mHintsList = new ArrayList<>();

                    for (int i = 0; i < mSubscriptionList.size(); i++) {
                        mHintsList.add(mSubscriptionList.get(i).getSubName());
                    }

                    SearchViewAdapter adapter = new SearchViewAdapter(getActivity()
                            , R.layout.item_search_view, R.id.txt_search_name, mHintsList);

                    mCustomSearchView.setAdapter(adapter);
                }
            }

        }

        @Override
        public void onFailure(Call<GetSubscriptionResponse> call, Throwable t) {
            call.cancel();
            ProgressDialogUtil.dismissProgress();
            Toast.makeText(getActivity(), getResources().getString(R.string.str_something_went_worng)
                    , Toast.LENGTH_LONG).show();

        }
    };

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();

        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeView(view);
        setListeners();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mRecyclerView != null) {
            if (mSharedPref.getUserType().equals("1")) {
                ProgressDialogUtil.showProgress(getActivity(), "Loading", "Please Wait...", false);
                subscriptionApi = mApiInterface.getSubscription(mSharedPref.getEmailId());
                subscriptionApi.enqueue(subscriptionResponse);
            } else {
                ProgressDialogUtil.showProgress(getActivity(), "Loading", "Please Wait...", false);
                subscriptionApi = mApiInterface.getSubscription();
                subscriptionApi.enqueue(subscriptionResponse);
            }
        }
    }

    private void initializeView(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view_home);
        mApiInterface = APIClient.getClient().create(APIInterface.class);
        mSharedPref = AppSharedPref.getInstance(getActivity());

        mCustomSearchView = getActivity().findViewById(R.id.toolbar_iv_search);
        mCustomSearchView.setSubmitButtonEnabled(true);
        mCustomSearchView.setVisibility(View.VISIBLE);

//        if (mSharedPref.getUserType().equals("1")) {
//            ProgressDialogUtil.showProgress(getActivity(), "Loading", "Please Wait...", false);
//            subscriptionApi = mApiInterface.getSubscription(mSharedPref.getEmailId());
//            subscriptionApi.enqueue(subscriptionResponse);
//        } else {
//            ProgressDialogUtil.showProgress(getActivity(), "Loading", "Please Wait...", false);
//            subscriptionApi = mApiInterface.getSubscription();
//            subscriptionApi.enqueue(subscriptionResponse);
//        }
    }

    private void setListeners() {
        mCustomSearchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("///////////", "Clicked");
                if (view instanceof TextView) {
                    String selectedText = ((TextView) view).getText().toString();

                    for (int j = 0; j < mSubscriptionList.size(); j++) {
                        if (selectedText.equalsIgnoreCase(mSubscriptionList.get(j).getSubName())) {
                            SubscriptionModel subscriptionModel = mSubscriptionList.get(j);
                            mSubscriptionList.clear();
                            mRecyclerView.getAdapter().notifyDataSetChanged();
                            mSubscriptionList.add(subscriptionModel);
                            mRecyclerView.getAdapter().notifyDataSetChanged();
                        }
                    }
                }
            }
        });

        mCustomSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                mSubscriptionList.clear();
                mRecyclerView.getAdapter().notifyDataSetChanged();
                mSubscriptionList.addAll(mOriginalList);
                mRecyclerView.getAdapter().notifyDataSetChanged();
                return false;
            }
        });
    }

}