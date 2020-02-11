package com.app.mobilityapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.error.ANError;
import com.app.mobilityapp.R;
import com.app.mobilityapp.adapter.ProductBrandListAdapter;
import com.app.mobilityapp.adapter.ProductPriceListAdapter;
import com.app.mobilityapp.adapter.SliderAdapterExample;
import com.app.mobilityapp.app_utils.BaseActivity;
import com.app.mobilityapp.app_utils.ConstantMethods;
import com.app.mobilityapp.app_utils.PicassoImageLoadingService;
import com.app.mobilityapp.app_utils.onClickInterface;
import com.app.mobilityapp.connection.CommonNetwork;
import com.app.mobilityapp.connection.JSONResult;
import com.app.mobilityapp.modals.CartModel;
import com.app.mobilityapp.modals.ColorModel;
import com.app.mobilityapp.modals.LocalQuantityModel;
import com.app.mobilityapp.modals.ProBrndModal;
import com.app.mobilityapp.modals.ProModlModel;
import com.app.mobilityapp.modals.ProductPriceModel;
import com.app.mobilityapp.modals.QuantityModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ss.com.bannerslider.Slider;

import static com.app.mobilityapp.app_utils.AppApis.GET_ALL_PROD_BRAND;

public class ProductACopy extends BaseActivity {
    private CartModel cartModel;
    private TextView nameDes, desTxt, addCart, buyNow, prodPrice;
    private ImageView prodImg;
    TextView textCartItemCount;
    int mCartItemCount = 0;
    Slider slider;
    RecyclerView prod_brand, prod_model, rec_price;
    String productId = "";
    String img1 = "https://rukminim1.flixcart.com/image/416/416/k12go7k0/mobile/7/e/e/apple-iphone-7-mn8x2hn-a-original-imafkqcqhzxuvcpd.jpeg?q=70";
    String img2 = "https://rukminim1.flixcart.com/image/416/416/k23m4cw0/mobile/7/e/e/apple-iphone-7-mn8x2hn-a-original-imafhgvghtfqtawr.jpeg?q=70";
    String img3 = "https://rukminim1.flixcart.com/image/416/416/k12go7k0/mobile/v/z/8/apple-iphone-7-mn922hn-a-original-imafkqcrpc8bcy6h.jpeg?q=70";
    JSONArray jsonArray = null;
    JSONObject jsonObject;
    JSONArray modeljsonArray = null;
    List<String> imgList = new ArrayList<>();
    private onClickInterface onclickInterface;
    JSONArray brandDetailsarr;
    List<ProModlModel> proModlModels = new ArrayList<>();
    private String mProductID;
    ProductBrandListAdapter productBrandAdapter;
    List<ProductPriceModel> productPriceModels;
    private BottomNavigationView navigationView;
    private SliderView sliderView;
    private Button addToCartBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConstantMethods.setTitleAndBack(this, "All Products");
        nameDes = findViewById(R.id.prod_des);
        prodPrice = findViewById(R.id.prod_price);
        sliderView = findViewById(R.id.image_slider);
        addToCartBtn = findViewById(R.id.add_to_cart);

        prod_brand = findViewById(R.id.prod_brand);
        prod_model = findViewById(R.id.prod_model);
        rec_price = findViewById(R.id.rec_price);
        navigationView = findViewById(R.id.navigation);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ProductACopy.this);
        prod_brand.setLayoutManager(linearLayoutManager);
        GridLayoutManager glm1 = new GridLayoutManager(ProductACopy.this, 2);
        prod_model.setLayoutManager(glm1);
        rec_price.setLayoutManager(new GridLayoutManager(this,3));
        onclickInterface = new onClickInterface() {
            @Override
            public void setClick(int abc) {
                Toast.makeText(ProductACopy.this, "Position is" + abc, Toast.LENGTH_LONG).show();
            }
        };
        productId = getIntent().getStringExtra("brand_id");
        Log.e("prod_brand_id", productId);
