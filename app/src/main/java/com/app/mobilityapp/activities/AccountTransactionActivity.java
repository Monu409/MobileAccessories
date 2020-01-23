package com.app.mobilityapp.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.androidnetworking.error.ANError;
import com.app.mobilityapp.R;
import com.app.mobilityapp.adapter.AccountTransactionAdapter;
import com.app.mobilityapp.app_utils.BaseActivity;
import com.app.mobilityapp.app_utils.ConstantMethods;
import com.app.mobilityapp.connection.CommonNetwork;
import com.app.mobilityapp.connection.JSONResult;
import com.app.mobilityapp.modals.AccTransModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import static com.app.mobilityapp.app_utils.AppApis.ACCOUNT_TRANSACTION;

public class AccountTransactionActivity extends BaseActivity {
    private RecyclerView accTransList;
    private TextView amountSumTxt,creditSumTxt,noDataTxt;
    private int amountSum = 0;
    private int creditSum = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConstantMethods.setTitleAndBack(this,"Account Statement");
        accTransList = findViewById(R.id.acc_sttmnt_list);
        amountSumTxt = findViewById(R.id.amount_sum_txt);
        creditSumTxt = findViewById(R.id.credit_sum_txt);
        noDataTxt = findViewById(R.id.no_data_txt);
        accTransList.setLayoutManager(new LinearLayoutManager(this));
        getAccTransaction();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_acc_transaction;
    }

    private void getAccTransaction(){
        CommonNetwork.getNetworkJsonObj(ACCOUNT_TRANSACTION, new JSONResult() {
            @Override
            public void notifySuccess(@NonNull JSONObject response) {
                Log.e("jkn",""+response);
                try {
                    String confirmation = response.getString("confirmation");
                    List<AccTransModel> accTransModels = new ArrayList<>();
                    if(confirmation.equals("success")) {
                        JSONArray jsonArray = response.getJSONArray("data");
                        if (jsonArray.length() == 0) {
                            noDataTxt.setVisibility(View.VISIBLE);
                            accTransList.setVisibility(View.GONE);
                        } else {
                            noDataTxt.setVisibility(View.GONE);
                            accTransList.setVisibility(View.VISIBLE);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String modeOfPayment = jsonObject.getString("modeOfPayment");
                                String amount = jsonObject.getString("amount");
                                int amountInt = Integer.parseInt(amount);
                                amountSum = amountSum + amountInt;
                                String creditamount = jsonObject.getString("creditamount");
                                String createdAt = jsonObject.getString("createdAt");
                                String[] parts = createdAt.split("T");
                                String date = parts[0];
                                String changeDateFormate = ConstantMethods.changeDateFormate(date);
                                String actualPayment = getModeOfPayment(modeOfPayment);
                                AccTransModel accTransModel = new AccTransModel();
                                accTransModel.setAmountStr(amount);
                                accTransModel.setCreditAmountStr(creditamount);
                                accTransModel.setDateStr(changeDateFormate);
                                accTransModel.setPayModeStr(actualPayment);
                                accTransModels.add(accTransModel);
                            }
                            AccountTransactionAdapter accountTransactionAdapter = new AccountTransactionAdapter(accTransModels, AccountTransactionActivity.this);
                            accTransList.setAdapter(accountTransactionAdapter);
                            amountSumTxt.setText(String.valueOf(amountSum));
                            creditSumTxt.setText(accTransModels.get(accTransModels.size() - 1).getCreditAmountStr());
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void notifyError(@NonNull ANError anError) {
                Log.e("jkn",""+anError);
            }
        },this);
    }

    private String getModeOfPayment(String payMode){
        String actualMode = "";
        switch (payMode){
            case "1":
                actualMode = "Bank Cheque";
                break;
            case "2":
                actualMode = "Online";
                break;
            case "3":
                actualMode = "Cash";
                break;
        }
        return actualMode;
    }
}
