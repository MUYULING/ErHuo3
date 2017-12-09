package com.erhuo.entity;

/**
 * Created by msi on 2017/12/7.
 */

public class Notice {

    private String owner;
    private String ownerID;
    private String applier;
    private String applierID;
    private String com;
    private int comID;
    private String Date;

    public Notice(String owner, String ownerID, String applier, String applierID, String com, int comID, String date) {
        this.owner = owner;
        this.ownerID = ownerID;
        this.applier = applier;
        this.applierID = applierID;
        this.com = com;
        this.comID = comID;
        Date = date;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public String getApplier() {
        return applier;
    }

    public void setApplier(String applier) {
        this.applier = applier;
    }

    public String getApplierID() {
        return applierID;
    }

    public void setApplierID(String applierID) {
        this.applierID = applierID;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public int getComID() {
        return comID;
    }

    public void setComID(int comID) {
        this.comID = comID;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
