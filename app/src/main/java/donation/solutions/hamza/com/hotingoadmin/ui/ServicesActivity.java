package donation.solutions.hamza.com.hotingoadmin.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import donation.solutions.hamza.com.hotingoadmin.R;
import donation.solutions.hamza.com.hotingoadmin.adapter.ServicesAdapter;
import donation.solutions.hamza.com.hotingoadmin.model.ServicesResponce;
import donation.solutions.hamza.com.hotingoadmin.service.ApiClient;
import donation.solutions.hamza.com.hotingoadmin.service.ApiEndpointInterface;
import donation.solutions.hamza.com.hotingoadmin.service.AuthInterceptor;
import donation.solutions.hamza.com.hotingoadmin.utils.Utilities;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class ServicesActivity extends AppCompatActivity {

    @BindView(R.id.servicesRV)
    RecyclerView servicesRecycler;

    private ServicesAdapter servicesAdapter;
    ArrayList<ServicesResponce> services;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        ButterKnife.bind(this);

        servicesRecycler.setLayoutManager
                (new LinearLayoutManager(this
                        , LinearLayoutManager.VERTICAL, false));

        Utilities.showLoadingDialog(this, R.color.colorAccent);

        ApiEndpointInterface apiService =
                ApiClient.getClient(new AuthInterceptor(null)).create(ApiEndpointInterface.class);


        Call<ArrayList<ServicesResponce>> call = apiService.getServices();

        call.enqueue(new Callback<ArrayList<ServicesResponce>>() {
            @Override
            public void onResponse(Call<ArrayList<ServicesResponce>> call, Response<ArrayList<ServicesResponce>> response) {
                Utilities.dismissLoadingDialog();
                if (response.isSuccessful()) {
                    services = new ArrayList<>();
                    services = response.body();

                    servicesAdapter = new ServicesAdapter(services,
                            R.layout.service_row_layout, ServicesActivity.this, new ServicesAdapter.onServiceClickListner() {

                        @Override
                        public void onserviceClickListner(ServicesResponce servicesResponce) {

                        }

                        @Override
                        public void editService(ServicesResponce service) {
                            Timber.d(service.getId());
                            android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
                            UpdateService updateService = new UpdateService();

                            Bundle bundle = new Bundle();
                            bundle.putString("id", service.getId());
                            updateService.setArguments(bundle);
                            updateService.show(fm, "Show fragment");

                        }

                        @Override
                        public void deleteService(ServicesResponce service) {

                            Utilities.showLoadingDialog(ServicesActivity.this, R.color.colorAccent);

                            ApiEndpointInterface apiService =
                                    ApiClient.getClient(new AuthInterceptor(null)).create(ApiEndpointInterface.class);


                            Call<Void> call = apiService.deleteService(service.getId(), "false");

                            call.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    Utilities.dismissLoadingDialog();

                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    Utilities.dismissLoadingDialog();
                                }


                            });


                        }

                    });

                    servicesRecycler.setAdapter(servicesAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ServicesResponce>> call, Throwable t) {
                Utilities.dismissLoadingDialog();
            }
        });

    }
}