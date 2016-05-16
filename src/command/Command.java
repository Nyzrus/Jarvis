package command;

import java.io.IOException;

public class Command {
	
	private String commandAction;
	private String commandObject;
	
	public Command(){
		
	}
	
	public Command(String action, String object) {
		this.commandAction = commandAction;
		this.commandObject = commandObject;
	}
	
	public String getCommandAction() {return null;}
	
	public String getCommandObject(){return null;}
	
	public String[] getCommandParameters(){return null;}

	public void setCommandAction(String commandAction){}
	
	public void setCommandObject(String commandObject){}
	
	public void setCommandParameters(String[] s){}
	
	public boolean executeCommand() throws IOException{return false;}
	
}
