package com.conestoga.whereismyfood.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.conestoga.whereismyfood.R;
import com.conestoga.whereismyfood.adapters.OrdersListAdapter;
import com.conestoga.whereismyfood.apiutils.APIClient;
import com.conestoga.whereismyfood.apiutils.APIInterface;
import com.conestoga.whereismyfood.databinding.ActivityOrdersBinding;
import com.conestoga.whereismyfood.models.OrderDetail;
import com.conestoga.whereismyfood.response.OrdersResponse;
import com.conestoga.whereismyfood.response.ReviewResponse;
import com.conestoga.whereismyfood.utils.AppSharedPref;
import com.conestoga.whereismyfood.utils.ProgressDialogUtil;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersActivity extends AppCompatActivity {

    private ActivityOrdersBinding ordersBinding;
    private ArrayList<OrderDetail> orderDetailList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ordersBinding = DataBindingUtil.setContentView(this, R.layout.activity_orders);

        setToolbar();
        callOrdersApi();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setToolbar() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }

        ordersBinding.toolbarInclude.toolbarTitle.setText(R.string.str_checkout);

        setSupportActionBar(ordersBinding.toolbarInclude.toolbar);

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayShowTitleEnabled(false);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowHomeEnabled(true);
        }

    }

    private void callOrdersApi() {
        ProgressDialogUtil.showProgress(getApplicationContext(), "Loading", "Please Wait...", false);

        APIInterface mApiInterface = APIClient.getClient().create(APIInterface.class);
        final Call<OrdersResponse> ordersApi;
        if (AppSharedPref.getInstance(getApplicationContext()).getUserType().equalsIgnoreCase("0")) {
            ordersApi = mApiInterface.getOrderHistory(AppSharedPref.getInstance(getApplicationContext()).getUserId());
        } else {
            ordersApi = mApiInterface.getOrderHistoryVendors(AppSharedPref.getInstance(getApplicationContext()).getVendorName());
        }

        ProgressDialogUtil.showProgress(this, "Loading", "Please Wait...", false);

        ordersApi.enqueue(new Callback<OrdersResponse>() {
            @Override
            public void onResponse(Call<OrdersResponse> call, Response<OrdersResponse> response) {
                ProgressDialogUtil.dismissProgress();

                OrdersResponse ordersResponse = response.body();
                if (ordersResponse.getSuccess().equalsIgnoreCase("0")) {
                    Toast.makeText(OrdersActivity.this, ordersResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    ordersBinding.txtNoOrderFound.setVisibility(View.VISIBLE);
                    ordersBinding.recyclerOrders.setVisibility(View.GONE);
                } else {
                    orderDetailList = ordersResponse.getOrderDetailArrayList();
                    if (orderDetailList.size() > 0) {
                        ordersBinding.txtNoOrderFound.setVisibility(View.GONE);
                        ordersBinding.recyclerOrders.setVisibility(View.VISIBLE);
                        ordersBinding.recyclerOrders.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        ordersBinding.recyclerOrders.setAdapter(new OrdersListAdapter(orderDetailList));
                    } else {
                        ordersBinding.txtNoOrderFound.setVisibility(View.VISIBLE);
                        ordersBinding.recyclerOrders.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<OrdersResponse> call, Throwable t) {
                call.cancel();
                ProgressDialogUtil.dismissProgress();
                Toast.makeText(OrdersActivity.this, getResources().getString(R.string.str_something_went_worng)
                        , Toast.LENGTH_LONG).show();
            }
        });

    }
}
