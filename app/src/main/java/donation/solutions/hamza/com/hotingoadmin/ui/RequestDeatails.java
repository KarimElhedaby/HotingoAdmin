package donation.solutions.hamza.com.hotingoadmin.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import donation.solutions.hamza.com.hotingoadmin.R;
import donation.solutions.hamza.com.hotingoadmin.model.ServiceOrderModel;
import donation.solutions.hamza.com.hotingoadmin.service.ApiClient;
import donation.solutions.hamza.com.hotingoadmin.service.ApiEndpointInterface;
import donation.solutions.hamza.com.hotingoadmin.service.AuthInterceptor;
import donation.solutions.hamza.com.hotingoadmin.utils.Utilities;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestDeatails extends AppCompatActivity {

    ServiceOrderModel serviceOrderD;
    @BindView(R.id.requestTypeTV)
    TextView requestTypeTV;

    @BindView(R.id.requestRoomTV)
    TextView requestRoomTV;

    @BindView(R.id.requstDescTV)
    TextView requstDescTV;

    @BindView(R.id.requestCustomerNameTV)
    TextView requestCustomerNameTV;

    @BindView(R.id.requestPhoneTV)
    TextView requestPhoneTV;

    @BindView(R.id.requestAcceptB)
    Button requestAcceptB;

    @BindView(R.id.requestRefuseB)
    Button requestRefuseB;
    @BindView(R.id.statusTV)
    TextView statusTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_deatails);
        ButterKnife.bind(this);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            serviceOrderD = (ServiceOrderModel) bundle.getSerializable("SERVICE_ORDER_DETAILS");

            requestTypeTV.setText(serviceOrderD.getService().getName().toString());
            requestRoomTV.setText(serviceOrderD.getRoomNo() + "");
            requstDescTV.setText(serviceOrderD.getNote().toString());
            requestCustomerNameTV.setText(serviceOrderD.getUserServicesOrded().getName().toString());
            requestPhoneTV.setText(serviceOrderD.getUserServicesOrded().getPhone().toString());
        }


        if (serviceOrderD.getStatus().equals("Rejected")) {
            requestRefuseB.setVisibility(View.GONE);
            requestAcceptB.setVisibility(View.VISIBLE);
            statusTV.setText("Rejected");

        } else if (serviceOrderD.getStatus().equals("Accepted")) {
            requestRefuseB.setVisibility(View.VISIBLE);
            requestAcceptB.setVisibility(View.GONE);
            statusTV.setText("Accepted");

        } else if (serviceOrderD.getStatus().equals("Pindding")) {
            requestRefuseB.setVisibility(View.VISIBLE);
            requestAcceptB.setVisibility(View.VISIBLE);
            statusTV.setText("Pindding");
        }

    }


    @OnClick({R.id.requestAcceptB, R.id.requestRefuseB})
    public void onViewClicked(View view) {

        if (view.getId() == R.id.requestAcceptB) {
            Utilities.showLoadingDialog(this, R.color.colorAccent);

            ApiEndpointInterface apiService =
                    ApiClient.getClient(new AuthInterceptor(null)).create(ApiEndpointInterface.class);

            Call<ServiceOrderModel> call = apiService.acceptOrderService(serviceOrderD.getId().toString());

            call.enqueue(new Callback<ServiceOrderModel>() {
                @Override
                public void onResponse(Call<ServiceOrderModel> call, Response<ServiceOrderModel> response) {
                    Utilities.dismissLoadingDialog();
                    if (response.isSuccessful()) {
                        requestRefuseB.setVisibility(View.VISIBLE);
                        requestAcceptB.setVisibility(View.GONE);
                        statusTV.setText("Accepted");
                        Toast.makeText(RequestDeatails.this, "Order Accepted Successfullly", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ServiceOrderModel> call, Throwable t) {
                    Toast.makeText(RequestDeatails.this, "Some thing went wronge", Toast.LENGTH_SHORT).show();
                    Utilities.dismissLoadingDialog();
                }
            });

        } else if (view.getId() == R.id.requestRefuseB) {
            Utilities.showLoadingDialog(this, R.color.colorAccent);

            ApiEndpointInterface apiService2 =
                    ApiClient.getClient(new AuthInterceptor(null)).create(ApiEndpointInterface.class);

            Call<ServiceOrderModel> call2 = apiService2.cancleOrderService(serviceOrderD.getId().toString());

            call2.enqueue(new Callback<ServiceOrderModel>() {
                @Override
                public void onResponse(Call<ServiceOrderModel> call, Response<ServiceOrderModel> response) {
                    Utilities.dismissLoadingDialog();
                    if (response.isSuccessful()) {
                        requestRefuseB.setVisibility(View.GONE);
                        requestAcceptB.setVisibility(View.VISIBLE);
                        statusTV.setText("Rejected");
                        Toast.makeText(RequestDeatails.this, "Order Canceled Successfullly", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ServiceOrderModel> call, Throwable t) {
                    Toast.makeText(RequestDeatails.this, "Some thing went wronge", Toast.LENGTH_SHORT).show();
                    Utilities.dismissLoadingDialog();
                }
            });

        }
    }
}

