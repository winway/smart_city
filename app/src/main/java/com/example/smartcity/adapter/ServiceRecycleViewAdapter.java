package com.example.smartcity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smartcity.R;
import com.example.smartcity.bean.Service;

import java.util.List;

/**
 * @PackageName: com.example.smartcity.adapter
 * @ClassName: ServiceRecycleViewAdapter
 * @Author: winwa
 * @Date: 2023/1/4 8:26
 * @Description:
 **/
public class ServiceRecycleViewAdapter extends RecyclerView.Adapter<ServiceRecycleViewAdapter.ServiceItemViewHolder> {
    private LayoutInflater mLayoutInflater;
    private List<Service.ServiceItem> mServiceItems;
    private Context mContext;
    private ServiceItemClickListener mServiceItemClickListener;

    public ServiceRecycleViewAdapter(List<Service.ServiceItem> serviceItems, Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
        mServiceItems = serviceItems;
    }

    public void setItemClickListener(ServiceItemClickListener listener) {
        mServiceItemClickListener = listener;
    }

    @NonNull
    @Override
    public ServiceItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.list_item_service, parent, false);
        ServiceItemViewHolder viewHolder = new ServiceItemViewHolder(view, mServiceItemClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceItemViewHolder holder, int position) {
        holder.mServiceNameTextView.setText(mServiceItems.get(position).getServiceName());
        String url = mServiceItems.get(position).getImgUrl();
        Glide.with(mContext).load(url).into(holder.mServiceImageView);
    }

    @Override
    public int getItemCount() {
        return mServiceItems.size();
    }

    public interface ServiceItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ServiceItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mServiceNameTextView;
        private ImageView mServiceImageView;
        private ServiceItemClickListener mServiceItemClickListener;

        public ServiceItemViewHolder(@NonNull View itemView, ServiceItemClickListener serviceItemClickListener) {
            super(itemView);
            mServiceItemClickListener = serviceItemClickListener;
            itemView.setOnClickListener(this);
            mServiceNameTextView = itemView.findViewById(R.id.service_name_text_view);
            mServiceImageView = itemView.findViewById(R.id.service_image_view);
        }

        @Override
        public void onClick(View view) {
            if (mServiceItemClickListener != null) {
                mServiceItemClickListener.onItemClick(view, getPosition());
            }
        }
    }
}
