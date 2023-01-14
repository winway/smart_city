package com.example.smartcity.fragment;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.smartcity.R;

/**
 * @PackageName: com.example.smartcity.fragment
 * @ClassName: NewsTvFragment
 * @Author: winwa
 * @Date: 2023/1/2 11:02
 * @Description:
 **/
public class NewsTvFragment extends BaseFragment {
    private static final String TAG = "NewsTvFragment";

    @Override
    public View initView() {
        Log.i(TAG, "initView: ");
        View view = View.inflate(getContext(), R.layout.fragment_news_tv, null);
        TextView textView = view.findViewById(R.id.text_view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        Log.i(TAG, "initData: ");
    }
}
