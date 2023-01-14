package com.example.smartcity.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @PackageName: com.example.smartcity.bean
 * @ClassName: RecommendTheme
 * @Author: winwa
 * @Date: 2023/1/6 8:40
 * @Description:
 **/
public class RecommendTheme {

    private int total;
    private int code;
    private String msg;
    @SerializedName("recommend_theme_items")
    private List<RecommendThemeItem> recommendThemeItems;

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

    public List<RecommendThemeItem> getRecommendThemeItems() {
        return recommendThemeItems;
    }

    public void setRecommendThemeItems(List<RecommendThemeItem> recommendThemeItems) {
        this.recommendThemeItems = recommendThemeItems;
    }

    @Override
    public String toString() {
        return "RecommendTheme{" +
                "total=" + total +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", recommendThemeItems=" + recommendThemeItems +
                '}';
    }

    public static class RecommendThemeItem {
        @SerializedName("create_time")
        private String createTime;
        private String updateTime;
        private int id;
        @SerializedName("service_name")
        private String serviceName;
        @SerializedName("service_desc")
        private String serviceDesc;
        @SerializedName("service_type")
        private String serviceType;
        @SerializedName("img_url")
        private String imgUrl;
        private int pid;
        private int isRecommend;
        private String link;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public String getServiceDesc() {
            return serviceDesc;
        }

        public void setServiceDesc(String serviceDesc) {
            this.serviceDesc = serviceDesc;
        }

        public String getServiceType() {
            return serviceType;
        }

        public void setServiceType(String serviceType) {
            this.serviceType = serviceType;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public int getIsRecommend() {
            return isRecommend;
        }

        public void setIsRecommend(int isRecommend) {
            this.isRecommend = isRecommend;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        @Override
        public String toString() {
            return "RecommendThemeItem{" +
                    "createTime='" + createTime + '\'' +
                    ", updateTime='" + updateTime + '\'' +
                    ", id=" + id +
                    ", serviceName='" + serviceName + '\'' +
                    ", serviceDesc='" + serviceDesc + '\'' +
                    ", serviceType='" + serviceType + '\'' +
                    ", imgUrl='" + imgUrl + '\'' +
                    ", pid=" + pid +
                    ", isRecommend=" + isRecommend +
                    ", link='" + link + '\'' +
                    '}';
        }
    }
}
