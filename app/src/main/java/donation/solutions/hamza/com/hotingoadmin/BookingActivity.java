package donation.solutions.hamza.com.hotingoadmin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import donation.solutions.hamza.com.hotingoadmin.adapter.BookingAdapter;

public class BookingActivity extends AppCompatActivity implements   BookingAdapter.onBookClickListner{

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

        bookingAdapter = new BookingAdapter(R.layout.booking_item, this, this);

        bookingRV.setAdapter(bookingAdapter);

    }

    @Override
    public void onBookClickListner() {
        startActivity(new Intent(BookingActivity.this, RoomDetails.class));

    }
}
