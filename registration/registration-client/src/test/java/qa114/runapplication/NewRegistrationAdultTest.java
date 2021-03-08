package qa114.runapplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.testfx.api.FxRobot;

import qa114.pojo.output.RID;
import qa114.testcases.*;
import qa114.utility.PropertiesUtil;
import javafx.application.Application;


public class NewRegistrationAdultTest{
	static FxRobot robot;
	public static void invokeRegClient(JSONObject idjson,HashMap<String, String> documentUpload,String[] args,
			String loginUserid,String loginPwd,String supervisorUserid,
			String supervisorUserpwd,String lang,String schemaversion,String jsonObjName )
	{

		LoginNewRegLogout lg=new LoginNewRegLogout();  

		Thread thread = new Thread() { 
			@Override
			public void run() {
				try {
					System.out.println("thread inside calling testcase"); 

					Thread.sleep(Long.parseLong(PropertiesUtil.getKeyValue("ApplicationLaunchTimeWait"))); 
					robot=new FxRobot();
					RID rid=lg.newRegistrationAdult(robot,loginUserid, loginPwd,supervisorUserid,supervisorUserpwd,StartApplication.primaryStage,idjson,documentUpload,lang,schemaversion,jsonObjName);
					System.out.println(rid.result + rid.ridDateTime + rid.rid);
				} catch (Exception e) {
					e.printStackTrace(); 
				}	
			}

		};

		thread.start();
		Application.launch(StartApplication.class, args);
	}

	public static void main(String[] args) throws InterruptedException, IOException, JSONException { 

		invokeRegClient(readJsonFileText(),readMapDocumentValues(),args,PropertiesUtil.getKeyValue("loginUserid"), 
				PropertiesUtil.getKeyValue("loginPwd"),PropertiesUtil.getKeyValue("supervisorUserid"), 
				PropertiesUtil.getKeyValue("supervisorUserpwd"),PropertiesUtil.getKeyValue("lang"),
				PropertiesUtil.getKeyValue("schemaversion"),
				PropertiesUtil.getKeyValue("jsonObjName"));

	}

	public static JSONObject readJsonFileText() throws IOException, JSONException
	{
		File f = new File(PropertiesUtil.getKeyValue("IDJsonPath"));
		JSONObject json = null;
		if (f.exists()){
			InputStream is = new FileInputStream(f);
			String jsonTxt = IOUtils.toString(is, "UTF-8");
			System.out.println(jsonTxt);


			json= new JSONObject(jsonTxt); 

		}
		return json;
	}


	public static HashMap<String, String> readMapDocumentValues()
	{
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("proofOfConsent","Registration Form");
		map.put("proofOfAddress","Barangay ID");
		map.put("proofOfIdentity","Postal ID");
		map.put("proofOfException","PhilHealth ID");
		return map;

	}

}


