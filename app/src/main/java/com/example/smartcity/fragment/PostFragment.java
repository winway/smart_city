package com.example.smartcity.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;

import com.example.smartcity.R;
import com.example.smartcity.activity.TakePhotoActivity;

/**
 * @PackageName: com.example.smartcity.fragment
 * @ClassName: PostFragment
 * @Author: winwa
 * @Date: 2022/12/17 11:45
 * @Description:
 **/
public class PostFragment extends BaseFragment {

    private static final String TAG = "PostFragment";

    private Button mPhotoButton;

    @Override
    public View initView() {
        View view = View.inflate(getActivity(), R.layout.fragment_post, null);
        mPhotoButton = view.findViewById(R.id.photo_button);
        return view;
    }

    @Override
    public void initData() {
        super.initData();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            builder.detectFileUriExposure();
        }

        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TakePhotoActivity.class);
                startActivity(intent);
            }
        });
    }
}
