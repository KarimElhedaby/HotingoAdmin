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
import donation.solutions.hamza.com.hotingoadmin.model.ServicesResponce;
import donation.solutions.hamza.com.hotingoadmin.service.ApiClient;
import donation.solutions.hamza.com.hotingoadmin.service.ApiEndpointInterface;
import donation.solutions.hamza.com.hotingoadmin.service.AuthInterceptor;
import donation.solutions.hamza.com.hotingoadmin.utils.Utilities;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("ValidFragment")
public class UpdateService extends DialogFragment {

    @BindView(R.id.updateServiceTitleTV)
    EditText updateServiceTitleTV;
    @BindView(R.id.updateServiceDescTV)
    EditText updateServiceDescTV;
    @BindView(R.id.updateServiceDoneBTN)
    Button updateServiceDoneBTN;
    String updateDesc, updateTitle;
    String id;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.update_service_dialog, container, false);
        ButterKnife.bind(this, view);

        id = getArguments().getString("id");
//        Timber.d(id);

        updateServiceDoneBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utilities.showLoadingDialog(getContext(), R.color.colorAccent);
                updateDesc = updateServiceDescTV.getText().toString();
                updateTitle = updateServiceTitleTV.getText().toString();


                sendReq();
            }

        });
        return view;
    }


    public void sendReq() {
        ApiEndpointInterface apiService =
                ApiClient.getClient(new AuthInterceptor(null)).create(ApiEndpointInterface.class);


        Call<ServicesResponce> call = apiService.updateService(id, updateDesc, updateTitle);

        call.enqueue(new Callback<ServicesResponce>() {

            @Override
            public void onResponse(Call<ServicesResponce> call, Response<ServicesResponce> response) {

                Utilities.dismissLoadingDialog();
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Successfully Updated Service", Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            }

            @Override
            public void onFailure(Call<ServicesResponce> call, Throwable t) {
                Toast.makeText(getContext(), "Some thing  Wronge try again..", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
    }


}