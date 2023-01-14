package com.example.smartcity.fragment;

import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.smartcity.R;
import com.example.smartcity.adapter.NewsPageAdapter;
import com.google.android.material.tabs.TabLayout;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @PackageName: com.example.smartcity.fragment
 * @ClassName: NewsFragment
 * @Author: winwa
 * @Date: 2022/12/17 11:45
 * @Description:
 **/
public class NewsFragment extends BaseFragment {

    private static final String TAG = "NewsFragment";

    private Banner mBanner;
    private NewsPageAdapter mNewsPageAdapter;
    private ViewPager mNewsViewPager;
    private TabLayout mNewsTabLayout;
    private List<com.example.smartcity.bean.Banner.BannerItem> mBannerItems;

    @Override
    public View initView() {
        View view = View.inflate(getActivity(), R.layout.fragment_news, null);
        mNewsTabLayout = view.findViewById(R.id.tab_layout);
        mBanner = view.findViewById(R.id.banner);
        mNewsViewPager = view.findViewById(R.id.view_pager);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        initBanner();
        initTab();
    }

    private void initBanner() {
        mBannerItems = new ArrayList<>();
        mBannerItems.add(new com.example.smartcity.bean.Banner.BannerItem("http://p2.itc.cn/q_70/images03/20221107/2a5a6713c3e243058159af96fd07e013.png", null));
        mBannerItems.add(new com.example.smartcity.bean.Banner.BannerItem("https://www.cyzone.cn/upload/2021/0628/d1638b9173df6f218d560ef247b508bc.png", null));
        mBannerItems.add(new com.example.smartcity.bean.Banner.BannerItem("http://www.transpeedtraffic.com/uploadfiles/pictures/news/20210916111221_4763.jpg", null));
        mBannerItems.add(new com.example.smartcity.bean.Banner.BannerItem("http://p2.itc.cn/images01/20200610/ff751f6071ed4897bca20c10156a6e76.png", null));

        mBanner.setAdapter(new BannerImageAdapter<com.example.smartcity.bean.Banner.BannerItem>(mBannerItems) {
                    @Override
                    public void onBindView(BannerImageHolder holder, com.example.smartcity.bean.Banner.BannerItem data, int position, int size) {
                        Glide.with(getActivity())
                                .load(data.getImgUrl())
                                .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                                .into(holder.imageView);
                    }
                }).addBannerLifecycleObserver(this)
                .setIndicator(new CircleIndicator(getActivity()))
                .setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(Object data, int position) {
                        Toast.makeText(getActivity(), "Click " + position, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initTab() {
        String[] title = {"时政", "电视", "旅游", "视频", "广播", "基层"};

        List<Fragment> fragmentList;
        fragmentList = new ArrayList<>();
        fragmentList.add(new NewsShiZhengFragment());
        fragmentList.add(new NewsTvFragment());
        fragmentList.add(new NewsTravelFragment());
        fragmentList.add(new NewsVideoFragment());
        fragmentList.add(new NewsBroadcastFragment());
        fragmentList.add(new NewsJiCengFragment());

        mNewsPageAdapter = new NewsPageAdapter(getChildFragmentManager(), fragmentList, title);
        mNewsViewPager.setAdapter(mNewsPageAdapter);
        mNewsTabLayout.setupWithViewPager(mNewsViewPager);
    }
}
