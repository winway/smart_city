package com.example.smartcity.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @PackageName: com.example.smartcity.bean
 * @ClassName: Order
 * @Author: winwa
 * @Date: 2023/1/8 11:16
 * @Description:
 **/
public class Order {

    private int code;
    private String msg;
    @SerializedName("order_items")
    private List<OrderItem> orderItems;

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

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public String toString() {
        return "Order{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", orderItems=" + orderItems +
                '}';
    }

    public static class OrderItem {

        @SerializedName("order_num")
        private String orderNum;
        @SerializedName("create_time")
        private String createTime;
        private int id;
        private String path;
        private String start;
        private String end;
        private int price;
        private String username;
        private String phone;
        private int userid;
        private int status;

        public String getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(String orderNum) {
            this.orderNum = orderNum;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return "OrderItem{" +
                    "orderNum='" + orderNum + '\'' +
                    ", createTime='" + createTime + '\'' +
                    ", id=" + id +
                    ", path='" + path + '\'' +
                    ", start='" + start + '\'' +
                    ", end='" + end + '\'' +
                    ", price=" + price +
                    ", username='" + username + '\'' +
                    ", phone='" + phone + '\'' +
                    ", userid=" + userid +
                    ", status=" + status +
                    '}';
        }
    }
}
