package donation.solutions.hamza.com.hotingoadmin.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import donation.solutions.hamza.com.hotingoadmin.R;
import donation.solutions.hamza.com.hotingoadmin.model.User;
import donation.solutions.hamza.com.hotingoadmin.service.ApiClient;
import donation.solutions.hamza.com.hotingoadmin.service.ApiEndpointInterface;
import donation.solutions.hamza.com.hotingoadmin.service.AuthInterceptor;
import donation.solutions.hamza.com.hotingoadmin.utils.MyApplication;
import donation.solutions.hamza.com.hotingoadmin.utils.Utilities;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsActivity extends AppCompatActivity {

    @BindView(R.id.userImage)
    CircleImageView userImage;
    @BindView(R.id.editProfileBTN)
    Button editProfileBTN;
    @BindView(R.id.addAdminBTN)
    Button addAdminBTN;
    @BindView(R.id.editNameET)
    EditText editNameET;
    @BindView(R.id.editNumberET)
    EditText editNumberET;
    @BindView(R.id.editEmailET)
    EditText editEmailET;
    @BindView(R.id.editPasswordET)
    EditText editPasswordET;
    @BindView(R.id.editConfirmPasswordET)
    EditText editConfirmPasswordET;
    @BindView(R.id.confirmBTN)
    Button confirmBTN;
    @BindView(R.id.editProfileCV)
    CardView editProfileCV;
    @BindView(R.id.adminNameET)
    EditText adminNameET;
    @BindView(R.id.adminNumberET)
    EditText adminNumberET;
    @BindView(R.id.adminEmailET)
    EditText adminEmailET;
    @BindView(R.id.adminPasswordET)
    EditText adminPasswordET;
    @BindView(R.id.adminConfirmPasswordET)
    EditText adminConfirmPasswordET;
    @BindView(R.id.addBTN)
    Button addBTN;
    @BindView(R.id.addAdminCV)
    CardView addAdminCV;

    public boolean EDIT_IS_OPEN = false;
    public boolean ADD_IS_OPEN = false;
    @BindView(R.id.userNameTV)
    TextView userNameTV;
    @BindView(R.id.userEmailTV)
    TextView userEmailTV;
    @BindView(R.id.userNumberTV)
    TextView userNumberTV;

    public User editedUser = new User(" ", " ", " ", " ");
    public User newAdmin = new User();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);


        Utilities.showLoadingDialog(this, R.color.colorAccent);

        ApiEndpointInterface apiService =
                ApiClient.getClient(new AuthInterceptor(MyApplication.getPrefManager(getApplicationContext()).getUser().getToken())).create(ApiEndpointInterface.class);

        Call<User> call = apiService.getProfile();

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Utilities.dismissLoadingDialog();
                if (response.isSuccessful()) {
                    Glide.with(SettingsActivity.this).load(response.body().getImg()).into(userImage);
                    userNameTV.setText(response.body().getName().toString());
                    userEmailTV.setText(response.body().getEmail().toString());
                    userNumberTV.setText(response.body().getPhone().toString());
                    editedUser.setName(response.body().getName().toString());
                    editedUser.setEmail(response.body().getEmail().toString());
                    editedUser.setPhone(response.body().getPhone().toString());
                    editedUser.setName(response.body().getName().toString());
                    editNumberET.setText(response.body().getPhone().toString());
                    editEmailET.setText(response.body().getEmail().toString());
                    editNameET.setText(response.body().getName().toString());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Utilities.dismissLoadingDialog();
            }
        });
    }

    @OnClick({R.id.editProfileBTN, R.id.addAdminBTN, R.id.confirmBTN, R.id.addBTN})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.editProfileBTN:
                if (EDIT_IS_OPEN || ADD_IS_OPEN) {
                    addAdminCV.setVisibility(View.GONE);
                    editProfileCV.setVisibility(View.GONE);
                    EDIT_IS_OPEN = false;
                    ADD_IS_OPEN = false;
                } else if (!EDIT_IS_OPEN) {
                    addAdminCV.setVisibility(View.GONE);
                    editProfileCV.setVisibility(View.VISIBLE);
                    EDIT_IS_OPEN = true;
                    ADD_IS_OPEN = false;
                }
                break;
            case R.id.addAdminBTN:
                if (EDIT_IS_OPEN || ADD_IS_OPEN) {
                    addAdminCV.setVisibility(View.GONE);
                    editProfileCV.setVisibility(View.GONE);
                    ADD_IS_OPEN = false;
                    EDIT_IS_OPEN = false;
                } else if (!ADD_IS_OPEN) {
                    addAdminCV.setVisibility(View.VISIBLE);
                    editProfileCV.setVisibility(View.GONE);
                    ADD_IS_OPEN = true;
                    EDIT_IS_OPEN = false;
                }
                break;
            case R.id.confirmBTN:
                if (!editPasswordET.getText().toString().equals(editConfirmPasswordET.getText().toString())) {
                    Toast.makeText(SettingsActivity.this, "Password confirm not match..", Toast.LENGTH_SHORT).show();
                } else if (editNumberET.getText().toString().isEmpty()) {
                    editNumberET.setError("Add Phone");
                } else if (editEmailET.getText().toString().isEmpty()) {
                    editEmailET.setError("Enter Email");
                } else if (editNameET.getText().toString().isEmpty()) {
                    editNameET.setError("Enter Name");
                } else if (editPasswordET.getText().toString().isEmpty()) {
                    editPasswordET.setError("Enter password");
                } else if (editConfirmPasswordET.getText().toString().isEmpty()) {
                    editConfirmPasswordET.setError("Enter confirm password");
                } else {
                    editedUser.setName(editNameET.getText().toString());
                    editedUser.setEmail(editEmailET.getText().toString());
                    editedUser.setPhone(editNumberET.getText().toString());
                    editedUser.setPassword(editPasswordET.getText().toString());
                    editedUser.setType("Admin");
                    Utilities.showLoadingDialog(this, R.color.colorAccent);

                    ApiEndpointInterface apiService =
                            ApiClient.getClient(new AuthInterceptor(MyApplication.getPrefManager(getApplicationContext()).getUser().getToken())).create(ApiEndpointInterface.class);

                    Call<User> call = apiService.editAdminProfile(MyApplication.getPrefManager(getApplicationContext()).getUser().getUser().getUser_id(),editedUser);

                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            Utilities.dismissLoadingDialog();
                            if (response.isSuccessful()) {
                                Toast.makeText(SettingsActivity.this, "Profile Edited Successfuly..", Toast.LENGTH_SHORT).show();
                                editProfileCV.setVisibility(View.GONE);
                                EDIT_IS_OPEN = false;
                                editNumberET.setText("");
                                editEmailET.setText("");
                                editNameET.setText("");
                                editPasswordET.setText("");
                                editConfirmPasswordET.setText("");

                                userNameTV.setText(response.body().getName().toString());
                                userEmailTV.setText(response.body().getEmail().toString());
                                userNumberTV.setText(response.body().getPhone().toString());

                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Utilities.dismissLoadingDialog();
                            Toast.makeText(SettingsActivity.this, "Error Please try again..", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                break;
            case R.id.addBTN:
                if (!adminPasswordET.getText().toString().equals(adminConfirmPasswordET.getText().toString())) {
                    Toast.makeText(SettingsActivity.this, "Password confirm not match..", Toast.LENGTH_SHORT).show();
                } else if (adminNumberET.getText().toString().isEmpty()) {
                    adminNumberET.setError("Add Phone");
                } else if (adminEmailET.getText().toString().isEmpty()) {
                    adminEmailET.setError("Enter Email");
                } else if (adminNameET.getText().toString().isEmpty()) {
                    adminNameET.setError("Enter Name");
                } else if (adminPasswordET.getText().toString().isEmpty()) {
                    adminPasswordET.setError("Enter password");
                } else if (adminConfirmPasswordET.getText().toString().isEmpty()) {
                    adminConfirmPasswordET.setError("Enter confirm password");
                } else {
                    newAdmin.setName(adminNameET.getText().toString());
                    newAdmin.setEmail(adminEmailET.getText().toString());
                    newAdmin.setPhone(adminNumberET.getText().toString());
                    newAdmin.setPassword(adminPasswordET.getText().toString());

                    Utilities.showLoadingDialog(this, R.color.colorAccent);

                    ApiEndpointInterface apiService =
                            ApiClient.getClient(new AuthInterceptor(MyApplication.getPrefManager(getApplicationContext()).getUser().getToken())).create(ApiEndpointInterface.class);

                    Call<User> call = apiService.addNewAdmin(newAdmin);

                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            Utilities.dismissLoadingDialog();
                            if (response.isSuccessful()) {
                                Toast.makeText(SettingsActivity.this, "Admin Added Successfuly..", Toast.LENGTH_SHORT).show();
                                addAdminCV.setVisibility(View.GONE);
                                ADD_IS_OPEN = false;
                                adminNumberET.setText("");
                                adminEmailET.setText("");
                                adminNameET.setText("");
                                adminPasswordET.setText("");
                                adminConfirmPasswordET.setText("");

                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Utilities.dismissLoadingDialog();
                            Toast.makeText(SettingsActivity.this, "Enter valid email and try again..", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                break;
        }
    }
}
