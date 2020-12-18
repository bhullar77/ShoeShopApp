package com.example.shoeshopapp;

public class User {
    private String fname;
    private String userid;
    private String pwd;

    public User() {
    }

    public User(String fname, String userid, String pwd) {
        this.fname = fname;
        this.userid = userid;
        this.pwd = pwd;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "User{" +
                "fname='" + fname + '\'' +
                ", userid='" + userid + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}
