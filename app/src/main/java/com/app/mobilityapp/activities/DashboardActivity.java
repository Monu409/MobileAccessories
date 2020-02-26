package com.app.mobilityapp.activities;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.app.mobilityapp.adapter.CartChangeAdapter;
import com.app.mobilityapp.adapter.SliderAdapterExample;
import com.app.mobilityapp.app_utils.BaseActivity;
import com.app.mobilityapp.app_utils.ConstantMethods;
import com.app.mobilityapp.app_utils.MainSliderAdapter;
import com.app.mobilityapp.app_utils.PicassoImageLoadingService;
import com.app.mobilityapp.connection.CommonNetwork;
import com.app.mobilityapp.connection.JSONResult;
import com.app.mobilityapp.fragments.GlassFragment;
import com.app.mobilityapp.modals.CartChangeModel;
import com.app.mobilityapp.modals.ProductsModal;
import com.app.mobilityapp.R;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
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
import static com.app.mobilityapp.app_utils.AppApis.GET_BANNER;
import static com.app.mobilityapp.app_utils.AppApis.GET_CATEGORY;
import static com.app.mobilityapp.app_utils.AppApis.GET_PROFILE;
import static com.app.mobilityapp.app_utils.AppApis.PROFILE_UPDATE;

public class DashboardActivity extends BaseActivity implements JSONResult ,NavigationView.OnNavigationItemSelectedListener{
    private SliderView sliderView;
    public String caseCatId = "";
    public String glassCatId = "";
    public String chargerCatId = "";
    private List<String> catList = new ArrayList<>();
    private BottomNavigationView navigationView;
    private static final int navigation_add_product = 1;
    private ImageView menuImg,callImg;
    private TextView profileTxt,ledgerTxt,orderTxt,logoutTxt,changeUserTxt,aboutTxt,helpSprtTxt;
    private EditText searchEdt;
    private LinearLayout menuView,sellerView;
    private boolean menuVisible;
    private RelativeLayout headerView;
    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    private ImageView profilePic;
    private TextView nameTxt,emailTxt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        sliderView = findViewById(R.id.image_slider);
        menuImg = findViewById(R.id.menu_img);
        callImg = findViewById(R.id.call_img);
        profileTxt = findViewById(R.id.profile_txt);
        ledgerTxt = findViewById(R.id.ledger_txt);
        orderTxt = findViewById(R.id.order_txt);
        logoutTxt = findViewById(R.id.logout_txt);
        searchEdt = findViewById(R.id.search_edt);
        menuView = findViewById(R.id.menu_optn_view);
        headerView = findViewById(R.id.header_view);
        changeUserTxt = findViewById(R.id.change_user_txt);
        aboutTxt = findViewById(R.id.about_txt);
        aboutTxt = findViewById(R.id.about_txt);
        sellerView = findViewById(R.id.seller_view);
        helpSprtTxt = findViewById(R.id.help_sprt_txt);

