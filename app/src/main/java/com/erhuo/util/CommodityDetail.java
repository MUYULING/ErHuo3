package com.erhuo.util;

/**
 * Created by Gary on 2017/12/3.
 */

public class CommodityDetail {
    private String userName;       //用户登录名
    private String commodityName;  //商品名称
    private int commodityId;       //商品id
    private double price;           //商品价格
    private TAG tag;                //商品标签
    private String description;     //商品描述
    private String imageID;            //图片ID
    private String upTime;           //上架时间
    private String downTime;         //下架时间
    private int detailId;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    private String state;       //商品状态

    public int getDetailId() {
        return detailId;
    }

    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }

    public CommodityDetail(String userName, String commodityName, int commodityId, double price, String tag, String description, String imageID, String upTime, String downTime, int detailId, String state) {
        this.userName = userName;
        this.commodityName = commodityName;
        this.commodityId = commodityId;
        this.price = price;
        this.tag = TAG.valueOf(tag);
        this.description = description;
        this.imageID = imageID;
        this.upTime = upTime;
        this.downTime = downTime;
        this.detailId = detailId;
        this.state = state;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public int getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(int commodityId) {
        this.commodityId = commodityId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public TAG getTag() {
        return tag;
    }

    public void setTag(TAG tag) {
        this.tag = tag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageID() {
        return imageID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    public String getUpTime() {
        return upTime;
    }

    public void setUpTime(String upTime) {
        this.upTime = upTime;
    }

    public String getDownTime() {
        return downTime;
    }

    public void setDownTime(String downTime) {
        this.downTime = downTime;
    }
}
