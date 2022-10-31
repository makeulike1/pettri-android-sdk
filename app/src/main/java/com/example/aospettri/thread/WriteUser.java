package com.example.aospettri.thread;

import com.example.aospettri.network.RestApi;

import org.json.JSONArray;

public class WriteUser  extends  Thread{

    private String ck;

    private String userId;

    private JSONArray propList;

    public WriteUser(String ck, String userId, JSONArray propList){
        this.ck = ck;
        this.userId = userId;
        this.propList = propList;
    }

    @Override
    public void run(){
        RestApi.callUserCreate(ck, userId, propList);
    }
}
