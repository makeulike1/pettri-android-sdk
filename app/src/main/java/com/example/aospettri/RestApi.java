package com.example.aospettri;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class RestApi {

    private static String serverURL             = "http://test.adrunner.co.kr:8083";

    private static String eventAPIPath          = "/event/create";

    private static String installAPIPath        = "/install/create";

    private static String bundleId              = "com.android.pettri";



    // 이벤트 로그 생성
    public static void callEventCreate(
            String userId, String name, List<ReqProp> propList){

        // 서버로 전달할 JSON 객체 생성
        JSONObject requestObject = new JSONObject();

        try {
            requestObject.put("bundleId",   bundleId);
            requestObject.put("userId",     userId);
            requestObject.put("name",       name);
            requestObject.put("prop",    new JSONArray(propList));
        }catch(Exception e){
            e.printStackTrace();
        }

        sendPost(requestObject, eventAPIPath);
    }




    // 최초 인스톨시 인스톨에 대한 로그 갱신
    public static void callInstallCreate(String ck,  List<ReqProp> propList){

        // 서버로 전달할 JSON 객체 생성
        JSONObject requestObject = new JSONObject();

        try {
            requestObject.put("bundleId",       bundleId);
            requestObject.put("clickKey",       ck);
            requestObject.put("prop",           new JSONArray(propList));
        }catch(Exception e){
            e.printStackTrace();
        }

        sendPost(requestObject, installAPIPath);
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
