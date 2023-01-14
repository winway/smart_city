package com.example.smartcity.activity;

import static com.example.smartcity.utils.APIConfig.UPDATE_PWD_URL;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcity.R;
import com.example.smartcity.bean.LoginResponse;
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

public class UpdatePasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "UpdatePasswordActivity";

    private ImageButton mBackImageButton;
    private EditText mUseridEditText;
    private EditText mOldPwdEditText;
    private EditText mNewPwdEditText;
    private Button mUpdatePwdButton;
    private Button mClearButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        initView();
    }

    private void initView() {
        mBackImageButton = findViewById(R.id.back_image_button);
        mUseridEditText = findViewById(R.id.userid_edit_text);
        mOldPwdEditText = findViewById(R.id.old_pwd_edit_text);
        mNewPwdEditText = findViewById(R.id.new_pwd_edit_text);
        mUpdatePwdButton = findViewById(R.id.update_password_button);
        mClearButton = findViewById(R.id.clear_button);

        mBackImageButton.setOnClickListener(this);
        mUpdatePwdButton.setOnClickListener(this);
        mClearButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_image_button:
                finish();
                break;
            case R.id.update_password_button:
                update();
                break;
            case R.id.clear_button:
                mUseridEditText.setText("");
                mOldPwdEditText.setText("");
                mNewPwdEditText.setText("");
                break;
        }
    }

    private void update() {
        String userid = mUseridEditText.getText().toString().trim();
        if (TextUtils.isEmpty(userid)) {
            Toast.makeText(this, "请输入你的用户编号", Toast.LENGTH_SHORT).show();
            return;
        }

        String oldPwd = mOldPwdEditText.getText().toString().trim();
        if (TextUtils.isEmpty(oldPwd)) {
            Toast.makeText(this, "请输入你的原始密码", Toast.LENGTH_SHORT).show();
            return;
        }

        String newPwd = mNewPwdEditText.getText().toString().trim();
        if (TextUtils.isEmpty(oldPwd)) {
            Toast.makeText(this, "请输入你的新密码", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences sharedPreferences = this.getSharedPreferences("token", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userid", userid);
            jsonObject.put("oldpwd", oldPwd);
            jsonObject.put("newpwd", newPwd);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
        RequestBody requestBody = RequestBody.create(mediaType, jsonObject.toString());
        Request request = new Request.Builder()
                .post(requestBody)
                .url(UPDATE_PWD_URL)
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
                    Gson gson = new Gson();
                    LoginResponse loginResponse = gson.fromJson(result, LoginResponse.class);
                    String code = loginResponse.getCode();
                    if (code.equals("200")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(UpdatePasswordActivity.this, "密码更新成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
    }
}