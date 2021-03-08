package qa114.pages;

import org.testfx.api.FxRobot;

import qa114.utility.WaitsUtil;

public class AuthenticationPage {
	FxRobot robot;
	WaitsUtil waitsUtil;
	String AuthenticationImg="#AuthenticationImg";
	String password="#password";
	String username="#username";
	String submitbtn="#submitbtn";

	public AuthenticationPage(FxRobot robot)
	{
		this.robot=robot;
		waitsUtil=new WaitsUtil(robot);
		//waitsUtil.clickNodeAssert(robot, AuthenticationImg);
		 
	}
	
	public void enterPassword(String pwd)
	{
		waitsUtil.clickNodeAssert( password);
		robot.write(pwd);
	}
	
	public void enterUserName(String userid)
	{
		waitsUtil.clickNodeAssert(username);
		robot.write(userid);
	}
	
	public void clicksubmitBtn()
	{
		waitsUtil.clickNodeAssert(submitbtn);
	}

}