//        setCartCount();


        Slider.init(new PicassoImageLoadingService(this));

        String modelName = getIntent().getStringExtra("brand_name");
        String content = getIntent().getStringExtra("brand_des");
        String imgArrStr = getIntent().getStringExtra("img_array");
        try {
            JSONArray jsonArray = new JSONArray(imgArrStr);
            List<String> imgList = new ArrayList<>();
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String imgUrl = jsonObject.getString("imageurl");
                imgList.add(imgUrl);
            }
            sliderView.setSliderAdapter(new SliderAdapterExample(this,imgList));
            sliderView.startAutoCycle();
            sliderView.setIndicatorAnimation(IndicatorAnimations.WORM);
            sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        nameDes.setText(content);
        prodPrice.setText(getResources().getString(R.string.rs) + "100");

        ConstantMethods.setTitleAndBack(this, modelName);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_product;
    }

    @Override
    protected void onResume() {
        super.onResume();
        setAdaptersData(productId);
    }

    private void setAdaptersData(String productId){
        String url = GET_ALL_PROD_BRAND + "/" + productId;
        CommonNetwork.getNetworkJsonObj(url, new JSONResult() {
            @Override
            public void notifySuccess(@NonNull JSONObject response) {
                Log.e("response", "" + response);
                List<ProBrndModal> proBrndModals = new ArrayList<>();
                try {
                    String confirmation = response.getString("confirmation");
                    if (confirmation.equals("success")) {
                        JSONObject dataObj = response.getJSONObject("data");
                        String productId = dataObj.getString("_id");
                        mProductID = productId;
                        JSONObject jsonObject1;
                        List<ProModlModel> proModlModels = new ArrayList<>();
                        productPriceModels = new ArrayList<>();
                        List<ColorModel> colorModels = new ArrayList<>();
                        brandDetailsarr = dataObj.getJSONArray("brandDetails");
                        JSONArray price = dataObj.getJSONArray("price");
                        JSONArray color = dataObj.getJSONArray("colour");
                        for (int i = 0; i < color.length(); i++) {
                            Log.e("color_code", color.get(i).toString());
                            ColorModel colorModel = new ColorModel(color.get(i).toString());
                            colorModel.setColor(color.get(i).toString());
                            colorModels.add(colorModel);
                        }
                        for (int k = 0; k < price.length(); k++) {
                            try {
                                JSONObject jsonObjectprice = price.getJSONObject(k);
                                String from = jsonObjectprice.getString("from");
                                String to = jsonObjectprice.getString("to");
                                String amt = jsonObjectprice.getString("amount");
                                ProductPriceModel productPriceModel = new ProductPriceModel(from, to, amt);
                                productPriceModel.setFrom(from);
                                productPriceModel.setTo(to);
                                productPriceModel.setAmount(amt);
                                productPriceModels.add(productPriceModel);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        for (int j = 0; j < brandDetailsarr.length(); j++) {
                            jsonObject1 = brandDetailsarr.getJSONObject(j);
                            JSONObject jsonObject2 = jsonObject1.getJSONObject("brand");
                            jsonArray = jsonObject1.getJSONArray("model");

                            String name = jsonObject2.optString("name");
                            String brandId = jsonObject2.optString("_id");
                            String imgurl = jsonObject2.optString("imgUrl");
                            img1 = jsonObject2.optString("imgUrl");
                            int indexId = j;

                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String model_name = jsonObject.getString("name");
                                    ProModlModel proModlModel = new ProModlModel();
                                    proModlModel.setName(model_name);
                                    proModlModels.add(proModlModel);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            ProBrndModal proBrndModal = new ProBrndModal();
                            if (TextUtils.isEmpty(name) || name.equalsIgnoreCase("") || name.equalsIgnoreCase("null")) {

                            } else {
                                proBrndModal.setName(name);
                                proBrndModal.setImgUrl(img1);
                                proBrndModal.setId(brandId);
                                proBrndModal.setIndexId(indexId);
                                proBrndModal.setImgUrl(imgurl);
//                                proBrndModal.setUniqueId(finalCatId);
                                proBrndModals.add(proBrndModal);
                            }
                        }
                        JSONObject productIdObj = new JSONObject();
                        productIdObj.put("productid",productId);
//                        getQtyData(productIdObj,proBrndModals);
                        prod_brand.setNestedScrollingEnabled(false);

                        ProductPriceListAdapter productPriceListAdapter = new ProductPriceListAdapter(productPriceModels, ProductACopy.this);
                        rec_price.setAdapter(productPriceListAdapter);

                        List<LocalQuantityModel> quantityModels =  ConstantMethods.getQtyArrayListShared(ProductACopy.this,"local_qty_models");
                        getBrandDetailArr(quantityModels);

                        productBrandAdapter = new ProductBrandListAdapter(proBrndModals, ProductACopy.this,quantityModels);
                        prod_brand.setAdapter(productBrandAdapter);

                        productBrandAdapter.onListClick(proBrndModal -> {
                            getModel(proBrndModal.getIndexId());
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
        },this);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {

        switch (item.getItemId()) {
            case R.id.navigation_chat:
                startActivity(new Intent(this,ChatActivity.class));
                return true;
            case R.id.navigation_cart:
                startActivity(new Intent(this,CartChangeActivity.class));
                return true;
            case R.id.navigation_home:
                startActivity(new Intent(this,DashboardActivity.class));
                return true;
        }
        return false;
    };
    String productIdforJson, brandId;
    private void getModel(int inx) {
        modeljsonArray = null;
        JSONObject val = null;
        try {
            val = brandDetailsarr.getJSONObject(inx);
            proModlModels.clear();
            JSONArray jsonArrayModel = val.getJSONArray("model");
            for (int i = 0; i < jsonArrayModel.length(); i++) {
                try {
                    JSONObject jsonObject = jsonArrayModel.getJSONObject(i);
                    String model_name = jsonObject.getString("name");
                    String desc = jsonObject.getString("content");
                    productIdforJson = getIntent().getStringExtra("brand_id");
                    brandId = jsonObject.getString("brandId");
                    String id = jsonObject.getString("_id");
                    ProModlModel proModlModel = new ProModlModel();
                    proModlModel.setName(model_name);
                    proModlModel.setContent(desc);
                    proModlModel.setId(id);
                    proModlModel.setQty("0");
                    proModlModels.add(proModlModel);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Intent intent = new Intent(this, EnterQuantityACopy.class);
            intent.putExtra("qty_list",(ArrayList<ProModlModel>)proModlModels);
            intent.putExtra("position",inx);
            intent.putExtra("price_list",(ArrayList<ProductPriceModel>)productPriceModels);
            intent.putExtra("brand_id",productIdforJson);
            intent.putExtra("view_name","product");
            intent.putExtra("brandId",brandId);
            startActivity(intent);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void createNewCartJson(JSONArray jsonArray){
        JSONObject jsonObject = new JSONObject();
        int priceSum = 0;
        try {
            JSONObject dataObj = jsonArray.getJSONObject(0);
            jsonObject.put("productid",dataObj.getJSONObject("productid"));
            JSONArray brandInfoArr = new JSONArray();
            for(int i=0;i<jsonArray.length();i++){
                JSONObject childObj = jsonArray.getJSONObject(i);
                JSONObject brandInfoObj = new JSONObject();
                brandInfoObj.put("brandId",childObj.getJSONObject("brandid"));
                brandInfoObj.put("modallist",childObj.getJSONArray("modallist"));
                brandInfoArr.put(i,brandInfoObj);
                String priceStr = childObj.getString("price");
                int priceInt = Integer.parseInt(priceStr);
                priceSum = priceSum+priceInt;
            }
            jsonObject.put("brandInfoArr",brandInfoArr);
            jsonObject.put("price",priceSum);
            jsonObject.put("categoryId",dataObj.getJSONObject("categoryId"));
            jsonObject.put("subCategoryId",dataObj.getJSONObject("subCategoryId"));
            jsonObject.put("subcategory2",dataObj.getJSONObject("subcategory2"));
//            jsonObject.put("subcategory3",dataObj.getJSONObject("subcategory3"));
            jsonObject.put("isDeleted",dataObj.getString("isDeleted"));
            jsonObject.put("updatedAt",dataObj.getString("updatedAt"));
            jsonObject.put("_id",dataObj.getString("_id"));
            jsonObject.put("__v",dataObj.getString("__v"));
            Log.e("jsonIS",""+jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private JSONArray getBrandDetailArr(List<LocalQuantityModel> quantityModels){
        JSONArray jsonArray = new JSONArray();
        JSONObject brandDetails = new JSONObject();
        Gson gson = new Gson();
        String strJson = gson.toJson(quantityModels);
        Log.e("json",strJson);
        try {
            JSONArray getJsonArr = new JSONArray(strJson);
            for(int i=0;i<getJsonArr.length();i++){
                JSONObject childObj = getJsonArr.getJSONObject(i);
                JSONArray modallist = childObj.getJSONArray("modallist");
                String brandid = childObj.getString("brandid");
                brandDetails.put("modallist",modallist);
                brandDetails.put("brand",brandid);
            }
            jsonArray.put(0,brandDetails);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }
}
