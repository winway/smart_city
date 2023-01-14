package com.example.smartcity.fragment;

import static com.example.smartcity.utils.APIConfig.SERVICE_APPOINTMENT_URL;
import static com.example.smartcity.utils.APIConfig.SERVICE_BUS_URL;
import static com.example.smartcity.utils.APIConfig.SERVICE_CITY_SUBWAY_URL;
import static com.example.smartcity.utils.APIConfig.SERVICE_LIVING_PAY_URL;
import static com.example.smartcity.utils.APIConfig.SERVICE_PARK_URL;
import static com.example.smartcity.utils.APIConfig.SERVICE_URL;
import static com.example.smartcity.utils.APIConfig.SERVICE_WEI_ZHANG_URL;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcity.R;
import com.example.smartcity.activity.AppointmentActivity;
import com.example.smartcity.activity.BusActivity;
import com.example.smartcity.activity.CitySubwayActivity;
import com.example.smartcity.activity.LivingPayActivity;
import com.example.smartcity.activity.ParkActivity;
import com.example.smartcity.activity.WeiZhangActivity;
import com.example.smartcity.adapter.ServiceRecycleViewAdapter;
import com.example.smartcity.bean.Service;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @PackageName: com.example.smartcity.fragment
 * @ClassName: ServiceFragment
 * @Author: winwa
 * @Date: 2022/12/17 11:44
 * @Description:
 **/
public class ServiceFragment extends BaseFragment {

    private static final String TAG = "ServiceFragment";

    private static final int MSG_SERVICE = 0;

    private RecyclerView mServiceRecyclerView;
    private ServiceRecycleViewAdapter mServiceRecyclerViewAdapter;
    private List<Service.ServiceItem> mServiceItems;
    private EditText mSearchEditText;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_SERVICE) {
                Service service = (Service) msg.obj;
                mServiceItems = service.getServiceItems();
                Log.i(TAG, "handleMessage: " + mServiceItems.size());
                mServiceRecyclerViewAdapter = new ServiceRecycleViewAdapter(mServiceItems, getActivity());
                mServiceRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
                mServiceRecyclerView.setAdapter(mServiceRecyclerViewAdapter);

                mServiceRecyclerViewAdapter.setItemClickListener(new ServiceRecycleViewAdapter.ServiceItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String url = mServiceItems.get(position).getLink();
                        Intent intent = null;
                        if (position == 0) {
                            intent = new Intent(getActivity(), CitySubwayActivity.class);
                        } else if (position == 1) {
                            intent = new Intent(getActivity(), BusActivity.class);
                        } else if (position == 2) {
                            intent = new Intent(getActivity(), AppointmentActivity.class);
                        } else if (position == 3) {
                            intent = new Intent(getActivity(), LivingPayActivity.class);
                        } else if (position == 4) {
                            intent = new Intent(getActivity(), WeiZhangActivity.class);
                        } else if (position == 5) {
                            intent = new Intent(getActivity(), ParkActivity.class);
                        }

                        Bundle bundle = new Bundle();
                        bundle.putString("title", mServiceItems.get(position).getServiceName());
                        bundle.putString("url", url);
                        intent.putExtras(bundle);
                        getActivity().startActivity(intent);
                    }
                });
            }
        }
    };

    @Override

    public View initView() {
        View view = View.inflate(getActivity(), R.layout.fragment_service, null);
        mServiceRecyclerView = view.findViewById(R.id.service_recycler_view);
        mSearchEditText = view.findViewById(R.id.search_edit_text);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        getServiceData(SERVICE_URL);
        initSearchService();
    }

    public void getServiceData(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
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
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Gson gson = new Gson();
                            Service service = gson.fromJson(result, Service.class);
                            Message msg = new Message();
                            msg.what = MSG_SERVICE;
                            msg.obj = service;
                            mHandler.sendMessage(msg);
                        }
                    });
                }
            }
        });
    }

    private void initSearchService() {
        mSearchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    String query = mSearchEditText.getText().toString();
                    if (query.equals("城市地铁")) {
                        Log.i(TAG, "onEditorAction: 1");
                        getServiceData(SERVICE_CITY_SUBWAY_URL);
                    } else if (query.equals("智慧巴士")) {
                        getServiceData(SERVICE_BUS_URL);
                    } else if (query.equals("门诊预约")) {
                        getServiceData(SERVICE_APPOINTMENT_URL);
                    } else if (query.equals("停车场")) {
                        getServiceData(SERVICE_PARK_URL);
                    } else if (query.equals("生活缴费")) {
                        getServiceData(SERVICE_LIVING_PAY_URL);
                    } else if (query.equals("违章查询")) {
                        getServiceData(SERVICE_WEI_ZHANG_URL);
                    } else {
                        getServiceData(SERVICE_URL);
                    }
                }

                return false;
            }
        });
    }
}
