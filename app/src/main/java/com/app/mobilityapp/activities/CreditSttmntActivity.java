package com.app.mobilityapp.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.error.ANError;
import com.app.mobilityapp.R;
import com.app.mobilityapp.adapter.CreditStatementAdapter;
import com.app.mobilityapp.app_utils.BaseActivity;
import com.app.mobilityapp.app_utils.ConstantMethods;
import com.app.mobilityapp.connection.CommonNetwork;
import com.app.mobilityapp.connection.JSONResult;
import com.app.mobilityapp.modals.CreditStmntModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.app.mobilityapp.app_utils.AppApis.CREDIT_TRANSACTION;

public class CreditSttmntActivity extends BaseActivity {
    private RecyclerView statementList;
    double debitSum = 0;
    double creditSum = 0;
    private TextView creditSumTxt,balanceTxt,debitTxt,noDataTxt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConstantMethods.setTitleAndBack(this,"Credit Statement");
        statementList = findViewById(R.id.sttmnt_list);
        creditSumTxt = findViewById(R.id.credit_sum_txt);
        balanceTxt = findViewById(R.id.balance_txt);
        debitTxt = findViewById(R.id.debit_txt);
        noDataTxt = findViewById(R.id.no_data_txt);
        statementList.setLayoutManager(new LinearLayoutManager(this));
        getStatements();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_credit_sttment;
    }

    private void getStatements(){
        CommonNetwork.getNetworkJsonObj(CREDIT_TRANSACTION, new JSONResult() {
            @Override
            public void notifySuccess(@NonNull JSONObject response) {
                Log.e("test",""+response);
                try {
                    List<CreditStmntModel> creditStmntModels = new ArrayList<>();
                    String confirmation = response.getString("confirmation");
                    if(confirmation.equals("success")) {
                        JSONArray jsonArray = response.getJSONArray("data");
                        if (jsonArray.length() == 0) {
                            noDataTxt.setVisibility(View.VISIBLE);
                            statementList.setVisibility(View.GONE);
                        } else {
                            noDataTxt.setVisibility(View.GONE);
                            statementList.setVisibility(View.VISIBLE);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject childObj = jsonArray.getJSONObject(i);
                                String totallimit = childObj.getString("totallimit");
                                String dabitBlnc = childObj.getString("usedBalence");
                                String creditlimit = childObj.getString("creditlimit");
                                String creditBlnc = childObj.getString("creditBalence");
                                String createdAt = childObj.getString("ledgerdate");
                                String[] parts = createdAt.split("T");
                                String date = parts[0];
                                String showDate = ConstantMethods.changeDateFormate(date);
                                String dabitBlncShow = dabitBlnc;
                                String creditBlncShow = creditBlnc;
                                if (dabitBlnc.equals("null")) {
                                    dabitBlnc = "0";
                                    dabitBlncShow = "--";
                                }
                                if (creditBlnc.equals("null")) {
                                    creditBlnc = "0";
                                    creditBlncShow = "--";
                                }
                                double debitInt = Double.parseDouble(dabitBlnc);
                                double creditInt = Double.parseDouble(creditBlnc);
                                debitSum = debitSum + debitInt;
                                creditSum = creditSum + creditInt;
                                CreditStmntModel creditStmntModel = new CreditStmntModel();
                                if(totallimit==null||totallimit.equals("null")){
//                                    Toast.makeText(CreditSttmntActivity.this, "You have total limit 0", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    creditStmntModel.setTotallimit(Double.parseDouble(totallimit));
                                    creditStmntModel.setUsedBalence(dabitBlncShow);
                                    creditStmntModel.setCreditBalence(creditBlncShow);
                                    creditStmntModel.setCreditlimit(creditlimit);
                                    creditStmntModel.setCreatedAt(showDate);
                                    creditStmntModels.add(creditStmntModel);
                                }
                            }
                            CreditStatementAdapter creditStatementAdapter = new CreditStatementAdapter(creditStmntModels, CreditSttmntActivity.this);
                            statementList.setAdapter(creditStatementAdapter);
                            debitTxt.setText(String.valueOf(debitSum));
                            creditSumTxt.setText(String.valueOf(creditSum));
                            balanceTxt.setText(String.valueOf(creditStmntModels.get(0).getCreditlimit()));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void notifyError(@NonNull ANError anError) {
                Toast.makeText(CreditSttmntActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        },this);
    }
}
