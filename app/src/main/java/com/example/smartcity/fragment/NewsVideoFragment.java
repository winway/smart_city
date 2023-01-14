package com.example.smartcity.fragment;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.smartcity.R;

/**
 * @PackageName: com.example.smartcity.fragment
 * @ClassName: NewsVideoFragment
 * @Author: winwa
 * @Date: 2023/1/2 10:54
 * @Description:
 **/
public class NewsVideoFragment extends BaseFragment {
    private static final String TAG = "NewsVideoFragment";

    @Override
    public View initView() {
        Log.i(TAG, "initView: ");
        View view = View.inflate(getContext(), R.layout.fragment_news_video, null);
        TextView textView = view.findViewById(R.id.text_view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        Log.i(TAG, "initData: ");
    }
}
