package com.example.mystore.fragments_of_tablayout_admin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mystore.ForgotPassword;
import com.example.mystore.R;
import com.example.mystore.adapters.ProductsRecyclerAdapter;
import com.example.mystore.classes.Product;
import com.example.mystore.databinding.FragmentProductsBinding;
import com.example.mystore.pages.ProductData;
import com.example.mystore.view_models.ProductDataViewModel;

import java.util.ArrayList;
import java.util.List;

public class ProductsFragment extends Fragment {

FragmentProductsBinding binding;
private ProductDataViewModel productDataViewModel;
private ProductsRecyclerAdapter adapter;

  public static ProductsFragment getInstance(){
      return new ProductsFragment();
  }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productDataViewModel=new ViewModelProvider(this).get(ProductDataViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentProductsBinding.inflate(inflater,container,false);

        adapter=new ProductsRecyclerAdapter(new ArrayList<>(),getActivity());
        binding.productsItemsRecyclerView.setAdapter(adapter);
        binding.productsItemsRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        productDataViewModel.getProductData().observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                Log.i("DATA",products.toString());
                adapter.setData(products);

            }
        });



        binding.floatingActionButton2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), ProductData.class);
                startActivity(intent);
            }
        });
        return binding.getRoot();
    }



    @Override
    public void onStart() {
        super.onStart();
        productDataViewModel=new ViewModelProvider(this).get(ProductDataViewModel.class);
        productDataViewModel.getProductData().observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                Log.i("DATA",products.toString());
                adapter.setData(products);

            }
        });
    }
}