package com.example.subrahmanya;


import android.app.Application;

public class global extends Application {
    public static global instance;

    // Global variable
    public String nm;
    public String eml;
    public  String phn;





    // Restrict the constructor from being instantiated
    public global(){}

    public void setData(String el){
        this.eml=el;
    }
    public String getData(){
        return this.eml;
    }
    public String getNm() {
        return nm;
    }

    public void setNm(String nm) {
        this.nm = nm;
    }

    public String getPhn() {
        return phn;
    }

    public void setPhn(String phn) {
        this.phn = phn;
    }

    public static synchronized global getInstance(){
        if(instance==null){
            instance=new global();
        }
        return instance;
    }
}
