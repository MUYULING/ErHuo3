package com.erhuo.entity;

/**
 * Created by Gary on 2017/12/5.
 */

public class UserMe {
    private String userName;
    private String nickName;
    private String imageID;
    private GENDER gender;

    public UserMe(String userName, String nickName, String imageID, String gender) {
        this.userName = userName;
        this.nickName = nickName;
        this.imageID = imageID;
        this.gender = GENDER.valueOf(gender);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getImageID() {
        return imageID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    public GENDER getGender() {
        return gender;
    }

    public void setGender(GENDER gender) {
        this.gender = gender;
    }
}
