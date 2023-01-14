package com.example.smartcity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcity.MainActivity;
import com.example.smartcity.R;
import com.example.smartcity.bean.RegisterResponse;
import com.example.smartcity.utils.APIConfig;
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

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "RegisterActivity";

    private static final int MSG_REGISTER_RESPONSE = 1;

    private EditText mUsernameEditText;
    private EditText mNicknameEditText;
    private EditText mPhoneEditText;
    private EditText mPasswordEditText;
    private RadioGroup mSexRadioGroup;
    private RadioButton mSexManRadioButton;
    private RadioButton mSexWomanRadioButton;
    private Button mLoginButton;
    private Button mRegisterButton;

    private String mSex = "man";

    private OkHttpClient mOkHttpClient = new OkHttpClient();
    private Intent mIntent = null;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_REGISTER_RESPONSE) {
                String resultStr = (String) msg.obj;
                Log.i(TAG, "handleMessage: " + resultStr);
                final RegisterResponse response = new Gson().fromJson(resultStr, RegisterResponse.class);
                int resultCode = response.getCode();
                if (resultCode == 200) {
                    Toast.makeText(RegisterActivity.this, "注册成功：" + resultStr, Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "注册失败：" + resultStr, Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
    }

    private void initView() {
        mUsernameEditText = findViewById(R.id.username_edit_text);
        mNicknameEditText = findViewById(R.id.nickname_edit_text);
        mPhoneEditText = findViewById(R.id.phone_edit_text);
        mPasswordEditText = findViewById(R.id.password_edit_text);
        mSexRadioGroup = findViewById(R.id.sex_radio_group);
        mSexManRadioButton = findViewById(R.id.sex_man_radio_button);
        mSexWomanRadioButton = findViewById(R.id.sex_woman_radio_button);
        mLoginButton = findViewById(R.id.login_button);
        mRegisterButton = findViewById(R.id.register_button);

        mRegisterButton.setOnClickListener(this);
        mLoginButton.setOnClickListener(this);

        mSexRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == mSexManRadioButton.getId()) {
                    mSex = "man";
                } else {
                    mSex = "woman";
                }
            }
        });

        mSexManRadioButton.toggle();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_button:
                register();
                break;
            case R.id.login_button:
                mIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(mIntent);
                break;
        }
    }

    private void register() {
        String username = mUsernameEditText.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return;
        }

        String nickname = mNicknameEditText.getText().toString().trim();
        if (TextUtils.isEmpty(nickname)) {
            Toast.makeText(this, "请输入昵称", Toast.LENGTH_SHORT).show();
            return;
        }

        String phone = mPhoneEditText.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }

        String password = mPasswordEditText.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username);
            jsonObject.put("nickname", nickname);
            jsonObject.put("phone", phone);
            jsonObject.put("sex", mSex);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MediaType mediaType = MediaType.parse("application/json;charset=utf-8");

        final RequestBody requestBody = RequestBody.create(mediaType, jsonObject.toString());
        Request request = new Request.Builder()
                .post(requestBody)
                .url(APIConfig.REGISTER_URL)
                .build();

        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                Message msg = new Message();
                msg.what = MSG_REGISTER_RESPONSE;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        });
    }
}