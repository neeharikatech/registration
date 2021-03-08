package qa114.controls;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.testfx.api.FxRobot;

import javafx.scene.control.Button;
import qa114.utility.WaitsUtil;

public class Buttons {
	String continueBtn="#continueBtn",
			backBtn="#backBtn",
			Confirm="Confirm",
			uploadBtn="#uploadBtn";


	WaitsUtil waitsUtil;
	FxRobot robot;
	public Buttons(FxRobot robot)
	{
		this.robot=robot;
		waitsUtil=new WaitsUtil(robot);
	}

	public void clickContinueBtn()
	{
		waitsUtil.clickNodeAssert( continueBtn);
	}

	public void clickBackBtn()
	{
		waitsUtil.clickNodeAssert( backBtn);
	}

	public void clickConfirmBtn()
	{
		waitsUtil.clickNodeAssert( Confirm);
	}
	public void clickuploadBtnBtn()
	{
		waitsUtil.clickNodeAssert( uploadBtn);
	}
}
