package com.example.smartcity.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @PackageName: com.example.smartcity.bean
 * @ClassName: News
 * @Author: winwa
 * @Date: 2023/1/2 14:19
 * @Description:
 **/
public class News {

    private int total;
    private int code;
    private String msg;
    @SerializedName("news_items")
    private List<NewsItem> newsItems;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<NewsItem> getNewsItems() {
        return newsItems;
    }

    public void setNewsItems(List<NewsItem> newsItems) {
        this.newsItems = newsItems;
    }

    @Override
    public String toString() {
        return "News{" +
                "total=" + total +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", newsItems=" + newsItems.toString() +
                '}';
    }

    public static class NewsItem {
        private Object searchValue;
        private Object createBy;
        @SerializedName("create_time")
        private String createTime;
        private Object updateBy;
        private String updateTime;
        private Object remark;
        private int id;
        private String title;
        private String content;
        @SerializedName("img_url")
        private String imgUrl;
        private String pressCategory;
        private int isRecommend;
        @SerializedName("like_number")
        private int likeNumber;
        @SerializedName("views_number")
        private int viewsNumber;
        private int userId;
        private int pressStatus;

        public NewsItem(String createTime, String title, String content, String imgUrl, String pressCategory, int likeNumber, int viewsNumber) {
            this.createTime = createTime;
            this.title = title;
            this.content = content;
            this.imgUrl = imgUrl;
            this.pressCategory = pressCategory;
            this.likeNumber = likeNumber;
            this.viewsNumber = viewsNumber;
        }

        public Object getSearchValue() {
            return searchValue;
        }

        public void setSearchValue(Object searchValue) {
            this.searchValue = searchValue;
        }

        public Object getCreateBy() {
            return createBy;
        }

        public void setCreateBy(Object createBy) {
            this.createBy = createBy;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public Object getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(Object updateBy) {
            this.updateBy = updateBy;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getPressCategory() {
            return pressCategory;
        }

        public void setPressCategory(String pressCategory) {
            this.pressCategory = pressCategory;
        }

        public int getIsRecommend() {
            return isRecommend;
        }

        public void setIsRecommend(int isRecommend) {
            this.isRecommend = isRecommend;
        }

        public int getLikeNumber() {
            return likeNumber;
        }

        public void setLikeNumber(int likeNumber) {
            this.likeNumber = likeNumber;
        }

        public int getViewsNumber() {
            return viewsNumber;
        }

        public void setViewsNumber(int viewsNumber) {
            this.viewsNumber = viewsNumber;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getPressStatus() {
            return pressStatus;
        }

        public void setPressStatus(int pressStatus) {
            this.pressStatus = pressStatus;
        }

        @Override
        public String toString() {
            return "NewsItem{" +
                    "searchValue=" + searchValue +
                    ", createBy=" + createBy +
                    ", createTime='" + createTime + '\'' +
                    ", updateBy=" + updateBy +
                    ", updateTime='" + updateTime + '\'' +
                    ", remark=" + remark +
                    ", id=" + id +
                    ", title='" + title + '\'' +
                    ", content='" + content + '\'' +
                    ", imgUrl='" + imgUrl + '\'' +
                    ", pressCategory='" + pressCategory + '\'' +
                    ", isRecommend=" + isRecommend +
                    ", likeNumber=" + likeNumber +
                    ", viewsNumber=" + viewsNumber +
                    ", userId=" + userId +
                    ", pressStatus=" + pressStatus +
                    '}';
        }
    }
}
