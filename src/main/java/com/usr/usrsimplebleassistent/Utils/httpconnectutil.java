package com.usr.usrsimplebleassistent.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class httpconnectutil {

	private String IP = "http://192.168.101.133:85/";
	private String dianchiApi = "API/pandapay.Battery.info.ashx";
	private String gongjuApi = "API/pandapay.Charger.info.ashx";
	private String waisheApi = "API/pandapay.Peripheral.info.ashx";
	private String zhuceApi = "API/pandapay.user.register.ashx";
	private String dengluApi = "API/pandapay.getuser.info.ashx";
	
	public String Post(int req,String reqcont) {
		String urls="" ;
		String result = "";
		switch(req){
		  case 1:urls=IP+dianchiApi;break;
		  case 2:urls=IP+gongjuApi;break;
		  case 3:urls=IP+waisheApi;break;
		  case 4:urls=IP+zhuceApi;break;
		  case 5:urls=IP+dengluApi;break;
		  default:break;
		}
	         try {
	             URL url = new URL(urls);
	             URLConnection con = url.openConnection();
	             con.setDoOutput(true);
	             //con.setConnectTimeout(5000);
	             con.setReadTimeout(5000);
	             con.setRequestProperty("Pragma", "no-cache");
	             con.setRequestProperty("Cache-Control", "no-cache");
	             con.setRequestProperty("Content-Type", "text/xml");
	             OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());   
	             String xmlInfo = reqcont;
	             System.out.println("urlStr=" + url);
	             System.out.println("xmlInfo=" + xmlInfo);
	             out.write(new String(xmlInfo.getBytes("utf-8")));
	             out.flush();
	             out.close();
	             BufferedReader br = new BufferedReader(new InputStreamReader(con
	                     .getInputStream()));
	             String line = "";
	             
	             for (line = br.readLine(); line != null; line = br.readLine()) {
	            	 result+=line;
	                 //System.out.println();
	             }
	             InputStream is = con.getInputStream();
	             is.close();
	             return new String(result.getBytes("utf-8"));
	         } catch (MalformedURLException e) {
	             e.printStackTrace();
	         } catch (IOException e) {
	             e.printStackTrace();
	         }
			return null;
	     }

}
