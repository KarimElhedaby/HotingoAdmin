package donation.solutions.hamza.com.hotingoadmin.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import donation.solutions.hamza.com.hotingoadmin.R;
import donation.solutions.hamza.com.hotingoadmin.adapter.NotificationAdapter;
import donation.solutions.hamza.com.hotingoadmin.model.NotificationModel;
import donation.solutions.hamza.com.hotingoadmin.service.ApiClient;
import donation.solutions.hamza.com.hotingoadmin.service.ApiEndpointInterface;
import donation.solutions.hamza.com.hotingoadmin.service.AuthInterceptor;
import donation.solutions.hamza.com.hotingoadmin.utils.MyApplication;
import donation.solutions.hamza.com.hotingoadmin.utils.Utilities;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity {

    @BindView(R.id.notificationRV)
    RecyclerView notificationRV;

    NotificationAdapter notificationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        ButterKnife.bind(this);


        notificationRV.setLayoutManager
                (new GridLayoutManager(this, 1));


        Utilities.showLoadingDialog(this, R.color.colorAccent);

        ApiEndpointInterface apiService =
                ApiClient.getClient(new AuthInterceptor(MyApplication.getPrefManager(getApplicationContext()).getUser().getToken())).create(ApiEndpointInterface.class);

        Call<ArrayList<NotificationModel>> call = apiService.getNotification();

        call.enqueue(new Callback<ArrayList<NotificationModel>>() {
            @Override
            public void onResponse(Call<ArrayList<NotificationModel>> call, Response<ArrayList<NotificationModel>> response) {
                Utilities.dismissLoadingDialog();
                if (response.isSuccessful()) {

                    notificationAdapter = new NotificationAdapter(
                            R.layout.notification_list_row, NotificationActivity.this, response.body(), new NotificationAdapter.onNotificationClickListener() {
                        @Override
                        public void onNotificationClick(NotificationModel notification) {
                            // go to room details here..

                        }
                    });

                    notificationRV.setAdapter(notificationAdapter);

                }
            }

            @Override
            public void onFailure(Call<ArrayList<NotificationModel>> call, Throwable t) {
                Utilities.dismissLoadingDialog();
            }
        });
    }
}
