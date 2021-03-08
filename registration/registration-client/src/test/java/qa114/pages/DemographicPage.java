package qa114.pages;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.assertj.core.api.SoftAssertions;
import org.awaitility.Awaitility;
import org.json.JSONObject;
import org.testfx.api.FxRobot;

import ch.qos.logback.classic.gaffer.PropertyUtil;
import io.mosip.registration.dto.mastersync.DocumentCategoryDto;
import io.mosip.registration.dto.mastersync.GenericDto;
import javafx.application.Platform;
import javafx.geometry.VerticalDirection;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import qa114.controls.Buttons;
import qa114.pojo.schema.Root;
import qa114.pojo.schema.Schema;
import qa114.pojo.testdata.RootTestData;
import qa114.pojo.testdata.TestData;
import  qa114.utility.JsonUtil;
import qa114.utility.PropertiesUtil;
import qa114.utility.WaitsUtil;

public class DemographicPage {

	FxRobot robot;
	Stage applicationPrimaryStage;
	Scene scene;
	Node node;
	TextField demoTextField;
	Schema schema;
	TestData testdata;
	Root rootSchema; 
	RootTestData rootTestData;
	Boolean flagContinueBtnDemograph=true;

	Boolean flagContinueBtnDocumentUpload=true;
	DocumentUploadPage documentUploadPage;
	BiometricUploadPage biometricUploadPage;
	WaitsUtil waitsUtil;
	String DemoDetailsImg="#DemoDetailsImg";
	WebViewDocument webViewDocument;
	Buttons buttons;


	public DemographicPage(FxRobot robot) {
		this.robot=robot;
		waitsUtil=new WaitsUtil(robot);
		//waitsUtil.clickNodeAssert( DemoDetailsImg); 
		documentUploadPage=new DocumentUploadPage(robot);
		biometricUploadPage=new BiometricUploadPage(robot);
		buttons=new Buttons(robot);
	}

	public String getTextFields() {
		return demoTextField.getText();
	}

	public void setTextFields(String id,String idSchema) throws InterruptedException {
		this.robot=robot;
		demoTextField= waitsUtil.lookupById(id);
		assertNotNull(demoTextField, id+" not present");

		if(demoTextField.isEditable() && demoTextField.isVisible())
		{	
			robot.clickOn(id);	//Must Needed
			demoTextField.setText(idSchema);
		}		
	}



