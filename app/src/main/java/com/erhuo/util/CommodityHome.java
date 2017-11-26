package com.erhuo.util;

/**
 * Created by Gary on 2017/11/25.
 */

enum TAG{food, book, toiletry, drink, digital, clothes, cosmetic, bag, shoes};

public class CommodityHome {
    private String user_name;       //用户登录名
    private String commodity_name;  //商品名称
    private int commodity_id;       //商品id
    private double price;           //商品价格
    private TAG tag;                //商品标签
    private String description;     //商品描述
    private int imageID;            //图片ID

    public CommodityHome(String commodity_name, int imageID) {
        this.commodity_name = commodity_name;
        this.imageID = imageID;
    }

    public CommodityHome(String user_name, String commodity_name, int commodity_id, double price, TAG tag, String description, int imageID) {
        this.user_name = user_name;
        this.commodity_name = commodity_name;
        this.commodity_id = commodity_id;
        this.price = price;
        this.tag = tag;
        this.description = description;
        this.imageID = imageID;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setCommodity_name(String commodity_name) {
        this.commodity_name = commodity_name;
    }

    public void setCommodity_id(int commodity_id) {
        this.commodity_id = commodity_id;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setTag(TAG tag) {
        this.tag = tag;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getCommodity_name() {
        return commodity_name;
    }

    public int getCommodity_id() {
        return commodity_id;
    }

    public double getPrice() {
        return price;
    }

    public TAG getTag() {
        return tag;
    }

    public String getDescription() {
        return description;
    }

    public int getImageID() {
        return imageID;
    }
}
