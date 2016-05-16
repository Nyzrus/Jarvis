package command;

import java.io.IOException;

public class RunCommand {

	public static boolean executeCommand(Command command) throws IOException{
		return command.executeCommand();
	}
	
}
