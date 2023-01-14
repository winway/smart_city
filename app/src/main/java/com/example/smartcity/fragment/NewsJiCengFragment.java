package com.example.smartcity.fragment;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.smartcity.R;

/**
 * @PackageName: com.example.smartcity.fragment
 * @ClassName: NewsJiCengFragment
 * @Author: winwa
 * @Date: 2023/1/2 10:42
 * @Description:
 **/
public class NewsJiCengFragment extends BaseFragment {

    private static final String TAG = "NewsJiCengFragment";

    @Override
    public View initView() {
        Log.i(TAG, "initView: ");
        View view = View.inflate(getContext(), R.layout.fragment_news_jiceng, null);
        TextView textView = view.findViewById(R.id.text_view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        Log.i(TAG, "initData: ");
    }
}
