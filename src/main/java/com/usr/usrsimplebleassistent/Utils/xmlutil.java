package com.usr.usrsimplebleassistent.Utils;

import com.usr.usrsimplebleassistent.bean.DengluRequest;
import com.usr.usrsimplebleassistent.bean.DengluRespones;
import com.usr.usrsimplebleassistent.bean.DianchiRequest;
import com.usr.usrsimplebleassistent.bean.DianchiRespones;
import com.usr.usrsimplebleassistent.bean.GongjuRequest;
import com.usr.usrsimplebleassistent.bean.GongjuRespones;
import com.usr.usrsimplebleassistent.bean.WaisheRequest;
import com.usr.usrsimplebleassistent.bean.WaisheRespones;
import com.usr.usrsimplebleassistent.bean.ZhuceRequest;
import com.usr.usrsimplebleassistent.bean.ZhuceRespones;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Iterator;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class xmlutil {
	
	
	//电池请求
	public static String DianchiRquestXmlstr(DianchiRequest unrre){
		Document doc = DocumentHelper.createDocument();
        Element root = doc.addElement("Transinfo");
        root.addElement("PosCode").setText(unrre.getPosCode());
        root.addElement("BatteryID").setText(unrre.getBatteryID());
        root.addElement("BatteryString").setText(unrre.getBatteryString());
        root.addElement("BatteryStatus").setText(unrre.getBatteryStatus());
        root.addElement("TotalVoltage").setText(unrre.getTotalVoltage());
        root.addElement("SocElePercentage").setText(unrre.getSocElePercentage());
        root.addElement("Electricity").setText(unrre.getElectricity());
        root.addElement("Residuallife").setText(unrre.getResiduallife());
        root.addElement("MaxTemperature").setText(unrre.getMaxTemperature());
        root.addElement("MonomerVoltage").setText(unrre.getMonomerVoltage());
		root.addElement("SensorTemperature ").setText(unrre.getSensorTemperature ());
		root.addElement("BatteryLockStatus").setText(unrre.getBatteryLockStatus());
		root.addElement("CumulativeNum").setText(unrre.getCumulativeNum());
		root.addElement("BatteryPackVs").setText(unrre.getBatteryPackVs());
        OutputFormat format = OutputFormat.createCompactFormat(); //createPrettyPrint() ��θ�ʽ��
        format.setEncoding("utf-8");
        StringWriter writer = new StringWriter();
        XMLWriter output = new XMLWriter(writer, format);
        try {
            output.write(doc);
            writer.close();
            output.close();
            System.out.println(writer.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return writer.toString();
	}
	
	//电池返回
	public static DianchiRespones DianchiResponesResponseXmlstr(String resultstr){
		DianchiRespones unrre = new DianchiRespones();
	    try {  
	        SAXReader reader = new SAXReader();  
	         Document doc;   
	         doc = DocumentHelper.parseText(resultstr);   
	        Element root = doc.getRootElement();  
	        Attribute testCmd= root.attribute("Transinfo");  	        
	        for (Iterator iter = root.elementIterator(); iter.hasNext();){
	        	 Element element = (Element) iter.next(); 
	        	 if (element.getName().equals("ResultCode")) {  
	        		 unrre.setResultCode(element.getTextTrim());
	        	 }else if(element.getName().equals("HostTime")){
	        		 unrre.setHostTime(element.getTextTrim());
	        	 }else if(element.getName().equals("Note")){
	        		 unrre.setNote(element.getTextTrim());
	        	 }
	        }
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    }
	    return unrre;
	}

	//工具请求
	public static String GongjuRquestXmlstr(GongjuRequest unrre){
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("Transinfo");
		root.addElement("PosCode").setText(unrre.getPosCode());
		root.addElement("BatteryID").setText(unrre.getBatteryID());
		root.addElement("ChargerStatus").setText(unrre.getChargerStatus());
		OutputFormat format = OutputFormat.createCompactFormat(); //createPrettyPrint() ��θ�ʽ��
		format.setEncoding("utf-8");
		StringWriter writer = new StringWriter();
		XMLWriter output = new XMLWriter(writer, format);
		try {
			output.write(doc);
			writer.close();
			output.close();
			System.out.println(writer.toString());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return writer.toString();
	}

	//工具返回
	public static GongjuRespones GongjuResponseXmlstr(String resultstr){
		GongjuRespones unrre = new GongjuRespones();
		try {
			SAXReader reader = new SAXReader();
			Document doc;
			doc = DocumentHelper.parseText(resultstr);
			Element root = doc.getRootElement();
			Attribute testCmd= root.attribute("Transinfo");
			for (Iterator iter = root.elementIterator(); iter.hasNext();){
				Element element = (Element) iter.next();
				if (element.getName().equals("ResultCode")) {
					unrre.setResultCode(element.getTextTrim());
				}else if(element.getName().equals("HostTime")){
					unrre.setHostTime(element.getTextTrim());
				}else if(element.getName().equals("Note")){
					unrre.setNote(element.getTextTrim());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return unrre;
	}

	//外设请求
	public static String WaisheRquestXmlstr(WaisheRequest unrre){
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("Transinfo");
		root.addElement("PosCode").setText(unrre.getPosCode());
		root.addElement("PeriFirmwareVs").setText(unrre.getPeriFirmwareVs());
		root.addElement("MotorTemperature").setText(unrre.getMotorTemperature());
		root.addElement("MotorSpeed").setText(unrre.getMotorSpeed());
		root.addElement("ControllerTemperature").setText(unrre.getControllerTemperature());
		root.addElement("WorkCurrent").setText(unrre.getWorkCurrent());
		root.addElement("ReservedBit").setText(unrre.getReservedBit());
		OutputFormat format = OutputFormat.createCompactFormat(); //createPrettyPrint() ��θ�ʽ��
		format.setEncoding("utf-8");
		StringWriter writer = new StringWriter();
		XMLWriter output = new XMLWriter(writer, format);
		try {
			output.write(doc);
			writer.close();
			output.close();
			System.out.println(writer.toString());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return writer.toString();
	}

	//外设返回
	public static WaisheRespones WaisheResponseXmlstr(String resultstr){
		WaisheRespones unrre = new WaisheRespones();
		try {
			SAXReader reader = new SAXReader();
			Document doc;
			doc = DocumentHelper.parseText(resultstr);
			Element root = doc.getRootElement();
			Attribute testCmd= root.attribute("Transinfo");
			for (Iterator iter = root.elementIterator(); iter.hasNext();){
				Element element = (Element) iter.next();
				if (element.getName().equals("ResultCode")) {
					unrre.setResultCode(element.getTextTrim());
				}else if(element.getName().equals("HostTime")){
					unrre.setHostTime(element.getTextTrim());
				}else if(element.getName().equals("Note")){
					unrre.setNote(element.getTextTrim());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return unrre;
	}

	//外设请求
	public static String ZhuceRquestXmlstr(ZhuceRequest unrre){
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("Transinfo");
		root.addElement("UserName").setText(unrre.getUserName());
		root.addElement("UserPwd").setText(unrre.getUserPwd());
		OutputFormat format = OutputFormat.createCompactFormat(); //createPrettyPrint() ��θ�ʽ��
		format.setEncoding("utf-8");
		StringWriter writer = new StringWriter();
		XMLWriter output = new XMLWriter(writer, format);
		try {
			output.write(doc);
			writer.close();
			output.close();
			System.out.println(writer.toString());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return writer.toString();
	}

	//外设返回
	public static ZhuceRespones ZhuceResponseXmlstr(String resultstr){
		ZhuceRespones unrre = new ZhuceRespones();
		try {
			SAXReader reader = new SAXReader();
			Document doc;
			doc = DocumentHelper.parseText(resultstr);
			Element root = doc.getRootElement();
			Attribute testCmd= root.attribute("Transinfo");
			for (Iterator iter = root.elementIterator(); iter.hasNext();){
				Element element = (Element) iter.next();
				if (element.getName().equals("ResultCode")) {
					unrre.setResultCode(element.getTextTrim());
				}else if(element.getName().equals("HostTime")){
					unrre.setHostTime(element.getTextTrim());
				}else if(element.getName().equals("UserId")){
					unrre.setUserId(element.getTextTrim());
				}else if(element.getName().equals("Note")){
					unrre.setNote(element.getTextTrim());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return unrre;
	}

	//外设请求
	public static String DengluRquestXmlstr(DengluRequest unrre){
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("Transinfo");
		root.addElement("UserId").setText(unrre.getUserName());
		root.addElement("UserPwd").setText(unrre.getUserPwd());
		OutputFormat format = OutputFormat.createCompactFormat(); //createPrettyPrint() ��θ�ʽ��
		format.setEncoding("utf-8");
		StringWriter writer = new StringWriter();
		XMLWriter output = new XMLWriter(writer, format);
		try {
			output.write(doc);
			writer.close();
			output.close();
			System.out.println(writer.toString());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return writer.toString();
	}

	//外设返回
	public static DengluRespones DengluResponseXmlstr(String resultstr){
		DengluRespones unrre = new DengluRespones();
		try {
			SAXReader reader = new SAXReader();
			Document doc;
			doc = DocumentHelper.parseText(resultstr);
			Element root = doc.getRootElement();
			Attribute testCmd= root.attribute("Transinfo");
			for (Iterator iter = root.elementIterator(); iter.hasNext();){
				Element element = (Element) iter.next();
				if (element.getName().equals("ResultCode")) {
					unrre.setResultCode(element.getTextTrim());
				}else if(element.getName().equals("HostTime")){
					unrre.setHostTime(element.getTextTrim());
				}else if(element.getName().equals("UserName")){
					unrre.setUserName(element.getTextTrim());
				}else if(element.getName().equals("Note")){
					unrre.setNote(element.getTextTrim());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return unrre;
	}

}
