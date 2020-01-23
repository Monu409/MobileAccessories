package com.app.mobilityapp.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.androidnetworking.error.ANError;
import com.app.mobilityapp.R;
import com.app.mobilityapp.app_utils.BaseActivity;
import com.app.mobilityapp.app_utils.ConstantMethods;
import com.app.mobilityapp.app_utils.MultiSpinner;
import com.app.mobilityapp.connection.CommonNetwork;
import com.app.mobilityapp.connection.JSONResult;
import com.app.mobilityapp.modals.AddedBrands;
import com.app.mobilityapp.modals.AddedPrice;
import com.app.mobilityapp.modals.ConsModel;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.app.mobilityapp.app_utils.AppApis.UPLOAD_PRODUCT;

public class Add_ProductActivity extends BaseActivity {
    LinearLayout brand_root, price_root;
    JSONObject post_json;
    List<String> colors = new ArrayList<>(Arrays.asList("#FFEB3B", "#616468", "#616468", "#616468", "#616468"));

    @Override
    protected int getLayoutResourceId() {
        return R.layout.add_product_activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConstantMethods.setTitleAndBack(this,"Add Product");
        post_json = new JSONObject();
        post_brands = new ArrayList();
        post_prices = new ArrayList();
        findViewId();
        get_category("http://132.148.158.82:3004/custom/category", new JSONObject(), "category");
        get_category("http://132.148.158.82:3004/custom/brand", new JSONObject(), "brand");
        add_new_price(new AddedPrice());
    }

