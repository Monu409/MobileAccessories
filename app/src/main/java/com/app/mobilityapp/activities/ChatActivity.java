package com.app.mobilityapp.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.error.ANError;
import com.app.mobilityapp.R;
import com.app.mobilityapp.adapter.ChatAdapter;
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

import java.util.ArrayList;
import java.util.List;

import static com.app.mobilityapp.app_utils.AppApis.GET_CONVERSATION_LIST;
import static com.app.mobilityapp.app_utils.AppApis.SEND_MESSAGE;

public class ChatActivity extends BaseActivity {
    private EditText sendMsgEdt;
    private RecyclerView chatList;
    private ImageView sendImg;
    private List<ChatModel> chatModels = new ArrayList<>();
    private ChatAdapter chatAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConstantMethods.setTitleAndBack(this,"Chat");
        sendMsgEdt = findViewById(R.id.send_msg_edt);
        sendImg = findViewById(R.id.send_img);
        chatList = findViewById(R.id.chat_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        chatList.setLayoutManager(linearLayoutManager);

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
                chatList.smoothScrollToPosition(chatModels.size()-1);
//                chatAdapter.notifyDataSetChanged();
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("senderFor","5dd6714a4b01823cc68a81bc");
                    jsonObject.put("content",message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                sendMessage(jsonObject);
            }
        });

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
                    chatAdapter = new ChatAdapter(conversationChildModels,ChatActivity.this,senderBy);
                    chatList.setAdapter(chatAdapter);
                }
            }

            @Override
            public void notifyError(@NonNull ANError anError) {
                Log.e("response",""+anError);
            }
        },this);
    }
}
