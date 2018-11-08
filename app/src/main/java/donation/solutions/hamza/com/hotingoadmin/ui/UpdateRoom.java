package donation.solutions.hamza.com.hotingoadmin.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import donation.solutions.hamza.com.hotingoadmin.R;
import donation.solutions.hamza.com.hotingoadmin.model.RoomModel;
import donation.solutions.hamza.com.hotingoadmin.model.ServicesResponce;
import donation.solutions.hamza.com.hotingoadmin.service.ApiClient;
import donation.solutions.hamza.com.hotingoadmin.service.ApiEndpointInterface;
import donation.solutions.hamza.com.hotingoadmin.service.AuthInterceptor;
import donation.solutions.hamza.com.hotingoadmin.utils.Utilities;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

@SuppressLint("ValidFragment")
public class UpdateRoom extends DialogFragment {


    @BindView(R.id.updateRoomPriceTV)
    EditText updateRoomPriceTV;
    @BindView(R.id.updateRoomDescTV)
    EditText updateRoomDescTV;
    @BindView(R.id.updateRoomNumberTV)
    EditText updateRoomNumberTV;
    @BindView(R.id.updateRoomDoneBTN)
    Button updateRoomDoneBTN;
    String updateDesc, updatePrice, updateNumber;
    String id;
    RoomModel roomModel ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.update_room_dialog, container, false);
        ButterKnife.bind(this, view);

//        id = getArguments().getString("id");
        roomModel = (RoomModel) getArguments().getSerializable("id");

        updateRoomDescTV.setText(roomModel.getDesc());
        updateRoomNumberTV.setText(roomModel.getNumber());
        updateRoomPriceTV.setText(String.valueOf(roomModel.getPrice()));

        updateRoomDoneBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utilities.showLoadingDialog(getContext(), R.color.colorAccent);
                updateDesc = updateRoomDescTV.getText().toString();
                updateNumber = updateRoomNumberTV.getText().toString();
                updatePrice = updateRoomPriceTV.getText().toString();

                sendReq();
            }

        });
        return view;
    }


    public void sendReq() {
        ApiEndpointInterface apiService =
                ApiClient.getClient(new AuthInterceptor(null)).create(ApiEndpointInterface.class);


        Call<RoomModel> call = apiService.updateRoom(roomModel.getId(),updateNumber ,Integer.valueOf(updatePrice), updateDesc);



        call.enqueue(new Callback<RoomModel>() {
            @Override
            public void onResponse(Call<RoomModel> call, Response<RoomModel> response) {

                Utilities.dismissLoadingDialog();
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Successfully Updated Room", Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            }

            @Override
            public void onFailure(Call<RoomModel> call, Throwable t) {
                Toast.makeText(getContext(), "Some thing  Wronge try again..", Toast.LENGTH_SHORT).show();
                Timber.d(t.getMessage());
                Utilities.dismissLoadingDialog();
            }
        });
    }
}