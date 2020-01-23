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
import android.util.Log;
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
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;
import com.androidnetworking.error.ANError;
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
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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
import static com.app.mobilityapp.app_utils.AppApis.GET_CATEGORY;
import static com.app.mobilityapp.app_utils.AppApis.PROFILE_UPDATE;

public class DashboardActivity extends BaseActivity implements JSONResult {
//    Slider slider;
    String img1 = "https://rukminim1.flixcart.com/image/832/832/screen-guard/tempered-glass/c/x/d/kg-mobile-accessories-tg-2-original-imae88hxxn294rhx.jpeg";
    String img2 = "https://pkkharido.com/wp-content/uploads/2019/08/ma.jpeg";
    String img3 = "https://infiswap.com/wp-content/uploads/2017/10/SWSA25.jpg";
    List<String> imgList = new ArrayList<>();
    private SliderView sliderView;
    public String caseCatId = "";
    public String glassCatId = "";
    public String chargerCatId = "";
    private List<String> catList = new ArrayList<>();
    Uri fileUri = Uri.parse(img1);
    Uri fileUri2 = Uri.parse(img2);
    Uri fileUri3 = Uri.parse(img3);
    private BottomNavigationView navigationView;
    private static final int navigation_add_product = 1;
    private ImageView menuImg,callImg;
    private TextView profileTxt,ledgerTxt,orderTxt,logoutTxt,changeUserTxt;
    private EditText searchEdt;
    private LinearLayout menuView;
    private boolean menuVisible;
    private RelativeLayout headerView;

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

        sliderView.setSliderAdapter(new SliderAdapterExample(this));
        sliderView.startAutoCycle();
        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        imgList.add(fileUri.toString());
        imgList.add(fileUri2.toString());
        imgList.add(fileUri3.toString());

        profileTxt.setOnClickListener(v->startActivity(new Intent(this, UpdateProfileActivity.class)));
        ledgerTxt.setOnClickListener(v->startActivity(new Intent(this, CreditSttmntActivity.class)));
        orderTxt.setOnClickListener(v->startActivity(new Intent(this, OrderListActivity.class)));
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

        menuImg.setOnClickListener(v->{
            if(menuVisible){
                menuView.setVisibility(View.GONE);
                menuVisible = false;
            }
            else {
                menuView.setVisibility(View.VISIBLE);
                menuVisible = true;
            }
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
        }
        else {
            changeUserTxt.setText("Becomes a Seller");
            changeUserTxt.setOnClickListener(v->{
                dialogForBecomeSeller();
                menuView.setVisibility(View.GONE);
                menuVisible = false;
            });
        }
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setCartCount();

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
        return R.layout.activity_dashboard;
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
                int position = tabLayout.getSelectedTabPosition();
                if(position>3){
                    tabLayout.setTabMode(TabLayout.MODE_FIXED);
                }
                else{
                    tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                }
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
//            case R.id.action_cart:
//                startActivity(new Intent(this, CartChangeActivity.class));
//                break;
            case R.id.action_credit:
                startActivity(new Intent(this, CreditSttmntActivity.class));
                break;
//            case R.id.action_acc_transaction:
//                startActivity(new Intent(this, AccountTransactionActivity.class));
//                break;
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
        super.onBackPressed();
        finishAffinity();
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
            case R.id.navigation_order:
                startActivity(new Intent(this,OrderListActivity.class));
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
            BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(1); //set this to 0, 1, 2, or 3.. accordingly which menu item of the bottom bar you want to show badge
            View notificationBadge = LayoutInflater.from(this).inflate(R.layout.notification_badge, menuView, false);
            TextView textView = notificationBadge.findViewById(R.id.count_txt);
            textView.setText(count);
            itemView.addView(notificationBadge);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void changeUserType(){
//        String userTypeId = ConstantMethods.getStringPreference("user_type",this);
//        String changeTypeStr;
//        if(userTypeId.equals("3")){
//            changeTypeStr = "4";
//        }
//        else {
//            changeTypeStr = "3";
//        }
        ConstantMethods.showProgressbar(this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("requestSeller", true );
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
//                            updateProfilePic(destinationFile);
                        Toast.makeText(DashboardActivity.this, "Request sent...", Toast.LENGTH_SHORT).show();
//                        finish();
//                        startActivity(getIntent());
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
    private void dialogForBecomeSeller(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("Becomes a seller");
        builder1.setMessage("Do you want to becomes a seller");
        builder1.setPositiveButton(
                "Yes",
                (dialog, id) -> {
                    dialog.cancel();
                    changeUserType();
                });

        builder1.setNegativeButton(
                "No",
                (dialog, id) -> dialog.cancel());

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
