package qa114.pages;

import org.testfx.api.FxRobot;

import qa114.utility.WaitsUtil;

public class EodApprovalPage {

	FxRobot robot;
	WaitsUtil waitsUtil;
	String filterField="#filterField";
	String approvalBtn="#approvalBtn";
	String authenticateBtn="#authenticateBtn";
	EodApprovalPage(FxRobot robot)
	{this.robot=robot;
	waitsUtil=new WaitsUtil(robot);
	}
	
	public void clickOnfilterField()
	{
		waitsUtil.clickNodeAssert( filterField);
	}

	public void enterFilterDetails(String rid) {
		// TODO Auto-generated method stub
		robot.write(rid);
	}

	public void clickOnApprovalBtn()
	{
		waitsUtil.clickNodeAssert( approvalBtn);
	}
	
	public AuthenticationPage clickOnAuthenticateBtn()
	{
		waitsUtil.clickNodeAssert( authenticateBtn);
		return new AuthenticationPage(robot);
	}
	
	
	
}
