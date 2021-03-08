 package  qa114.runapplication;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.testfx.api.FxRobot;
import org.testfx.api.FxRobotContext;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import io.mosip.registration.config.AppConfig;
import io.mosip.registration.controller.Initialization;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import qa114.pages.LoginPage;
import qa114.utility.WaitsUtil;



/***
 * 
 * @author Neeharika.Garg
 * 
 * First Launch invokes start method and this will launch Registration Client
 * 
 */

public class StartApplication extends Application{

	static Stage primaryStage;
	ApplicationContext applicationContext;
	String upgradeServer = null;
	String tpmRequired = "Y";
	FxRobotContext context;
	Scene scene;
	@Start //2
	public void start(Stage primaryStage) {

		String[] args=new String[2];
		try {
			System.setProperty("java.net.useSystemProxies", "true");
			System.setProperty("file.encoding", "UTF-8");
			io.mosip.registration.context.ApplicationContext.getInstance();
			if (args.length > 1) {
				upgradeServer = args[0];
				tpmRequired = args[1];
				io.mosip.registration.context.ApplicationContext.setTPMUsageFlag(args[1]);
			}

			applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

			System.out.println("Automaiton Script - ApplicationContext has taken");

			Initialization initialization=new Initialization();
			Initialization.setApplicationContext(applicationContext);
			StartApplication.primaryStage=primaryStage;
			initialization.start(primaryStage);
			this.primaryStage=primaryStage;
			System.out.println("----------------------------------REG CLIENT LAUNCHED--------------------");
			
			
		} catch(Throwable e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws Exception {
		System.out.println("Before Launch Line");
		launch(args);//1
		System.out.println("After Launch Line");
	}
}
