
package  qa114.testcases;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.testfx.api.FxRobot;
import org.testfx.api.FxRobotContext;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import io.mosip.registration.config.AppConfig;
import io.mosip.registration.controller.Initialization;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.geometry.VerticalDirection;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import qa114.controls.Buttons;
import qa114.pages.AuthenticationPage;
import qa114.pages.BiometricUploadPage;
import qa114.pages.DemographicPage;
import qa114.pages.EodApprovalPage;
import qa114.pages.HomePage;
import qa114.pages.LoginPage;
import qa114.pages.UploadPacketPage;
import qa114.pages.WebViewDocument;
import qa114.pojo.output.*;
import qa114.pojo.schema.Root;
import qa114.pojo.schema.Schema;
import qa114.utility.JsonUtil;
import  qa114.utility.PropertiesUtil;
import qa114.utility.RobotActions;



/***
 * 
 * @author Neeharika.Garg
 * Login and Logout RegClient	
 * Steps Run this using Junit
 * First start method invokes and this will launch Registration Client and through dependency injection
 * 
 * Fxrobot will take control of primary stage and perform keyboard and mouse driven activities.
 *
 */
public class LoginNewRegLogout {

	FxRobot robot;
	Schema schema;
	Root root; 
	Scene scene;
	Node node;
	Boolean flagContinueBtnFileUpload=true;
	Boolean flagContinueBtnBioUpload=true;
	private static ApplicationContext applicationContext;
	private static Stage applicationPrimaryStage;
	private static String upgradeServer = null;
	private static String tpmRequired = "Y";
	LoginPage loginPage;
	HomePage homePage;
	PropertiesUtil propertiesUtil;
	FxRobotContext context;
	Boolean result=false;
	DemographicPage demographicPage;
	BiometricUploadPage biometricUploadPage;
	Buttons buttons;
	WebViewDocument webViewDocument;
	RID rid;
	AuthenticationPage authenticationPage;
	RobotActions robotActions;
	EodApprovalPage eodApprovalPage;
	UploadPacketPage uploadPacketPage;


	@ParameterizedTest
	@CsvFileSource(resources = "/login.csv" , numLinesToSkip = 1)
	public RID newRegistrationAdult(FxRobot robot,String loginUserid,String loginPwd,String supervisorUserid,
			String supervisorUserpwd,Stage applicationPrimaryStage1,JSONObject idjson,HashMap<String, String> documentUpload
			,String lang,String schemaversion,String jsonObjName) throws Exception {

		loginPage=new LoginPage(robot);
		buttons=new Buttons(robot);
		authenticationPage=new AuthenticationPage(robot);	
		robotActions=new RobotActions(robot);

		//Load Login screen
		loginPage.loadLoginScene(applicationPrimaryStage1);

		//Enter userid and password
		loginPage.setUserId(loginUserid);


		homePage=loginPage.setPassword(loginPwd);

		//New Registration

		demographicPage=homePage.clickNewRegistration();
		webViewDocument=demographicPage.scemaDemoDocUpload(schemaversion,lang,idjson,jsonObjName,documentUpload);

		//buttons.clickContinueBtn();

		//webViewDocument=biometricUploadPage.bioUpload(list);

		buttons.clickContinueBtn();

		rid=webViewDocument.acceptPreview(); //return thread and wait on thread

		buttons.clickContinueBtn();


		/**
		 * Authentication enter password
		 * Click Continue 
		 */

		authenticationPage.enterPassword(loginPwd);

		buttons.clickContinueBtn();


		/**
		 * Click Home, eodapprove, approval Button, authenticate button
		 * Enter user details
		 */

		homePage.clickHomeImg();


		eodApprovalPage=homePage.clickeodApprovalImageView( applicationPrimaryStage, scene);
		eodApprovalPage.clickOnfilterField();
		eodApprovalPage.enterFilterDetails(rid.getRid());
		eodApprovalPage.clickOnApprovalBtn();
		authenticationPage=eodApprovalPage.clickOnAuthenticateBtn();
		authenticationPage.enterUserName(supervisorUserid);
		authenticationPage.enterPassword(supervisorUserpwd);
		authenticationPage.clicksubmitBtn();
		robotActions.clickWindow();
		homePage.clickHomeImg();	
		buttons.clickConfirmBtn();

		/**
		 * Upload the packet
		 */

		uploadPacketPage=homePage.clickuploadPacketImageView( applicationPrimaryStage, scene);
		uploadPacketPage.selectPacket(rid.getRid());
		buttons.clickuploadBtnBtn();
		/**
		 * Verify Success Upload
		 */
		result=uploadPacketPage.verifyPacketUpload(rid.getRid());


		//Logout Regclient
		loginPage.logout();


		buttons.clickConfirmBtn();


		rid.setResult(result);
//		try
//		{
//		assertTrue("TestCase Failed",result);
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//		}
return rid;
	}
}

