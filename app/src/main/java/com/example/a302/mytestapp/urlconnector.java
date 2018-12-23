package com.example.a302.mytestapp;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class urlconnector extends Thread {
    String temp;

    public void run() {

        final String output = request("http://203.250.137.165/json.php");

        temp = output;
    }

    public String getTemp(){
        return temp;
    }

    String request(String urlStr){
        StringBuilder output=new StringBuilder();
        try{
            URL url=new URL(urlStr);
            HttpURLConnection conn=(HttpURLConnection)url.openConnection();
            if(conn!=null){
                conn.setConnectTimeout(5000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                int resCode=conn.getResponseCode();
                if(resCode==HttpURLConnection.HTTP_OK){
                    BufferedReader reader=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line=null;
                    while (true  ){
                        line=reader.readLine();
                        if (line==null){
                            break;
                        }
                        output.append(line+"\n");
                    }
                    reader.close();
                    conn.disconnect();
                }
            }
        }catch (Exception ex){
            Log.e("SampleHTTP","Exception in processing response",ex);
            ex.printStackTrace();
        }
        return  output.toString();
    }
}
