package com.example.larion.controller;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Larion on 08.04.2015.
 */

public class SendDataToQuadcopter implements OnVariablesChanged {
    private OnVariablesChanged variablesChangedListener;
    @Override
    public void onChange(float pitch, float roll,float yaw, float throttle) {
        class SendVariables implements Runnable {
            float pitch;
            float roll;
            float yaw;
            float throttle;
            DatagramSocket ds = null;

            SendVariables(float p,float r,float y,float t) {
                pitch = p;
                roll= r;
                yaw=y;
                throttle=t;
            }
                public void run() {
                    try {
                        String udpMsg = "pitch "+pitch+",roll "+roll+",yaw "+yaw+",throttle "+throttle;
                        ds = new DatagramSocket(1562);

                        InetAddress serverAddr = InetAddress.getByName("192.168.4.1");

                        DatagramPacket dp;

                        dp = new DatagramPacket(udpMsg.getBytes(), udpMsg.length(), serverAddr, 1562);

                        ds.send(dp);
                        ds.close();

                    } catch (SocketException e) {

                        e.printStackTrace();

                    }catch (UnknownHostException e) {

                        e.printStackTrace();

                    } catch (IOException e) {

                        e.printStackTrace();

                    } catch (Exception e) {

                        e.printStackTrace();

                    } finally {

                    }
                    /*try{
                        URI website = new URI("http://192.168.4.1:80/?!pitch="+pitch+"&roll="+roll+"&yaw="+yaw+"&throttle="+throttle+"#");
                        HttpParams params = new BasicHttpParams();
                        HttpConnectionParams.setSoTimeout(params, 10);
                        HttpGet httpget = new HttpGet();
                        httpget.setURI(website);
                        HttpClient httpclient = new DefaultHttpClient(params);
                        HttpResponse response = httpclient.execute(httpget);
                        Log.i("Sended", "True");
                    } catch (Exception e) {e.printStackTrace();}*/
                }
            }
        Thread thread = new Thread(new SendVariables(pitch,roll,yaw,throttle));
        thread.start();
    }
    @Override
    public void setOnVariablesChangedListener(OnVariablesChanged listener){
        this.variablesChangedListener=listener;
    }
    @Override
    public void awakeOnReleaseListener(float pitch,float roll,float yaw,float throttle)
    {
        Log.i("Awaked", "True");
        if(variablesChangedListener != null)
        {
            variablesChangedListener.onChange(pitch,roll,yaw,throttle);
        }
    }
}
