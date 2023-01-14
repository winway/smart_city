package com.example.smartcity.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.smartcity.MainActivity;
import com.example.smartcity.R;
import com.example.smartcity.adapter.GuideAdapter;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity {

    private final int[] mGuideImages = {R.drawable.guide_picture_1, R.drawable.guide_picture_2, R.drawable.guide_picture_3, R.drawable.guide_picture_4};
    private ViewPager mGuideViewPager;
    private List<ImageView> mGuideImageViews;
    private Button mGuideStartButton;
    private ImageView[] mGuideDotViews;
    private GuideAdapter mGuideAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        mGuideViewPager = findViewById(R.id.guide_view_pager);
        mGuideStartButton = findViewById(R.id.guide_start_button);

        initImages();

        initDots();

        mGuideAdapter = new GuideAdapter(mGuideImageViews);
        mGuideViewPager.setAdapter(mGuideAdapter);

        mGuideStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("is_first", "true");
                editor.apply();

                Intent intent = new Intent(GuideActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mGuideViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < mGuideDotViews.length; i++) {
                    if (position == i) {
                        mGuideDotViews[i].setImageResource(R.drawable.guide_dot_selected);
                    } else {
                        mGuideDotViews[i].setImageResource(R.drawable.guide_dot_unselected);
                    }

                    if (position == mGuideDotViews.length - 1) {
                        mGuideStartButton.setVisibility(View.VISIBLE);
                    } else {
                        mGuideStartButton.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initImages() {
        ViewPager.LayoutParams params = new ViewPager.LayoutParams();

        mGuideImageViews = new ArrayList<>();
        for (int guideImage : mGuideImages) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(params);
            imageView.setImageResource(guideImage);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            mGuideImageViews.add(imageView);
        }
    }

    private void initDots() {
        LinearLayout layout = findViewById(R.id.guide_linear_layout);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
        params.setMargins(10, 0, 10, 0);

        mGuideDotViews = new ImageView[mGuideImages.length];
        for (int i = 0; i < mGuideImages.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(params);
            if (i == 0) {
                imageView.setImageResource(R.drawable.guide_dot_unselected);
            } else {
                imageView.setImageResource(R.drawable.guide_dot_selected);
            }
            mGuideDotViews[i] = imageView;

            final int position = i;
            mGuideDotViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mGuideViewPager.setCurrentItem(position);
                }
            });

            layout.addView(imageView);
        }
    }
}