package com.example.aospettri.thread;

import com.example.aospettri.ReqProp;
import com.example.aospettri.RestApi;

import org.json.JSONArray;

import java.util.List;

public class UserLoggingThread  extends  Thread{

    private String ck;

    private String userId;

    private JSONArray propList;

    public UserLoggingThread(String ck, String userId, JSONArray propList){
        this.ck = ck;
        this.userId = userId;
        this.propList = propList;
    }

    @Override
    public void run(){
        RestApi.callUserCreate(ck, userId, propList);
    }
}
