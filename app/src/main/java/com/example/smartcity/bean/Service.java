package com.example.smartcity.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @PackageName: com.example.smartcity.bean
 * @ClassName: Service
 * @Author: winwa
 * @Date: 2023/1/4 8:18
 * @Description:
 **/
public class Service {

    private int total;
    private int code;
    private String msg;
    @SerializedName("service_items")
    private List<Service.ServiceItem> serviceItems;

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

    public List<Service.ServiceItem> getServiceItems() {
        return serviceItems;
    }

    public void setServiceItems(List<Service.ServiceItem> serviceItems) {
        this.serviceItems = serviceItems;
    }

    @Override
    public String toString() {
        return "Service{" +
                "total=" + total +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", serviceItems=" + serviceItems +
                '}';
    }

    public static class ServiceItem {
        private Object searchValue;
        private Object createBy;
        @SerializedName("create_time")
        private String createTime;
        private Object updateBy;
        private String updateTime;
        private Object remark;
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

        public ServiceItem(String serviceName, String serviceDesc, String imgUrl, String link) {
            this.serviceName = serviceName;
            this.serviceDesc = serviceDesc;
            this.imgUrl = imgUrl;
            this.link = link;
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
            return "ServiceItem{" +
                    "searchValue=" + searchValue +
                    ", createBy=" + createBy +
                    ", createTime='" + createTime + '\'' +
                    ", updateBy=" + updateBy +
                    ", updateTime='" + updateTime + '\'' +
                    ", remark=" + remark +
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
