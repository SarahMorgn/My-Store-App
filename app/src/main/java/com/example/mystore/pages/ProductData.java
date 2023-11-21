package com.example.mystore.pages;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mystore.R;
import com.example.mystore.classes.Product;
import com.example.mystore.databinding.ActivityProductDataBinding;
import com.example.mystore.firestore.ProductsStore;
import com.example.mystore.storages.OnImageCompleteListener;
import com.example.mystore.storages.ProductImages;
import com.google.firebase.storage.StorageException;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class ProductData extends AppCompatActivity {
    private static final String PRODUCT_NAME = "productName";
    private static final String PRODUCT_PRICE = "productPrice";
    private static final String PRODUCT_DESC = "productDesc";
    private static final String PRODUCT_SIZES = "productSizes";
    private static final String PRODUCT_IMG = "productImg";
    private static final String EDIT_PRODUCT = "EDIT_PRODUCT";
    private static final int READ_STORAGE_REQUEST_CODE=1;
    private static final int IMG_PICKER_REQUEST_CODE=1;
    ActivityProductDataBinding binding;
    Map<Integer,String>sizesMap;
    Uri imageUri=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=DataBindingUtil.setContentView(this,R.layout.activity_product_data);
        setSupportActionBar(binding.productDataToolbar);

        if (savedInstanceState!=null){
            binding.productName.setText(savedInstanceState.getString(PRODUCT_NAME));
            binding.productPrice.setText(savedInstanceState.getString(PRODUCT_PRICE));
            binding.productDescription.setText(savedInstanceState.getString(PRODUCT_DESC));
            ArrayList<String> sizes=savedInstanceState.getStringArrayList(PRODUCT_SIZES);

            for (int i=0;i<binding.smallSizesLayout.getChildCount();i++){
                View view=binding.smallSizesLayout.getChildAt(i);
                if (view instanceof CheckBox){
                    ((CheckBox) view).setChecked(true);
                }
            }
            String imgUriPath=savedInstanceState.getString(PRODUCT_IMG);
            Picasso.get().load(imgUriPath).into(binding.productImg);
        }

        Product editProduct= (Product) getIntent().getSerializableExtra(EDIT_PRODUCT);
        if(editProduct!=null){
            binding.productName.setText(editProduct.getProductName());
            binding.productPrice.setText(editProduct.getPrice());
            binding.productDescription.setText(editProduct.getDescription());
            ArrayList<String> sizes=editProduct.getSize();

            for (int i=0;i<binding.smallSizesLayout.getChildCount();i++){
                View view=binding.smallSizesLayout.getChildAt(i);
                if (view instanceof CheckBox){
                    ((CheckBox) view).setChecked(true);
                }
            }
            String imgUriPath=editProduct.getImgUrl();
            Picasso.get().load(imgUriPath).into(binding.productImg);
        }

        sizesMap=new HashMap<>();
        sizesMap.put(R.id.XS_checkBox,"XS");
        sizesMap.put(R.id.S_checkBox,"S");
        sizesMap.put(R.id.M_checkBox,"M");
        sizesMap.put(R.id.L_checkBox,"L");
        sizesMap.put(R.id.XL_checkBox,"XL");
        sizesMap.put(R.id.XXL_checkBox,"2XL");
        sizesMap.put(R.id.XXXL_checkBox,"3XL");
        sizesMap.put(R.id.XXXXL_checkBox,"4XL");

        binding.uploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(ProductData.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ProductData.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_STORAGE_REQUEST_CODE);
                } else {
                    accessPhoto();
                }
            }
        });


        binding.saveProductData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Product productData=getProductDataFromUser();
                 if(productData==null) {
                     Toasty.warning(ProductData.this, getResources().getString(R.string.warning_data_entering),Toasty.LENGTH_SHORT).show();
                 } else if (editProduct!=null && imageUri!=null) {
                     ProductImages productImages=new ProductImages();
                     productImages.updateProductPhoto(imageUri, editProduct.getImgUrl(), new OnImageCompleteListener() {
                         @Override
                         public void onComplete(String downloadUri) {
                             ProductsStore productsStore=new ProductsStore();
                             productsStore.updateProduct(productData,ProductData.this,downloadUri);
                         }
                     });
                     finish();

                 } else if (editProduct!=null && imageUri==null) {
                     ProductsStore productsStore=new ProductsStore();
                     productsStore.updateProduct(productData,ProductData.this,productData.getImgUrl());
                     finish();
                 } else {
                     if(imageUri!=null){
                     ProductImages productImages=new ProductImages();
                     productImages.addProductPhoto(imageUri, new OnImageCompleteListener() {
                         @Override
                         public void onComplete(String downloadUri) {
                             ProductsStore productsStore=new ProductsStore();
                             productsStore.addProduct(productData,ProductData.this,downloadUri);

                         }
                     });
                finish();}
                     else{
                         ProductsStore productsStore=new ProductsStore();
                         productsStore.addProduct(productData,ProductData.this,null);

                         finish();
                     }
                 }

            }
        });


    }


    private void accessPhoto() {
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,IMG_PICKER_REQUEST_CODE);
    }

    public Product getProductDataFromUser(){
        String productName=binding.productName.getText().toString();
        String productPrice=binding.productPrice.getText().toString();
        String productDescription=binding.productDescription.getText().toString();
        ArrayList<String> checkedSizes=new ArrayList<>();
        LinearLayout smallSizesLayout=binding.smallSizesLayout;
        LinearLayout largeSizesLayout=binding.largeSizesLayout;

        for (int i=0;i<smallSizesLayout.getChildCount();i++){
            View view=smallSizesLayout.getChildAt(i);
            Log.i("SIZE",i+"");
            if (((CheckBox) view).isChecked())
            {
                String size=getSizeFromId(view);
                checkedSizes.add(size);
                Log.i("SIZE",size);
            }
            else {
                Log.i("SIZE","unchecked");
            }
        }
        for (int i=0;i<largeSizesLayout.getChildCount();i++){
            View view=largeSizesLayout.getChildAt(i);
            if (((CheckBox)view).isChecked() ){
                String size=getSizeFromId(view);
                checkedSizes.add(size);
                Log.i("SIZE",size);
            }
            else {
                Log.i("SIZE","unchecked");
            }
        }
        if(productName.isEmpty() || productPrice.isEmpty() || productDescription.isEmpty() ||checkedSizes.isEmpty()){
           return null;
        }else {
            return new Product(productName, productDescription, checkedSizes, productPrice);
        }
    }
    public String getSizeFromId(View view){
       return sizesMap.get(view.getId());
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        String productName=binding.productName.getText().toString();
        String productPrice=binding.productPrice.getText().toString();
        String productDescription=binding.productDescription.getText().toString();
        ArrayList<String> checkedSizes=new ArrayList<>();
        LinearLayout smallSizesLayout=binding.smallSizesLayout;
        LinearLayout largeSizesLayout=binding.largeSizesLayout;

        for (int i=0;i<smallSizesLayout.getChildCount();i++){
            View view=smallSizesLayout.getChildAt(i);
            Log.i("SIZE",i+"");
            if (((CheckBox) view).isChecked())
            {
                String size=getSizeFromId(view);
                checkedSizes.add(size);
                Log.i("SIZE",size);
            }
            else {
                Log.i("SIZE","unchecked");
            }
        }
        for (int i=0;i<largeSizesLayout.getChildCount();i++){
            View view=largeSizesLayout.getChildAt(i);
            if (((CheckBox)view).isChecked() ) {
                String size = getSizeFromId(view);
                checkedSizes.add(size);
            }
        }
        Product product=getProductDataFromUser();
        outState.putString(PRODUCT_NAME,productName);
        outState.putString(PRODUCT_PRICE,productPrice);
        outState.putString(PRODUCT_DESC,productDescription);
        outState.putStringArrayList(PRODUCT_SIZES,checkedSizes);
        outState.putString(PRODUCT_IMG, String.valueOf(imageUri));

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==READ_STORAGE_REQUEST_CODE){
            if(grantResults.length>0 &&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                accessPhoto();
            }
            else {
                Toasty.warning(ProductData.this,getString(R.string.permissionDenied),Toasty.LENGTH_SHORT).show();

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==IMG_PICKER_REQUEST_CODE && resultCode==RESULT_OK && data!=null){
            imageUri=data.getData();
            Picasso.get().load(imageUri).into(binding.productImg);
        }
    }
}