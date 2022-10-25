package com.example.aospettri.thread;

import com.example.aospettri.ReqProp;
import com.example.aospettri.RestApi;

import java.util.List;

public class EventLoggingThread  extends  Thread{

    private String eventName;

    private String userId;

    private List<ReqProp> propList;


    public EventLoggingThread(String eventName, String userId, List<ReqProp> propList){
        this.eventName = eventName;
        this.userId = userId;
        this.propList = propList;
    }

    @Override
    public void run(){
        RestApi.callEventCreate(userId, eventName, propList);
    }

}
