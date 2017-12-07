package com.erhuo.entity;

/**
 * Created by msi on 2017/12/7.
 */

public class Notice {
    private String firstname;
    private String secondname;
    private String date;
    public Notice(String firstname,String secondname,String date){
        this.firstname = firstname;
        this.secondname = secondname;
        this.date = date;
    }
    public String getFirstname(){
        return firstname;
    }
    public String getSecondname(){
        return secondname;
    }
    public String getDate(){
        return date;
    }
}
