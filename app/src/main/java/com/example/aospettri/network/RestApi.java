package com.example.aospettri.network;

import com.example.aospettri.AppConfig;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class RestApi {

    private static String serverURL                     = "http://test.adrunner.co.kr:8083";

    private static String createEventAPIPath            = "/event/create";

    private static String createInstallAPIPath          = "/install/create";

    private static String createUserAPIPath             = "/user/create";


    public static void callEventCreate(
            String ck, String userId, String name, JSONArray propList){

        JSONObject requestObject = new JSONObject();

        try {
            requestObject.put("ck", ck);
            requestObject.put("trackingId",   AppConfig.trkId);
            requestObject.put("userId",       userId);
            requestObject.put("name",         name);
            requestObject.put("prop",         propList);
        }catch(Exception e){
            e.printStackTrace();
        }

        sendPost(requestObject, createEventAPIPath);
    }




    public static void callInstallCreate(String ck,  JSONArray propList){

        JSONObject requestObject = new JSONObject();

        try {
            requestObject.put("trackingId",     AppConfig.trkId);
            requestObject.put("ck",             ck);
            requestObject.put("prop",           propList);
        }catch(Exception e){
            e.printStackTrace();
        }

        sendPost(requestObject, createInstallAPIPath);
    }




    public static void callUserCreate(String ck, String userId, JSONArray propList){

        JSONObject requestObject = new JSONObject();


        try{
            requestObject.put("ck", ck);
            requestObject.put("trackingId",     AppConfig.trkId);
            requestObject.put("userId",         userId);
            requestObject.put("prop",           propList);

        }catch(Exception e){
            e.printStackTrace();
        }

        sendPost(requestObject, createUserAPIPath);
    }



    public static Integer sendPost(JSONObject json, String path){

        String responseMessage = "";

        try {

            HttpURLConnection conn;

            URL url = new URL(serverURL + path);

            System.out.println("*** Calling API : "+serverURL+path);

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Accept-Charset", "UTF-8");

            conn.setConnectTimeout(10000);
            conn.setReadTimeout(100000);

            System.out.println("Request Header : " + json.toString());
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            byte[] input = json.toString().getBytes("utf-8");
            os.write(input, 0, input.length);


            os.flush();


            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

            String line;

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK)
                return 0;

            while ((line = reader.readLine()) != null)
                responseMessage += line;

            System.out.println("*** Response["+serverURL+path+"] : "+responseMessage);

            conn.disconnect();
        }catch(Exception e){
            e.printStackTrace();
        }

        return 200;

    }
}
