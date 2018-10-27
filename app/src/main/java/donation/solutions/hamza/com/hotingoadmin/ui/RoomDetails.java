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
import donation.solutions.hamza.com.hotingoadmin.model.RoomOrderModel;
import donation.solutions.hamza.com.hotingoadmin.service.ApiClient;
import donation.solutions.hamza.com.hotingoadmin.service.ApiEndpointInterface;
import donation.solutions.hamza.com.hotingoadmin.service.AuthInterceptor;
import donation.solutions.hamza.com.hotingoadmin.utils.Utilities;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomDetails extends AppCompatActivity {

    RoomOrderModel roomOrderD;

    @BindView(R.id.roomDetailsNumTV)
    TextView roomDetailsNumTV;

    @BindView(R.id.bookingCustomerRoomTV)
    TextView bookingCustomerRoomTV;

    @BindView(R.id.roomDetailsCustomerNameTV)
    TextView roomDetailsCustomerNameTV;

    @BindView(R.id.bookingCustomerPhoneTV)
    TextView bookingCustomerPhoneTV;

    @BindView(R.id.bookingCustomerDuarationTV)
    TextView bookingCustomerDuarationTV;

    @BindView(R.id.roomOrderAcceptBTN)
    Button roomOrderAcceptBTN;

    @BindView(R.id.roomOrderRefuseBTN)
    Button roomOrderRefuseBTN;
    @BindView(R.id.statusTV)
    TextView statusTV;
    @BindView(R.id.roomNotesTV)
    TextView roomNotesTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_details);
        ButterKnife.bind(this);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            roomOrderD = (RoomOrderModel) bundle.getSerializable("ROOM_ORDER_DETAILS");
            roomDetailsNumTV.setText(roomOrderD.getRoom().getNumber().toString());
            bookingCustomerRoomTV.setText(roomOrderD.getRoom().getPrice() + "");
            roomDetailsCustomerNameTV.setText(roomOrderD.getUserRoomorder().getName().toString());
            bookingCustomerPhoneTV.setText(roomOrderD.getUserRoomorder().getPhone().toString());
            bookingCustomerDuarationTV.setText(roomOrderD.getDuration().toString());
            if (roomOrderD.getNotes() != null) {
                roomNotesTV.setText(roomOrderD.getNotes().toString());
            }
        }


        if (roomOrderD.getStatus().equals("Rejected")) {
            roomOrderRefuseBTN.setVisibility(View.GONE);
            roomOrderAcceptBTN.setVisibility(View.VISIBLE);
            statusTV.setText("Rejected");

        } else if (roomOrderD.getStatus().equals("Accepted")) {
            roomOrderRefuseBTN.setVisibility(View.VISIBLE);
            roomOrderAcceptBTN.setVisibility(View.GONE);
            statusTV.setText("Accepted");

        } else if (roomOrderD.getStatus().equals("Pindding")) {
            roomOrderRefuseBTN.setVisibility(View.VISIBLE);
            roomOrderAcceptBTN.setVisibility(View.VISIBLE);
            statusTV.setText("Pindding");
        }

    }

    @OnClick({R.id.roomOrderAcceptBTN, R.id.roomOrderRefuseBTN})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.roomOrderAcceptBTN) {

            Utilities.showLoadingDialog(this, R.color.colorAccent);

            ApiEndpointInterface apiService =
                    ApiClient.getClient(new AuthInterceptor(null)).create(ApiEndpointInterface.class);

            Call<RoomOrderModel> call = apiService.acceptOrderRoom(roomOrderD.getId().toString());

            call.enqueue(new Callback<RoomOrderModel>() {
                @Override
                public void onResponse(Call<RoomOrderModel> call, Response<RoomOrderModel> response) {
                    Utilities.dismissLoadingDialog();
                    if (response.isSuccessful()) {
                        roomOrderRefuseBTN.setVisibility(View.VISIBLE);
                        roomOrderAcceptBTN.setVisibility(View.GONE);
                        statusTV.setText("Accepted");
                        Toast.makeText(RoomDetails.this, "Room Order Accepted Successfully", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<RoomOrderModel> call, Throwable t) {
                    Toast.makeText(RoomDetails.this, "Some thing went wronge", Toast.LENGTH_SHORT).show();
                    Utilities.dismissLoadingDialog();
                }
            });

        } else if (view.getId() == R.id.roomOrderRefuseBTN) {

            Utilities.showLoadingDialog(this, R.color.colorAccent);

            ApiEndpointInterface apiService2 =
                    ApiClient.getClient(new AuthInterceptor(null)).create(ApiEndpointInterface.class);

            Call<RoomOrderModel> call2 = apiService2.cancleOrderRoom(roomOrderD.getId().toString());

            call2.enqueue(new Callback<RoomOrderModel>() {
                @Override
                public void onResponse(Call<RoomOrderModel> call, Response<RoomOrderModel> response) {
                    Utilities.dismissLoadingDialog();
                    if (response.isSuccessful()) {
                        roomOrderRefuseBTN.setVisibility(View.GONE);
                        roomOrderAcceptBTN.setVisibility(View.VISIBLE);
                        statusTV.setText("Rejected");
                        Toast.makeText(RoomDetails.this, "Room Order Canceled Successfully", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<RoomOrderModel> call, Throwable t) {
                    Toast.makeText(RoomDetails.this, "Some thing went wronge", Toast.LENGTH_SHORT).show();
                    Utilities.dismissLoadingDialog();
                }
            });

        }

    }
}

