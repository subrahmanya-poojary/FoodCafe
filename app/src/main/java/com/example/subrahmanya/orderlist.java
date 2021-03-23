package com.example.subrahmanya;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class orderlist {

    public String foodname;
    public String foodrate;
    public String foodqty;
    public String foodtotal;
    public String url;
    public String orderdate;


    public orderlist(String foodname, String foodrate, String foodqty, String foodtotal) {
        this.foodname = foodname;
        this.foodrate = foodrate;
        this.foodqty=foodqty;
        this.foodtotal=foodtotal;
        this.url=url;
    }

    public String getFoodqty() {
        return foodqty;
    }

    public void setFoodqty(String foodqty) {
        this.foodqty = foodqty;
    }

    public String getFoodtotal() {
        return foodtotal;
    }

    public void setFoodtotal(String foodtotal) {
        this.foodtotal = foodtotal;
    }


    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public String getFoodrate() {
        return foodrate;
    }

    public void setFoodrate(String foodrate) {
        this.foodrate = foodrate;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public String getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate;
    }

    orderlist()
    {

    }

}
