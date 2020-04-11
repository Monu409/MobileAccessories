package com.app.mobilityapp.activities;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.app.mobilityapp.app_utils.BaseActivity;
import com.app.mobilityapp.app_utils.CircleImageView;
import com.app.mobilityapp.app_utils.ConstantMethods;
import com.app.mobilityapp.app_utils.FileUtils;
import com.app.mobilityapp.camera.CameraActivity;
import com.app.mobilityapp.connection.CommonNetwork;
import com.app.mobilityapp.connection.JSONResult;
import com.app.mobilityapp.R;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.app.mobilityapp.app_utils.AppApis.BASE_URL;
import static com.app.mobilityapp.app_utils.AppApis.GET_PROFILE;
import static com.app.mobilityapp.app_utils.AppApis.PROFILE_UPDATE;
import static com.app.mobilityapp.app_utils.AppApis.UPLOAD_PROFILE_PIC;
import static com.app.mobilityapp.app_utils.AppApis.UPLOAD_PROFILE_PICTURE;

public class UpdateProfileActivity extends BaseActivity {
    public static final int PICK_IMAGE_CAMERA = 100;
    public static final int PICK_IMAGE_GALLERY = 200;
    private CircleImageView profileImg;
    private ImageView camImg;
    private String imgPath="";
    private EditText nameEdt, emailEdt, passwordEdt, dobEdt, phoneEdt, addressEdt, gstEdt,cityEdt;
    private Button updateBtn;
    private File destinationFile;
    private String ADDRESS_MATCHER = "[!#$%&(){|}~:;<=>?@*+,./^_`\\'\\\" \\t\\r\\n\\f-]+";
    private String GSTINFORMAT_REGEX1 = "^([0]{1}[1-9]{1}|[1-2]{1}[0-9]{1}|[3]{1}[0-7]{1})([A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}[Z]{1}[0-9A-Z]{1})+$";
    String userType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConstantMethods.setTitleAndBack(this, "Profile");
        if (checkAndRequestPermissions()) {
            profileImg = findViewById(R.id.profile_img);
            camImg = findViewById(R.id.cam_img);
            camImg.setOnClickListener(v -> selectImage());
            nameEdt = findViewById(R.id.input_fname);
            emailEdt = findViewById(R.id.input_email);
            passwordEdt = findViewById(R.id.input_pass);
            phoneEdt = findViewById(R.id.input_mobile);
            updateBtn = findViewById(R.id.update_btn);
            addressEdt = findViewById(R.id.input_address);
            gstEdt = findViewById(R.id.input_gstno);
            cityEdt = findViewById(R.id.input_city);
            userType = ConstantMethods.getStringPreference("user_type", this);
            blackColorEditTexts();
            updateBtn.setOnClickListener(v -> {
                if (updateBtn.getText().toString().equals("Edit Profile")) {
                    grayColorEditTexts();
                    updateBtn.setText("Save Profile");
                } else {
                    postProfileData();
                }
            });
            getProfileData();
        }
    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_update_profile;
    }

    private void postProfileData() {
        String nameStr = nameEdt.getText().toString().trim();
        String emailStr = emailEdt.getText().toString();
        String phoneStr = phoneEdt.getText().toString();
        String passStr = passwordEdt.getText().toString();
        String addressStr = addressEdt.getText().toString();
        String gstNoStr = gstEdt.getText().toString();
        if (formValidation(nameStr, phoneStr, addressStr, gstNoStr)) {
            ConstantMethods.showProgressbar(this);
            String id = ConstantMethods.getStringPreference("user_id", this);
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("displayName", nameStr);
                jsonObject.put("email", emailStr);
                jsonObject.put("phone", phoneStr);
                jsonObject.put("address", addressStr);
                jsonObject.put("cityName", cityEdt.getText().toString());
                jsonObject.put("gstno", gstEdt.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            CommonNetwork.putNetworkJsonObj(PROFILE_UPDATE + "/" + id, jsonObject, new JSONResult() {
                @Override
                public void notifySuccess(@NonNull JSONObject response) {
                    ConstantMethods.dismissProgressBar();
                    try {
                        String confirmation = response.getString("confirmation");
                        if (confirmation.equals("success")) {
                            blackColorEditTexts();
                            Toast.makeText(UpdateProfileActivity.this, "Update successfully", Toast.LENGTH_SHORT).show();
                            updateBtn.setText("Edit Profile");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void notifyError(@NonNull ANError anError) {
                    ConstantMethods.dismissProgressBar();
                    Log.e("response", "" + anError);
                }
            }, this);
        }
    }

    private void getProfileData() {
        ConstantMethods.showProgressbar(this);
        String userId = ConstantMethods.getStringPreference("user_id", this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("_id", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        CommonNetwork.postNetworkJsonObj(GET_PROFILE, jsonObject, new JSONResult() {
            @Override
            public void notifySuccess(@NonNull JSONObject response) {
                ConstantMethods.dismissProgressBar();
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    JSONObject userInfo = jsonArray.getJSONObject(0);
                    String name = userInfo.getString("displayName");
                    String email = userInfo.getString("email");
                    String phone = userInfo.getString("phone");

                    String userPicture = userInfo.getString("userPicture");
                    JSONObject addressObj = userInfo.getJSONObject("addressId");
                    String addressStr = addressObj.getString("address");
                    String cityStr = userInfo.getString("cityName");
                    if (addressStr.equals("null")) {
                        addressStr = "No address specified";
                    }

                    String gstNo = userInfo.getString("gstno");
                    nameEdt.setText(name);
                    emailEdt.setText(email);
                    phoneEdt.setText(phone);
                    addressEdt.setText(addressStr);
                    gstEdt.setText(gstNo);
                    cityEdt.setText(cityStr);
                    Glide
                            .with(UpdateProfileActivity.this)
                            .load(userPicture)
//                            .error(R.drawable.profile_icon)
//                            .centerCrop()
                            .into(profileImg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void notifyError(@NonNull ANError anError) {
                ConstantMethods.dismissProgressBar();
            }
        }, this);
    }

    private void selectImage() {
        try {
            PackageManager pm = getPackageManager();
            int hasPerm = pm.checkPermission(Manifest.permission.CAMERA, getPackageName());
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                final CharSequence[] options = {"Take Photo", "Choose From Gallery", "Cancel"};
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Select Option");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Take Photo")) {
                            dialog.dismiss();
                            cameraImage();

                        } else if (options[item].equals("Choose From Gallery")) {
                            dialog.dismiss();
                            showChooser();
                        } else if (options[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            } else
                Toast.makeText(this, "Camera Permission error", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Camera Permission error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

//    private Uri imageToUploadUri;

    String imagePath = "";
    String directory;

    private void cameraImage() {
        directory = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
        String imgcurTime = "" + System.currentTimeMillis();
        File imageDirectory = new File(directory);
        imageDirectory.mkdirs();
        imagePath = directory + imgcurTime + ".jpg";
        startCamera(PICK_IMAGE_CAMERA, imgcurTime + ".jpg", true, false);
    }

    public void startCamera(int request_code, String name, boolean isTime, boolean isScan) {
        Intent camera = new Intent(this, CameraActivity.class);
        camera.putExtra("name", name);
        camera.putExtra("path", directory);
        camera.putExtra("istime", isTime);
        camera.putExtra("isScan", isScan);
        startActivityForResult(camera, request_code);
    }


    private void showChooser() {
        // Use the GET_CONTENT intent from the utility class
        Intent target = createGetContentIntent();
        // Create the chooser Intent
        Intent intent = Intent.createChooser(target, getString(R.string.app_name));
        try {
            startActivityForResult(intent, PICK_IMAGE_GALLERY);
        } catch (ActivityNotFoundException e) {
            // The reason for the existence of aFileChooser
        }
    }

    public static Intent createGetContentIntent() {
        // Implicitly allow the user to select a particular kind of data
        final Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        // The MIME data page_type filter
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        // Only return URIs that can be opened with ContentResolver
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        return intent;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICK_IMAGE_GALLERY:
                // If the file selection was successful
                if (resultCode == RESULT_OK) {
                    if (data.getClipData() != null) {
                        int count = data.getClipData().getItemCount();
                        int currentItem = 0;
                        while (currentItem < count) {
                            Uri imageUri = data.getClipData().getItemAt(currentItem).getUri();
                            //do something with the image (save it to some directory or whatever you need to do with it here)
                            currentItem = currentItem + 1;
                            Log.d("Uri Selected", imageUri.toString());
                            try {
                                // Get the file path from the URI
                                String path = FileUtils.getPath(this, imageUri);
                                Log.d("Multiple File Selected", path);
                            } catch (Exception e) {

                            }
                        }
                    } else if (data.getData() != null) {
                        //do something with the image (save it to some directory or whatever you need to do with it here)
                        final Uri uri = data.getData();
                        try {
                            // Get the file path from the URI
                            final String path = FileUtils.getPath(this, uri);
                            Log.d("Single File Selected", path);
                            Bitmap bitmap = BitmapFactory.decodeFile(path);
//                            Bitmap bmp = BitmapFactory.decodeFile(filePath);
                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, bos);
                            profileImg.setImageBitmap(bitmap);
                            File file = new File(path);
                            uploadMedia(file);
                        } catch (Exception e) {
                            Log.e("", "File select error", e);
                        }
                    }
//                    getAllImagePaths("gallery");

                }
                break;

            case PICK_IMAGE_CAMERA:
                if (resultCode == RESULT_OK) {
                    Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
////                            Bitmap bmp = BitmapFactory.decodeFile(filePath);
//                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                    bitmap.compress(Bitmap.CompressFormat.JPEG, 70, bos);
                    profileImg.setImageBitmap(bitmap);
                    File file = new File(imagePath);
                    uploadMedia(file);
                }
        }

    }


    private void grayColorEditTexts() {
        nameEdt.setTextColor(getResources().getColor(R.color.textcolor));
        phoneEdt.setTextColor(getResources().getColor(R.color.textcolor));
        emailEdt.setTextColor(getResources().getColor(R.color.textcolor));
        addressEdt.setTextColor(getResources().getColor(R.color.textcolor));
        gstEdt.setTextColor(getResources().getColor(R.color.textcolor));
        cityEdt.setTextColor(getResources().getColor(R.color.textcolor));
        nameEdt.setEnabled(true);
        phoneEdt.setEnabled(true);
        emailEdt.setEnabled(true);
        addressEdt.setEnabled(true);
        gstEdt.setEnabled(true);
        cityEdt.setEnabled(true);
    }

    private void blackColorEditTexts() {
        nameEdt.setTextColor(getResources().getColor(R.color.gray_color));
        phoneEdt.setTextColor(getResources().getColor(R.color.gray_color));
        emailEdt.setTextColor(getResources().getColor(R.color.gray_color));
        addressEdt.setTextColor(getResources().getColor(R.color.gray_color));
        gstEdt.setTextColor(getResources().getColor(R.color.gray_color));
        cityEdt.setTextColor(getResources().getColor(R.color.gray_color));
        nameEdt.setEnabled(false);
        phoneEdt.setEnabled(false);
        emailEdt.setEnabled(false);
        addressEdt.setEnabled(false);
        gstEdt.setEnabled(false);
        cityEdt.setEnabled(false);
    }

    private boolean formValidation(String nameStr, String phoneStr, String addressStr, String gstNoStr) {
        boolean valid = false;
        if (userType.equals("3")) {
            if (nameStr.isEmpty() || phoneStr.isEmpty()) {
                ConstantMethods.getAlertMessage(this, "Enter name and mobile");
                valid = false;
            } else if (phoneStr.length() > 13 || phoneStr.length() < 10) {
                Toast.makeText(this, "Enter valid mobile", Toast.LENGTH_SHORT).show();
                valid = false;
            } else {
                valid = true;
            }
        } else if (userType.equals("4")) {
            if (nameStr.isEmpty()) {
                Toast.makeText(this, "Enter your", Toast.LENGTH_SHORT).show();
            }
            else if(gstNoStr.isEmpty()){
                Toast.makeText(this, "Enter gst number", Toast.LENGTH_SHORT).show();
            }

            else if(phoneStr.isEmpty()){
                Toast.makeText(this, "Enter phone number", Toast.LENGTH_SHORT).show();
            }

            else if(addressStr.isEmpty()){
                Toast.makeText(this, "Enter address", Toast.LENGTH_SHORT).show();
            }
            else if (phoneStr.length() > 13 || phoneStr.length() < 10) {
                Toast.makeText(this, "Enter valid mobile", Toast.LENGTH_SHORT).show();
                valid = false;
            } else if (!gstNoStr.matches(GSTINFORMAT_REGEX1)) {
                ConstantMethods.getAlertMessage(this, "Enter valid GST No.");
                valid = false;
            } else {
                valid = true;
            }
        }
        return valid;
    }

    private void uploadMedia(File file) {
        ConstantMethods.showProgressbar(this);
        AndroidNetworking
                .upload(UPLOAD_PROFILE_PICTURE)
                .addMultipartFile("image", file)
                .addMultipartParameter("baseurl", BASE_URL)
                .addMultipartParameter("fileSize", String.valueOf(file.getTotalSpace()))
                .setTag("uploadTest")
                .setPriority(Priority.HIGH)
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {
                        Log.e("progress", "" + bytesUploaded);
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        ConstantMethods.dismissProgressBar();
                        Log.e("progress", "" + response);
                        Toast.makeText(UpdateProfileActivity.this, "Profile image uploaded", Toast.LENGTH_SHORT).show();
                        try {
                            String imageUrl = response.getString("imageurl");
//                            Glide
//                                    .with(UpdateProfileActivity.this)
//                                    .load(imageUrl)
//                                    .error(R.drawable.profile_icon)
//                                    .centerCrop()
//                                    .into(profileImg);
                            postProfilePicture(imageUrl);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        Log.e("progress", "" + error);
                    }
                });
    }


    private void postProfilePicture(String imageUrl) {
        String id = ConstantMethods.getStringPreference("user_id", this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userPicture", imageUrl);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        CommonNetwork.putNetworkJsonObj(PROFILE_UPDATE + "/" + id, jsonObject, new JSONResult() {
            @Override
            public void notifySuccess(@NonNull JSONObject response) {
                ConstantMethods.dismissProgressBar();
                try {
                    String confirmation = response.getString("confirmation");
                    if (confirmation.equals("success")) {
                        Toast.makeText(UpdateProfileActivity.this, "Update successfully", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void notifyError(@NonNull ANError anError) {
                ConstantMethods.dismissProgressBar();
                Log.e("response", "" + anError);
            }
        }, this);
    }

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    private boolean checkAndRequestPermissions() {
        int camera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int wtite = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int read = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
//        int call = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (wtite != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (read != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
//        if (call != PackageManager.PERMISSION_GRANTED) {
//            listPermissionsNeeded.add(Manifest.permission.CALL_PHONE);
//        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return true;
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        Log.d("in fragment on request", "Permission callback called-------");
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {

                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Log.d("in fragment on request", "CAMERA & WRITE_EXTERNAL_STORAGE READ_EXTERNAL_STORAGE permission granted");
                        // process the normal flow
                        //else any one or both the permissions are not granted
                    } else {
                        Log.d("in fragment on request", "Some permissions are not granted ask again ");
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
//                        // shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            showDialogOK("Camera and Storage Permission required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkAndRequestPermissions();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    // proceed with logic by disabling the related features or quit the app.
                                                    break;
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(this, "Go to settings and enable permissions", Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                }
            }
        }

    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }


}
