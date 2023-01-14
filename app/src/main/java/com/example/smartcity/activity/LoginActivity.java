package com.example.smartcity.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcity.MainActivity;
import com.example.smartcity.R;
import com.example.smartcity.bean.LoginResponse;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";

    private static final int MSG_LOGIN_SUCCEED = 1;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_LOGIN_SUCCEED) {
                Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_LONG).show();

                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        }
    };

    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private Button mLoginButton;
    private Button mRegisterButton;

    private int mCode;
    private String mToken;
    private Intent mIntent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        mUsernameEditText = findViewById(R.id.username_edit_text);
        mPasswordEditText = findViewById(R.id.password_edit_text);
        mLoginButton = findViewById(R.id.login_button);
        mRegisterButton = findViewById(R.id.register_button);

        mLoginButton.setOnClickListener(this);
        mRegisterButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                login();
                break;
            case R.id.register_button:
                mIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(mIntent);
                break;
            default:
                break;
        }
    }

    private void login() {
        final String username = mUsernameEditText.getText().toString().trim();
        final String password = mPasswordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        OkHttpClient client = new OkHttpClient();

        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

        final RequestBody requestBody = RequestBody.create(mediaType, jsonObject.toString());

        Request request = new Request.Builder()
                .post(requestBody)
                .url(APIConfig.LOGIN_URL)
                .build();

        okhttp3.Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "onFailure: 请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Log.i(TAG, "onResponse: 请求成功");
                    final String responseBody = response.body().string();
                    Log.i(TAG, "responseBody: " + responseBody);

                    Gson gson = new Gson();
                    LoginResponse loginResponse = gson.fromJson(responseBody, LoginResponse.class);

                    String loginResultCode = loginResponse.getCode();
                    Log.i(TAG, "loginResultCode: " + loginResultCode);

                    if (loginResultCode.equals("200")) {
                        Log.i(TAG, "onResponse: 登陆成功");

                        mToken = loginResponse.getCode();

                        SharedPreferences.Editor editor = getSharedPreferences("token", MODE_PRIVATE).edit();
                        editor.putString("token", mToken);
                        editor.putString("username", username);
                        editor.putString("password", password);
                        editor.apply();

                        Message message = mHandler.obtainMessage(MSG_LOGIN_SUCCEED);
                        message.obj = mToken;
                        mHandler.sendMessage(message);
                    } else {
                        Log.i(TAG, "onResponse: 登陆失败");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
    }
}