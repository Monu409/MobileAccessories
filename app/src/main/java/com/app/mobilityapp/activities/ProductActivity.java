package com.app.mobilityapp.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.error.ANError;
import com.app.mobilityapp.adapter.ProductBrandListAdapter;
import com.app.mobilityapp.adapter.ProductPriceListAdapter;
import com.app.mobilityapp.app_utils.BaseActivity;
import com.app.mobilityapp.app_utils.ConstantMethods;
import com.app.mobilityapp.app_utils.MainSliderAdapter;
import com.app.mobilityapp.app_utils.PicassoImageLoadingService;
import com.app.mobilityapp.app_utils.onClickInterface;
import com.app.mobilityapp.connection.CommonNetwork;
import com.app.mobilityapp.connection.JSONResult;
import com.app.mobilityapp.modals.CartChangeModel;
import com.app.mobilityapp.modals.CartModel;
import com.app.mobilityapp.modals.ColorModel;
import com.app.mobilityapp.modals.ProBrndModal;
import com.app.mobilityapp.modals.ProModlModel;
import com.app.mobilityapp.modals.ProductPriceModel;
import com.app.mobilityapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ss.com.bannerslider.Slider;

import static com.app.mobilityapp.app_utils.AppApis.ADD_INTO_CART;
import static com.app.mobilityapp.app_utils.AppApis.FILL_CART;
import static com.app.mobilityapp.app_utils.AppApis.GET_ALL_PROD_BRAND;

public class ProductActivity extends BaseActivity {
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
    ImageView img_left, img_right;
    RadioGroup rgb_range;
    EditText custom_value_edit_text;
    LinearLayout lnr_content;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConstantMethods.setTitleAndBack(this, "All Products");
        nameDes = findViewById(R.id.prod_des);
//        addCart = findViewById(R.id.add_txt);
//        buyNow = findViewById(R.id.buy_txt);
        prodImg = findViewById(R.id.prod_img);
        prodPrice = findViewById(R.id.prod_price);

        prod_brand = findViewById(R.id.prod_brand);
        prod_model = findViewById(R.id.prod_model);
        rec_price = findViewById(R.id.rec_price);
        navigationView = findViewById(R.id.navigation);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ProductActivity.this);
        prod_brand.setLayoutManager(linearLayoutManager);
        GridLayoutManager glm1 = new GridLayoutManager(ProductActivity.this, 2);
        prod_model.setLayoutManager(glm1);
        rec_price.setLayoutManager(new GridLayoutManager(this,3));
        onclickInterface = new onClickInterface() {
            @Override
            public void setClick(int abc) {
                Toast.makeText(ProductActivity.this, "Position is" + abc, Toast.LENGTH_LONG).show();
            }
        };

        setCartCount();


        Slider.init(new PicassoImageLoadingService(this));
        productId = getIntent().getStringExtra("brand_id");
        Log.e("prod_brand_id", productId);
        String modelName = getIntent().getStringExtra("brand_name");
        String content = getIntent().getStringExtra("brand_des");


        nameDes.setText(content);
        prodPrice.setText(getResources().getString(R.string.rs) + "100");

        ConstantMethods.setTitleAndBack(this, modelName);

