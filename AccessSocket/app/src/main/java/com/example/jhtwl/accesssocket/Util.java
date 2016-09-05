package com.example.jhtwl.accesssocket;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
	public static final String IP="10.10.0.111";
	public static final int PORT=8080; 
	public static Socket socket;
	public static BufferedReader mReader ;
	public static BufferedWriter mWriter ; 
	public static OutputStream outputStream; 
	public static synchronized Socket getSocketInstacne() throws UnknownHostException, IOException{
		if(socket==null){
			socket = new Socket(IP,PORT);
		}
		return socket;
	}
	public static synchronized BufferedReader getBufferedReader() throws UnknownHostException, IOException{
		if(mReader==null){
			mReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
		}
		return mReader;
	}
	public static synchronized BufferedWriter getBufferedWriter() throws UnknownHostException, IOException{
		if(mWriter==null){
			mWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"));
		}
		return mWriter;
	}
	
	public static synchronized OutputStream getOutputStream() throws UnknownHostException, IOException{
		outputStream= socket.getOutputStream();
		return outputStream;
	}
	
	public static void sendMsg(byte msg[] ,Context context) {
		try {
			outputStream= socket.getOutputStream();
            outputStream.write(msg);
            outputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("exception", e.getMessage());
		}
	}
	public static void send(final byte msg[],final Context context) {
		new AsyncTask<String, Integer, String>() {

			@Override
			protected String doInBackground(String... params) {
				sendMsg(msg,context);
				return null;
			}
		}.execute();
	}
	
	public static String getTime(long millTime) {
		Date d = new Date(millTime);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(sdf.format(d));
		return sdf.format(d);
	}
	
	public static String getIP(Context context){
		 WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);  
        //�ж�wifi�Ƿ���  
        if (!wifiManager.isWifiEnabled()) {  
        	wifiManager.setWifiEnabled(true);    
        }  
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();       
        int ipAddress = wifiInfo.getIpAddress();   
        String ip = intToIp(ipAddress);   
        return ip;
	}
	private static String intToIp(int i) {       
        return (i & 0xFF ) + "." +       
      ((i >> 8 ) & 0xFF) + "." +       
      ((i >> 16 ) & 0xFF) + "." +       
      ( i >> 24 & 0xFF) ;  
   }
		
	
}
