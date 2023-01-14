package com.example.smartcity.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.smartcity.R;
import com.example.smartcity.activity.AdviceActivity;
import com.example.smartcity.activity.LoginActivity;
import com.example.smartcity.activity.OrderActivity;
import com.example.smartcity.activity.UpdatePasswordActivity;
import com.example.smartcity.activity.UserInfoActivity;

/**
 * @PackageName: com.example.smartcity.fragment
 * @ClassName: UserFragment
 * @Author: winwa
 * @Date: 2022/12/17 11:46
 * @Description:
 **/
public class UserFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "UserFragment";

    private TextView mUserCenterTextView;
    private ImageButton mUserAvatarImageButton;
    private TextView mUsernameTextView;
    private Button mLogoutButton;
    private LinearLayout mUserInfoLinearLayout;
    private LinearLayout mOrderLinearLayout;
    private LinearLayout mUpdatePasswordLinearLayout;
    private LinearLayout mAdviceLinearLayout;
    private Toolbar mToolbar;

    private Intent mIntent = null;

    @Override
    public View initView() {
        View view = View.inflate(getActivity(), R.layout.fragment_user, null);
        mAdviceLinearLayout = view.findViewById(R.id.advice_linear_layout);
        mUserInfoLinearLayout = view.findViewById(R.id.user_info_linear_layout);
        mOrderLinearLayout = view.findViewById(R.id.order_linear_layout);
        mUpdatePasswordLinearLayout = view.findViewById(R.id.update_password_linear_layout);
        mLogoutButton = view.findViewById(R.id.logout_button);
        mUsernameTextView = view.findViewById(R.id.username_text_view);
        mUserAvatarImageButton = view.findViewById(R.id.user_avatar_image_button);
        mAdviceLinearLayout.setOnClickListener(this);
        mUserInfoLinearLayout.setOnClickListener(this);
        mOrderLinearLayout.setOnClickListener(this);
        mUpdatePasswordLinearLayout.setOnClickListener(this);
        mLogoutButton.setOnClickListener(this);
        mUserAvatarImageButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void initData() {
        super.initData();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("token", Context.MODE_PRIVATE);
        String user = sharedPreferences.getString("username", "");
        mUsernameTextView.setText("用户");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_info_linear_layout:
                mIntent = new Intent(getContext(), UserInfoActivity.class);
                break;
            case R.id.order_linear_layout:
                mIntent = new Intent(getContext(), OrderActivity.class);
                break;
            case R.id.update_password_linear_layout:
                mIntent = new Intent(getContext(), UpdatePasswordActivity.class);
                break;
            case R.id.advice_linear_layout:
                mIntent = new Intent(getContext(), AdviceActivity.class);
                break;
            case R.id.logout_button:
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("token", Context.MODE_PRIVATE).edit();
                editor.clear();
                editor.apply();
                mIntent = new Intent(getContext(), LoginActivity.class);
                mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                break;
            case R.id.user_avatar_image_button:
                break;
        }

        startActivity(mIntent);
    }
}
