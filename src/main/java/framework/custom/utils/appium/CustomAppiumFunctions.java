package framework.custom.utils.appium;

import java.util.Set;

import framework.testtools.utils.SeleniumUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;

public class CustomAppiumFunctions {

	public static AppiumDriver<WebElement> _driver;

	public CustomAppiumFunctions(AppiumDriver<WebElement> driver){
		_driver = driver;
	}
	
	public void sendKeys(String text) {
		
		new Actions(_driver).sendKeys(text).perform();
	}
	
	public boolean switchToWebContext() {
		
        Set<String> contexts = _driver.getContextHandles();
        for (String context : contexts) {
            System.out.println(context);
            if (context.contains("WEBVIEW")) {
                _driver.context(context);
                System.out.println("Context switched to: "+context);
                return true;
            }
        }
        
        return false;
	}
	
	public boolean switchToNativeContext() {
		Set<String> contexts = _driver.getContextHandles();
        for (String context : contexts) {
            System.out.println(context);
            if (context.contains("NATIVE")) {
                _driver.context(context);
                return true;
            }
        }
        return false;
	}
	
	public static WebElement FindObject(String locator) throws Exception{
		WebElement element = SeleniumUtils.findObject(_driver, locator, null, 60);
		if(element == null)
			throw new Exception("Element " + locator + " not found");
		return element;
	}
	
	public boolean activateProcess(String appPackage) {
		try {
			_driver.activateApp(appPackage);
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public boolean killProcess(String appPackage) {
		try {
			_driver.terminateApp(appPackage);
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public void clickElementInPosition(String locator,String x,String y) throws Exception {
		WebElement element = FindObject(locator);
		int X = Integer.parseInt(x);
        int Y = Integer.parseInt(y);
     
		if(X != 0 && Y != 0) {
			// Calcular el 80% de la altura del elemento
	        int porcentajeAltura = (element.getSize().height * Y )/ 100;

	        // Calcular el 80% de la anchura del elemento
	        int porcentajeAncho = (element.getSize().width * X )/ 100;
	        
			TouchAction<?> touch = new TouchAction<>(_driver);
				touch.tap(PointOption.point(element.getLocation().getX() + porcentajeAncho, element.getLocation().getY() + porcentajeAltura))
				//touch.tap(PointOption.point(element.getLocation().getX() + X, element.getLocation().getY() + Y))
                //.release()
                .perform();
		}else
			//clickElementInPosition(locator,x,y);
			throw new Exception("Valores de porcentajes incorrectos");
 
	}
	
	public void fullScrollVertical(String direction, int swipes) throws Exception {
		try {
			WebElement element = null;
			//WebElement element = SeleniumUtils.findObject(_driver, locator, null, 60);

			String cont;
			cont = _driver.getContext();
			System.out.println(cont);
			Dimension size;
			_driver.context("NATIVE_APP");
			size = _driver.manage().window().getSize();
			System.out.println(size);
			int starty;
			int endy;
			int intentos = 0;

			while (intentos < swipes) { //element == null && intentos < 10
				switch (direction.toLowerCase()) {

					case "up":
						endy = (int) (size.height * 0.80);
						starty= endy - size.height/4;
						break;
					case "down":
						starty = (int) (size.height * 0.60);
						endy= starty - size.height/4;
						break;
					default:
						starty = (int) (size.height * 0.60);
						endy= starty - size.height/4;
						break;
				}
				TouchAction action = new TouchAction(_driver);
				action.press(PointOption.point(size.width / 2, starty))
						.waitAction(new WaitOptions().withDuration(Duration.ofMillis(2000))) //you can change wait durations as per your requirement
						.moveTo(PointOption.point(size.width / 2, endy))
						.release()
						.perform();
				intentos++;
				System.out.println("Intentos: " + intentos);
			}

		} catch (Exception e) {

			throw e;
		}
	}

		public void scrollToObject(String locator, String direction) throws Exception {
			try {
				WebElement element = null;
				//WebElement element = SeleniumUtils.findObject(_driver, locator, null, 60);

				String cont;
				cont=_driver.getContext();
				System.out.println(cont);
				Dimension size;
				_driver.context("NATIVE_APP");
				size = _driver.manage().window().getSize();
				System.out.println(size);
				int starty;
				int endy;
				int intentos=0;

				System.out.println("Elemento: "+element);

				while(element == null && intentos < 20) { //element == null && intentos < 20
					System.out.println("entro whle");
					switch(direction.toLowerCase()){

						case "up":
							endy = (int) (size.height * 0.80);
							starty= endy - size.height/4;
							break;
						case "down":
							starty = (int) (size.height * 0.60);
							endy= starty - size.height/4;
							break;
						default:
							starty = (int) (size.height * 0.80);
							endy= starty - size.height/4;
							break;
					}
					System.out.println("4");
					TouchAction action = new TouchAction(_driver);
					action.press(PointOption.point(size.width / 2,starty))
							.waitAction(new WaitOptions().withDuration(Duration.ofMillis(2000))) //you can change wait durations as per your requirement
							.moveTo(PointOption.point(size.width / 2, endy))
							.release()
							.perform();
					System.out.println(element);
					System.out.println(locator);
					element = FindObject(locator);
					intentos++;
					System.out.println("Intentos: " + intentos);
				}

			} catch (Exception e) {

				throw e;
				}
		
		}
		

	}
