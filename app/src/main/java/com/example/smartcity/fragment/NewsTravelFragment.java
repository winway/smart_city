package com.example.smartcity.fragment;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.smartcity.R;

/**
 * @PackageName: com.example.smartcity.fragment
 * @ClassName: NewsTravelFragment
 * @Author: winwa
 * @Date: 2023/1/2 11:00
 * @Description:
 **/
public class NewsTravelFragment extends BaseFragment {
    private static final String TAG = "NewsTravelFragment";

    @Override
    public View initView() {
        Log.i(TAG, "initView: ");
        View view = View.inflate(getContext(), R.layout.fragment_news_travel, null);
        TextView textView = view.findViewById(R.id.text_view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        Log.i(TAG, "initData: ");
    }
}
