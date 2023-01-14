package com.example.smartcity.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @PackageName: com.example.smartcity.bean
 * @ClassName: NewsComment
 * @Author: winwa
 * @Date: 2023/1/2 14:09
 * @Description:
 **/
public class NewsComment {

    private int total;
    private int code;
    private String msg;
    @SerializedName("news_comment_items")
    private List<NewsCommentItem> newsCommentItems;

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

    public List<NewsCommentItem> getNewsCommentItems() {
        return newsCommentItems;
    }

    public void setNewsCommentItems(List<NewsCommentItem> newsCommentItems) {
        this.newsCommentItems = newsCommentItems;
    }

    public static class NewsCommentItem {
        @SerializedName("create_time")
        private String createTime;
        private String content;
        @SerializedName("nickname")
        private String nickName;
        @SerializedName("username")
        private String userName;
        private String avatar;

        public NewsCommentItem(String createTime, String content, String nickName, String userName, String avatar) {
            this.createTime = createTime;
            this.content = content;
            this.nickName = nickName;
            this.userName = userName;
            this.avatar = avatar;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        @Override
        public String toString() {
            return "NewsCommentItem{" +
                    "createTime='" + createTime + '\'' +
                    ", content='" + content + '\'' +
                    ", nickName='" + nickName + '\'' +
                    ", userName='" + userName + '\'' +
                    ", avatar='" + avatar + '\'' +
                    '}';
        }
    }
}
