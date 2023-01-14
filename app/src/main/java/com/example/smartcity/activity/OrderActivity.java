package com.example.smartcity.activity;

import static com.example.smartcity.utils.APIConfig.ORDER_URL;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.smartcity.R;
import com.example.smartcity.bean.Order;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OrderActivity extends AppCompatActivity {

    private static final String TAG = "OrderActivity";

    private Toolbar mToolbar;
    private ListView mListView;

    private Order mOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        mToolbar = findViewById(R.id.tool_bar);
        mListView = findViewById(R.id.order_list_view);

        getOrderData();
    }

    private void getOrderData() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("token", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
//
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("userid", 111);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
        OkHttpClient client = new OkHttpClient();
//
//        MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
//        RequestBody requestBody = RequestBody.create(mediaType, jsonObject.toString());
        Request request = new Request.Builder()
                .url(ORDER_URL)
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
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Gson gson = new Gson();
                            mOrder = gson.fromJson(result, Order.class);
                            OrderListAdapter adapter = new OrderListAdapter();
                            mListView.setAdapter(adapter);
                            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    Toast.makeText(OrderActivity.this, "Clicked " + i, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mToolbar.setTitle("订单列表");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.mipmap.top_bar_left_back);
        mToolbar.setTitleMarginEnd(200);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public class OrderListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mOrder.getOrderItems().size();
        }

        @Override
        public Object getItem(int i) {
            return mOrder.getOrderItems().get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (view == null) {
                view = View.inflate(getApplicationContext(), R.layout.list_item_order, null);
                viewHolder = new ViewHolder();
                viewHolder.mOrderNumTextView = view.findViewById(R.id.order_num_text_view);
                viewHolder.mOrderDateTextView = view.findViewById(R.id.order_date_text_view);
                viewHolder.mOrderTypeTextView = view.findViewById(R.id.order_type_text_view);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            viewHolder.mOrderNumTextView.setText(mOrder.getOrderItems().get(i).getOrderNum());
            viewHolder.mOrderDateTextView.setText(mOrder.getOrderItems().get(i).getCreateTime());
            viewHolder.mOrderTypeTextView.setText("地铁");

            return view;
        }

        class ViewHolder {
            TextView mOrderNumTextView;
            TextView mOrderDateTextView;
            TextView mOrderTypeTextView;
        }
    }
}