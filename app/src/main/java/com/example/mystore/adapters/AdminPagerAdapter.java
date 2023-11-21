package com.example.mystore.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.mystore.fragments_of_tablayout_admin.HomeFragment;
import com.example.mystore.fragments_of_tablayout_admin.MoreFragment;
import com.example.mystore.fragments_of_tablayout_admin.OrdersFragment;
import com.example.mystore.fragments_of_tablayout_admin.ProductsFragment;

public class AdminPagerAdapter extends FragmentStateAdapter {
public static final int num_of_pages=4;

    public AdminPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:return HomeFragment.getInstance();
            case 1: return  OrdersFragment.getInstance();
            case 2:return ProductsFragment.getInstance();
            case 3:return   MoreFragment.getInstance();
            default:return null;
        }
    }

    @Override
    public int getItemCount() {
        return num_of_pages;
    }
}
