package com.erhuo.util;

/**
 * Created by Gary on 2017/12/2.
 */

enum GENDER{male, female}

public class UserHome {

    private String userName;    //用户名
    private String nickName;    //昵称
    private GENDER gender;      //性别
    private double mark;        //评分
    private String stuID;       //学号
    private String school;      //学校
    private String imageID;     //头像
    private String phone;       //电话
    private String QQ;          //QQ
    private String weChat;      //微信
    private String email;       //电子邮件

    public UserHome(String userName, String nickName, String gender, double mark, String stuID, String school, String imageID, String phone, String QQ, String weChat, String email) {
        this.userName = userName;
        this.nickName = nickName;
        this.gender = GENDER.valueOf(gender);
        this.mark = mark;
        this.stuID = stuID;
        this.school = school;
        this.imageID = imageID;
        this.phone = phone;
        this.QQ = QQ;
        this.weChat = weChat;
        this.email = email;
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

    public GENDER getGender() {
        return gender;
    }

    public void setGender(GENDER gender) {
        this.gender = gender;
    }

    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    public String getStuID() {
        return stuID;
    }

    public void setStuID(String stuID) {
        this.stuID = stuID;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getImageID() {
        return imageID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQQ() {
        return QQ;
    }

    public void setQQ(String QQ) {
        this.QQ = QQ;
    }

    public String getWeChat() {
        return weChat;
    }

    public void setWeChat(String weChat) {
        this.weChat = weChat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
