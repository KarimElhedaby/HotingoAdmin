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
import donation.solutions.hamza.com.hotingoadmin.adapter.BookingAdapter;
import donation.solutions.hamza.com.hotingoadmin.model.RoomOrderModel;
import donation.solutions.hamza.com.hotingoadmin.service.ApiClient;
import donation.solutions.hamza.com.hotingoadmin.service.ApiEndpointInterface;
import donation.solutions.hamza.com.hotingoadmin.service.AuthInterceptor;
import donation.solutions.hamza.com.hotingoadmin.utils.Utilities;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class BookingActivity extends AppCompatActivity {

    @BindView(R.id.bookingRV)
    RecyclerView bookingRV;

    BookingAdapter bookingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        ButterKnife.bind(this);

        bookingRV.setLayoutManager
                (new GridLayoutManager(this, 1));


        Utilities.showLoadingDialog(this, R.color.colorAccent);

        ApiEndpointInterface apiService =
                ApiClient.getClient(new AuthInterceptor(null)).create(ApiEndpointInterface.class);

        Call<ArrayList<RoomOrderModel>> call = apiService.getRoomOrder();

        call.enqueue(new Callback<ArrayList<RoomOrderModel>>() {
            @Override
            public void onResponse(Call<ArrayList<RoomOrderModel>> call, final Response<ArrayList<RoomOrderModel>> response) {
                Utilities.dismissLoadingDialog();
                if (response.isSuccessful()) {

                    Timber.d(response.body().toString());
                    Timber.d(String.valueOf(response.body().size()));

                    bookingAdapter = new BookingAdapter(R.layout.booking_item, getApplicationContext(), response.body(), new BookingAdapter.onBookClickListner() {
                        @Override
                        public void onBookClickListner(RoomOrderModel roomOrderModel) {

                            //Do some thing with room order click here ..

                            Intent i = new Intent(getApplicationContext(),RoomDetails.class);
                            i.putExtra("ROOM_ORDER_DETAILS",roomOrderModel);
                            startActivity(i);


                        }
                    });

                    bookingRV.setAdapter(bookingAdapter);

                }
            }

            @Override
            public void onFailure(Call<ArrayList<RoomOrderModel>> call, Throwable t) {
                Timber.d(t.getMessage().toString());
                Utilities.dismissLoadingDialog();
            }
        });


    }

}
