package com.example.smartcity.fragment;

import static com.example.smartcity.utils.APIConfig.SERVICE_URL;
import static com.example.smartcity.utils.APIConfig.THEME_URL;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.smartcity.R;
import com.example.smartcity.activity.AppointmentActivity;
import com.example.smartcity.activity.BannerWebViewActivity;
import com.example.smartcity.activity.BusActivity;
import com.example.smartcity.activity.CitySubwayActivity;
import com.example.smartcity.activity.LivingPayActivity;
import com.example.smartcity.activity.ParkActivity;
import com.example.smartcity.activity.RecommendThemeWebViewActivity;
import com.example.smartcity.activity.SearchResultActivity;
import com.example.smartcity.activity.WeiZhangActivity;
import com.example.smartcity.adapter.NewsListAdapter;
import com.example.smartcity.adapter.NewsPageAdapter;
import com.example.smartcity.adapter.RecommendThemeRecyclerViewAdapter;
import com.example.smartcity.adapter.ServiceRecycleViewAdapter;
import com.example.smartcity.bean.News;
import com.example.smartcity.bean.RecommendTheme;
import com.example.smartcity.bean.Service;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @PackageName: com.example.smartcity.fragment
 * @ClassName: HomeFragment
 * @Author: winwa
 * @Date: 2022/12/17 11:32
 * @Description:
 **/
public class HomeFragment extends BaseFragment {

    private static final String TAG = "HomeFragment";
    private static final int MSG_SERVICE = 0;
    private static final int MSG_THEME = 1;

    private TextView mNewsMoreTextView;
    private TextView mRecommendThemeTextView;
    private TextView mHotThemeTextView;
    private EditText mSearchEditText;
    private Banner mBanner;
    private RecyclerView mServiceRecyclerView;
    private RecyclerView mThemeRecyclerView;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private ServiceRecycleViewAdapter mServiceRecycleViewAdapter;
    private RecommendThemeRecyclerViewAdapter mThemeRecyclerViewAdapter;
    private NewsPageAdapter mNewsPageAdapter;

    private List<Service.ServiceItem> mServiceItems;
    private List<RecommendTheme.RecommendThemeItem> mThemeItems;

