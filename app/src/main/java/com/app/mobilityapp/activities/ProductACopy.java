package com.app.mobilityapp.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
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

import static com.app.mobilityapp.app_utils.AppApis.ADD_INTO_CART;
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
        rec_price = findViewById(R.id.rec_price);
        navigationView = findViewById(R.id.navigation);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ProductACopy.this);
        prod_brand.setLayoutManager(linearLayoutManager);
        rec_price.setLayoutManager(new GridLayoutManager(this,3));
        onclickInterface = abc -> Toast.makeText(ProductACopy.this, "Position is" + abc, Toast.LENGTH_LONG).show();
        productId = getIntent().getStringExtra("brand_id");

        addToCartBtn.setOnClickListener(v->{
            List<LocalQuantityModel> quantityModels =  ConstantMethods.getQtyArrayListShared(ProductACopy.this,"local_qty_models");
            JSONObject jsonObject = getBrandDetailArr(quantityModels);
            addDataIntoCart(jsonObject,ADD_INTO_CART);
        });
        Log.e("prod_brand_id", productId);
        setCartCount();
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
                            JSONObject imgObj = jsonObject2.getJSONObject("image");
//                            String imgurl = jsonObject2.optString("imgUrl");
                            img1 = imgObj.optString("imageurl");
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
//                                proBrndModal.setImgUrl(imgurl);
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

    private JSONObject getBrandDetailArr(List<LocalQuantityModel> quantityModels){
        int priceSum = 0;
        JSONArray jsonArray = new JSONArray();
        JSONObject cartJson = new JSONObject();
        Gson gson = new Gson();
        String strJson = gson.toJson(quantityModels);
        Log.e("json",strJson);
        try {
            JSONArray getJsonArr = new JSONArray(strJson);
            for(int i=0;i<getJsonArr.length();i++){
                JSONObject brandDetails = new JSONObject();
                JSONObject childObj = getJsonArr.getJSONObject(i);
                JSONArray modallist = childObj.getJSONArray("modallist");
                String brandid = childObj.getString("brandid");
                String price = childObj.getString("price");
                int priceInt = Integer.parseInt(price);
                priceSum = priceSum+priceInt;
                brandDetails.put("brand",brandid);
                brandDetails.put("modallist",modallist);
                jsonArray.put(i,brandDetails);
            }
            String productId = getJsonArr.getJSONObject(0).getString("productid");
            cartJson.put("productid",productId);
            cartJson.put("brandDetails",jsonArray);
            cartJson.put("price",priceSum);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cartJson;
    }

    private void addDataIntoCart(JSONObject jsonObject, String apiUrl) {
        ConstantMethods.showProgressbar(this);
        CommonNetwork.postNetworkJsonObj(apiUrl, jsonObject, new JSONResult() {
            @Override
            public void notifySuccess(@NonNull JSONObject response) {
                ConstantMethods.dismissProgressBar();
                try {
                    String confirmation = response.getString("confirmation");
//                    if (confirmation.equals("success")) {
//                        Toast.makeText(ProductACopy.this, "Added into cart", Toast.LENGTH_SHORT).show();
//                        onBackPressed();
//                    }
                    showDialog(ProductACopy.this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void notifyError(@NonNull ANError anError) {
                ConstantMethods.dismissProgressBar();
                Toast.makeText(ProductACopy.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }, this);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        List<LocalQuantityModel> quantityModels =  ConstantMethods.getQtyArrayListShared(ProductACopy.this,"local_qty_models");
        quantityModels.clear();
        ConstantMethods.saveQtyListShared(quantityModels,this,"local_qty_models");
    }

    private void setCartCount() {
        CommonNetwork.getNetworkJsonObj(ADD_INTO_CART, new JSONResult() {
            @Override
            public void notifySuccess(@NonNull JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    int size = jsonArray.length();
                    String cartSize = String.valueOf(size);
                    if(cartSize.equals("")){
                        cartSize = "0";
                    }
                    addBadgeView(cartSize);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void notifyError(@NonNull ANError anError) {
            }
        },this);
    }

    private void addBadgeView(String count) {
        try {
            BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigationView.getChildAt(0);
            BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(1);
            View notificationBadge = LayoutInflater.from(this).inflate(R.layout.notification_badge, menuView, false);
            TextView textView = notificationBadge.findViewById(R.id.count_txt);
            textView.setText(count);
            itemView.addView(notificationBadge);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showDialog(Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_custom_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Window window = dialog.getWindow();
        window.setLayout(GridLayout.LayoutParams.MATCH_PARENT, GridLayout.LayoutParams.MATCH_PARENT);

        FrameLayout mDialogNo = dialog.findViewById(R.id.frmNo);
        TextView msgTxt = dialog.findViewById(R.id.msg_txt);
        TextView leftTxt = dialog.findViewById(R.id.txt_left);
        TextView rightTxt = dialog.findViewById(R.id.txt_right);
        msgTxt.setText("Successfully added into cart");
        leftTxt.setText("Go To Bag");
        rightTxt.setText("Continue Shopping");
        rightTxt.setOnClickListener(v -> {
            startActivity(new Intent(ProductACopy.this,DashboardActivity.class));
            List<LocalQuantityModel> quantityModels =  ConstantMethods.getQtyArrayListShared(ProductACopy.this,"local_qty_models");
            quantityModels.clear();
            ConstantMethods.saveQtyListShared(quantityModels,this,"local_qty_models");
            dialog.dismiss();
        });

        leftTxt.setOnClickListener(v -> {
            startActivity(new Intent(ProductACopy.this,CartChangeActivity.class));
            List<LocalQuantityModel> quantityModels =  ConstantMethods.getQtyArrayListShared(ProductACopy.this,"local_qty_models");
            quantityModels.clear();
            ConstantMethods.saveQtyListShared(quantityModels,this,"local_qty_models");
            dialog.dismiss();
        });

        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chat_menu, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_chat:
                startActivity(new Intent(this,ChatActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }


}