package donation.solutions.hamza.com.hotingoadmin.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import donation.solutions.hamza.com.hotingoadmin.R;
import donation.solutions.hamza.com.hotingoadmin.adapter.RequestAdapter;
import donation.solutions.hamza.com.hotingoadmin.model.ServiceOrderModel;
import donation.solutions.hamza.com.hotingoadmin.service.ApiClient;
import donation.solutions.hamza.com.hotingoadmin.service.ApiEndpointInterface;
import donation.solutions.hamza.com.hotingoadmin.service.AuthInterceptor;
import donation.solutions.hamza.com.hotingoadmin.utils.Utilities;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestActivity extends AppCompatActivity {

    @BindView(R.id.requestsRV)
    RecyclerView requestsRV;

    RequestAdapter requestAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        ButterKnife.bind(this);

        requestsRV.setLayoutManager
                (new GridLayoutManager(this, 1));


        Utilities.showLoadingDialog(this, R.color.colorAccent);

        ApiEndpointInterface apiService =
                ApiClient.getClient(new AuthInterceptor(null)).create(ApiEndpointInterface.class);

        Call<ArrayList<ServiceOrderModel>> call = apiService.getServiceOrders();

        call.enqueue(new Callback<ArrayList<ServiceOrderModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ServiceOrderModel>> call, Response<ArrayList<ServiceOrderModel>> response) {
                Utilities.dismissLoadingDialog();
                if (response.isSuccessful()) {

                    requestAdapter = new RequestAdapter(
                            R.layout.request_item, RequestActivity.this, response.body(), new RequestAdapter.onRequestClickListner() {

                        @Override
                        public void onRequestClickListner(ServiceOrderModel serviceOrderModel) {


                            Intent i = new Intent(getApplicationContext(),RequestDeatails.class);
                            i.putExtra("SERVICE_ORDER_DETAILS",serviceOrderModel);
                            startActivity(i);

                        }
                    });

                    requestsRV.setAdapter(requestAdapter);

                }
            }

            @Override
            public void onFailure(Call<ArrayList<ServiceOrderModel>> call, Throwable t) {
                Utilities.dismissLoadingDialog();
            }
        });

    }


}

