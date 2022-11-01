package com.example.aospettri.network;

import com.example.aospettri.network.object.Response;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class RestApi {

    public static String serverURL                     = "http://test.adrunner.co.kr:8083";

    public static String createEventAPIPath            = "/event/create";

    public static String createInstallAPIPath          = "/install/create";

    public static String createUserAPIPath             = "/user/create";

    public static String createReInstallAPIPath        = "/re-install/create";

    public static String checkReInstall                = "/re-install/check-rei";



    /*** Send HTTP GET Request to Attribution. ***/
    public static Response sendGet(String path){

        String responseMessage = "";

        try {

            HttpURLConnection conn;


            URL url = new URL(serverURL + path);

            System.out.println("Calling API : "+serverURL+path);

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");

            conn.setConnectTimeout(10000);
            conn.setReadTimeout(100000);


            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

            String line;

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK)
                return new Response(-1, "error.");

            while ((line = reader.readLine()) != null)
                responseMessage += line;

            System.out.println("Response["+serverURL+path+"] : "+responseMessage);

            conn.disconnect();
        }catch(Exception e){
            e.printStackTrace();
        }

        return new Response(200, responseMessage);

    }



    /*** Send HTTP Post Request to Attribution. ***/
    public static Response sendPost(JSONObject json, String path){

        String responseMessage = "";

        try {

            HttpURLConnection conn;

            URL url = new URL(serverURL + path);

            System.out.println("Calling API : "+serverURL+path);

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
                return new Response(-1, "error.");

            while ((line = reader.readLine()) != null)
                responseMessage += line;

            System.out.println("Response["+serverURL+path+"] : "+responseMessage);

            conn.disconnect();
        }catch(Exception e){
            e.printStackTrace();
        }

        return new Response(200, responseMessage);

    }
}
