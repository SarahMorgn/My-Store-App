package com.example.mystore.auths;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;

import com.example.mystore.R;
import com.example.mystore.adapters.AdminPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class AdminHome extends AppCompatActivity {
ViewPager2 viewPager2;
TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        viewPager2=findViewById(R.id.admin_viewPager);
        tabLayout=findViewById(R.id.adminTabLayout);

        AdminPagerAdapter adminPagerAdapter=new AdminPagerAdapter(this);
        viewPager2.setAdapter(adminPagerAdapter);

        tabLayout.getTabAt(0).setIcon(R.drawable.selected_home_24);
        tabLayout.getTabAt(0).getIcon().setColorFilter(getResources().getColor(R.color.light_blue), PorterDuff.Mode.SRC_IN);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
                switch (tab.getPosition()){
                    case 0:
                        tab.setIcon(R.drawable.selected_home_24);
                        tab.getIcon().setColorFilter(getResources().getColor(R.color.light_blue), PorterDuff.Mode.SRC_IN);
                        break;
                    case 1:
                        tab.setIcon(R.drawable.selected_order);
                        tab.getIcon().setColorFilter(getResources().getColor(R.color.light_blue), PorterDuff.Mode.SRC_IN);
                        break;
                    case 2:
                        tab.setIcon(R.drawable.baseline_products_24);
                        tab.getIcon().setColorFilter(getResources().getColor(R.color.light_blue), PorterDuff.Mode.SRC_IN);
                        break;
                    case 3:
                        tab.setIcon(R.drawable.selected_more_24);
                        tab.getIcon().setColorFilter(getResources().getColor(R.color.light_blue), PorterDuff.Mode.SRC_IN);

                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        tab.setIcon(R.drawable.outline_home_24);
                        tab.getIcon().setColorFilter(getResources().getColor(R.color.silver), PorterDuff.Mode.SRC_IN);
                        break;
                    case 1:
                        tab.setIcon(R.drawable.orders_24);
                        tab.getIcon().setColorFilter(getResources().getColor(R.color.silver), PorterDuff.Mode.SRC_IN);
                        break;
                    case 2:
                        tab.setIcon(R.drawable.outline_product_24);
                        tab.getIcon().setColorFilter(getResources().getColor(R.color.silver), PorterDuff.Mode.SRC_IN);
                        break;
                    case 3:
                        tab.setIcon(R.drawable.more_24);
                        tab.getIcon().setColorFilter(getResources().getColor(R.color.silver), PorterDuff.Mode.SRC_IN);

                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);


            }
        });
    }
    public  Context getContext(){
        return AdminHome.this;
    }
}