	public WebViewDocument scemaDemoDocUpload(String schemaFileName,String lang,JSONObject idjson,String jsonObjName,HashMap<String, String> documentUpload) throws IOException, TimeoutException {

		//String schemafile = System.getProperty("user.dir")+"\\"+schemaFileName;

		String schemafile = schemaFileName;

		
		/**
		 *  convertJsonintoJava
		 */
		String jsonFromSchema = Files.readString(Paths.get(schemafile));
		System.out.println("Automaiton Script - Printing Json" + jsonFromSchema);
		rootSchema = JsonUtil.convertJsonintoJava(jsonFromSchema, Root.class);

		System.out.println(rootSchema.getId() + " " + rootSchema.getIdVersion());
		System.out.println("Automaiton Script - Printing Json" + idjson);

		System.out.println("Automaiton Script - Schema ID Follows :- ");



		for (int i = 2; i < rootSchema.getSchema().size()-1; i++) {

			schema = rootSchema.getSchema().get(i);
			System.out.println("Automaiton Script - id="+i + "=" +schema.getId() + "\tSchemaControlType=" + schema.getControlType());

			String id="#"+schema.getId(); 
			String key=schema.getId(); 

			scrollVerticalDirection(i,schema);

			try {
				switch (schema.getControlType()) {
				case "textbox":

					String value=null;
					if(schema.getType().contains("simpleType"))
						value=JsonUtil.JsonObjParsing(idjson,jsonObjName,key,lang);
					else
						value=JsonUtil.JsonObjParsing(idjson,jsonObjName,key);

					setTextFields(id,value);
					System.out.println("Textbox");
					break;
				case "ageDate":
					
					String dateofbirth[]=JsonUtil.JsonObjParsing(idjson,jsonObjName,key).split("/");
					
					if(!schema.getLabel().secondary.equals(null)) {
					robot.press(KeyCode.TAB).release(KeyCode.TAB);
					robot.press(KeyCode.TAB).release(KeyCode.TAB);}
					else { robot.press(KeyCode.TAB).release(KeyCode.TAB);}
					robot.write(dateofbirth[0]);
					robot.press(KeyCode.TAB).release(KeyCode.TAB);
					robot.write(dateofbirth[1]);
					robot.press(KeyCode.TAB).release(KeyCode.TAB);
					robot.write(dateofbirth[2]);
					break;
				case "dropdown": 
					GenericDto dto=new GenericDto();
					if(schema.getType().contains("simpleType"))
						dto.setName(JsonUtil.JsonObjParsing(idjson,jsonObjName,key,lang));
					else
						dto.setName(JsonUtil.JsonObjParsing(idjson,jsonObjName,key));
					user_selects_combo_item(id,dto);
					break;

				case "checkbox":
					if(JsonUtil.JsonObjParsing(idjson,jsonObjName,key).contains("Y"))
						waitsUtil.clickNodeAssert(id);

					break;

				case "fileupload":
					try {
						if(flagContinueBtnDemograph==true) {
							buttons.clickContinueBtn();
							flagContinueBtnDemograph=false;								
						}

						//if(schema.getRequiredOn().equals("") ||(schema.getRequiredOn()).get(0).getExpr().contains("identity.isNew"))//eval expressions
						if(schema.isInputRequired())
							documentUploadPage.documentScan(documentUpload.get(key),schema,id);
					}catch(Exception e)
					{
						e.printStackTrace();
					}

					break;
				case "biometrics":
					try {
						if(flagContinueBtnDocumentUpload==true) {
							buttons.clickContinueBtn();
							flagContinueBtnDocumentUpload=false;								
						}
						if((schema.isInputRequired()) && (schema.isRequired()))
						{
							webViewDocument=biometricUploadPage.newRegbioUpload(schema.getBioAttributes());
						}

					}catch(Exception e)
					{
						e.printStackTrace();
					}

					break;

				}

			}
			catch(Exception e )
			{e.printStackTrace();
			}

		}
		return webViewDocument;


	}



	private void scrollVerticalDirection(int i,Schema schema) throws NumberFormatException, IOException {
		if(schema.getId().equals(schema.getSubType())) 
			if(i==Integer.parseInt(PropertiesUtil.getKeyValue("scrollVerticalDirection6")))
				robot.scroll(Integer.parseInt(PropertiesUtil.getKeyValue("scrollVerticalDirection6")), VerticalDirection.DOWN);
				else
					robot.scroll(Integer.parseInt(PropertiesUtil.getKeyValue("scrollVerticalDirection2")), VerticalDirection.DOWN);
				else if(i>Integer.parseInt(PropertiesUtil.getKeyValue("scrollVerticalDirection6")))
					
					robot.scroll(Integer.parseInt(PropertiesUtil.getKeyValue("scrollVerticalDirection2")), VerticalDirection.DOWN);

				//if((i==10)||(i==16)||(i==20)||(i==25)||(i==30)||(i==35))
				//if(i%5==0)	

	}

	public void user_selects_combo_item(String comboBoxId, GenericDto dto) throws InterruptedException, NumberFormatException, IOException {
		Thread taskThread = new Thread(new Runnable() {
			@Override
			public void run() {


				Platform.runLater(new Runnable() {
					@Override
					public void run() {



						ComboBox comboBox= waitsUtil.lookupById(comboBoxId);

						comboBox.getSelectionModel().select(dto); 


					}}); 
			}});



		taskThread.start();
		taskThread.join();
		Thread.sleep(Long.parseLong(PropertiesUtil.getKeyValue("ComboItemTimeWait"))); 

	}


}




