package com.example.aospettri.thread;

import com.example.aospettri.ReqProp;
import com.example.aospettri.RestApi;

import org.json.JSONArray;

import java.util.List;

public class EventLoggingThread  extends  Thread{

    private String eventName;

    private String userId;

    private JSONArray propList;


    public EventLoggingThread(String eventName, String userId, JSONArray propList){
        this.eventName = eventName;
        this.userId = userId;
        this.propList = propList;
    }

    @Override
    public void run(){
        RestApi.callEventCreate(userId, eventName, propList);
    }

}
