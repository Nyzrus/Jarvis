package command;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class UnixCommand extends Command{

	private String commandAction;
	private String commandObject;
	private String[] commandParameters;
	private static Runtime rt;
	private static Process pr;
	
	private static final Map<String, String> commandActionMap;
	private static final Map<String, String> commandObjectMap;
	
    static {
        Map<String, String> initActionMap = new HashMap<String, String>();
        initActionMap.put("open", "open -a");
        initActionMap.put("close", "close");
        commandActionMap = Collections.unmodifiableMap(initActionMap);
        
        Map<String, String> initObjectMap = new HashMap<String, String>();
        initObjectMap.put("eclipse", "eclipse");
        initObjectMap.put("command", "terminal");
        initObjectMap.put("internet", "safari");
        commandObjectMap = Collections.unmodifiableMap(initObjectMap);
    }
    
	public UnixCommand(String action, String object){
		super();
		this.commandAction = action;
		this.commandObject = object;
	}
	
	public UnixCommand(String action, String object, String[] commandParameters){
		this(action, object);
		this.commandParameters = commandParameters;
	}

	@Override
	public String getCommandAction() {
		return commandActionMap.get(commandAction);
	}

	@Override
	public String getCommandObject() {
		return commandObjectMap.get(commandObject);
	}

	@Override
	public void setCommandAction(String commandAction) {
		this.commandAction = commandAction;
	}

	@Override
	public void setCommandObject(String commandObject) {
		this.commandObject = commandObject;
	}

	@Override
	public String[] getCommandParameters() {
		return this.commandParameters;
	}

	@Override
	public void setCommandParameters(String[] s) {
		this.commandParameters = s;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(this.getCommandAction()).append(" ").append(this.getCommandObject());
		return sb.toString();
	}

	@Override
	public boolean executeCommand() throws IOException {
		rt = Runtime.getRuntime();
		try {
			pr = rt.exec(this.toString());
			return true;
		}catch(IOException ex){
			throw new IOException();
		}
	}

	
}
