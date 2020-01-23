package com.app.mobilityapp.activities;
//
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;

import com.app.mobilityapp.R;
import com.app.mobilityapp.app_utils.BaseActivity;
public class Test extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_dashboard;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.app_bar_menu, menu);
        MenuItem myActionMenuItem = menu.findItem( R.id.action_search);
        final MenuItem menuItem = menu.findItem(R.id.action_cart);
//        View actionView = MenuItemCompat.getActionView(menuItem);
//        textCartItemCount = actionView.findViewById(R.id.cart_badge);
////        int cartSizeInt = getCartDetail();
////        String cartSize = String.valueOf(cartSizeInt);
////        if(cartSize.equals("")){
////            cartSize = "0";
////        }
////        textCartItemCount.setText(cartSize);
//        getCartDetail(menu);
//        actionView.setOnClickListener(v -> onOptionsItemSelected(menuItem));
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(myActionMenuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if( ! searchView.isIconified()) {
                    searchView.setIconified(true);
                    Intent intent = new Intent(Test.this,ProductNamePriceActivity.class);
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
//                onCallBtnClick();
                break;
            case R.id.action_profile:
                startActivity(new Intent(this, UpdateProfileActivity.class));
                break;
            case R.id.logout_menu:
//                alertDialogForLogout();
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
}