package donation.solutions.hamza.com.hotingoadmin.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import donation.solutions.hamza.com.hotingoadmin.R;
import donation.solutions.hamza.com.hotingoadmin.service.ApiClient;
import donation.solutions.hamza.com.hotingoadmin.service.ApiEndpointInterface;
import donation.solutions.hamza.com.hotingoadmin.service.AuthInterceptor;
import donation.solutions.hamza.com.hotingoadmin.utils.MyApplication;
import donation.solutions.hamza.com.hotingoadmin.utils.Utilities;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.bookingCV)
    CardView booking;

    @BindView(R.id.requestsCV)
    CardView request;

    @BindView(R.id.roomsCV)
    CardView rooms;

    @BindView(R.id.servicesCV)
    CardView services;

    @BindView(R.id.notification)
    ImageView notification;
    @BindView(R.id.notificationCount)
    TextView notificationCount;

    @BindView(R.id.logoutBTN)
    ImageView logoutBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Utilities.showLoadingDialog(this, R.color.colorAccent);

        ApiEndpointInterface apiService =
                ApiClient.getClient(new AuthInterceptor(MyApplication.getPrefManager(getApplicationContext()).getUser().getToken())).create(ApiEndpointInterface.class);

        Call<Integer> call = apiService.getNotificationCount();

        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Utilities.dismissLoadingDialog();
                if (response.isSuccessful()) {
                    //Toast.makeText(getApplicationContext(), response.body().toString(), Toast.LENGTH_SHORT).show();
                    if (response.body() != 0) {
                        notificationCount.setText(response.body().toString());
                        notificationCount.setVisibility(View.VISIBLE);
                    } else {
                        notificationCount.setVisibility(View.GONE);
                    }

                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Utilities.dismissLoadingDialog();
            }
        });

    }

    @OnClick(R.id.requestsCV)
    void openRequest() {
        startActivity(new Intent(MainActivity.this, RequestActivity.class));

    }


    @OnClick(R.id.roomsCV)
    void openRooms() {
        startActivity(new Intent(MainActivity.this, RoomsActivity.class));

    }

    @OnClick(R.id.bookingCV)
    void openbooking() {
        startActivity(new Intent(MainActivity.this, BookingActivity.class));

    }

    @OnClick(R.id.logoutBTN)
    void logout() {
        MyApplication.getPrefManager(this).clear();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }


    @OnClick(R.id.notification)
    void openNotification() {
        notificationCount.setText("0");
        notificationCount.setVisibility(View.GONE);
        startActivity(new Intent(MainActivity.this, NotificationActivity.class));

    }

    @OnClick(R.id.servicesCV)
    void openServices() {
        startActivity(new Intent(MainActivity.this, ServicesActivity.class));

    }

}
