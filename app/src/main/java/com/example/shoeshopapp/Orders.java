package com.example.shoeshopapp;

import java.util.List;

public class Orders {
    private int orderid;
    private String order_date;
    private String userid;
    private List<Cart> clist;

    public Orders() {
    }

    public Orders(int orderid, String order_date, String userid, List<Cart> clist) {
        this.orderid = orderid;
        this.order_date = order_date;
        this.userid = userid;
        this.clist = clist;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public List<Cart> getClist() {
        return clist;
    }

    public void setClist(List<Cart> clist) {
        this.clist = clist;
    }
}
