package com.example.smartcity.fragment;

import static com.example.smartcity.utils.APIConfig.NEWS_URL;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.smartcity.R;
import com.example.smartcity.activity.NewsDetailActivity;
import com.example.smartcity.adapter.NewsListAdapter;
import com.example.smartcity.bean.News;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @PackageName: com.example.smartcity.fragment
 * @ClassName: NewsShiZhengFragment
 * @Author: winwa
 * @Date: 2023/1/2 10:58
 * @Description:
 **/
public class NewsShiZhengFragment extends BaseFragment {
    private static final String TAG = "NewsShiZhengFragment";

    private ListView mListView;

    private NewsListAdapter mNewsListAdapter;

    @Override
    public View initView() {
        Log.i(TAG, "initView: ");
        View view = View.inflate(getContext(), R.layout.fragment_news_shizheng, null);
        mListView = view.findViewById(R.id.news_list_view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        Log.i(TAG, "initData: ");

        getNewsData();
    }

    public void getNewsData() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(NEWS_URL)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    Log.i(TAG, "onResponse: " + result);
                    Gson gson = new Gson();
                    News news = gson.fromJson(result, News.class);
                    Log.i(TAG, "onResponse: " + news.toString());
                    List<News.NewsItem> mNewsItems = news.getNewsItems();
                    Log.i(TAG, "onResponse: " + mNewsItems.toString());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mNewsListAdapter = new NewsListAdapter(mNewsItems, getActivity());
                            mListView.setAdapter(mNewsListAdapter);

//                            setListViewHeightBasedOnChildren(mListView);

                            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                                    ImageView newsImage = view.findViewById(R.id.news_image_view);
                                    newsImage.setDrawingCacheEnabled(true);
                                    intent.putExtra("bitmap", newsImage.getDrawingCache());
                                    intent.putExtra("title", mNewsItems.get(i).getTitle());
                                    intent.putExtra("time", mNewsItems.get(i).getCreateTime());
                                    intent.putExtra("content", mNewsItems.get(i).getContent());
                                    startActivity(intent);
                                }
                            });
                        }
                    });
                }
            }
        });
    }
}