        ImageView searchImg = findViewById(R.id.srch_img);
        searchEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().trim().length() > 0){
                    searchImg.setVisibility(View.GONE);
                }
                else{
                    searchImg.setVisibility(View.VISIBLE);
                }
            }
        });

        profileTxt.setOnClickListener(v->startActivity(new Intent(this, UpdateProfileActivity.class)));
        ledgerTxt.setOnClickListener(v->startActivity(new Intent(this, CreditSttmntActivity.class)));
        orderTxt.setOnClickListener(v->startActivity(new Intent(this, OrderListActivity.class)));
        aboutTxt.setOnClickListener(v->startActivity(new Intent(this, AboutUsActivity.class)));
        helpSprtTxt.setOnClickListener(v->startActivity(new Intent(this, HelpSupportActivity.class)));
        callImg.setOnClickListener(v->onCallBtnClick());
        logoutTxt.setOnClickListener(v->alertDialogForLogout());

        searchEdt.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String query = searchEdt.getText().toString();
                Intent intent = new Intent(DashboardActivity.this,ProductNamePriceActivity.class);
                intent.putExtra("last_view","search");
                intent.putExtra("search_key",query);
                startActivity(intent);
                return true;
            }
            return false;
        });


        sliderView.setOnClickListener(v->{
            menuView.setVisibility(View.GONE);
            menuVisible = false;
        });
        headerView.setOnClickListener(v->{
            menuView.setVisibility(View.GONE);
            menuVisible = false;
        });


        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("isDeleted","false");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        CommonNetwork.postNetworkJsonObj(GET_CATEGORY,jsonObject,this,this);
        navigationView = findViewById(R.id.navigation);
        String userType = ConstantMethods.getStringPreference("user_type",this);
        if(userType.equals("4")){
            Menu menu = navigationView.getMenu();
            menu.add(Menu.NONE, navigation_add_product, Menu.NONE, getString(R.string.add_product))
                    .setIcon(R.drawable.addproduct);
            changeUserTxt.setVisibility(View.GONE);
            sellerView.setVisibility(View.VISIBLE);
        }
        else {
            changeUserTxt.setText("Becomes a Seller");
            changeUserTxt.setOnClickListener(v->{
                dialogForBecomeSeller();
                menuView.setVisibility(View.GONE);
                menuVisible = false;
                sellerView.setVisibility(View.GONE);
            });
        }
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setCartCount();

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        View hView =  navigationView.getHeaderView(0);
        profilePic = hView.findViewById(R.id.profile_pic);
        nameTxt = hView.findViewById(R.id.user_name);
        emailTxt = hView.findViewById(R.id.user_email);


        menuImg.setOnClickListener(v->{
//            if(menuVisible){
//                menuView.setVisibility(View.GONE);
//                menuVisible = false;
//            }
//            else {
//                menuView.setVisibility(View.VISIBLE);
//                menuVisible = true;
//            }
            drawer.openDrawer(Gravity.START);
        });


        getBannerData();

    }
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
//        drawer.openDrawer(Gravity.RIGHT);
        int id = item.getItemId();
        switch (id){
            case R.id.my_profile:
                startActivity(new Intent(this, UpdateProfileActivity.class));
                break;
            case R.id.my_ledger:
                startActivity(new Intent(this, CreditSttmntActivity.class));
                break;
            case R.id.my_order:
                startActivity(new Intent(this, OrderListActivity.class));
                break;
            case R.id.become_seller:
                dialogForBecomeSeller();
                break;
            case R.id.about_us:
                startActivity(new Intent(this, AboutUsActivity.class));
                break;
            case R.id.hlp_suport:
                startActivity(new Intent(this, HelpSupportActivity.class));
                break;
            case R.id.logout:
                alertDialogForLogout();
                break;
        }
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        menuView.setVisibility(View.GONE);
        menuVisible = false;
        searchEdt.setText("");
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.test_drawer;
    }
    @Override
    public void notifySuccess(JSONObject response) {
        Log.e("response", "" + response);
        List<String> catIdList = new ArrayList<>();
        List<String> catNameList = new ArrayList<>();
        try {
            String result = response.getString("confirmation");
            if (result.equals("success")) {
                JSONArray jsonArray = response.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String name = jsonObject.getString("name");
                    String content = jsonObject.getString("content");
                    boolean status = jsonObject.getBoolean("status");
                    String createdBy = jsonObject.getString("createdBy");
                    String id = jsonObject.getString("_id");
                    catIdList.add(id);
                    catNameList.add(name);
                    String createdAt = jsonObject.getString("createdAt");
                    String updatedAt = jsonObject.getString("updatedAt");
                    String _v = jsonObject.getString("__v");
                    ProductsModal productsModal = new ProductsModal();
                    productsModal.setContent(content);
                    productsModal.setId(id);
                }
                TabLayout tabLayout = findViewById(R.id.tab_layout);

                ViewPager viewPager = findViewById(R.id.pager);
                viewPager.setOffscreenPageLimit(3);
                viewPager.setAdapter(new SectionPagerAdapter(getSupportFragmentManager(),catIdList,catNameList));
                tabLayout.setupWithViewPager(viewPager);
                for (int i = 0; i < tabLayout.getTabCount(); i++) {
                    TextView tv=(TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab,null);
                    tabLayout.getTabAt(i).setCustomView(tv);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void notifyError(ANError anError) {
        Log.e("response", "" + anError);
    }

    public class SectionPagerAdapter extends FragmentPagerAdapter {
        List<String> catIdList;
        List<String> catNameList;

        public SectionPagerAdapter(FragmentManager fm,List<String> catIdList,List<String> catNameList) {
            super(fm);
            this.catIdList = catIdList;
            this.catNameList = catNameList;
        }

        @Override
        public Fragment getItem(int position) {
            return new GlassFragment(catIdList.get(position));
        }

        @Override
        public int getCount() {
            return catIdList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return (catNameList.get(position)).toUpperCase();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        invalidateOptionsMenu();
        setCartCount();
        getProfileData();
    }
    TextView textCartItemCount;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.menu_main, menu);
        MenuItem myActionMenuItem = menu.findItem( R.id.action_search);
        final MenuItem menuItem = menu.findItem(R.id.action_cart);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(myActionMenuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if( ! searchView.isIconified()) {
                    searchView.setIconified(true);
                    Intent intent = new Intent(DashboardActivity.this,ProductNamePriceActivity.class);
                    intent.putExtra("last_view","search");
                    intent.putExtra("search_key",query);
                    startActivity(intent);
                }
                MenuItemCompat.collapseActionView(myActionMenuItem);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_call:
                onCallBtnClick();
                break;
            case R.id.action_profile:
                startActivity(new Intent(this, UpdateProfileActivity.class));
                break;
            case R.id.logout_menu:
                alertDialogForLogout();
                break;
            case R.id.action_credit:
                startActivity(new Intent(this, CreditSttmntActivity.class));
                break;
            case R.id.action_order:
                startActivity(new Intent(this, OrderListActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void onCallBtnClick(){
        if (Build.VERSION.SDK_INT < 23) {
            phoneCall();
        }else {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                phoneCall();
            }else {
                final String[] PERMISSIONS_STORAGE = {Manifest.permission.CALL_PHONE};
                //Asking request Permissions
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, 9);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        boolean permissionGranted = false;
        switch(requestCode){
            case 9:
                permissionGranted = grantResults[0]== PackageManager.PERMISSION_GRANTED;
                break;
        }
        if(permissionGranted){
            phoneCall();
        }else {
            Toast.makeText(this, "You don't assign permission.", Toast.LENGTH_SHORT).show();
        }
    }

    private void phoneCall(){
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:9999999999"));
            startActivity(callIntent);
        }else{
            Toast.makeText(this, "You don't assign permission.", Toast.LENGTH_SHORT).show();
        }
    }

    private void alertDialogForLogout(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("Logout");
        builder1.setMessage("Do you want to logout?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                (dialog, id) -> {
                    ConstantMethods.setStringPreference("login_status","logout",DashboardActivity.this);
                    startActivity(new Intent(DashboardActivity.this,LoginActivity.class));
                });

        builder1.setNegativeButton(
                "No",
                (dialog, id) -> dialog.cancel());

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)) {
           drawer.closeDrawers();
        }
        else {
            super.onBackPressed();
            finishAffinity();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener

            = item -> {
        navigationView.getMenu().getItem(0).setIcon(R.drawable.chat_unslct);
        navigationView.getMenu().getItem(1).setIcon(R.drawable.cart_unslctd);
        navigationView.getMenu().getItem(2).setIcon(R.drawable.orders_unslctd);
        switch (item.getItemId()) {
            case R.id.navigation_chat:
                startActivity(new Intent(this,ChatActivity.class));
                item.setIcon(R.drawable.chat_slctd);
                return true;
            case R.id.navigation_cart:
                startActivity(new Intent(this,CartChangeActivity.class));
                item.setIcon(R.drawable.cart_slctd);
                return true;
            case R.id.navigation_order:
                startActivity(new Intent(this,OrderListActivity.class));
                item.setIcon(R.drawable.orders_slctd);
                return true;
            case navigation_add_product:
                startActivity(new Intent(this,Add_ProductActivity.class));
                return true;
        }
        return false;
    };

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


    private void dialogForBecomeSeller(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("Becomes a seller");
        builder1.setMessage("Do you want to becomes a seller");
        builder1.setPositiveButton(
                "Yes",
                (dialog, id) -> {
                    dialog.cancel();
                    getGSTNo();
                });

        builder1.setNegativeButton(
                "No",
                (dialog, id) -> dialog.cancel());

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private void getGSTNo(){
        ConstantMethods.showProgressbar(this);
        String userId = ConstantMethods.getStringPreference("user_id",this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("_id",userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        CommonNetwork.postNetworkJsonObj(GET_PROFILE, jsonObject, new JSONResult() {
            @Override
            public void notifySuccess(@NonNull JSONObject response) {
                ConstantMethods.dismissProgressBar();
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    JSONObject userInfo = jsonArray.getJSONObject(0);
                    String gstno = userInfo.getString("gstno");
                    if(gstno.isEmpty()){
                        gstNoPopup();
                    }
                    else {
                        Intent intent = new Intent(DashboardActivity.this,ThankuActivity.class);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void notifyError(@NonNull ANError anError) {
                ConstantMethods.dismissProgressBar();
            }
        },this);
    }

    private void changeUserType(String gstNo){
        ConstantMethods.showProgressbar(this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("requestSeller", true );
            jsonObject.put("gstno", gstNo );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String id = ConstantMethods.getStringPreference("user_id",this);
        CommonNetwork.putNetworkJsonObj(PROFILE_UPDATE+"/"+id, jsonObject, new JSONResult() {
            @Override
            public void notifySuccess(@NonNull JSONObject response) {
                ConstantMethods.dismissProgressBar();
                try {
                    String confirmation = response.getString("confirmation");
                    if(confirmation.equals("success")){
                        Toast.makeText(DashboardActivity.this, "Request sent...", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DashboardActivity.this,ThankuActivity.class);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void notifyError(@NonNull ANError anError) {
                ConstantMethods.dismissProgressBar();
                Log.e("response",""+anError);
            }
        },this);
    }

    private void gstNoPopup(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        EditText edittext = new EditText(this);
        edittext.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        alert.setTitle("Enter your GST Number");

        alert.setView(edittext);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String gstStr = edittext.getText().toString();
                changeUserType(gstStr);
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
            }
        });

        alert.show();
    }

    private void getProfileData() {
        ConstantMethods.showProgressbar(this);
        String userId = ConstantMethods.getStringPreference("user_id", this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("_id", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        CommonNetwork.postNetworkJsonObj(GET_PROFILE, jsonObject, new JSONResult() {
            @Override
            public void notifySuccess(@NonNull JSONObject response) {
                ConstantMethods.dismissProgressBar();
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    JSONObject userInfo = jsonArray.getJSONObject(0);
                    String name = userInfo.getString("displayName");
                    String email = userInfo.getString("email");
                    String userPicture = userInfo.getString("userPicture");
                    nameTxt.setText(name);
                    emailTxt.setText(email);
                    Glide
                            .with(DashboardActivity.this)
                            .load(userPicture)
                            .centerCrop()
                            .into(profilePic);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void notifyError(@NonNull ANError anError) {
                ConstantMethods.dismissProgressBar();
            }
        }, this);
    }

    public void getBannerData(){
        List<String> urlList = new ArrayList<>();
        AndroidNetworking
                .get(GET_BANNER)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("confirmation");
                            if(status.equals("success")){
                                JSONArray jsonArray = response.getJSONArray("data");
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject childObj = jsonArray.getJSONObject(i);
                                    String bannerUrl = childObj.getString("imgUrl");
                                    urlList.add(bannerUrl);
                                }
                                sliderView.setSliderAdapter(new SliderAdapterExample(DashboardActivity.this,urlList));
                                sliderView.startAutoCycle();
                                sliderView.setIndicatorAnimation(IndicatorAnimations.WORM);
                                sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }
}
