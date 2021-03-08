package qa114.pages;

import java.io.IOException;

import org.testfx.api.FxRobot;

import com.itextpdf.text.log.SysoCounter;

import javafx.scene.input.KeyCode;
import qa114.utility.PropertiesUtil;
import qa114.utility.RobotActions;
import qa114.utility.WaitsUtil;

public class UploadPacketPage {

	FxRobot robot;
	WaitsUtil waitsUtil;
	String filterField="#filterField";
	String approvalBtn="#approvalBtn";
	String authenticateBtn="#authenticateBtn";
	String uploaded="UPLOADED";
	RobotActions robotActions;
	UploadPacketPage(FxRobot robot)
	{this.robot=robot;
	waitsUtil=new WaitsUtil(robot);
	robotActions=new RobotActions(robot);
	}
	
	public void clickOnfilterField(FxRobot robot)
	{
		waitsUtil.clickNodeAssert( filterField);
	}

	public void enterFilterDetails(String rid) {
		// TODO Auto-generated method stub
		robot.write(rid);
	}

	public void clickOnApprovalBtn(FxRobot robot)
	{
		waitsUtil.clickNodeAssert( approvalBtn);
	}
	
	public AuthenticationPage clickOnAuthenticateBtn(FxRobot robot)
	{
		waitsUtil.clickNodeAssert( authenticateBtn);
		return new AuthenticationPage(robot);
	}

	public void selectPacket(String rid) {
		waitsUtil.clickNodeAssert( "#filterField");
		robot.write(rid);
		
		waitsUtil.clickNodeAssert( rid);
		robot.press(KeyCode.TAB).release(KeyCode.TAB);
		robot.press(KeyCode.TAB).release(KeyCode.TAB);
		robot.press(KeyCode.SPACE).release(KeyCode.SPACE);

		
	}

	public Boolean verifyPacketUpload(String rid) throws InterruptedException  {
		Boolean result=false;

		try {
			Thread.sleep(Long.parseLong(PropertiesUtil.getKeyValue("PacketUploadTimeWait")));
			waitsUtil.clickNodeAssert( uploaded);
			
			waitsUtil.clickNodeAssert( rid);
			result=true;
			robotActions.closeWindow();
		} catch (Exception e) {
			System.out.println("Failure Unable to upload");
			result=false;
			e.printStackTrace();
		} 
		
		
		return result;

	}
	
	
	
}
