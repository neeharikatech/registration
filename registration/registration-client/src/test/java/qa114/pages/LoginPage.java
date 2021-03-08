package qa114.pages;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.testfx.api.FxRobot;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import qa114.runapplication.StartApplication;
import  qa114.utility.WaitsUtil;

public class LoginPage {

	FxRobot robot;
	Stage applicationPrimaryStage;
	Scene scene;
	Node node;

	TextField userIdTextField;
	String userId="#userId";

	TextField passwordTextField;
	String password="#password";
	
	String loginScreen="#loginScreen";	
	String homeSelectionMenu="#homeSelectionMenu";
	String logout="#logout";
	
	WaitsUtil waitsUtil;
	
	public LoginPage(FxRobot robot, Stage applicationPrimaryStage,Scene scene)
	{
		this.robot=robot;
		this.applicationPrimaryStage=applicationPrimaryStage;
		this.scene=scene;
		waitsUtil=new WaitsUtil(robot);
	}

	public LoginPage(FxRobot robot)
	{
		this.robot=robot;
		waitsUtil=new WaitsUtil(robot);
		waitsUtil.clickNodeAssert( loginScreen);
	}
	
	
	public String getUserId() {
		return userIdTextField.getText();
	}

	public void setUserId(String userIdText) {
		
		userIdTextField=waitsUtil.lookupByIdTextField(userId, robot);
		
		assertNotNull(userIdTextField, "userIdTextField not present");
		
		robot.clickOn(userIdTextField);
		
		userIdTextField.clear();
		userIdTextField.setText(userIdText);
		System.out.println("User id Entered ");
		
		assertEquals(userIdText, userIdTextField.getText(),"User id is not as same as entered");
		


		robot.press(KeyCode.ENTER).release(KeyCode.ENTER);
		
		
	}


	public HomePage setPassword(String pwd) {
		
		passwordTextField=waitsUtil.lookupByIdTextField(password, robot);

		assertNotNull(passwordTextField, "passwordTextField Not Present");
		
		robot.clickOn(passwordTextField);
		
		passwordTextField.setText(pwd);
		
		robot.press(KeyCode.ENTER).release(KeyCode.ENTER);
		
		return new HomePage(robot);

	}


	public void loadLoginScene(Stage applicationPrimaryStage) throws InterruptedException
	{
		System.out.println("In Login test Loaded");
		
		//this.robot=robot;
		waitsUtil.clickNodeAssert(loginScreen);
		
		
		scene=applicationPrimaryStage.getScene();
		node=scene.lookup(loginScreen);
		while(node.isDisable())
			{Thread.sleep(500);
				System.out.println("Disable login screen waiting to get it on");
			}
		assertNotNull(node,"Login Page is not shown");
		System.out.println("Sceen Loaded");
		

		
	}
	
	public void logout()
	{
		/**
		 * Click Menu
		 * Logout
		 */
		waitsUtil.clickNodeAssert( homeSelectionMenu);
		
		waitsUtil.clickNodeAssert(logout);
		
	
	}

}
