package com.example.aospettri.settings;

import android.content.Context;

import androidx.room.Room;

import com.example.aospettri.AppConfig;
import com.example.aospettri.network.IPConfig;
import com.example.aospettri.room.AppDatabase;
import com.example.aospettri.room.Appdata;
import com.example.aospettri.room.AppdataDao;
import com.example.aospettri.thread.WriteInstall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Pettri {

    public static void init(String ck, String trkID, Context context){

        Thread th = new Thread(new Runnable(){

            @Override
            public void run(){

                AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "test").build();
                AppdataDao appdataDao = db.appdataDao();

                String isCK         = appdataDao.findCK();
                String isTrkID      = appdataDao.findTrkId();
                //appdataDao.delete();

                if (isCK == null) {
                    if ((ck != null) && (trkID != null)){
                        Appdata ap = new Appdata(1, ck, trkID);
                        appdataDao.insert(ap);
                        System.out.println("*** Click key " + ck + " is successfully saved into Room DB.");

                        JSONArray propList = getInstallProp();
                        WriteInstall thread = new WriteInstall(ck, propList);
                        thread.start();

                        AppConfig.trkId = trkID;
                        AppConfig.ck = ck;
                    }

                } else setAppConfig(isCK, isTrkID);

                db.close();
            }


        });

        th.start();
    }



    public static void setAppConfig(String ck, String trkID){
        System.out.println("*** Getting saved click key from Room DB : " + ck);
        System.out.println("*** Getting saved tracking id from Room DB : " + trkID);

        AppConfig.ck = ck;
        AppConfig.trkId = trkID;
    }




    public static JSONArray getInstallProp(){
        JSONArray propList = new JSONArray();
        String ip = IPConfig.getIPAddress(true); // IPv4

        try {

            JSONObject prop1 = new JSONObject();
            prop1.put("key", "ip");
            prop1.put("value", ip);
            propList.put(prop1);

        }catch(JSONException e){
            e.printStackTrace();
        }

        return propList;
    }



}
