package com.example.aospettri.thread;

import com.example.aospettri.network.RestApi;

import org.json.JSONArray;

public class WriteInstall  extends  Thread{
    private String ck;

    private JSONArray propList;

    public WriteInstall(String ck, JSONArray propList){
        this.ck = ck;
        this.propList = propList;
    }

    @Override
    public void run(){
        RestApi.callInstallCreate(ck, propList);
    }

}
