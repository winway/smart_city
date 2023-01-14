package com.example.smartcity.activity;

import static com.example.smartcity.utils.APIConfig.ADD_COMMENT_URL;
import static com.example.smartcity.utils.APIConfig.NEWS_COMMENT_URL;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.smartcity.R;
import com.example.smartcity.bean.NewsComment;
import com.example.smartcity.bean.NewsResponse;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @PackageName: com.example.smartcity.activity
 * @ClassName: NewsDetailActivity
 * @Author: winwa
 * @Date: 2023/1/3 8:45
 * @Description:
 **/
public class NewsDetailActivity extends AppCompatActivity {

    private static final String TAG = "NewsDetailActivity";

    private Toolbar mToolbar;
    private TextView mNewsDetailTitleTextView;
    private TextView mNewsTitleTextView;
    private TextView mNewsDateTextView;
    private ImageView mNewsImageView;
    private TextView mNewsContentTextView;
    private Toolbar mCommentToolbar;
    private EditText mCommentContentEditText;
    private Button mCommentButton;
    private ListView mCommentListView;

    private CommentListAdapter mCommentListAdapter;
    private NewsComment mNewsComment;

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        CommentListAdapter commentListAdapter = (CommentListAdapter) listView.getAdapter();
        if (commentListAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < commentListAdapter.getCount(); i++) {
            View v = commentListAdapter.getView(i, null, listView);
            v.measure(0, 0);
            totalHeight += v.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (commentListAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        initView();
        initData();
        getNewsComment();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.news_detail_toolbar);
        mNewsDetailTitleTextView = findViewById(R.id.news_detail_title_text_view);
        mNewsTitleTextView = (TextView) findViewById(R.id.news_title_text_view);
        mNewsDateTextView = (TextView) findViewById(R.id.news_date_text_view);
        mNewsImageView = (ImageView) findViewById(R.id.news_image_view);
        mNewsContentTextView = (TextView) findViewById(R.id.news_content_text_view);
        mCommentToolbar = (Toolbar) findViewById(R.id.news_comment_toolbar);
        mCommentContentEditText = findViewById(R.id.news_comment_content_edit_text);
        mCommentButton = findViewById(R.id.news_comment_button);
        mCommentListView = findViewById(R.id.news_comment_list_view);
        mCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendComment();
            }
        });
    }

    public void sendComment() {
        String comment = mCommentContentEditText.getText().toString().trim();
        SharedPreferences sharedPreferences = this.getSharedPreferences("token", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", 1);
            jsonObject.put("comment", comment);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
        RequestBody requestBody = RequestBody.create(mediaType, jsonObject.toString());
        Request request = new Request.Builder()
                .url(ADD_COMMENT_URL)
                .post(requestBody)
                .addHeader("Authorization", token)
                .build();

        OkHttpClient client = new OkHttpClient();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    Gson gson = new Gson();
                    NewsResponse newsResponse = gson.fromJson(result, NewsResponse.class);
                    String code = newsResponse.getCode();
                    if (code.equals("200")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(NewsDetailActivity.this, "评论成功: " + result, Toast.LENGTH_SHORT).show();
                                mCommentListView.setAdapter(mCommentListAdapter);
                                mCommentListView.deferNotifyDataSetChanged();
                            }
                        });
                    }
                }
            }
        });
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent != null && intent.getParcelableExtra("bitmap") != null) {
            String title = intent.getStringExtra("title");
            String content = intent.getStringExtra("content");
            String time = intent.getStringExtra("time");
            Bitmap bitmap = getIntent().getParcelableExtra("bitmap");
            mNewsImageView.setImageBitmap(bitmap);
            mNewsTitleTextView.setText(title);
            mNewsDateTextView.setText(time);
            mNewsContentTextView.setText(content);

            mToolbar.setNavigationIcon(R.mipmap.top_bar_left_back);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    public void getNewsComment() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(NEWS_COMMENT_URL)
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
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Gson gson = new Gson();
                            mNewsComment = gson.fromJson(result, NewsComment.class);
                            mCommentListAdapter = new CommentListAdapter();
                            mCommentListView.setAdapter(mCommentListAdapter);

                            setListViewHeightBasedOnChildren(mCommentListView);

                            mCommentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    Toast.makeText(getApplication(), "Click " + i, Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    });
                }
            }
        });
    }

    public class CommentListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mNewsComment.getNewsCommentItems().size();
        }

        @Override
        public Object getItem(int i) {
            return mNewsComment.getNewsCommentItems().get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if (view == null) {
                view = View.inflate(getApplicationContext(), R.layout.list_item_news_comment, null);
                holder = new ViewHolder();
                holder.commentTime = view.findViewById(R.id.comment_time_text_view);
                holder.commentNickname = view.findViewById(R.id.comment_nickname_text_view);
                holder.commentContent = view.findViewById(R.id.comment_content_text_view);
                holder.commentImg = view.findViewById(R.id.comment_icon_image_view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            holder.commentContent.setText(mNewsComment.getNewsCommentItems().get(i).getContent());
            holder.commentNickname.setText(mNewsComment.getNewsCommentItems().get(i).getNickName());
            holder.commentTime.setText(mNewsComment.getNewsCommentItems().get(i).getCreateTime());
            String url = mNewsComment.getNewsCommentItems().get(i).getAvatar();
            Glide.with(getApplicationContext()).load(url).into(holder.commentImg);
            return view;
        }

        class ViewHolder {
            TextView commentTime;
            TextView commentNickname;
            TextView commentContent;
            ImageView commentImg;
        }
    }
}
