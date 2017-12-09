package com.erhuo.entity;

/**
 * Created by Gary on 2017/12/7.
 */

public class User {

    private String userName;
    private String nickName;
    private String imageID;
    private GENDER gender;
    private String phone;
    private double mark;
    private String studentID;
    private String school;

    public User(String userName, String nickName, String imageID, String gender, String phone, double mark, String studentID, String school) {
        this.userName = userName;
        this.nickName = nickName;
        this.imageID = imageID;
        this.gender = GENDER.valueOf(gender);
        this.phone = phone;
        this.mark = mark;
        this.studentID = studentID;
        this.school = school;
    }

    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
