package donation.solutions.hamza.com.hotingoadmin.ui;

import android.Manifest;
import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import donation.solutions.hamza.com.hotingoadmin.R;
import donation.solutions.hamza.com.hotingoadmin.adapter.RoomImagesAdapter;
import donation.solutions.hamza.com.hotingoadmin.model.AddServiceResponse;
import donation.solutions.hamza.com.hotingoadmin.service.ApiClient;
import donation.solutions.hamza.com.hotingoadmin.service.ApiEndpointInterface;
import donation.solutions.hamza.com.hotingoadmin.service.AuthInterceptor;
import donation.solutions.hamza.com.hotingoadmin.utils.Utilities;
import gun0912.tedbottompicker.TedBottomPicker;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddServiceDialog extends DialogFragment {
    //get access to Storage permsion
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    View view;
    ArrayList<File> imagesFiles = new ArrayList<>();
    @BindView(R.id.projectIV)
    ImageView projectIV;
    @BindView(R.id.projectDescET)
    EditText projectDescET;
    @BindView(R.id.projectTitleET)
    EditText projectTitleET;
    @BindView(R.id.doneBTN)
    Button doneBTN;
    @BindView(R.id.donateprojectRV)
    RecyclerView donateProjectRV;
    String requestTitle, requestDesc;
    private RoomImagesAdapter roomImagesAdapter;
    private ArrayList<MultipartBody.Part> imagessParts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.add_service_dialog, container, false);
        ButterKnife.bind(this, view);

        imagessParts = new ArrayList<MultipartBody.Part>();
        projectIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CheckUserPermsions();
            }
        });

        doneBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Utilities.showLoadingDialog(getContext(), R.color.colorAccent);
                requestTitle = projectTitleET.getText().toString();
                requestDesc = projectDescET.getText().toString();


                if (projectTitleET.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Please enter Room Number", Toast.LENGTH_SHORT).show();
                } else if (projectDescET.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Please enter Room descreption", Toast.LENGTH_SHORT).show();
                } else if (imagesFiles.size() == 0) {
                    Toast.makeText(getContext(), "Please select Room images", Toast.LENGTH_SHORT).show();
                } else {
                    sendReq();
                }
            }
        });

        return view;
    }

    void CheckUserPermsions() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
                return;
            }
        }
        OpenImagePicker();
    }

    public void OpenImagePicker() {

        TedBottomPicker bottomSheetDialogFragment = new TedBottomPicker.Builder(getContext())
                .setOnImageSelectedListener(new TedBottomPicker.OnImageSelectedListener() {
                    @Override
                    public void onImageSelected(Uri uri) {
                        ArrayList<Uri> uriList = new ArrayList<>();
                        uriList.add(uri);

                        projectIV.setVisibility(View.GONE);
                        donateProjectRV.setVisibility(View.VISIBLE);
                        donateProjectRV.setLayoutManager
                                (new LinearLayoutManager(getContext()
                                        , LinearLayoutManager.HORIZONTAL, false));
                        roomImagesAdapter = new RoomImagesAdapter(
                                R.layout.room_image_row, getContext(), uriList);
                        donateProjectRV.setAdapter(roomImagesAdapter);


                        for (Uri urii : uriList) {
                            String imagePath = getPath(getContext(), urii);
                            File imageFile = new File(imagePath);
                            imagesFiles.add(imageFile);
                        }
                        convertImageFileToMultiPart(imagesFiles, imagessParts);
                    }


                    // here is selected uri list


                })
                .setPeekHeight(1600)
                .showTitle(false)
                .setCompleteButtonText("Done")
                .setEmptySelectionText("No Select")
                .create();

        bottomSheetDialogFragment.show(getActivity().getSupportFragmentManager());

    }

    private void convertImageFileToMultiPart(List<File> imageFiles, List<MultipartBody.Part> imagesParts) {

        for (File file : imageFiles) {
            //Toast.makeText(getContext(), "file : " + file.getName().toString(), Toast.LENGTH_SHORT).show();
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);

            MultipartBody.Part filePart = MultipartBody.Part.createFormData("img", file.getName(), requestBody);
            imagesParts.add(filePart);

        }


    }

    public void sendReq() {
        ApiEndpointInterface apiService =
                ApiClient.getClient(new AuthInterceptor(null)).create(ApiEndpointInterface.class);


        Call<AddServiceResponse> call = apiService.addService
                (
                        RequestBody.create(MediaType.parse("text/plain"), requestTitle),
                        RequestBody.create(MediaType.parse("text/plain"), requestDesc),
                        imagessParts
                );

        call.enqueue(new Callback<AddServiceResponse>() {

            @Override
            public void onResponse(Call<AddServiceResponse> call, Response<AddServiceResponse> response) {
                Utilities.dismissLoadingDialog();
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Successfully Added Service", Toast.LENGTH_SHORT).show();
                    dismiss();
                }

            }

            @Override
            public void onFailure(Call<AddServiceResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Some thing  Wronge try again..", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    OpenImagePicker();// init the contact list
                } else {
                    // Permission Denied
                    Toast.makeText(getContext(), "your message", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public static String getPath(final Context context, final Uri uri) {

        // check here to KITKAT or new version
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {

            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/"
                            + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection,
                        selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri,
                                       String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri
                .getAuthority());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
