package framework.custom.utils.autoIt;

import java.io.File;
import java.io.IOException;

import com.jacob.com.LibraryLoader;

import autoitx4java.AutoItX;
import framework.utils.Utils;

public class CustomAutoItFunctions {
	
	private AutoItX autoIt;
	
	private AutoItObj autoItObj = new AutoItObj();
	
	public CustomAutoItFunctions() {
		try {
			if (autoIt == null) {
				File file = new File("lib", "jacob-1.19-x64.dll"); // path to the jacob dll
				System.setProperty(LibraryLoader.JACOB_DLL_PATH, file.getAbsolutePath());

				//autoIt = new AutoItX();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void ConfigAutoIt(String locator) {
		autoItObj.captureAttach(locator);
		autoIt.winWait(autoItObj.title);
		autoIt.winActivate(autoItObj.title);
	}
	
	class AutoItObj {
		public String title;
		public String text;
		public String controlID;

		public void setAutoItObj(String title, String text, String controlID) {
			this.title = title;
			this.text = text;
			this.controlID = controlID;
		}

		public void captureAttach(String locator) {
			 this.title = Utils.CaptureProp(locator, "TITLE");
			 this.text = Utils.CaptureProp(locator, "TEXT");
			 this.controlID = Utils.CaptureProp(locator, "CONTROLID");
		}
	}
	
	public void winWait(String locator, int seconds) throws Exception {
		ConfigAutoIt(locator);
		if(!autoIt.winWait(autoItObj.title, autoItObj.text, seconds))
			throw new Exception("[winWait]: Tiempo de espera superado.");
	}
	
	public void sendKeys(String keys, boolean isCommand) {
		
		autoIt.send(keys, isCommand);
	}
	
	public void winSetState(String locator, String state) {
		
		int sw = 1;
		if(state.equals("esconder ventana"))
			sw = autoIt.SW_HIDE;
		if(state.equals("maximizar ventana"))
			sw = autoIt.SW_SHOWMAXIMIZED;
		
		ConfigAutoIt(locator);
		autoIt.winSetState(autoItObj.title, autoItObj.text, sw);
	}
	
	public void mouseClick(String button, int x, int y, int clicks, int speed) {
		
		autoIt.mouseClick(button, x, y, clicks, speed);
	}
	
	public void taskKill(String program) throws InterruptedException, IOException {
		
		Runtime.getRuntime().exec("taskkill /f /im "+ program +" /t").waitFor();
	}
	
	public String getText(String locator) {
		ConfigAutoIt(locator);
		return autoIt.controlGetText(autoItObj.title, autoItObj.text, autoItObj.controlID);
	}

}
