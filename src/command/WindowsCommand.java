package command;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class WindowsCommand extends Command{

	private String commandAction;
	private String commandObject;
	private String[] commandParameters;
	private static Map<String, String> commandMap;
	
    static {
        Map<String, String> initMap = new HashMap<String, String>();
        //not implemented
        commandMap = Collections.unmodifiableMap(initMap);
    }
	
	@Override
	public String getCommandAction() {
		//not implemented
		return null;
	}

	@Override
	public String getCommandObject() {
		//not implemented
		return null;
	}

	@Override
	public String[] getCommandParameters() {
		//not implemented
		return null;
	}

	@Override
	public void setCommandAction(String commandAction) {
		//not implemented
		
	}

	@Override
	public void setCommandObject(String commandObject) {
		//not implemented
		
	}

	@Override
	public void setCommandParameters(String[] s) {
		//not implemented
		
	}

	@Override
	public boolean executeCommand() throws IOException {
		//not implemented
		return false;
	}

}
