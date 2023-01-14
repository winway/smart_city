package com.example.smartcity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.smartcity.fragment.HomeFragment;
import com.example.smartcity.fragment.NewsFragment;
import com.example.smartcity.fragment.PostFragment;
import com.example.smartcity.fragment.ServiceFragment;
import com.example.smartcity.fragment.UserFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private List<Fragment> mFragmentList;

    private String[] titles = {"首页", "服务", "发布", "新闻", "用户中心"};

    private int[] mTabItemResId = {R.mipmap.main_home,
            R.mipmap.main_type,
            R.mipmap.main_cart,
            R.mipmap.main_community,
            R.mipmap.main_user
    };
    private int[] mTabItemPressResId = {R.mipmap.main_home_press,
            R.mipmap.main_type_press,
            R.mipmap.main_cart_press,
            R.mipmap.main_community_press,
            R.mipmap.main_user_press
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = findViewById(R.id.view_pager);
        mTabLayout = findViewById(R.id.tab_layout);

        initData();
    }

    public void initData() {
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new HomeFragment());
        mFragmentList.add(new ServiceFragment());
        mFragmentList.add(new PostFragment());
        mFragmentList.add(new NewsFragment());
        mFragmentList.add(new UserFragment());

        MainPageAdapter mainPageAdapter = new MainPageAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mainPageAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            tab.setCustomView(mainPageAdapter.getTabItemView(i));
        }

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                ImageView imageView = view.findViewById(R.id.tab_item_image_view);
                imageView.setImageResource(mTabItemPressResId[tab.getPosition()]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                ImageView imageView = view.findViewById(R.id.tab_item_image_view);
                imageView.setImageResource(mTabItemResId[tab.getPosition()]);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public class MainPageAdapter extends FragmentPagerAdapter {

        public MainPageAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return titles.length;
        }

//        @Nullable
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return titles[position];
//        }

        public View getTabItemView(int position) {
            View view = View.inflate(MainActivity.this, R.layout.tab_layout_item_main, null);
            ImageView imageView = view.findViewById(R.id.tab_item_image_view);
            TextView textView = view.findViewById(R.id.tab_item_text_view);

            if (mTabLayout.getTabAt(position).isSelected()) {
                imageView.setImageResource(mTabItemPressResId[position]);
            } else {
                imageView.setImageResource(mTabItemResId[position]);
            }
            textView.setText(titles[position]);
            textView.setTextColor(mTabLayout.getTabTextColors());

            return view;
        }
    }
}