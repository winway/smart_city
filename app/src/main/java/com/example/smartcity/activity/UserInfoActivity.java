package com.example.smartcity.activity;

import static com.example.smartcity.utils.APIConfig.UPDATE_INFO_URL;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "UserInfoActivity";

    private ImageButton mBackImageButton;
    private ImageView mAvatarImageView;
    private EditText mNicknameEditText;
    private EditText mPhoneEditText;
    private EditText mEmailEditText;
    private EditText mIdentityEditText;
    private RadioButton mManRadioButton;
    private RadioButton mWomanRadioButton;
    private RadioGroup mSexRadioGroup;
    private Button mUpdateButton;
    private Button mClearButton;

    private String sex = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        initView();
    }

    private void initView() {
        mBackImageButton = findViewById(R.id.back_image_button);
        mAvatarImageView = findViewById(R.id.my_avatar_image_view);
        mNicknameEditText = findViewById(R.id.nickname_edit_text);
        mPhoneEditText = findViewById(R.id.phone_edit_text);
        mEmailEditText = findViewById(R.id.email_edit_text);
        mIdentityEditText = findViewById(R.id.identity_edit_text);
        mManRadioButton = findViewById(R.id.sex_man_radio_button);
        mWomanRadioButton = findViewById(R.id.sex_woman_radio_button);
        mSexRadioGroup = findViewById(R.id.sex_radio_group);
        mUpdateButton = findViewById(R.id.update_button);
        mClearButton = findViewById(R.id.clear_button);

        mBackImageButton.setOnClickListener(this);
        mUpdateButton.setOnClickListener(this);
        mClearButton.setOnClickListener(this);
        mSexRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (mManRadioButton.isChecked()) {
                    sex = "1";
                } else {
                    sex = "0";
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_image_button:
                finish();
                break;
            case R.id.update_button:
                updateInfo();
                break;
            case R.id.clear_button:
                mNicknameEditText.setText("");
                mPhoneEditText.setText("");
                mEmailEditText.setText("");
                mIdentityEditText.setText("");
                sex = "";
                break;
        }
    }

    private void updateInfo() {
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

        String email = mEmailEditText.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "请输入邮箱地址", Toast.LENGTH_SHORT).show();
            return;
        }

        String identity = mIdentityEditText.getText().toString().trim();
        if (TextUtils.isEmpty(identity)) {
            Toast.makeText(this, "请输入身份信息", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(sex)) {
            Toast.makeText(this, "请输入性别", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences sharedPreferences = this.getSharedPreferences("token", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        String username = sharedPreferences.getString("username", "");

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userid", 111);
            jsonObject.put("username", username);
            jsonObject.put("nickname", nickname);
            jsonObject.put("phone", phone);
            jsonObject.put("email", email);
            jsonObject.put("identity", identity);
            jsonObject.put("sex", sex);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
        RequestBody requestBody = RequestBody.create(mediaType, jsonObject.toString());
        Request request = new Request.Builder()
                .post(requestBody)
                .url(UPDATE_INFO_URL)
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
                                Toast.makeText(UserInfoActivity.this, "用户信息修改成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
    }
}