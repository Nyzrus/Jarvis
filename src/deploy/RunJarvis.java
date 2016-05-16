package deploy;


import deploySphinx.Components.SetupBase;
import deploySphinx.Components.UserVariables;

public class RunJarvis {

	public static void main(String[] args){
		System.out.println("Loading...");	
		UserVariables userVars = UserVariables.getInstance();
	    SetupBase ssb = new SetupBase();
	    ssb.setupUI();
	    ssb.startSphinx("jarvis.config.xml");	
	}
}
