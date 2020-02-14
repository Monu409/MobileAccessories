package com.app.mobilityapp.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.error.ANError;
import com.app.mobilityapp.adapter.ProductNamePriceAdapter;
import com.app.mobilityapp.app_utils.BaseActivity;
import com.app.mobilityapp.app_utils.ConstantMethods;
import com.app.mobilityapp.connection.CommonNetwork;
import com.app.mobilityapp.connection.JSONResult;
import com.app.mobilityapp.modals.ProBrndModal;
import com.app.mobilityapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.app.mobilityapp.app_utils.AppApis.PRODUCT_DETAIL;
import static com.app.mobilityapp.app_utils.AppApis.PRODUCT_DETAIL_CHANGE;
import static com.app.mobilityapp.app_utils.AppApis.SEARCH_DATA;

public class ProductNamePriceActivity extends BaseActivity {
    private RecyclerView brandList;
    private int sortVal = 1;
    private String url = "";
    private JSONObject jsonObjectParent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        brandList = findViewById(R.id.brand_list);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
//        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            ConstantMethods.setTitleAndBack(this, getIntent().getStringExtra("cat_name"));
        }
        brandList.setLayoutManager(new GridLayoutManager(this, 2));
        /***Space Between Column**/

        brandList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(view); // item position
                int spanCount = 2;
                int spacing = 2;//spacing between views in grid

                if (position >= 0) {
                    int column = position % spanCount; // item column

                    outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                    outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                    if (position < spanCount) { // top edge
                        outRect.top = spacing;
                    }
                    outRect.bottom = spacing; // item bottom
                } else {
                    outRect.left = 0;
                    outRect.right = 0;
                    outRect.top = 0;
                    outRect.bottom = 0;
                }
            }
        });

        ConstantMethods.showProgressbar(this);

        Intent intent = getIntent();



        String lastView = getIntent().getStringExtra("last_view");

//        JSONObject jsonObject = null;
//        jsonObjectParent = new JSONObject();
//        try {
//            // Log.e("catId",cat_id);
//            jsonObjectParent.put(jsonKey, cat_id);
//            jsonObject.put("sort", sortVal);
//            Log.e("request", jsonObject.toString());
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        JSONObject jsonObject = getInitialJSONObject();
        try {
            jsonObject.put("sort", sortVal);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("request", jsonObject.toString());
        getProductData(url,jsonObject);

        if (lastView.equals("sub_cat")) {
            url = PRODUCT_DETAIL_CHANGE;
            getProductData(url,jsonObject);
        }


        if (lastView.equals("search")) {
            url = SEARCH_DATA;
            String keyWord = getIntent().getStringExtra("search_key");
            jsonObject = new JSONObject();
            try {
                // Log.e("catId",cat_id);
                jsonObject.put("name", keyWord);
                jsonObject.put("sort", "1");
                Log.e("request", jsonObject.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_product_brand;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filter_menu, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_filter:
//                showFilterDialog();
                CreateAlertDialog();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    AlertDialog alertDialog1;
    CharSequence[] values = {"Price low to high", "Price high to low"};

    public void CreateAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Your Choice");
        builder.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch (item) {
                    case 0:
                        try {
                            sortVal = 1;
                            JSONObject jsonObject = getInitialJSONObject();
                            jsonObject.put("sort", sortVal);
                            Log.e("request", jsonObject.toString());
                            getProductData(url,jsonObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        break;
                    case 1:
                        try {
                            sortVal = -1;
                            JSONObject jsonObject = getInitialJSONObject();
                            jsonObject.put("sort", sortVal);
                            Log.e("request", jsonObject.toString());
                            getProductData(url,jsonObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                }
                alertDialog1.dismiss();
            }
        });
        alertDialog1 = builder.create();
        alertDialog1.show();

    }

    private void getProductData(String url, JSONObject jsonObject) {
        CommonNetwork.postNetworkJsonObj(url, jsonObject, new JSONResult() {
            @Override
            public void notifySuccess(@NonNull JSONObject response) {
                Log.e("response", "" + response);
                ConstantMethods.dismissProgressBar();
                List<ProBrndModal> proBrndModals = new ArrayList<>();
                try {
                    String confirmation = response.getString("confirmation");
                    if (confirmation.equals("success")) {
                        JSONArray jsonArray = response.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String name = jsonObject.getString("name");
                            String content = jsonObject.getString("content");
                            String brandId = jsonObject.getString("_id");
                            JSONArray priceArr = jsonObject.getJSONArray("price");
                            JSONObject priceObjFirst = priceArr.getJSONObject(0);
                            JSONObject priceObjLast = priceArr.getJSONObject(priceArr.length() - 1);
                            String maxPrice = priceObjFirst.getString("amount");
                            String minPrice = priceObjLast.getString("amount");
                            String fPrice = minPrice + "-" + maxPrice;
                            String moqStr = "";
                            JSONObject subcategory3 = null;
                            try {
                                subcategory3 = jsonObject.getJSONObject("subcategory3");
                                moqStr = subcategory3.getString("moq");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            JSONObject subcategory2 = null;
                            try {
                                subcategory2 = jsonObject.getJSONObject("subcategory2");
                                moqStr = subcategory2.getString("moq");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            JSONObject subCategoryId = null;
                            try {
                                subCategoryId = jsonObject.getJSONObject("subCategoryId");
                                moqStr = subCategoryId.getString("moq");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            JSONArray imgArr = null;
                            try {
                                imgArr = jsonObject.getJSONArray("image");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            ProBrndModal proBrndModal = new ProBrndModal();
                            proBrndModal.setName(name);
                            proBrndModal.setContent(String.valueOf(Html.fromHtml(content)));
                            proBrndModal.setId(brandId);
                            proBrndModal.setPriceRange("₹ " + fPrice);
                            proBrndModal.setMoqStr(moqStr);
                            proBrndModal.setImgArr(imgArr);
                            proBrndModals.add(proBrndModal);
                        }
                        ProductNamePriceAdapter productBrandAdapter = new ProductNamePriceAdapter(proBrndModals, ProductNamePriceActivity.this);
                        brandList.setAdapter(productBrandAdapter);
                        productBrandAdapter.onListClick(proBrndModal -> {
                            Log.e("position", "" + proBrndModal);
                            Intent intent = new Intent(ProductNamePriceActivity.this, ProductACopy.class);
//                            Intent intent = new Intent(ProductNamePriceActivity.this, ProductActivity.class);
                            Log.e("brand_name", proBrndModal.getName());
                            Log.e("brand_des", proBrndModal.getContent());

                            intent.putExtra("brand_id", proBrndModal.getId());
                            intent.putExtra("brand_name", proBrndModal.getName());
                            intent.putExtra("brand_des", proBrndModal.getContent());
                            intent.putExtra("brand_img", proBrndModal.getImgUrl());
                            intent.putExtra("img_array", proBrndModal.getImgArr().toString());
                            ProductNamePriceActivity.this.startActivity(intent);
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void notifyError(@NonNull ANError anError) {
                Log.e("response", "" + anError);
                ConstantMethods.dismissProgressBar();
            }
        }, this);
    }

    private JSONObject getInitialJSONObject(){
        String layer = getIntent().getStringExtra("layer");
        String cat_id = getIntent().getStringExtra("cat_id");
        String jsonKey = "subCategoryId";
        if (layer.equals("one")) {
            jsonKey = "subCategoryId";
        } else if (layer.equals("two")) {
            jsonKey = "subcategory2";
        } else if (layer.equals("three")) {
            jsonKey = "subcategory3";
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(jsonKey, cat_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}

