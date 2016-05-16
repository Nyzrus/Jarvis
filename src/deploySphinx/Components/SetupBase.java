package deploySphinx.Components;

import java.awt.BorderLayout;
import java.awt.Font;
import java.io.IOException;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import command.Command;
import command.RunCommand;
import command.UnixCommand;
import deploy.RunJarvis;
import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;
import edu.cmu.sphinx.util.props.PropertyException;

public class SetupBase {

	private JLabel info;
	private JLabel commandLabel;
	private String commandAction;
	private String commandObject;
	
	
	public void setupUI(){
		UserVariables var = UserVariables.getInstance();
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
				JFrame frame = new JFrame("");
		        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        
		        
		        JPanel mainPanel = new JPanel();
		        mainPanel.setLayout(new BorderLayout());
		        JLabel jarvis = new JLabel("Jarvis");
		        info = new JLabel("loading...");
		        commandLabel = new JLabel("");
		        mainPanel.add(jarvis, BorderLayout.CENTER);
		        mainPanel.add(info, BorderLayout.SOUTH);
		        mainPanel.add(commandLabel, BorderLayout.EAST);
		        jarvis.setFont(new Font("Serif", Font.PLAIN, 32));
		        info.setFont(new Font("Serif", Font.PLAIN, 14));
		        jarvis.setHorizontalAlignment(0);
		        info.setHorizontalAlignment(0);
		        
		        mainPanel.setBorder(new EmptyBorder(0,0,30,0));
		        
		        frame.add(mainPanel);
		        
		        frame.setSize(300, 200);
		
		        frame.setLocationRelativeTo(null);
		        frame.setVisible(true);
		    }
		  });
	}
	
	public void updateInfoLabel(final String update){
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	info.setText(update);
		    }
		  });	
	}
	
	
	private class Sphinx implements Runnable{
		private URL url;
		private ConfigurationManager cm;
		private Recognizer attentionRecognizer;
		private Recognizer actionRecognizer;
		private Recognizer objectRecognizer;
		private Recognizer executeRecognizer;
		private Microphone microphone;
		private Result result;
		
		public Sphinx(String configFile){
			this.url = RunJarvis.class.getResource(configFile);
		}
		
		private Result changeState(int newState, String rawInput){
			switch(newState){
				case 0:
					result = attentionRecognizer.recognize();
					info.setText("");
					break;
				case 1:
					result = actionRecognizer.recognize();
					break;
				case 2:
					result = objectRecognizer.recognize();
					commandAction = rawInput;
					break;
				case 3:
					result = executeRecognizer.recognize();
					commandObject = rawInput;
					break;
				case -1:
					result = null;
					break;
				default:
					result = attentionRecognizer.recognize();
					break;
			}
			return result;
		}

		@Override
		public void run() {
			try{
		        cm = new ConfigurationManager(url);
		
			    attentionRecognizer = (Recognizer) cm.lookup("attentionRecognizer");
			    attentionRecognizer.allocate();
			    
			    actionRecognizer = (Recognizer) cm.lookup("actionRecognizer");
			    actionRecognizer.allocate();
			    
			    objectRecognizer = (Recognizer) cm.lookup("objectRecognizer");
			    objectRecognizer.allocate();
			    
			    executeRecognizer = (Recognizer) cm.lookup("executeRecognizer");
			    executeRecognizer.allocate();
			    
			    microphone = (Microphone) cm.lookup("microphone");
			    
				JarvisVoice jv = new JarvisVoice();
			    if (microphone.startRecording()) {
					System.out.println
					    ("Get Jarvis' attention");
					info.setText("listening...");
					String resultText = "";
					int state = 0;
					while(!(state==-1)){
						result = changeState(state, resultText);
						if(result != null) {
							resultText = result.getBestFinalResultNoFiller();
							System.out.println("You said: " + resultText + "\n");
							info.setText(info.getText() + " " + resultText);
						}
						
						if((!resultText.equals("cancel")) && state < 3){
							jv.jarvisSpeak(state);
							state++;
						}else{
							state = 0;
							if(!resultText.equals("cancel")){
								try{
									if(System.getProperty("os.name").equalsIgnoreCase("Mac OS X")){
										RunCommand.executeCommand(new UnixCommand(commandAction, commandObject));
									}else if(System.getProperty("os.name").equalsIgnoreCase("Windows XP")){
										//RunCommand.executeCommand(new WindowsCommand(commandAction, commandObject));
									}
								}catch(IOException ex){
									jv.jarvisSpeak(-1);
								}
							}
							info.setText("listening...");
						}
						
					}
					jv.closeJarvisVoice();
			    } else {
					System.out.println("Cannot start microphone.");
					attentionRecognizer.deallocate();
					System.exit(1);
			    }
			} catch (IOException e) {
		        System.out.println("Problem when executing: " + e);
		        e.printStackTrace();
		    } catch (PropertyException e) {
		        System.err.println("Problem configuring Jarvis: " + e);
		        e.printStackTrace();
		    } catch (InstantiationException e) {
		        System.err.println("Problem creating Jarvis: " + e);
		        e.printStackTrace();
		    }
			
		}
					
	}
	
	public void startSphinx(String configFile){
		Runnable sphinx = new Sphinx(configFile);
		Thread sphinxThread = new Thread(sphinx);
		sphinxThread.start();
	}
	
	private class JarvisVoice {
		
		private Voice voice;
		private VoiceManager vm;
		
		public JarvisVoice(){
			this.vm = VoiceManager.getInstance();
			voice = vm.getVoice("kevin16");
			voice.allocate();
		}
		
		private void jarvisSpeak(int state){
			switch(state){
				case -1:
					voice.speak("Unfortunately I cannot do that at the moment");
					break;
				case 0:
					voice.speak("Yes sir?");
					break;
				case 1:
					voice.speak("Which application?");
					break;
				case 2:
					voice.speak("Confirm your command");
					break;
				case 3:
					voice.speak("Right away sir");
					break;
				default:
					break;
			}
		}
	
		private void closeJarvisVoice(){
			voice.deallocate();
		}
		
	}
	
}
