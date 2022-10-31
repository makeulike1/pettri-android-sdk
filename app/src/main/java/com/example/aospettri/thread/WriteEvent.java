package com.example.aospettri.thread;

import com.example.aospettri.network.RestApi;

import org.json.JSONArray;

public class WriteEvent  extends  Thread{

    private String  ck;

    private String  eventName;

    private String  userId;

    private JSONArray propList;


    public WriteEvent(String ck, String eventName, String userId, JSONArray propList){
        this.eventName = eventName;
        this.userId = userId;
        this.propList = propList;
        this.ck = ck;
    }

    @Override
    public void run(){
        RestApi.callEventCreate(ck, userId, eventName, propList);
    }

}
