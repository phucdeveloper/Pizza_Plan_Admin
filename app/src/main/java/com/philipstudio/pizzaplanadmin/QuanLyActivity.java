package com.philipstudio.pizzaplanadmin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

public class QuanLyActivity extends AppCompatActivity {

    TabLayout tabLayout;
    androidx.viewpager.widget.ViewPager viewPager;
    Button btnHoaDon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanly);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager_container);
        btnHoaDon = findViewById(R.id.button_hoadon);

        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        btnHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuanLyActivity.this, HoaDonActivity.class);
                startActivity(intent);
            }
        });

    }

     class PagerAdapter extends FragmentPagerAdapter{

        public PagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0 :
                    return new QuanLyMenuFragment();
                case 1 :
                    return new QuanLyMatKhauFragment();
                case 2 :
                    return new QuanLyChiNhanhFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

         @Nullable
         @Override
         public CharSequence getPageTitle(int position) {
             switch (position){
                 case 0 : return "Menu";
                 case 1 : return "Mật khẩu";
                 case 2 : return "Chi nhánh";
             }
             return null;
         }
     }
}