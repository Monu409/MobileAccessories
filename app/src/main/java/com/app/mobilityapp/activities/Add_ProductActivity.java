package com.app.mobilityapp.activities;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.app.mobilityapp.R;
import com.app.mobilityapp.adapter.MyAdapter;
import com.app.mobilityapp.app_utils.BaseActivity;
import com.app.mobilityapp.app_utils.ConstantMethods;
import com.app.mobilityapp.app_utils.FileUtils;
import com.app.mobilityapp.app_utils.MultiSpinner;
import com.app.mobilityapp.camera.CameraActivity;
import com.app.mobilityapp.connection.CommonNetwork;
import com.app.mobilityapp.connection.JSONResult;
import com.app.mobilityapp.modals.AddedBrands;
import com.app.mobilityapp.modals.AddedPrice;
import com.app.mobilityapp.modals.ConsModel;
import com.app.mobilityapp.modals.MyProductModel;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.app.mobilityapp.app_utils.AppApis.BASE_URL;
import static com.app.mobilityapp.app_utils.AppApis.SEND_MEDIA_TO_PRODUCT;
import static com.app.mobilityapp.app_utils.AppApis.UPLOAD_PRODUCT;
import com.app.mobilityapp.app_utils.ExpandableHeightGridView;

public class Add_ProductActivity extends BaseActivity {
    LinearLayout brand_root, price_root;
    JSONObject post_json;
    List<String> colors = new ArrayList<>(Arrays.asList("#FFEB3B", "#616468", "#616468", "#616468", "#616468"));
    final int PICK_IMAGE_CAMERA = 1;
    final int PICK_IMAGE_GALLERY = 2;
    private ArrayList<String> pathlist;
    //    private ArrayList<Uri> arrayList;
    private ExpandableHeightGridView listView;
    public JSONArray imageArr = new JSONArray();
    private String productId = "";

    @Override
    protected int getLayoutResourceId() {
        return R.layout.add_product_activity;
    }

    String page_type = "";
    MyProductModel.MyProductChild productChild;
    MyAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        post_json = new JSONObject();
        post_brands = new ArrayList();
        post_prices = new ArrayList();
        pathlist = new ArrayList<>();
//        arrayList = new ArrayList<>();
        if (getIntent().getExtras() != null) {
            page_type = getIntent().getStringExtra("page_type");
            productChild = gson.fromJson(getIntent().getStringExtra("data"), MyProductModel.MyProductChild.class);
            productId = getIntent().getStringExtra("product_id");
        }

        findViewId();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("status", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        get_category("http://132.148.158.82:3004/custom/category", jsonObject, "category");
        get_category("http://132.148.158.82:3004/custom/brand", new JSONObject(), "brand");
        if (page_type == null || page_type.equals("")) {
            ConstantMethods.setTitleAndBack(this, "Add Product");
            add_new_price(new AddedPrice());
        } else {
            ConstantMethods.setTitleAndBack(this, "Edit Product");
            if (productChild != null) {
                editPrices(productChild.getPrice());
                contentEdt.setText(productChild.getContent());
                product_edt.setText(productChild.getName());
                moqEdt.setText(productChild.getMoq());
                for (MyProductModel.Image image : productChild.getImage()) {
                    pathlist.add(image.getImageurl());
                }

                if (mAdapter != null) {
                    mAdapter.Update(pathlist);
                }
            }
            Log.e("edit price", " is now here");
        }
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
                    if (post_prices.size() < 3) {
                        add_new_price(new AddedPrice());
                    } else {
                        Toast.makeText(Add_ProductActivity.this, "You can't add more price", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.continue_btn:
                    getAllImagePaths();
                    break;
                case R.id.add_image:
                    selectImage();
                    break;
            }
        }
    };

