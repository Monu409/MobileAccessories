package com.app.mobilityapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.app.mobilityapp.app_utils.BaseActivity;
import com.app.mobilityapp.app_utils.ConstantMethods;
import com.app.mobilityapp.R;
import com.app.mobilityapp.connection.CommonNetwork;
import com.app.mobilityapp.connection.JSONResult;
import com.app.mobilityapp.modals.CityModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.app.mobilityapp.app_utils.AppApis.ADDRESS_DATA;
import static com.app.mobilityapp.app_utils.AppApis.USER_SIGNUP;


public class SignUpActivity extends BaseActivity {
    private EditText nameEdt,emailEdt,phoneEdt,passEdt,rePassEdt,gstNo,addresssEdt;
    private AutoCompleteTextView cityEdt;
    private Spinner userTypeSpnr;
    private Button signupBtn;
    private Map<String, String> userMap;
    private String countryId,stateId,cityId, cityStr;
    public static final String GSTINFORMAT_REGEX = "[0-9]{2}[a-zA-Z]{5}[0-9]{4}[a-zA-Z]{1}[1-9A-Za-z]{1}[Z]{1}[0-9a-zA-Z]{1}";
    public static final String GSTN_CODEPOINT_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private String GSTINFORMAT_REGEX1 =  "^([0]{1}[1-9]{1}|[1-2]{1}[0-9]{1}|[3]{1}[0-7]{1})([A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}[Z]{1}[0-9A-Z]{1})+$";
    private String ADDRESS_MATCHER = "[!#$%&(){|}~:;<=>?@*+,./^_`\\'\\\" \\t\\r\\n\\f-]+";
    private String mobileNo = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConstantMethods.setTitleAndBack(this,"Sign up");
        nameEdt = findViewById(R.id.input_fname);
        emailEdt = findViewById(R.id.input_email);
        phoneEdt = findViewById(R.id.input_mobile);
//        passEdt = findViewById(R.id.input_pass);
//        rePassEdt = findViewById(R.id.input_retypepass);
        userTypeSpnr = findViewById(R.id.type_user_spnr);
        signupBtn = findViewById(R.id.sign_up_btn);
        cityEdt = findViewById(R.id.city_edt);
        gstNo = findViewById(R.id.input_gst);
        addresssEdt = findViewById(R.id.input_address);
        Intent intent = getIntent();
        mobileNo = intent.getStringExtra("mobile_number");
        phoneEdt.setText(mobileNo);


        cityEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("searchkey",s.toString());
                    getAddressData(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        List<String> typeList = new ArrayList<>();
        userMap = ConstantMethods.getUserType();
        for (Map.Entry<String, String> entry : userMap.entrySet()) {
            String value = entry.getValue();
            typeList.add(value);
        }
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,typeList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userTypeSpnr.setAdapter(aa);
        userTypeSpnr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedVal = parent.getItemAtPosition(position).toString();
                if(selectedVal.equals("Seller")){
                    gstNo.setHint("GST No. *");
                }
                else if(selectedVal.equals("Buyer")){
                    gstNo.setHint("GST No. (Optional)");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        signupBtn.setOnClickListener(v->{
            String nameStr = nameEdt.getText().toString();
            String emailStr = emailEdt.getText().toString();
            String mobileStr = phoneEdt.getText().toString();
//            String passStr = passEdt.getText().toString();
//            String rePassStr = rePassEdt.getText().toString();
            String gstNoStr = gstNo.getText().toString();
            String typeStr = userTypeSpnr.getSelectedItem().toString();
            String typeStrKey = (String)getKeyFromValue(userMap,typeStr);
            if(fieldsValidation()) {
                if (typeStrKey.equals("4")) {
                    if (!gstNoStr.matches(GSTINFORMAT_REGEX1)) {
                        ConstantMethods.getAlertMessage(this, "Enter valid GST No.");
                    } else {
                        validSign(nameStr, emailStr, mobileStr, typeStrKey, gstNoStr);
                    }
                } else if (typeStrKey.equals("3")) {
                    if (gstNoStr.isEmpty()) {
                        validSign(nameStr, emailStr, mobileStr, typeStrKey, gstNoStr);
                    } else if (!gstNoStr.matches(GSTINFORMAT_REGEX1)) {
                        ConstantMethods.getAlertMessage(this, "Enter valid GST No.");
                    } else {
                        validSign(nameStr, emailStr, mobileStr, typeStrKey, gstNoStr);
                    }
                }
            }
        });
    }
    private void validSign(String nameStr,String emailStr,String mobileStr,String typeStrKey,String gstNoStr){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("displayName",nameStr);
            jsonObject.put("email",emailStr);
            jsonObject.put("phone",mobileStr);
            jsonObject.put("password","");
            jsonObject.put("userType",typeStrKey);
            jsonObject.put("userRollId","5d7dffecb909fc0dec41f14e");
            jsonObject.put("address", cityStr);
            jsonObject.put("stateId",stateId);
            jsonObject.put("countryId",countryId);
            jsonObject.put("cityId",cityId);
            jsonObject.put("gstno",gstNoStr);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        signupUser(jsonObject);
    }

    private boolean fieldsValidation(){
        boolean isValid;
        boolean digitsOnly = TextUtils.isDigitsOnly(addresssEdt.getText());
        String nameStr = nameEdt.getText().toString();
        String emailStr = emailEdt.getText().toString();
        String mobileStr = phoneEdt.getText().toString();
//        String passStr = passEdt.getText().toString();
//        String rePassStr = rePassEdt.getText().toString();
        String gstNoStr = gstNo.getText().toString();
        String addressStr = addresssEdt.getText().toString();
        String typeStr = userTypeSpnr.getSelectedItem().toString();
        String typeStrKey = (String)getKeyFromValue(userMap,typeStr);
        String cityStr = cityEdt.getText().toString();
//        String mobileFirstPos = String.valueOf(mobileStr.charAt(0));
        if(nameStr.isEmpty()||emailStr.isEmpty()||mobileStr.isEmpty()){
            Toast.makeText(this, "Enter all the fields", Toast.LENGTH_SHORT).show();
            isValid = false;
        }
        else if(!ConstantMethods.isValidMail(emailStr)){
            Toast.makeText(this, "Enter a valid email", Toast.LENGTH_SHORT).show();
            isValid = false;
        }
        else if(!ConstantMethods.isValidMobile(mobileStr) || !ConstantMethods.isFirstLetterMobileNum(mobileStr)|| mobileStr.length()>13||mobileStr.length()<9){
            Toast.makeText(this, "Enter a valid mobile", Toast.LENGTH_SHORT).show();
            isValid = false;
        }
//        else if(!passStr.equals(rePassStr)){
//            Toast.makeText(this, "Password does'nt match", Toast.LENGTH_SHORT).show();
//            isValid = false;
//        }
        else if(typeStr.equals("Select user type")){
            Toast.makeText(this, "Please select any user type", Toast.LENGTH_SHORT).show();
            isValid = false;
        }
        else if(typeStr.equals("Select user type")){
            Toast.makeText(this, "Please select any user type", Toast.LENGTH_SHORT).show();
            isValid = false;
        }
        else if(!ConstantMethods.checkAddress(cityStr)){
            Toast.makeText(this, "Please enter valid city", Toast.LENGTH_SHORT).show();
            isValid = false;
        }
        else if(digitsOnly){
            Toast.makeText(this, "Please select a valid address", Toast.LENGTH_SHORT).show();
            isValid = false;
        }
        else if(addressStr.trim().matches(ADDRESS_MATCHER)){
            Toast.makeText(this, "Please select a valid address", Toast.LENGTH_SHORT).show();
            isValid = false;
        }
        else {
            isValid = true;
        }
        return isValid;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_signup;
    }

    private void signupUser(JSONObject jsonObject){
        ConstantMethods.showProgressbar(this);
        AndroidNetworking
                .post(USER_SIGNUP)
                .addJSONObjectBody(jsonObject)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("res",response.toString());
                        try {
                            ConstantMethods.dismissProgressBar();
                            String mResponse = response.getString("confirmation");
                            if(mResponse.equals("success")){
                                Toast.makeText(SignUpActivity.this, "Account create successfully", Toast.LENGTH_SHORT).show();
                                JSONObject dataObj = response.getJSONObject("data");
                                Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }else if(mResponse.equals("error")){
                                String message = response.getString("message");
                                Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        ConstantMethods.dismissProgressBar();
                        Log.e("anError",anError.toString());
                        Toast.makeText(SignUpActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public static Object getKeyFromValue(Map hm, Object value) {
        for (Object o : hm.keySet()) {
            if (hm.get(o).equals(value)) {
                return o;
            }
        }
        return null;
    }

    private void getAddressData(JSONObject jsonObject){
        CommonNetwork.postNetworkJsonObj(ADDRESS_DATA, jsonObject, new JSONResult() {
            List<CityModel> cityData = new ArrayList<>();
            List<String> cityDataShown = new ArrayList<>();
            @Override
            public void notifySuccess(@NonNull JSONObject response) {
                Log.e("res",""+response);
                try {
                    String confirmation = response.getString("confirmation");
                    if(confirmation.equals("success")){
                        JSONArray jsonArray = response.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            CityModel cityModel = new CityModel();
                            JSONObject childObj = jsonArray.getJSONObject(i);
                            String cityStr = childObj.getString("cityName");
                            JSONObject contryJSON = childObj.getJSONObject("countryId");
                            String countryId = contryJSON.getString("_id");
                            JSONObject stateJSON = childObj.getJSONObject("stateId");
                            String stateId = stateJSON.getString("_id");
                            String cityId = childObj.getString("_id");
                            cityModel.setCityName(cityStr);
                            cityModel.setCityId(cityId);
                            cityModel.setStateId(stateId);
                            cityModel.setCountryId(countryId);
                            cityData.add(cityModel);
                            cityDataShown.add(cityStr);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(SignUpActivity.this, android.R.layout.simple_dropdown_item_1line, cityDataShown);
                cityEdt.setAdapter(adapter);
                cityEdt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        CityModel cityModel = cityData.get(position);
                        cityId = cityModel.getCityId();
                        countryId = cityModel.getCountryId();
                        stateId = cityModel.getStateId();
                        cityStr = cityModel.getCityName();
                    }
                });
            }

            @Override
            public void notifyError(@NonNull ANError anError) {
                Log.e("res",""+anError);
            }
        },this);
    }

    private static boolean validGSTIN(String gstin) throws Exception {
        boolean isValidFormat = false;
        if (checkPattern(gstin, GSTINFORMAT_REGEX)) {
            isValidFormat = verifyCheckDigit(gstin);
        }
        return isValidFormat;

    }

    private static boolean verifyCheckDigit(String gstinWCheckDigit) throws Exception {
        Boolean isCDValid = false;
        String newGstninWCheckDigit = getGSTINWithCheckDigit(
                gstinWCheckDigit.substring(0, gstinWCheckDigit.length() - 1));

        if (gstinWCheckDigit.trim().equals(newGstninWCheckDigit)) {
            isCDValid = true;
        }
        return isCDValid;
    }

    public static boolean checkPattern(String inputval, String regxpatrn) {
        boolean result = false;
        if ((inputval.trim()).matches(regxpatrn)) {
            result = true;
        }
        return result;
    }

    public static String getGSTINWithCheckDigit(String gstinWOCheckDigit) throws Exception {
        int factor = 2;
        int sum = 0;
        int checkCodePoint = 0;
        char[] cpChars;
        char[] inputChars;

        try {
            if (gstinWOCheckDigit == null) {
                throw new Exception("GSTIN supplied for checkdigit calculation is null");
            }
            cpChars = GSTN_CODEPOINT_CHARS.toCharArray();
            inputChars = gstinWOCheckDigit.trim().toUpperCase().toCharArray();

            int mod = cpChars.length;
            for (int i = inputChars.length - 1; i >= 0; i--) {
                int codePoint = -1;
                for (int j = 0; j < cpChars.length; j++) {
                    if (cpChars[j] == inputChars[i]) {
                        codePoint = j;
                    }
                }
                int digit = factor * codePoint;
                factor = (factor == 2) ? 1 : 2;
                digit = (digit / mod) + (digit % mod);
                sum += digit;
            }
            checkCodePoint = (mod - (sum % mod)) % mod;
            return gstinWOCheckDigit + cpChars[checkCodePoint];
        } finally {
            inputChars = null;
            cpChars = null;
        }
    }
}
