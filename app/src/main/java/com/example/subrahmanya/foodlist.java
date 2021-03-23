package com.example.subrahmanya;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class foodlist {

    private String name;
    private String mrp;
    private float rating;
    private  String purl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }

    public foodlist(String name, String mrp, float rating, String purl) {
        this.name = name;
        this.mrp = mrp;
        this.rating = rating;
        this.purl = purl;
    }
    foodlist()
    {

    }
}
