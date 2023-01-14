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
import com.example.smartcity.bean.RecommendTheme;

import java.util.List;

/**
 * @PackageName: com.example.smartcity.adapter
 * @ClassName: RecommendThemeRecyclerViewAdapter
 * @Author: winwa
 * @Date: 2023/1/6 8:45
 * @Description:
 **/
public class RecommendThemeRecyclerViewAdapter extends RecyclerView.Adapter<RecommendThemeRecyclerViewAdapter.RecommendThemeItemViewHolder> {
    private LayoutInflater mLayoutInflater;
    private List<RecommendTheme.RecommendThemeItem> mRecommendThemeItems;
    private Context mContext;
    private RecommendThemeRecyclerViewAdapter.RecommendThemeItemClickListener mRecommendThemeItemClickListener;

    public RecommendThemeRecyclerViewAdapter(List<RecommendTheme.RecommendThemeItem> recommendThemeItems, Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
        mRecommendThemeItems = recommendThemeItems;
    }

    public void setItemClickListener(RecommendThemeRecyclerViewAdapter.RecommendThemeItemClickListener listener) {
        mRecommendThemeItemClickListener = listener;
    }

    @NonNull
    @Override
    public RecommendThemeRecyclerViewAdapter.RecommendThemeItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.list_item_theme, parent, false);
        RecommendThemeRecyclerViewAdapter.RecommendThemeItemViewHolder viewHolder = new RecommendThemeRecyclerViewAdapter.RecommendThemeItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendThemeRecyclerViewAdapter.RecommendThemeItemViewHolder holder, int position) {
        holder.mThemeNameTextView.setText(mRecommendThemeItems.get(position).getServiceName());
        holder.mThemeDescTextView.setText(mRecommendThemeItems.get(position).getServiceDesc());
        String url = mRecommendThemeItems.get(position).getImgUrl();
        Glide.with(mContext).load(url).into(holder.mThemeImageView);
    }

    @Override
    public int getItemCount() {
        return mRecommendThemeItems.size();
    }

    public interface RecommendThemeItemClickListener {
        void onItemClick(int position, List<RecommendTheme.RecommendThemeItem> items);
    }

    public class RecommendThemeItemViewHolder extends RecyclerView.ViewHolder {
        private TextView mThemeNameTextView;
        private TextView mThemeDescTextView;
        private ImageView mThemeImageView;

        public RecommendThemeItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mThemeNameTextView = itemView.findViewById(R.id.theme_name_text_view);
            mThemeDescTextView = itemView.findViewById(R.id.theme_desc_text_view);
            mThemeImageView = itemView.findViewById(R.id.theme_image_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mRecommendThemeItemClickListener != null) {
                        mRecommendThemeItemClickListener.onItemClick(getAdapterPosition(), mRecommendThemeItems);
                    }
                }
            });
        }
    }
}
