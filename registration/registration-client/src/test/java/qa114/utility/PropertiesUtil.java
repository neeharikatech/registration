package qa114.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {
	
	
public static  String getKeyValue(String key) throws IOException
{
			// create a reader object on the properties file 
			String configFilePath="C:\\Users\\Neeharika.Garg\\gitNeehaFork\\registration\\registration\\registration-client\\src\\test\\resources\\config.properties";
			
			FileReader reader = new FileReader(configFilePath);
			// create properties object 
			Properties p = new Properties(); 

			// Add a wrapper around reader object 
			p.load(reader); 

			// access properties data 
			return p.getProperty(key);
		
}
public static void main(String[] args) throws IOException {
	String value=getKeyValue("PropertyFilePath");
	System.out.println(value);
}

/**
 * The method get env config details
 * 
 * @return properties

private static Properties getRunConfigData() {
	Properties prop = new Properties();
	InputStream input = null;
	try {
		RunConfigUtil.objRunConfig.setUserDirectory();
		input = new FileInputStream(new File(RunConfigUtil.getResourcePath()+"admin/TestData/RunConfig/envRunConfig.properties").getAbsolutePath());
		prop.load(input);
		input.close();
		return prop;
	} catch (Exception e) {
		adminLogger.error("Exception: " + e.getMessage());
		return prop;
	}
}

//Property p=getRunConfigData();
//p.getProperty(lastname); Parse long
//
 */
}
