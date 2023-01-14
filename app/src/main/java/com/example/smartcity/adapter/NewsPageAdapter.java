package com.example.smartcity.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @PackageName: com.example.smartcity.adapter
 * @ClassName: NewsPageAdapter
 * @Author: winwa
 * @Date: 2023/1/2 11:06
 * @Description:
 **/
public class NewsPageAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList;
    private String[] mTitles;

    public NewsPageAdapter(@NonNull FragmentManager fm, List<Fragment> fragmentList, String[] titles) {
        super(fm);
        mFragmentList = fragmentList;
        mTitles = titles;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
