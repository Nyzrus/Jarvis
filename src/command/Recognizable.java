package command;

public class Recognizable {

	public boolean isRecognizable(String s){
		if(s.equalsIgnoreCase("hey jarvis open command")){
			return true;
		}else{
			return false;
		}
		
	}
	
}
