package deploySphinx.Components;

import java.util.Properties;

public class UserVariables {

	private static Properties userProperties;
	private static UserVariables userVariables = new UserVariables();
	
	private UserVariables(){
	}
	
	public void getOS(){
		//return 
	}
	
	public static UserVariables getInstance(){
		userProperties = new Properties();
		Properties properties = new Properties();
		properties.put("userDir", System.getProperty("user.dir"));
		properties.put("userOS", System.getProperty("os.name"));
		return userVariables;
	}
}
