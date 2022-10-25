package com.example.aospettri.thread;

import com.example.aospettri.ReqProp;
import com.example.aospettri.RestApi;

import java.util.List;

public class InstallLoggingThread  extends  Thread{
    private String ck;

    private List<ReqProp> propList;

    public InstallLoggingThread(String ck, List<ReqProp> propList){
        this.ck = ck;
        this.propList = propList;
    }

    @Override
    public void run(){
        RestApi.callInstallCreate(ck, propList);
    }

}
