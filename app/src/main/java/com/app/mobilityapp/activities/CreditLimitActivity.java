package com.app.mobilityapp.activities;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.androidnetworking.error.ANError;
import com.app.mobilityapp.R;
import com.app.mobilityapp.app_utils.BaseActivity;
import com.app.mobilityapp.app_utils.ConstantMethods;
import com.app.mobilityapp.connection.CommonNetwork;
import com.app.mobilityapp.connection.JSONResult;
import org.json.JSONException;
import org.json.JSONObject;

import static com.app.mobilityapp.app_utils.AppApis.CREDIT_LIMIT;

public class CreditLimitActivity extends BaseActivity {
    TextView creditLimitTxt;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConstantMethods.setTitleAndBack(this,"Credit Limit");
        creditLimitTxt = findViewById(R.id.credit_limit_txt);
        getCreditLimit();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_credit_limit;
    }

    private void getCreditLimit(){
        ConstantMethods.showProgressbar(this);
        CommonNetwork.getNetworkJsonObj(CREDIT_LIMIT, new JSONResult() {
            @Override
            public void notifySuccess(@NonNull JSONObject response) {
                try {
                    ConstantMethods.dismissProgressBar();
                    JSONObject creditObj = response.getJSONObject("data");
                    String creditLimitStr = creditObj.getString("creditLimit");
                    if(creditLimitStr.equals("null")){
                        creditLimitTxt.setText("Your credit limit is not set\nPlease contact to your admin");
                    }
                    else {
                        creditLimitTxt.setText("₹ " + creditLimitStr);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void notifyError(@NonNull ANError anError) {
                Toast.makeText(CreditLimitActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                ConstantMethods.dismissProgressBar();
            }
        },this);
    }
}
