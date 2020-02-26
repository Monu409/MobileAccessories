package com.app.mobilityapp.activities;

import android.Manifest;
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

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.app.mobilityapp.app_utils.BaseActivity;
import com.app.mobilityapp.app_utils.CircleImageView;
import com.app.mobilityapp.app_utils.ConstantMethods;
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
import java.util.Date;
import java.util.Locale;

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
    private String imgPath;
    private EditText nameEdt, emailEdt, passwordEdt, dobEdt, phoneEdt, addressEdt, gstEdt;
    private Button updateBtn;
    private File destinationFile;
    private String ADDRESS_MATCHER = "[!#$%&(){|}~:;<=>?@*+,./^_`\\'\\\" \\t\\r\\n\\f-]+";
    private String GSTINFORMAT_REGEX1 = "^([0]{1}[1-9]{1}|[1-2]{1}[0-9]{1}|[3]{1}[0-7]{1})([A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}[Z]{1}[0-9A-Z]{1})+$";
    String userType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConstantMethods.setTitleAndBack(this, "Profile");
        profileImg = findViewById(R.id.profile_img);
        camImg = findViewById(R.id.cam_img);
        camImg.setOnClickListener(v -> selectImage());
        nameEdt = findViewById(R.id.input_fname);
        emailEdt = findViewById(R.id.input_email);
        passwordEdt = findViewById(R.id.input_pass);
        //dobEdt = findViewById(R.id.input_dob);
        phoneEdt = findViewById(R.id.input_mobile);
        updateBtn = findViewById(R.id.update_btn);
        addressEdt = findViewById(R.id.input_address);
        gstEdt = findViewById(R.id.input_gstno);
        userType = ConstantMethods.getStringPreference("user_type", this);
        blackColorEditTexts();

//        dobEdt.setOnClickListener(v->{
//            ConstantMethods.setDate(dobEdt,this);
//        });
        updateBtn.setOnClickListener(v -> {
            if (updateBtn.getText().toString().equals("Edit Profile")) {
                grayColorEditTexts();
                updateBtn.setText("Save Profile");
            } else {
                postProfileData();
            }
        });
        getProfileData();
//        String getProfileImg = ConstantMethods.getStringPreference("saved_image_path", this);
//        if (getProfileImg != null) {
//            Bitmap bitmap = decodeToBase64(getProfileImg);
//            if (!getProfileImg.equals(""))
//                profileImg.setImageBitmap(bitmap);
//        }
    }

    public static Bitmap decodeToBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
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
//                jsonObject.put("dob",dobStr);
                jsonObject.put("email", emailStr);
                jsonObject.put("phone", phoneStr);
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
                    if (addressStr.equals("null")) {
                        addressStr = "No address specified";
                    }
                    String gstNo = userInfo.getString("gstno");
                    nameEdt.setText(name);
                    emailEdt.setText(email);
                    phoneEdt.setText(phone);
                    addressEdt.setText(addressStr);
                    gstEdt.setText(gstNo);
                    Glide
                            .with(UpdateProfileActivity.this)
                            .load(userPicture)
                            .centerCrop()
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
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, PICK_IMAGE_CAMERA);
                        } else if (options[item].equals("Choose From Gallery")) {
                            dialog.dismiss();
                            Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_CAMERA) {
            try {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);

                Log.e("Activity", "Pick from Camera::>>> ");

                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                String filePath = Environment.getExternalStorageDirectory() + "/" +
                        getString(R.string.app_name);
                File destination = new File(filePath, "IMG_" + timeStamp + ".jpg");
                File dir = new File(filePath);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String imgPath = destination.getAbsolutePath();

                profileImg.setImageBitmap(bitmap);
                File file = new File(imgPath);
                uploadMedia(file);

//                profileImg.setImageBitmap(bmp);
//                File file = new File(imgPath);
//                uploadMedia(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == PICK_IMAGE_GALLERY) {
            Uri selectedImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
                Log.e("Activity", "Pick from Gallery::>>> ");

                imgPath = getRealPathFromURI(selectedImage);
                destinationFile = new File(imgPath);
                profileImg.setImageBitmap(bitmap);
                File file = new File(imgPath);
                uploadMedia(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


    private void updateProfilePic(File file) {
        String userId = ConstantMethods.getStringPreference("user_id", this);
        AndroidNetworking
                .put(UPLOAD_PROFILE_PIC)
                .addFileBody(file)
                .addHeaders("Content-type", "image/jpeg")
                .addBodyParameter("login_id", userId)
                .addBodyParameter("baseurl", BASE_URL + "images/profile/")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("response", "" + response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("response", "" + anError);
                    }
                });
    }

    public static String encodeToBase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.d("Image Log:", imageEncoded);
        return imageEncoded;
    }

    private void grayColorEditTexts() {
        nameEdt.setTextColor(getResources().getColor(R.color.gray_color));
        phoneEdt.setTextColor(getResources().getColor(R.color.gray_color));
        emailEdt.setTextColor(getResources().getColor(R.color.gray_color));
        addressEdt.setTextColor(getResources().getColor(R.color.gray_color));
        gstEdt.setTextColor(getResources().getColor(R.color.gray_color));
        nameEdt.setEnabled(true);
        phoneEdt.setEnabled(true);
        emailEdt.setEnabled(true);
        addressEdt.setEnabled(true);
        gstEdt.setEnabled(true);
    }

    private void blackColorEditTexts() {
        nameEdt.setTextColor(getResources().getColor(R.color.textcolor));
        phoneEdt.setTextColor(getResources().getColor(R.color.textcolor));
        emailEdt.setTextColor(getResources().getColor(R.color.textcolor));
        addressEdt.setTextColor(getResources().getColor(R.color.textcolor));
        gstEdt.setTextColor(getResources().getColor(R.color.textcolor));
        nameEdt.setEnabled(false);
        phoneEdt.setEnabled(false);
        emailEdt.setEnabled(false);
        addressEdt.setEnabled(false);
        gstEdt.setEnabled(false);
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
            if (nameStr.isEmpty() || gstNoStr.isEmpty() || phoneStr.isEmpty() || addressStr.isEmpty()) {
                ConstantMethods.getAlertMessage(this, "Enter name,mobile,address and GST No.");
            } else if (phoneStr.length() > 13 || phoneStr.length() < 10) {
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
        AndroidNetworking
                .upload(UPLOAD_PROFILE_PICTURE)
                .addMultipartFile("image", file)
                //.addMultipartFile("baseurl", BASE_URL)
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
                        Log.e("progress", "" + response);
                        Toast.makeText(UpdateProfileActivity.this, "Profile image uploaded", Toast.LENGTH_SHORT).show();
                        try {
                            String imageUrl = response.getString("imageurl");
                            postProfilePicture(imageUrl);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        try {
//                            response.put("senderFor","5dd6714a4b01823cc68a81bc");
//                            response.put("content","");
//                            sendMessage(response);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }

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


}
