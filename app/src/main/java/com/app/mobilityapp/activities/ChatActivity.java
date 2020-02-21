package com.app.mobilityapp.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.app.mobilityapp.R;
import com.app.mobilityapp.adapter.ChatAdapter;
import com.app.mobilityapp.adapter.MultiViewTypeAdapter;
import com.app.mobilityapp.app_utils.AppApis;
import com.app.mobilityapp.app_utils.BaseActivity;
import com.app.mobilityapp.app_utils.ConstantMethods;
import com.app.mobilityapp.connection.CommonNetwork;
import com.app.mobilityapp.connection.JSONResult;
import com.app.mobilityapp.modals.ChatModel;
import com.app.mobilityapp.modals.ConversationChildModel;
import com.app.mobilityapp.modals.ConversationModel;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import static com.app.mobilityapp.app_utils.AppApis.BASE_URL;
import static com.app.mobilityapp.app_utils.AppApis.GET_CONVERSATION_LIST;
import static com.app.mobilityapp.app_utils.AppApis.SEND_MEDIA_TO_CHAT;
import static com.app.mobilityapp.app_utils.AppApis.SEND_MESSAGE;

public class ChatActivity extends BaseActivity {
    private EditText sendMsgEdt;
    private RecyclerView chatList;
    private ImageView sendImg,attachImg;
    private List<ChatModel> chatModels = new ArrayList<>();
    private MultiViewTypeAdapter chatAdapter;
    private static final int PICK_IMAGE_CAMERA = 1;
    private static final int PICK_IMAGE_GALLERY = 2;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConstantMethods.setTitleAndBack(this,"Chat");
        sendMsgEdt = findViewById(R.id.send_msg_edt);
        sendImg = findViewById(R.id.send_img);
        attachImg = findViewById(R.id.attach_img);
        chatList = findViewById(R.id.chat_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        chatList.setLayoutManager(linearLayoutManager);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("senderFor","5dd6714a4b01823cc68a81bc");
            jsonObject.put("content","test");
            jsonObject.put("imagename","");
            jsonObject.put("imageurl","");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        sendMessage(jsonObject);

        sendImg.setOnClickListener(v->{
            String message = sendMsgEdt.getText().toString();
            if(message.isEmpty()){
                Toast.makeText(this, "Type a message first", Toast.LENGTH_SHORT).show();
            }
            else {
                ChatModel chatModel = new ChatModel();
                chatModel.setSentMsg(message);
                chatModels.add(chatModel);
                sendMsgEdt.setText("");
//                chatList.smoothScrollToPosition(chatModels.size()-1);
//                chatList.smoothScrollToPosition(chatList.getAdapter().getItemCount() - 1);
//                chatAdapter.notifyDataSetChanged();
                JSONObject jsonObject1 = new JSONObject();
                try {
                    jsonObject1.put("senderFor","5dd6714a4b01823cc68a81bc");
                    jsonObject1.put("content",message);
                    jsonObject.put("imagename","");
                    jsonObject.put("imageurl","");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                sendMessage(jsonObject1);
            }
        });
        attachImg.setOnClickListener(v->selectImage());

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_chat;
    }

    private void sendMessage(JSONObject msgObject){
        CommonNetwork.postNetworkJsonObj(SEND_MESSAGE, msgObject, new JSONResult() {
            @Override
            public void notifySuccess(@NonNull JSONObject response) {
                Log.e("response",""+response);
                try {
                    JSONObject childObj = response.getJSONObject("data");
                    String conversationId = childObj.getString("conversationId");
                    String senderBy = childObj.getString("senderBy");
                    getConversationID(conversationId,senderBy);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void notifyError(@NonNull ANError anError) {
                Log.e("response",""+anError);
            }
        },this);
    }

    private void getConversationID(String convrstnId, String senderBy){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("conversationId",convrstnId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        CommonNetwork.postNetworkJsonObj(GET_CONVERSATION_LIST, jsonObject, new JSONResult() {
            @Override
            public void notifySuccess(@NonNull JSONObject response) {
                Log.e("response",""+response);
                Gson gson = new Gson();
                ConversationModel conversationModel = gson.fromJson(String.valueOf(response),ConversationModel.class);
                String confirmation = conversationModel.getConfirmation();
                if(confirmation.equals("success")){
                    List<ConversationChildModel> conversationChildModels = conversationModel.getData();
                    chatAdapter = new MultiViewTypeAdapter(conversationChildModels,ChatActivity.this,senderBy);
                    chatList.setAdapter(chatAdapter);
                    chatList.smoothScrollToPosition(chatList.getAdapter().getItemCount() - 1);
                }
            }

            @Override
            public void notifyError(@NonNull ANError anError) {
                Log.e("response",""+anError);
            }
        },this);
    }

    private void selectImage() {
        try {
            PackageManager pm = getPackageManager();
            int hasPerm = pm.checkPermission(Manifest.permission.CAMERA, getPackageName());
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                final CharSequence[] options = {"Take Photo", "Choose From Gallery","Cancel"};
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
                File file = new File(imgPath);
                uploadMedia(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == PICK_IMAGE_GALLERY) {
            Uri selectedImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
                String imgPath = getRealPathFromURI(selectedImage);
                File destination = new File(imgPath);
                uploadMedia(destination);
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


    private void uploadMedia(File file) {
        AndroidNetworking
                .upload(SEND_MEDIA_TO_CHAT)
                .addMultipartFile("image", file)
                //.addMultipartFile("baseurl", BASE_URL)
                .addMultipartParameter("baseurl",BASE_URL)
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
                        try {
                            response.put("senderFor","5dd6714a4b01823cc68a81bc");
                            response.put("content","");
                            sendMessage(response);
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
}