    public void upload_product() {
        String productName = product_edt.getText().toString();
        String moqStr = moqEdt.getText().toString();
        int moqInt = Integer.parseInt(moqStr);
        if (productName.isEmpty()) {
            Toast.makeText(Add_ProductActivity.this, "Enter product name", Toast.LENGTH_SHORT).show();
        }
        else if(moqStr.isEmpty()){
            Toast.makeText(Add_ProductActivity.this, "Enter MOQ", Toast.LENGTH_SHORT).show();
        }else if(moqInt<=0){
            Toast.makeText(Add_ProductActivity.this, "Enter valid MOQ", Toast.LENGTH_SHORT).show();
        } else{
            try {
                putJson(post_json, "brandDetails", new JSONArray(gson.toJson(post_brands)));
                putJson(post_json, "price", new JSONArray(gson.toJson(post_prices)));
                putJson(post_json, "name", product_edt.getText().toString());
                putJson(post_json, "content", contentEdt.getText().toString().trim());
                putJson(post_json, "moq", moqInt);
                putJson(post_json, "image", imageArr);
                putJson(post_json, "colour", new JSONArray());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("final json ", " to post " + post_json);
            if (productId.equals("")) {
                addProduct(post_json);
            } else {
                updateProduct(post_json, productId);
            }

        }
    }


    Spinner cat_spnr, scat_spnr, scat2_spnr;
    private TextView addview_btn, addprice_btn, continue_btn, addImage;
    MultiSpinner color_spnr;
    EditText product_edt, contentEdt,moqEdt;

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
        contentEdt = findViewById(R.id.content_edt);
        addImage = findViewById(R.id.add_image);
        listView = findViewById(R.id.gv);
        moqEdt = findViewById(R.id.moq_edt);
        set_listeners();
        mAdapter = new MyAdapter(this, pathlist);
        listView.setAdapter(mAdapter);
        listView.setExpanded(true);
    }

    public void set_listeners() {
        addview_btn.setOnClickListener(onclick);
        addprice_btn.setOnClickListener(onclick);
        continue_btn.setOnClickListener(onclick);
        addImage.setOnClickListener(onclick);
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
                putJson(post_json, "subcategory3", null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        color_spnr.setItems(Arrays.asList(getResources().getStringArray(R.array.color_array)));
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
        final View view1 = getLayoutInflater().inflate(R.layout.brand_item, brand_root, false);
        final ImageView delete_btn = view1.findViewById(R.id.delete_btn);
        final Spinner brand_spinr = view1.findViewById(R.id.brand_spinr);
        final MultiSpinner model_spnr = view1.findViewById(R.id.model_spinr);

        brand_spinr.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, all_brands));
        brand_spinr.setSelection(getIndex(all_brands, brands.getBrand_id()));

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
                if (position > 0) {
                    ConsModel selected_brand = (ConsModel) parent.getSelectedItem();
                    if (!check_duplicate(post_brands, selected_brand)) {
                        brands.setBrand_id(selected_brand.getId());
                        get_model("http://132.148.158.82:3004/custom/model", putJson(new JSONObject(), "brandId", selected_brand.getId()), model_spnr, brands.getModel_ids());
                    } else {
                        Toast.makeText(Add_ProductActivity.this, "This Brand already selected please select different one", Toast.LENGTH_SHORT).show();
                        brand_spinr.setSelection(0);
                    }
                }
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
//        if (page_type == null || page_type.equals("ledgerdate"))
//            post_brands.add(brands);
//        else {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                post_brands.add(brands);
            }
        }, 1000);