    private OkHttpClient mClient = new OkHttpClient();

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                Service service = (Service) msg.obj;
                mServiceItems = service.getServiceItems();
                mServiceRecycleViewAdapter = new ServiceRecycleViewAdapter(mServiceItems, getActivity());
                mServiceRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
                mServiceRecyclerView.setAdapter(mServiceRecycleViewAdapter);
                mServiceRecycleViewAdapter.setItemClickListener(new ServiceRecycleViewAdapter.ServiceItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String url = mServiceItems.get(position).getLink();
                        Intent intent = null;
                        if (position == 0) {
                            intent = new Intent(getActivity(), CitySubwayActivity.class);
                        } else if (position == 1) {
                            intent = new Intent(getActivity(), BusActivity.class);
                        } else if (position == 2) {
                            intent = new Intent(getActivity(), AppointmentActivity.class);
                        } else if (position == 3) {
                            intent = new Intent(getActivity(), LivingPayActivity.class);
                        } else if (position == 4) {
                            intent = new Intent(getActivity(), WeiZhangActivity.class);
                        } else if (position == 5) {
                            intent = new Intent(getActivity(), ParkActivity.class);
                        }

                        Bundle bundle = new Bundle();
                        bundle.putString("title", mServiceItems.get(position).getServiceName());
                        bundle.putString("url", url);
                        intent.putExtras(bundle);
                        getActivity().startActivity(intent);
                    }
                });
            } else if (msg.what == MSG_THEME) {
                RecommendTheme recommendTheme = (RecommendTheme) msg.obj;
                mThemeItems = recommendTheme.getRecommendThemeItems();
                mThemeRecyclerViewAdapter = new RecommendThemeRecyclerViewAdapter(mThemeItems, getActivity());
                mThemeRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                mThemeRecyclerView.setAdapter(mThemeRecyclerViewAdapter);
                mThemeRecyclerViewAdapter.setItemClickListener(new RecommendThemeRecyclerViewAdapter.RecommendThemeItemClickListener() {
                    @Override
                    public void onItemClick(int position, List<RecommendTheme.RecommendThemeItem> items) {
                        String url = items.get(position).getLink();
                        Intent intent = new Intent(getActivity(), RecommendThemeWebViewActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("title", items.get(position).getServiceName());
                        bundle.putString("url", url);
                        intent.putExtras(bundle);
                        getActivity().startActivity(intent);
                    }
                });
            }
        }
    };

    @Override
    public View initView() {
        View view = View.inflate(getActivity(), R.layout.fragment_home, null);
        mSearchEditText = view.findViewById(R.id.search_edit_text);
        mBanner = view.findViewById(R.id.banner);
        mServiceRecyclerView = view.findViewById(R.id.service_recycler_view);
        mThemeRecyclerView = view.findViewById(R.id.recommend_theme_recycler_view);
        mTabLayout = view.findViewById(R.id.news_tab_layout);
        mViewPager = view.findViewById(R.id.news_view_pager);
        mNewsMoreTextView = view.findViewById(R.id.news_more_text_view);
        mRecommendThemeTextView = view.findViewById(R.id.recommend_theme_title_text_view);
        mHotThemeTextView = view.findViewById(R.id.hot_theme_title_text_view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();

        initSearchService();
        initBanner();
        getServiceData(SERVICE_URL);
        getRecommendThemeData();
        initNews();
    }

    private void initSearchService() {
        mSearchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    String query = mSearchEditText.getText().toString();
                    Intent intent = new Intent(getActivity(), SearchResultActivity.class);
                    intent.putExtra("query", query);
                    startActivity(intent);
                    return true;
                }

                return false;
            }
        });

        mNewsMoreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "查看更多点击底部进入", Toast.LENGTH_SHORT).show();
            }
        });

        mHotThemeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "更多主题点击全部服务进入", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initBanner() {
        List<com.example.smartcity.bean.Banner.BannerItem> mBannerItemList = new ArrayList<>();
        mBannerItemList.add(new com.example.smartcity.bean.Banner.BannerItem("http://p2.itc.cn/q_70/images03/20221107/2a5a6713c3e243058159af96fd07e013.png", "智慧城市"));
        mBannerItemList.add(new com.example.smartcity.bean.Banner.BannerItem("https://www.cyzone.cn/upload/2021/0628/d1638b9173df6f218d560ef247b508bc.png", "智慧养老"));
        mBannerItemList.add(new com.example.smartcity.bean.Banner.BannerItem("http://www.transpeedtraffic.com/uploadfiles/pictures/news/20210916111221_4763.jpg", "智慧社区"));
        mBannerItemList.add(new com.example.smartcity.bean.Banner.BannerItem("http://p2.itc.cn/images01/20200610/ff751f6071ed4897bca20c10156a6e76.png", "智慧生活"));

        mBanner.setAdapter(new BannerImageAdapter<com.example.smartcity.bean.Banner.BannerItem>(mBannerItemList) {
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
                        Intent intent = new Intent(getActivity(), BannerWebViewActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("url", mBannerItemList.get(position).getImgUrl());
                        intent.putExtras(bundle);
                        getActivity().startActivity(intent);
                    }
                });
    }

    public void getServiceData(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Call call = mClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i(TAG, "onFailure: ");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String result = response.body().string();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Gson gson = new Gson();
                                Service service = gson.fromJson(result, Service.class);
                                Message msg = new Message();
                                msg.what = MSG_SERVICE;
                                msg.obj = service;
                                mHandler.sendMessage(msg);
                            }
                        });
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getRecommendThemeData() {
        Request request = new Request.Builder()
                .url(THEME_URL)
                .build();
        try {
            Call call = mClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i(TAG, "onFailure: ");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String result = response.body().string();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Gson gson = new Gson();
                                RecommendTheme theme = gson.fromJson(result, RecommendTheme.class);
                                Message msg = new Message();
                                msg.what = MSG_THEME;
                                msg.obj = theme;
                                mHandler.sendMessage(msg);
                            }
                        });
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initNews() {
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
        mViewPager.setAdapter(mNewsPageAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
