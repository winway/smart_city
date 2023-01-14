package com.example.smartcity.activity;

import static com.example.smartcity.utils.APIConfig.ADVISE_URL;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcity.R;

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

public class AdviceActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "AdviceActivity";

    private static final int MSG_ADVICE = 0;

    private ImageButton mBackImageButton;
    private EditText mAdviceEditText;
    private Button mSubmitAdviceButton;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_ADVICE) {
                Toast.makeText(AdviceActivity.this, "反馈成功：" + msg.obj.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advise);

        initView();
    }

    private void initView() {
        mBackImageButton = findViewById(R.id.back_image_button);
        mAdviceEditText = findViewById(R.id.advice_edit_text);
        mSubmitAdviceButton = findViewById(R.id.submit_advice_button);

        mBackImageButton.setOnClickListener(this);
        mSubmitAdviceButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_image_button:
                finish();
                break;
            case R.id.submit_advice_button:
                sendAdvice();
                break;
        }
    }

    private void sendAdvice() {
        String advice = mAdviceEditText.getText().toString().trim();
        if (TextUtils.isEmpty(advice)) {
            Toast.makeText(this, "内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences sharedPreferences = this.getSharedPreferences("token", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("content", advice);
            jsonObject.put("userid", 8888);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
        RequestBody requestBody = RequestBody.create(mediaType, jsonObject.toString());
        Request request = new Request.Builder()
                .post(requestBody)
                .url(ADVISE_URL)
                .addHeader("Authorization", token)
                .build();
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
                    Message message = new Message();
                    message.what = MSG_ADVICE;
                    message.obj = result;
                    mHandler.sendMessage(message);
                }
            }
        });
    }
}