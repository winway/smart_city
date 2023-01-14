package com.example.smartcity.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

/**
 * @PackageName: com.example.smartcity.adapter
 * @ClassName: GuideAdapter
 * @Author: winwa
 * @Date: 2022/12/14 7:41
 * @Description:
 **/
public class GuideAdapter extends PagerAdapter {

    private final List<ImageView> mImageViews;

    public GuideAdapter(List<ImageView> imageViews) {
        mImageViews = imageViews;
    }

    @Override
    public int getCount() {
        return mImageViews.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(mImageViews.get(position));
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(mImageViews.get(position));
        return mImageViews.get(position);
    }
}
