package com.example.smartcity.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcity.MainActivity;
import com.example.smartcity.R;

public class SplashActivity extends AppCompatActivity {

    private LinearLayout mSplashLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mSplashLinearLayout = findViewById(R.id.splash_linear_layout);

        setAlphaAnimation();
    }

    /**
     * 设置渐变效果
     */
    private void setAlphaAnimation() {
        AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
        animation.setDuration(3000);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                jump2MainActivity();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mSplashLinearLayout.setAnimation(animation);
    }

    /**
     * 判断是否首次启动，依此跳转到主页面或指引页面
     */
    private void jump2MainActivity() {
        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        String isFirst = sharedPreferences.getString("is_first", "false");

        Intent intent = new Intent();
        if ("false".equals(isFirst)) {
            intent.setClass(this, GuideActivity.class);
        } else {
            intent.setClass(this, LoginActivity.class);
        }

        startActivity(intent);
        finish();
    }
}