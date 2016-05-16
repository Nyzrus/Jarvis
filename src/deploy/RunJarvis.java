package deploy;


import deploySphinx.Components.SetupBase;

public class RunJarvis {

	public static void main(String[] args){
		System.out.println("Loading...");	
	    SetupBase ssb = new SetupBase();
	    ssb.setupUI();
	    ssb.startSphinx("jarvis.config.xml");	
	}
}
