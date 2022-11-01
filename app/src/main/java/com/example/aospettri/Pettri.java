package com.example.aospettri;

import android.content.Context;

import androidx.room.Room;

import com.example.aospettri.network.IPConfig;
import com.example.aospettri.network.RestApi;
import com.example.aospettri.network.object.Response;
import com.example.aospettri.room.AppDatabase;
import com.example.aospettri.room.Appdata;
import com.example.aospettri.room.AppdataDao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.provider.Settings.Secure;

public class Pettri {

    private static String TRACKING_ID  = "";

    private static String CLICK_KEY    = "";

    /*** Initialize the Pettri SDK. ***/
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
                        System.out.println("Click key : " + ck + " is successfully saved into Room DB.");
                        System.out.println("Tracking ID : " + trkID + " is successfully saved into Room DB.");

                        TRACKING_ID = trkID;
                        CLICK_KEY = ck;


                        Boolean isReInstall = isReInstalled(context);

                        System.out.println("Detecting the application was re-installed. : " + isReInstall);

                        if(!isReInstall) {

                            JSONArray propList = getInstallProp(context);
                            sendFirstInstall(ck, propList);

                        }else sendReInstall(context);

                    }

                } else setAppConfig(isCK, isTrkID);

                db.close();
            }


        });

        th.start();
    }




    /*** Save Click Key and Tracking ID into the Application Memory ***/
    public static void setAppConfig(String ck, String trkID){
        System.out.println("Getting saved click key from Room DB : " + ck);
        System.out.println("Getting saved tracking id from Room DB : " + trkID);

        TRACKING_ID = trkID;
        CLICK_KEY = ck;
    }





    public static JSONArray getInstallProp(Context context){
        String androidID = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);

        JSONArray propList = new JSONArray();
        String ip = IPConfig.getIPAddress(true); // IPv4

        try {

            JSONObject prop1 = new JSONObject();
            prop1.put("key", "ip");
            prop1.put("value", ip);
            propList.put(prop1);

            JSONObject prop2 = new JSONObject();
            prop2.put("key", "android_id");
            prop2.put("value", androidID);
            propList.put(prop2);

        }catch(JSONException e){
            e.printStackTrace();
        }

        return propList;
    }





    /*** Detect the application is re-installed. ***/
    public static Boolean isReInstalled(Context context){
        String deviceID = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
        System.out.println("Android ID : " + deviceID);
        return Boolean.valueOf(RestApi.sendGet(RestApi.checkReInstall+"?tracking_id=" +TRACKING_ID+ "&device_id="+deviceID+"&platform=0").getMessage());
    }







    /*** Send Re-Installation Log to Attribution . ***/
    public static Response sendReInstall(Context context){
        String deviceId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
        System.out.println("Android ID : " + deviceId);

        JSONObject requestObject = new JSONObject();

        try {
            requestObject.put("trackingId",         TRACKING_ID);
            requestObject.put("deviceId",           deviceId);
        }catch(Exception e) {
            e.printStackTrace();
        }

        return RestApi.sendPost(requestObject, RestApi.createReInstallAPIPath);
    }





    /*** Send First Installation Log to Attribution . ***/
    public static Response sendFirstInstall(String clickKey, JSONArray propList){
        JSONObject requestObject = new JSONObject();

        try {
            requestObject.put("trackingId",     TRACKING_ID);
            requestObject.put("ck",             clickKey);
            requestObject.put("prop",           propList);
        }catch(Exception e) {
            e.printStackTrace();
        }

        return RestApi.sendPost(requestObject, RestApi.createInstallAPIPath);
    }







    /*** Send Event Log to Attribution. ***/
    public static Response sendEvent(String eventName, JSONArray propList){
        JSONObject requestObject = new JSONObject();

        try {
            requestObject.put("ck",             CLICK_KEY);
            requestObject.put("trackingId",     TRACKING_ID);
            requestObject.put("userId",         LoginUser.userId);
            requestObject.put("name",           eventName);
            requestObject.put("prop",           propList);
        }catch(Exception e){
            e.printStackTrace();
        }

        return RestApi.sendPost(requestObject, RestApi.createEventAPIPath);
    }





    /*** Send Creating User Log to Attribution. ***/
    public static Response createUser(String userId, JSONArray propList){
        JSONObject requestObject = new JSONObject();

        try {
            requestObject.put("ck",             CLICK_KEY);
            requestObject.put("trackingId",     TRACKING_ID);
            requestObject.put("userId", userId);
            requestObject.put("prop", propList);
        }catch(Exception e){
            e.printStackTrace();
        }

        return RestApi.sendPost(requestObject, RestApi.createUserAPIPath);
    }



}
