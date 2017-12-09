package com.erhuo.entity;

/**
 * Created by Gary on 2017/12/9.
 */

public class Record {
    private int recordID;
    private String buyerName;
    private String buyerNickName;
    private String sellerName;
    private String sellerNickName;
    private String comName;
    private int comID;
    private String recordState;
    private String recordTime;

    public Record(int recordID, String buyerName, String buyerNickName, String sellerName, String sellerNickName, String comName, int comID, String recordState, String recordTime) {
        this.recordID = recordID;
        this.buyerName = buyerName;
        this.buyerNickName = buyerNickName;
        this.sellerName = sellerName;
        this.sellerNickName = sellerNickName;
        this.comName = comName;
        this.comID = comID;
        this.recordState = recordState;
        this.recordTime = recordTime;
    }

    public int getRecordID() {
        return recordID;
    }

    public void setRecordID(int recordID) {
        this.recordID = recordID;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerNickName() {
        return buyerNickName;
    }

    public void setBuyerNickName(String buyerNickName) {
        this.buyerNickName = buyerNickName;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerNickName() {
        return sellerNickName;
    }

    public void setSellerNickName(String sellerNickName) {
        this.sellerNickName = sellerNickName;
    }

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public int getComID() {
        return comID;
    }

    public void setComID(int comID) {
        this.comID = comID;
    }

    public String getRecordState() {
        return recordState;
    }

    public void setRecordState(String recordState) {
        this.recordState = recordState;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }
}
