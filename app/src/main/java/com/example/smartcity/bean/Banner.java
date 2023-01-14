package com.example.smartcity.bean;

import java.util.List;

/**
 * @PackageName: com.example.smartcity.bean
 * @ClassName: Banner
 * @Author: winwa
 * @Date: 2022/12/18 9:54
 * @Description:
 **/
public class Banner {

    private int total;
    private int code;
    private String msg;
    private List<BannerItem> rows;

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

    public List<BannerItem> getRows() {
        return rows;
    }

    public void setRows(List<BannerItem> rows) {
        this.rows = rows;
    }

    public static class BannerItem {
        private int id;
        private String imgUrl;
        private String type;
        private String createTime;
        private String sort;
        private String display;

        public BannerItem(String imgUrl, String type) {
            this.imgUrl = imgUrl;
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getDisplay() {
            return display;
        }

        public void setDisplay(String display) {
            this.display = display;
        }

        @Override
        public String toString() {
            return "BannerItem{" +
                    "id=" + id +
                    ", imgUrl='" + imgUrl + '\'' +
                    ", type='" + type + '\'' +
                    ", createTime='" + createTime + '\'' +
                    ", sort='" + sort + '\'' +
                    ", display='" + display + '\'' +
                    '}';
        }
    }
}