//        addCart.setOnClickListener(v -> {
//            Toast.makeText(this, "Added into cart_unslctd", Toast.LENGTH_SHORT).show();
//            mCartItemCount++;
//            if (mCartItemCount > 0) {
//                setupBadge();
//            }
//
//            Intent intent = new Intent(ProductActivity.this, ProductBrandActivity.class);
//            ProductActivity.this.startActivity(intent);
//        });
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setAdaptersData();
        invalidateOptionsMenu();
        setCartCount();
    }

    private void setAdaptersData(){
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
                        getQtyData(productIdObj,proBrndModals);
                        prod_brand.setNestedScrollingEnabled(false);

                        ProductPriceListAdapter productPriceListAdapter = new ProductPriceListAdapter(productPriceModels, ProductActivity.this);
                        rec_price.setAdapter(productPriceListAdapter);
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

    @Override
    protected int getLayoutResourceId() {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_product;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {

        switch (item.getItemId()) {
            case R.id.navigation_chat:
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        boolean permissionGranted = false;
        switch (requestCode) {
            case 9:
                permissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (permissionGranted) {
            phoneCall();
        } else {
            Toast.makeText(this, "You don't assign permission.", Toast.LENGTH_SHORT).show();
        }
    }

    private void phoneCall() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:9999999999"));
            startActivity(callIntent);
        } else {
            Toast.makeText(this, "You don't assign permission.", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupBadge() {

        if (textCartItemCount != null) {
            if (mCartItemCount == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 99)));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }

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
            Intent intent = new Intent(this, EnterQuantityActivity.class);
            intent.putExtra("qty_list",(ArrayList<ProModlModel>)proModlModels);
            intent.putExtra("price_list",(ArrayList<ProductPriceModel>)productPriceModels);
            intent.putExtra("brand_id",productIdforJson);
            intent.putExtra("view_name","product");
            intent.putExtra("brandId",brandId);
            startActivity(intent);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    int i;
    private void getQtyData(JSONObject productIdObj,List<ProBrndModal> proBrndModals){
        List<CartChangeModel> cartChangeModels = new ArrayList<>();
        CommonNetwork.postNetworkJsonObj(FILL_CART, productIdObj, new JSONResult() {
            @Override
            public void notifySuccess(@NonNull JSONObject response) {
                Log.e("res",""+response);
                try {
                    String confirmation = response.getString("confirmation");
                    int totalCartPrice = 0;
                    if (confirmation.equals("success")){
                        JSONArray dataArr = response.getJSONArray("data");
                        if(dataArr.length()==0){
                            productBrandAdapter = new ProductBrandListAdapter(proBrndModals, ProductActivity.this);
                            prod_brand.setAdapter(productBrandAdapter);
                            productBrandAdapter.onListClick(proBrndModal -> {
                                slctd_brand=proBrndModal;
                                getModel(proBrndModal.getIndexId());
                            });
                        }
                        else {
                            for (i = 0; i < dataArr.length(); i++) {
                                int qtySum = 0;
                                CartChangeModel cartChangeModel = new CartChangeModel();
                                JSONObject dataObj = dataArr.getJSONObject(i);
                                JSONArray qtyArr = dataObj.getJSONArray("modallist");
                                JSONObject brandidObj = dataObj.getJSONObject("brandid");
                                JSONObject categoryId = dataObj.getJSONObject("categoryId");
                                JSONObject productObj = dataObj.getJSONObject("productid");
                                String brandidStr = brandidObj.getString("_id");
                                cartChangeModel.setBrandId(brandidStr);
                                for (int j = 0; j < qtyArr.length(); j++) {
                                    JSONObject qtyObj = qtyArr.getJSONObject(j);
                                    String qtyStr = qtyObj.getString("quantity");
                                    int qtyInt = Integer.parseInt(qtyStr);
                                    qtySum = qtySum + qtyInt;
                                }

                                String brandName = brandidObj.getString("name");
                                String catName = categoryId.getString("name");
                                String brandImgUrl = brandidObj.getString("imgUrl");
                                String brandPrice = dataObj.getString("price");
                                int brandPriceInt = Integer.parseInt(brandPrice);
                                totalCartPrice = totalCartPrice+brandPriceInt;
                                String brandId = brandidObj.getString("_id");
                                String productId = productObj.getString("_id");
                                String cartId = dataObj.getString("_id");
//                                String qty = String.valueOf(totalBrndQty);
                                cartChangeModel.setBrandName(brandName);
                                cartChangeModel.setCatName(catName);
                                cartChangeModel.setImgUrl(brandImgUrl);
//                                cartChangeModel.setQuantity(qty);
                                cartChangeModel.setTotalPrice(brandPrice);
                                cartChangeModel.setBrandId(brandId);
                                cartChangeModel.setProductId(productId);
                                cartChangeModel.setJsonArray(qtyArr.toString());
                                cartChangeModel.setCartId(cartId);
//                                cartIdArray.put(i,cartId);


                                cartChangeModel.setQuantity(String.valueOf(qtySum));
                                cartChangeModels.add(cartChangeModel);
                                productBrandAdapter = new ProductBrandListAdapter(proBrndModals, ProductActivity.this, cartChangeModels);
                                prod_brand.setAdapter(productBrandAdapter);
                                productBrandAdapter.onListClick(proBrndModal -> {
                                    if(!TextUtils.isEmpty(proBrndModals.get(proBrndModal.getIndexId()).getQuantity())) {
                                        Intent intent = new Intent(ProductActivity.this, EnterQuantityActivity.class);
                                        intent.putExtra("view_name", "cart_unslctd");
                                        intent.putExtra("price_list",(ArrayList<ProductPriceModel>)productPriceModels);
                                        intent.putExtra("qty_arr", cartChangeModels.get(proBrndModal.getIndexId()));
                                        startActivity(intent);
                                    }
                                    else {
                                        slctd_brand=proBrndModal;
                                        getModel(proBrndModal.getIndexId());
                                    }
                                });
                            }
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void notifyError(@NonNull ANError anError) {
                Log.e("res",""+anError);
            }
        },this);
    }

    private void getCartDetail(Menu menu) {
        CommonNetwork.getNetworkJsonObj(ADD_INTO_CART, new JSONResult() {
            @Override
            public void notifySuccess(@NonNull JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    int size = jsonArray.length();
                    final MenuItem menuItem = menu.findItem(R.id.action_cart);
                    View actionView = MenuItemCompat.getActionView(menuItem);
                    textCartItemCount = actionView.findViewById(R.id.cart_badge);
                    String cartSize = String.valueOf(size);
                    if(cartSize.equals("")){
                        cartSize = "0";
                    }
                    textCartItemCount.setText(cartSize);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void notifyError(@NonNull ANError anError) {

            }
        },this);
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
            BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(1); //set this to 0, 1, 2, or 3.. accordingly which menu item of the bottom bar you want to show badge
            View notificationBadge = LayoutInflater.from(this).inflate(R.layout.notification_badge, menuView, false);
            TextView textView = notificationBadge.findViewById(R.id.count_txt);
            textView.setText(count);
            itemView.addView(notificationBadge);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
