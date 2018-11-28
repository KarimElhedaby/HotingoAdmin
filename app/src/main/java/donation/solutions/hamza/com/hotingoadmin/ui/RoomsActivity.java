package donation.solutions.hamza.com.hotingoadmin.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import donation.solutions.hamza.com.hotingoadmin.R;
import donation.solutions.hamza.com.hotingoadmin.adapter.RoomAdapter;
import donation.solutions.hamza.com.hotingoadmin.model.RoomModel;
import donation.solutions.hamza.com.hotingoadmin.service.ApiClient;
import donation.solutions.hamza.com.hotingoadmin.service.ApiEndpointInterface;
import donation.solutions.hamza.com.hotingoadmin.service.AuthInterceptor;
import donation.solutions.hamza.com.hotingoadmin.utils.Utilities;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomsActivity extends AppCompatActivity {

    @BindView(R.id.roomsRV)
    RecyclerView roomsRV;

    RoomAdapter roomAdapter;
    @BindView(R.id.addRoomFAB)
    FloatingActionButton addRoomFAB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);
        ButterKnife.bind(this);


        roomsRV.setLayoutManager
                (new GridLayoutManager(this, 1));


        Utilities.showLoadingDialog(this, R.color.colorAccent);

        ApiEndpointInterface apiService =
                ApiClient.getClient(new AuthInterceptor(null)).create(ApiEndpointInterface.class);

        Call<ArrayList<RoomModel>> call = apiService.getRooms();

        call.enqueue(new Callback<ArrayList<RoomModel>>() {
            @Override
            public void onResponse(Call<ArrayList<RoomModel>> call, Response<ArrayList<RoomModel>> response) {
                Utilities.dismissLoadingDialog();
                if (response.isSuccessful()) {

                    roomAdapter = new RoomAdapter(
                            R.layout.room_item, RoomsActivity.this, response.body(), new RoomAdapter.onRoomClickListener() {
                        @Override
                        public void onRoomClick(RoomModel room) {
                            // go to room details here..

                        }

                        @Override
                        public void editRoom(RoomModel room) {
                            android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
                            UpdateRoom updateRoom = new UpdateRoom();

                            Bundle bundle = new Bundle();
                            bundle.putSerializable("id", room);
                            updateRoom.setArguments(bundle);
                            updateRoom.show(fm, "Show fragment");

                        }

                        @Override
                        public void deleteRoom(RoomModel room) {

                            Utilities.showLoadingDialog(RoomsActivity.this, R.color.colorAccent);

                            ApiEndpointInterface apiService =
                                    ApiClient.getClient(new AuthInterceptor(null)).create(ApiEndpointInterface.class);


                            Call<Void> call = apiService.deleteRoom(room.getId(), "false");

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

                    roomsRV.setAdapter(roomAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<RoomModel>> call, Throwable t) {
                Utilities.dismissLoadingDialog();
            }
        });

    }

    @OnClick(R.id.addRoomFAB)
    public void onViewClicked() {
        FragmentManager fm = getSupportFragmentManager();
        AddRoomDialog roomDialog = new AddRoomDialog();
        roomDialog.show(fm, "Show fragment");
    }
}
