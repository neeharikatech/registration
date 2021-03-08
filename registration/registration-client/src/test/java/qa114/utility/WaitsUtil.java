package qa114.utility;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.concurrent.TimeUnit;

import org.awaitility.Awaitility;
import org.testfx.api.FxRobot;
import org.testfx.service.support.Capture;

import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
/**
 * 
 * @author Neeharika.Garg
 *@References https://www.codota.com/code/java/classes/org.awaitility.Awaitility
 */
public class WaitsUtil {
	Node node;
	FxRobot robot;

	public WaitsUtil(FxRobot robot) {
		this.robot=robot;
	}

	public <T extends Node> T lookupById( String controlId) {
		try {
			Awaitility
			.with().pollInSameThread()
			.await()
			.atMost(60, TimeUnit.SECONDS) // Read config 
		.until(() -> robot.lookup(controlId).query() != null);
		}catch(Exception e)
		{
			e.printStackTrace();
			System.out.println(controlId+"NOT PRESENT");
			Rectangle2D r=new Rectangle2D(0, 0, 600, 700);
			Capture c=robot.capture(r);
			c.getImage();
		}
		return robot.lookup(controlId).query();

	}


	public void clickNodeAssert(String id)
	{	try {
		node=lookupById(id);
		robot.clickOn(node);
		assertNotNull(node," "+id+" is not present");
	}catch(Exception e)
	{
		System.out.println(id+"NOT PRESENT");
		
		e.printStackTrace();
		Rectangle2D r=new Rectangle2D(0, 0, 600, 700);
		Capture c=robot.capture(r);
		c.getImage();
		
		
	}

	}



	public <T extends Node> TextField lookupByIdTextField( String controlId,FxRobot robot) {
try {
		Awaitility
		.with().pollInSameThread()
		.await()
		.atMost(60, TimeUnit.SECONDS) // Read config 
		.until(() -> (robot.lookup(controlId).queryAs(TextField.class))!= null);
	}catch(Exception e)
	{
		System.out.println(controlId+"NOT PRESENT");
		
		e.printStackTrace();
		Rectangle2D r=new Rectangle2D(0, 0, 600, 700);
		Capture c=robot.capture(r);
		c.getImage();
		
		
	}

		return robot.lookup(controlId).queryAs(TextField.class);

	}


	public <T extends Node> Button lookupByIdButton( String controlId,FxRobot robot) {
try {
		Awaitility
		.with().pollInSameThread()
		.await()
		.atMost(60, TimeUnit.SECONDS)
		.until(() -> (robot.lookup(controlId).queryAs(Button.class))!= null);
	}catch(Exception e)
	{
		System.out.println(controlId+"NOT PRESENT");
		
		e.printStackTrace();
		Rectangle2D r=new Rectangle2D(0, 0, 600, 700);
		Capture c=robot.capture(r);
		c.getImage();
		
		
	}

		return robot.lookup(controlId).queryAs(Button.class);

	}


}