    View.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.addview_btn:
                    if (all_brands != null)
                        add_new_brand(new AddedBrands());
                    break;
                case R.id.addprice_btn:
                    add_new_price(new AddedPrice());
                    break;
                case R.id.continue_btn:
                    try {
                        putJson(post_json, "brandDetails", new JSONArray(new Gson().toJson(post_brands)));
                        putJson(post_json, "price", new JSONArray(new Gson().toJson(post_prices)));
                        putJson(post_json, "colour", new JSONArray(new Gson().toJson(post_colors)));
                        putJson(post_json, "name", product_edt.getText().toString());
                        putJson(post_json, "content", "");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.e("final json ", " to post " + post_json);
                    uploadProduct(post_json);
                    break;
            }
        }
    };

    Spinner cat_spnr, scat_spnr, scat2_spnr;
    private TextView addview_btn, addprice_btn, continue_btn;
    MultiSpinner color_spnr;
    EditText product_edt;

    private void findViewId() {
        product_edt = findViewById(R.id.product_edt);
        color_spnr = findViewById(R.id.color_spnr);
        cat_spnr = findViewById(R.id.cat_spnr);
        scat_spnr = findViewById(R.id.scat_spnr);
        scat2_spnr = findViewById(R.id.scat2_spnr);
        addview_btn = findViewById(R.id.addview_btn);
        addprice_btn = findViewById(R.id.addprice_btn);
        brand_root = findViewById(R.id.brand_root);
        price_root = findViewById(R.id.price_root);
        continue_btn = findViewById(R.id.continue_btn);
        set_listeners();
    }

    public void set_listeners() {
        addview_btn.setOnClickListener(onclick);
        addprice_btn.setOnClickListener(onclick);
        continue_btn.setOnClickListener(onclick);
        cat_spnr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ConsModel categoryModel = (ConsModel) parent.getSelectedItem();
                ((ViewGroup) scat_spnr.getParent()).setVisibility(View.GONE);
                ((ViewGroup) scat2_spnr.getParent()).setVisibility(View.GONE);
                putJson(post_json, "categoryId", categoryModel.getId());
                get_category("http://132.148.158.82:3004/custom/subCategory", putJson(new JSONObject(), "categoryId", categoryModel.getId()), "subcategory");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        scat_spnr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ConsModel categoryModel = (ConsModel) parent.getSelectedItem();
                putJson(post_json, "subCategoryId", categoryModel.getId());
                ((ViewGroup) scat2_spnr.getParent()).setVisibility(View.GONE);
                get_category("http://132.148.158.82:3004/custom/subCategory2", putJson(new JSONObject(), "subCategoryId", categoryModel.getId()), "subcategory2");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        scat2_spnr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ConsModel categoryModel = (ConsModel) parent.getSelectedItem();
                putJson(post_json, "subcategory2", categoryModel.getId());
                putJson(post_json, "subcategory3", "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        color_spnr.setItems(Arrays.asList(getResources().getStringArray(R.array.color_array)), "Please Select");
        color_spnr.setItemSelectListener(new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(Boolean[] selected) {
                post_colors = new ArrayList<>();
                for (int j = 0; j < selected.length; j++) {
                    if (selected[j])
                        post_colors.add(colors.get(j));
                }
            }
        });
    }

    List<AddedBrands> post_brands;
    List<AddedPrice> post_prices;
    List<String> post_colors;

    private void add_new_brand(final AddedBrands brands) {
        post_brands.add(brands);
        final View view1 = getLayoutInflater().inflate(R.layout.brand_item, brand_root, false);
        final ImageView delete_btn = view1.findViewById(R.id.delete_btn);
        final Spinner brand_spinr = view1.findViewById(R.id.brand_spinr);
        final MultiSpinner model_spnr = view1.findViewById(R.id.model_spinr);

        brand_spinr.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, all_brands));
        view1.setTag(brand_root.getChildCount());
        if (brand_root.getChildCount() == 0) {
            delete_btn.setVisibility(View.GONE);
        }
        brand_root.addView(view1);

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                brand_root.removeView(view1);
                post_brands.remove(brands);
            }
        });
        brand_spinr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ConsModel selected_brand = (ConsModel) parent.getSelectedItem();
                brands.setBrand_id(selected_brand.getId());
                get_model("http://132.148.158.82:3004/custom/model", putJson(new JSONObject(), "brandId", selected_brand.getId()), model_spnr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        model_spnr.setSubmitListener(new MultiSpinner.OnSubmit() {
            @Override
            public void onSelection(List list) {
                if (brands.getModel_ids() != null)
                    brands.getModel_ids().clear();
                for (Object o : list)
                    brands.addModel_ids(((ConsModel) o).getId());

            }
        });
    }


    private void add_new_price(final AddedPrice prices) {
        post_prices.add(prices);
        final View view1 = getLayoutInflater().inflate(R.layout.price_item, price_root, false);
        final ImageView delete_btn = view1.findViewById(R.id.delete_btn);
        final EditText from_edt = view1.findViewById(R.id.from_edt);
        final EditText to_edt = view1.findViewById(R.id.to_edt);
        final EditText price_edt = view1.findViewById(R.id.price_edt);

        view1.setTag(price_root.getChildCount());
        if (price_root.getChildCount() == 0) {
            delete_btn.setVisibility(View.GONE);
        }
        price_root.addView(view1);

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                price_root.removeView(view1);
                post_prices.remove(prices);
            }
        });
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String s1 = s.toString();
                if(s1.equals("0")){
                    Toast.makeText(Add_ProductActivity.this, "Start value from 1", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (from_edt.hasFocus())
                        prices.setFrom(s1);
                    else if (to_edt.hasFocus())
                        prices.setTo(s1);
                    else if (price_edt.hasFocus())
                        prices.setAmount(s1);
                }

            }
        };
        from_edt.addTextChangedListener(textWatcher);
        to_edt.addTextChangedListener(textWatcher);
        price_edt.addTextChangedListener(textWatcher);
    }


    List<ConsModel> catList, sub_catList, sub_cat2List, all_brands;

    public void get_category(String url, JSONObject jsonObject, String type) {
        try {
            jsonObject.put("isDeleted", false);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        CommonNetwork.postNetworkJsonObj(url, jsonObject, new JSONResult() {
            @Override
            public void notifySuccess(@NonNull JSONObject response) {
                try {
                    if (response.getString("confirmation").equalsIgnoreCase("success")) {
                        switch (type) {
                            case "category":
                                catList = new ArrayList<>(Arrays.asList(new Gson().fromJson(response.getString("data"), ConsModel[].class)));
                                cat_spnr.setAdapter(new ArrayAdapter<>(Add_ProductActivity.this, android.R.layout.simple_list_item_1, catList));
                                break;
                            case "subcategory":
                                sub_catList = new ArrayList<>(Arrays.asList(new Gson().fromJson(response.getString("data"), ConsModel[].class)));
                                scat_spnr.setAdapter(new ArrayAdapter<>(Add_ProductActivity.this, android.R.layout.simple_list_item_1, sub_catList));
                                ((ViewGroup) scat_spnr.getParent()).setVisibility(View.VISIBLE);
                                break;
                            case "subcategory2":
                                sub_cat2List = new ArrayList<>(Arrays.asList(new Gson().fromJson(response.getString("data"), ConsModel[].class)));
                                scat2_spnr.setAdapter(new ArrayAdapter<>(Add_ProductActivity.this, android.R.layout.simple_list_item_1, sub_cat2List));
                                ((ViewGroup) scat2_spnr.getParent()).setVisibility(View.VISIBLE);
                                break;
                            case "brand":
                                all_brands = new ArrayList<>(Arrays.asList(new Gson().fromJson(response.getString("data"), ConsModel[].class)));
                                add_new_brand(new AddedBrands());
                                break;
                            case "models":
                                break;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void notifyError(@NonNull ANError anError) {

            }
        }, this);
    }

    public void get_model(String url, JSONObject jsonObject, MultiSpinner model_spnr) {
        CommonNetwork.postNetworkJsonObj(url, jsonObject, new JSONResult() {
            @Override
            public void notifySuccess(@NonNull JSONObject response) {
                try {
                    if (response.getString("confirmation").equalsIgnoreCase("success")) {
                        List<ConsModel> models = new ArrayList<>(Arrays.asList(new Gson().fromJson(response.getString("data"), ConsModel[].class)));
                        if (models.size() == 0)
                            ((ViewGroup) model_spnr.getParent()).setVisibility(View.GONE);
                        else
                            ((ViewGroup) model_spnr.getParent()).setVisibility(View.VISIBLE);
                        model_spnr.setItems(models, "Please select");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void notifyError(@NonNull ANError anError) {

            }
        }, this);
    }

    public JSONObject putJson(JSONObject jsonObject, String key, Object value) {
        try {
            jsonObject.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    
    private void uploadProduct(JSONObject jsonObject){
        ConstantMethods.showProgressbar(this);
        CommonNetwork.postNetworkJsonObj(UPLOAD_PRODUCT, jsonObject, new JSONResult() {
            @Override
            public void notifySuccess(@NonNull JSONObject response) {
                ConstantMethods.dismissProgressBar();
                try {
                    String confirmation = response.getString("confirmation");
                    if(confirmation.equals("success")){
                        Toast.makeText(Add_ProductActivity.this, "Product add successfully", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(Add_ProductActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(Add_ProductActivity.this, "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void notifyError(@NonNull ANError anError) {
                Toast.makeText(Add_ProductActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        },this);
    }
}
