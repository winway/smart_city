package com.example.smartcity.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.smartcity.R;
import com.example.smartcity.bean.News;

import java.util.List;

/**
 * @PackageName: com.example.smartcity.adapter
 * @ClassName: NewsListAdapter
 * @Author: winwa
 * @Date: 2023/1/6 8:28
 * @Description:
 **/
public class NewsListAdapter extends BaseAdapter {
    private List<News.NewsItem> mNewsItems;
    private Context mContext;

    public NewsListAdapter(List<News.NewsItem> newsItems, Context context) {
        mNewsItems = newsItems;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mNewsItems != null ? mNewsItems.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return mNewsItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = View.inflate(mContext, R.layout.list_item_news, null);
            holder = new ViewHolder();
            holder.newsTitle = view.findViewById(R.id.news_title_text_view);
            holder.newsContent = view.findViewById(R.id.news_content_text_view);
            holder.newsCreateTime = view.findViewById(R.id.news_date_text_view);
            holder.likeNumber = view.findViewById(R.id.like_number_text_view);
            holder.viewNumber = view.findViewById(R.id.view_number_text_view);
            holder.newsImage = view.findViewById(R.id.news_image_view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.likeNumber.setText(mNewsItems.get(i).getLikeNumber() + "");
        holder.viewNumber.setText(mNewsItems.get(i).getViewsNumber() + "");
        holder.newsCreateTime.setText(mNewsItems.get(i).getCreateTime());
        holder.newsContent.setText(mNewsItems.get(i).getContent());
        holder.newsTitle.setText(mNewsItems.get(i).getTitle());
        String url = mNewsItems.get(i).getImgUrl();
        Glide.with(mContext).load(url).into(holder.newsImage);
        return view;
    }

    class ViewHolder {
        TextView newsTitle;
        TextView newsContent;
        TextView newsCreateTime;
        ImageView newsImage;
        TextView viewNumber;
        TextView likeNumber;
    }
}