//        }
    }

    private boolean check_duplicate(List<AddedBrands> post_brands, ConsModel cat) {
        for (AddedBrands brand : post_brands) {
            if (brand.getBrand_id() != null && brand.getBrand_id().equalsIgnoreCase(cat.getId())) {
                return true;
            }
        }
        return false;
    }


    private void add_new_price(final AddedPrice prices) {
        post_prices.add(prices);
        if (post_prices.size() >= 3) {
            addprice_btn.setVisibility(View.GONE);
        }
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
                if (post_prices.size() < 3) {
                    addprice_btn.setVisibility(View.VISIBLE);
                } else {
                    addprice_btn.setVisibility(View.GONE);
                }
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
                if (from_edt.hasFocus()) {
                    if (s1.equals("0")) {
                        Toast.makeText(Add_ProductActivity.this, "Start value from 1", Toast.LENGTH_SHORT).show();
                        from_edt.setText("");
                    }
                    prices.setFrom(s1);
                } else if (to_edt.hasFocus()) {
                    if (s1.equals("0")) {
                        Toast.makeText(Add_ProductActivity.this, "Start value from 1", Toast.LENGTH_SHORT).show();
                        to_edt.setText("");
                    }
                    prices.setTo(s1);
                } else if (price_edt.hasFocus()) {
                    prices.setAmount(s1);
                    if (s1.equals("0")) {
                        Toast.makeText(Add_ProductActivity.this, "Start value from 1", Toast.LENGTH_SHORT).show();
                        price_edt.setText("");
                    }
                }


            }
        };
        from_edt.addTextChangedListener(textWatcher);
        to_edt.addTextChangedListener(textWatcher);
        price_edt.addTextChangedListener(textWatcher);

        if (prices.getAmount() != null) {
            from_edt.setText(prices.getFrom());
            to_edt.setText(prices.getTo());
            price_edt.setText(prices.getAmount());
        }

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
                ConstantMethods.dismissProgressBar();
                try {
                    if (response.getString("confirmation").equalsIgnoreCase("success")) {
                        String data = response.getString("data");
                        switch (type) {
                            case "category":
                                catList = new ArrayList<>(Arrays.asList(gson.fromJson(data, ConsModel[].class)));
                                cat_spnr.setAdapter(new ArrayAdapter<>(Add_ProductActivity.this, android.R.layout.simple_list_item_1, catList));
                                if (page_type != null && !page_type.equals("")) {
                                    cat_spnr.setSelection(getIndex(catList, productChild.getCategoryId().getName()));
                                }
                                break;
                            case "subcategory":
                                sub_catList = new ArrayList<>(Arrays.asList(gson.fromJson(data, ConsModel[].class)));
                                scat_spnr.setAdapter(new ArrayAdapter<>(Add_ProductActivity.this, android.R.layout.simple_list_item_1, sub_catList));
                                ((ViewGroup) scat_spnr.getParent()).setVisibility(View.VISIBLE);
                                if (page_type != null && !page_type.equals("")) {
                                    scat_spnr.setSelection(getIndex(sub_catList, productChild.getSubCategoryId().getName()));
                                }
                                break;
                            case "subcategory2":
                                sub_cat2List = new ArrayList<>(Arrays.asList(gson.fromJson(data, ConsModel[].class)));
                                scat2_spnr.setAdapter(new ArrayAdapter<>(Add_ProductActivity.this, android.R.layout.simple_list_item_1, sub_cat2List));
                                ((ViewGroup) scat2_spnr.getParent()).setVisibility(View.VISIBLE);
                                if (page_type != null && !page_type.equals("") && productChild.getSubcategory2() != null) {
                                    scat2_spnr.setSelection(getIndex(sub_catList, productChild.getSubcategory2().getName()));
                                }
                                break;
                            case "brand":
                                all_brands = new ArrayList<>(Arrays.asList(gson.fromJson(data, ConsModel[].class)));
                                ConsModel consModel = new ConsModel();
                                consModel.setName("Please Select");
                                consModel.setId("0");
                                all_brands.add(0, consModel);
                                if (page_type != null && !page_type.equals("")) {
                                    editBrands();
                                } else {
                                    add_new_brand(new AddedBrands());
                                }
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

    public void get_model(String url, JSONObject jsonObject, MultiSpinner model_spnr, List<String> last_selected) {
//        ConstantMethods.showProgressbar1(this,"get_model");
        CommonNetwork.postNetworkJsonObj(url, jsonObject, new JSONResult() {
            @Override
            public void notifySuccess(@NonNull JSONObject response) {
                ConstantMethods.dismissProgressBar();
                try {
                    if (response.getString("confirmation").equalsIgnoreCase("success")) {
                        List<ConsModel> models = new ArrayList<>(Arrays.asList(gson.fromJson(response.getString("data"), ConsModel[].class)));
                        if (models.size() == 0)
                            ((ViewGroup) model_spnr.getParent()).setVisibility(View.GONE);
                        else
                            ((ViewGroup) model_spnr.getParent()).setVisibility(View.VISIBLE);
                        if (last_selected != null) {
                            model_spnr.setItems(models, last_selected);
                        } else {
                            model_spnr.setItems(models);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void notifyError(@NonNull ANError anError) {
                ConstantMethods.dismissProgressBar();
                Toast.makeText(Add_ProductActivity.this, anError.getErrorBody(), Toast.LENGTH_SHORT).show();
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

    private void addProduct(JSONObject jsonObject) {
        ConstantMethods.showProgressbar(this);
        CommonNetwork.postNetworkJsonObj(UPLOAD_PRODUCT, jsonObject, new JSONResult() {
            @Override
            public void notifySuccess(@NonNull JSONObject response) {
                ConstantMethods.dismissProgressBar();
                try {
                    String confirmation = response.getString("confirmation");
                    if (confirmation.equals("success")) {
                        showDialog(Add_ProductActivity.this);
                    } else {
                        Toast.makeText(Add_ProductActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void notifyError(@NonNull ANError anError) {
                ConstantMethods.dismissProgressBar();
                Toast.makeText(Add_ProductActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }, this);
    }

    private void updateProduct(JSONObject jsonObject, String productId) {
        ConstantMethods.showProgressbar(this);
        CommonNetwork.putNetworkJsonObj(UPLOAD_PRODUCT + "/" + productId, jsonObject, new JSONResult() {
            @Override
            public void notifySuccess(@NonNull JSONObject response) {
                ConstantMethods.dismissProgressBar();
                try {
                    String confirmation = response.getString("confirmation");
                    if (confirmation.equals("success")) {
                        showDialog(Add_ProductActivity.this);
                    } else {
                        Toast.makeText(Add_ProductActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void notifyError(@NonNull ANError anError) {
                Toast.makeText(Add_ProductActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }, this);
    }


    private void selectImage() {
        try {
            PackageManager pm = getPackageManager();
            int hasPerm = pm.checkPermission(Manifest.permission.CAMERA, getPackageName());
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                final CharSequence[] options = {"Take Photo", "Choose From Gallery", "Cancel"};
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Select Option");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Take Photo")) {
                            dialog.dismiss();
                            cameraImage();

                        } else if (options[item].equals("Choose From Gallery")) {
                            dialog.dismiss();
                            showChooser();
                        } else if (options[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            } else
                Toast.makeText(this, "Camera Permission error", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Camera Permission error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

//    private Uri imageToUploadUri;

    String imagePath = "";
    String directory;

    private void cameraImage() {
        directory = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
        String imgcurTime = "" + System.currentTimeMillis();
        File imageDirectory = new File(directory);
        imageDirectory.mkdirs();
        imagePath = directory + imgcurTime + ".jpg";
        startCamera(PICK_IMAGE_CAMERA, imgcurTime + ".jpg", true, false);
    }

    public void startCamera(int request_code, String name, boolean isTime, boolean isScan) {
        Intent camera = new Intent(this, CameraActivity.class);
        camera.putExtra("name", name);
        camera.putExtra("path", directory);
        camera.putExtra("istime", isTime);
        camera.putExtra("isScan", isScan);
        startActivityForResult(camera, request_code);
    }


    private void showChooser() {
        // Use the GET_CONTENT intent from the utility class
        Intent target = createGetContentIntent();
        // Create the chooser Intent
        Intent intent = Intent.createChooser(target, getString(R.string.app_name));
        try {
            startActivityForResult(intent, PICK_IMAGE_GALLERY);
        } catch (ActivityNotFoundException e) {
            // The reason for the existence of aFileChooser
        }
    }

    public static Intent createGetContentIntent() {
        // Implicitly allow the user to select a particular kind of data
        final Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        // The MIME data page_type filter
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        // Only return URIs that can be opened with ContentResolver
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        return intent;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICK_IMAGE_GALLERY:
                // If the file selection was successful
                if (resultCode == RESULT_OK) {
                    if (data.getClipData() != null) {
                        int count = data.getClipData().getItemCount();
                        int currentItem = 0;
                        while (currentItem < count) {
                            Uri imageUri = data.getClipData().getItemAt(currentItem).getUri();
                            //do something with the image (save it to some directory or whatever you need to do with it here)
                            currentItem = currentItem + 1;
                            Log.d("Uri Selected", imageUri.toString());
                            try {
                                // Get the file path from the URI
                                String path = FileUtils.getPath(this, imageUri);
                                Log.d("Multiple File Selected", path);
                                pathlist.add(path);
//                                arrayList.add(imageUri);
                            } catch (Exception e) {

                            }
                        }
                        if (pathlist.size() > 10) {
                            Toast.makeText(this, "Can't select more then 10 images", Toast.LENGTH_SHORT).show();
                            pathlist.clear();
                        } else {
                            if (mAdapter != null) {
                                mAdapter.Update(pathlist);
                            }
                        }
                    } else if (data.getData() != null) {
                        //do something with the image (save it to some directory or whatever you need to do with it here)
                        final Uri uri = data.getData();
                        try {
                            // Get the file path from the URI
                            final String path = FileUtils.getPath(this, uri);
                            Log.d("Single File Selected", path);
                            pathlist.add(path);
//                            arrayList.add(uri);
                            if (mAdapter != null) {
                                mAdapter.Update(pathlist);
                            }
                        } catch (Exception e) {
                            Log.e("", "File select error", e);
                        }
                    }
//                    getAllImagePaths("gallery");
                }
                break;

            case PICK_IMAGE_CAMERA:
                if (resultCode == RESULT_OK) {
                    pathlist.add(imagePath);
                    if (mAdapter != null) {
                        mAdapter.Update(pathlist);
                    }
                }
        }

    }

    private void getAllImagePaths() {
        for (int i = 0; i < pathlist.size(); i++) {
            File file = new File(pathlist.get(i));
            if (file.exists()) {
                try {
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 10, new FileOutputStream(file));
                } catch (Throwable t) {
                    Log.e("ERROR", "Error compressing file." + t.toString());
                    t.printStackTrace();
                }
                uploadMedia(file);
            } else {
                try {
                    JSONObject jsonObject = new JSONObject();
                    String path = pathlist.get(i);
                    jsonObject.put("imagename", path.substring(path.lastIndexOf("/") + 1));
                    jsonObject.put("imageurl", path);
                    imageArr.put(mInt, jsonObject);
                    if (mInt == pathlist.size() - 1) {
                        upload_product();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mInt = mInt + 1;
            }
        }
    }


    /***** Edit Code here
     * @param prices*****/

    Gson gson = new Gson();

    private void editPrices(List<MyProductModel.Price> prices) {
        for (MyProductModel.Price price : prices) {
            add_new_price(gson.fromJson(gson.toJson(price), AddedPrice.class));
        }
    }

    private void editBrands() {
        List<MyProductModel.BrandDetail> brands = productChild.getBrandDetails();
        for (MyProductModel.BrandDetail brand : brands) {
            if (brand.getBrand() != null) {
                AddedBrands addedBrands = new AddedBrands();
                addedBrands.setBrand_id(brand.getBrand().getId());
                for (MyProductModel.Model model : brand.getModel()) {
                    addedBrands.addModel_ids(model.getId());
                }
                add_new_brand(addedBrands);
            }
        }
    }


    private int getIndex(List<ConsModel> list, String s) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).toString().equals(s) || list.get(i).getId().equals(s)) {
                return i;
            }
        }
        return -1;
    }

    private int mInt = 0;

    private void uploadMedia(File file) {
        ConstantMethods.showProgressbar(this);
        AndroidNetworking
                .upload(SEND_MEDIA_TO_PRODUCT)
                .addMultipartFile("image", file)
                .addMultipartParameter("baseurl", BASE_URL)
                .addMultipartParameter("fileSize", String.valueOf(file.getTotalSpace()))
                .setTag("uploadTest")
                .setPriority(Priority.HIGH)
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {
                        Log.e("progress", "" + bytesUploaded);
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("progress", "" + response);
                        ConstantMethods.dismissProgressBar();
                        try {
                            imageArr.put(mInt, response);
                            if (mInt == pathlist.size() - 1) {
                                upload_product();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        mInt = mInt + 1;
                    }

                    @Override
                    public void onError(ANError error) {
                        Log.e("progress", "" + error);
                        ConstantMethods.dismissProgressBar();
                    }
                });
    }

    public void showDialog(Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_custom_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Window window = dialog.getWindow();
        window.setLayout(GridLayout.LayoutParams.MATCH_PARENT, GridLayout.LayoutParams.MATCH_PARENT);
        TextView msgTxt = dialog.findViewById(R.id.msg_txt);
        TextView leftTxt = dialog.findViewById(R.id.txt_left);
        TextView rightTxt = dialog.findViewById(R.id.txt_right);
        msgTxt.setText("Product Added successfully");
        leftTxt.setText("Go To My Product");
        rightTxt.setText("Add More");
        rightTxt.setOnClickListener(v -> {
            Intent intent = new Intent(Add_ProductActivity.this, Add_ProductActivity.class);
            startActivity(intent);
            finish();
            dialog.dismiss();
        });

        leftTxt.setOnClickListener(v -> {
            Intent intent = new Intent(Add_ProductActivity.this, MyProductActivity.class);
            startActivity(intent);
            finish();
            dialog.dismiss();
        });

        dialog.show();
    }


}
