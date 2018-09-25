package donation.solutions.hamza.com.hotingoadmin.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import donation.solutions.hamza.com.hotingoadmin.R;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.bookingCV)
    CardView booking;

    @BindView(R.id.requestsCV)
    CardView request;

    @BindView(R.id.roomsCV)
    CardView rooms ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.requestsCV)
    void openRequest() {
        startActivity(new Intent(MainActivity.this, RequestActivity.class));

    }


    @OnClick(R.id.roomsCV)
    void openRooms(){
        startActivity(new Intent(MainActivity.this, RoomsActivity.class));

    }

    @OnClick(R.id.bookingCV)
    void openbooking() {
        startActivity(new Intent(MainActivity.this, BookingActivity.class));

    }

}
