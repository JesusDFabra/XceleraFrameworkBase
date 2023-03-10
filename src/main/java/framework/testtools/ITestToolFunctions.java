package framework.testtools;

import java.awt.image.BufferedImage;
import java.util.List;

import org.openqa.selenium.WebElement;

public interface ITestToolFunctions {

	/**
	 * The method finds the an object in the current screen and returns the object
	 * on screen as a current object knows by Test Tool
	 * 
	 * @param attach
	 *            - the object's identify
	 * @return the attach as a Object
	 */
	public Object guiObject(String attach) throws Exception;

	/**
	 * The method returns the text that contains in specify object
	 * 
	 * @param attach
	 *            - the object's identify
	 * @return captured text
	 * @throws Exception
	 */
	public void attach() throws Exception;

	public void acceptAlert() throws Exception;

	public void acceptAlert(String printPath) throws Exception;

	public String captureCss(String attach) throws Exception;

	/**
	 * The method verifies is the object exists in screen
	 * 
	 * @param Attach
	 *            - the object's identify
	 * @return if the attach exists or not
	 * @throws Exception
	 */

	public String captureCssColor(String attach) throws Exception;

	/**
	 * The method verifies is the object exists in screen
	 * 
	 * @param Attach
	 *            - the object's identify
	 * @return if the attach exists or not
	 * @throws Exception
	 */

	public String captureText(String attach) throws Exception;

	/**
	 * The method verifies is the object exists in screen
	 * 
	 * @param Attach
	 *            - the object's identify
	 * @return if the attach exists or not
	 * @throws Exception
	 */

	public String verificaIndice(List<WebElement> objeto) throws Exception;

	public String VerificaIndice(String Attach) throws Exception;

	public boolean visible(String Attach) throws Exception;

	/**
	 * The method verifies if the object is enabled to test
	 * 
	 * @param attach
	 *            - the object's identify
	 * @return if the object is visible or not
	 */
	public boolean exists(String Attach) throws Exception;

	public boolean exists(String Attach, int timeout) throws Exception;

	/**
	 * The method verifies if the object is enabled to test
	 * 
	 * @param attach
	 *            - the object's identify
	 * @return if the object is enabled or not
	 */
	public boolean enabled(String attach) throws Exception;

	/**
	 * The method verifies if the object is disabled
	 * 
	 * @param attach
	 * @return if the object is disabled or not
	 * @throws Exception
	 */
	public boolean disabled(String attach) throws Exception;

	/**
	 * The method launches an application
	 * 
	 * @param app
	 *            - application's name
	 */
	public boolean checked(String attach) throws Exception;

	/**
	 * The method launches an application
	 * 
	 * @param app
	 *            - application's name
	 */
	public void openApp(String app) throws Exception;

	/**
	 * The method launches an bowser application in a mobile device
	 * 
	 * @param url
	 *            - Web site URL
	 */
	public void openDeviceBrowser(String url) throws Exception;

	/**
	 * The method clicks in a specific coordinates on attach
	 * 
	 * @param objectAttach
	 * @param x
	 *            - coordinate x
	 * @param y
	 *            - coordinate y
	 */
	public void click(String objectAttach, int x, int y) throws Exception;

	/**
	 * The method clicks in a specific object by attach
	 * 
	 * @param objectAttach
	 */
	public void click(String objectAttach) throws Exception;

	/**
	 * The method clicks two times in specific object by attach
	 * 
	 * @param objectAttach
	 *            - the object's identify
	 */
	public void doubleClick(String objectAttach) throws Exception;

	/**
	 * The method clicks two times in specific coordinate in object
	 * 
	 * @param objectAttach
	 *            - the object's identify
	 * @param x
	 *            - coordinate x
	 * @param y
	 *            - coordinate y
	 */
	public void doubleClick(String objectAttach, int x, int y) throws Exception;

	/**
	 * The method clears a field
	 * 
	 * @throws Exception
	 */
	public void clearField(String attach) throws Exception;

	/**
	 * The method sets a text in specific object
	 * 
	 * @param objectAttach
	 *            - the object's identify
	 * @param value
	 *            - the text that you want to insert in the field
	 * @throws Exception
	 */
	public void setValue(String objectAttach, String value) throws Exception;

	/**
	 * This method is responsible for select a value from ComboBox. The methods has
	 * been elaborated for attends all of devices, for this it uses the native
	 * methods from class Client.
	 * 
	 * @param attach
	 *            - the object's identify
	 * @param value
	 *            - the text that you want to select
	 * @throws Exception
	 */
	public void selectValue(String attach, String tipo, String value) throws Exception;

	/**
	 * The method checks the field by the current status
	 * 
	 * @param Attach
	 *            - the object's identify
	 * @param checkedAction
	 * @throws Exception
	 */
	public void checkBox(String Attach, Boolean checkedAction) throws Exception;

	/**
	 * The method focus in a specific object
	 * 
	 * @param attach
	 * @throws Exception
	 */
	public void setFocus(String attach) throws Exception;

	/**
	 * The method maximizes the window
	 * 
	 * @param attach
	 * @throws Exception
	 */
	public void maximazedWindow(String attach) throws Exception;

	/**
	 * The method moves the cursor in a specific coordinate on attach
	 * 
	 * @param attach
	 * @param x
	 * @param y
	 * @throws Exception
	 */
	public void mouseMove(String attach, int x, int y) throws Exception;

	/**
	 * The method closes the current window
	 * 
	 * @param attach
	 * @throws Exception
	 */
	public void closeWindow(String attach) throws Exception;

	public void closeWindow() throws Exception;

	/**
	 * The method selects a text in a specific attach
	 * 
	 * @param attach
	 */
	public void textSelect(String attach) throws Exception;

	/**
	 * The method selects a specific id in a table
	 * 
	 * @param attach
	 * @param Valor
	 * @param event
	 * @throws Exception
	 */
	public void selectTdTable(String attach, String Valor, String event) throws Exception;

	/**
	 * The method captures a print of current screen
	 */
	public void capturePrint() throws Exception;

	/**
	 * The method captures a print of current screen using thread
	 */
	public void capturePrintThread() throws Exception;

	public void waitForPageLoaded() throws Exception;

	public void hover(String locator) throws Exception;

	/**
	 * This method waits an object until the timeout
	 * 
	 * @param obj
	 *            - the object's identify
	 * @param sec
	 *            - the timeout
	 * @return true if the object exists or false if the wait failed
	 * @throws Exception
	 */
	public boolean waitObject(String attach, String sec) throws Exception;

	/**
	 * This method closes applications.
	 * 
	 * @param application
	 */
	public void closeExe(String application) throws Exception;

	/**
	 * This method drags objects to another position
	 * 
	 * @param attach
	 *            - Object to move
	 * @param x
	 * @param y
	 */
	public void drag(String attach, int x, int y) throws Exception;

	/**
	 * This method scroll pages.
	 * 
	 * @param direction
	 *            - Direction to scroll the page
	 * @param offSet
	 *            - How much the screen should be moved
	 * @time how long the action will take to execute
	 */
	public void scroll(String direction, int offSet, int time) throws Exception;

	/**
	 * This method scroll pages.
	 * 
	 * @param attach
	 *            - Object to move
	 * @param direction
	 *            - Direction to scroll the page
	 * @param offSet
	 *            - How much the screen should be moved
	 * @time how long the action will take to execute
	 */
	public void scroll(String attach, String direction, int offSet, int time) throws Exception;

	/**
	 * This method gets an attribute from a object
	 * 
	 * @param attibute
	 *            The attribute that you want verify
	 * @return The object's attribute
	 */
	public String getAttribute(String attach, String attribute) throws Exception;

	public void sendKeys(String attach, String keys) throws Exception;

	public void closeBrowser() throws Exception;

	public void dragAndDrop() throws Exception;

	public void scrollToObject(String attachStr) throws Exception;

	public String captureCss(String attach, String attribute) throws Exception;

	public String captureCssColor(String attach, String attribute) throws Exception;

	public Object getDataGrid(String starcAttach) throws Exception;

	public WebElement ReturnElement(String attach) throws Exception;

	public WebElement returnElement(WebElement obj, String attach, String type) throws Exception;

	public List<WebElement> returnList(String attach) throws Exception;

	public void switchTab() throws Exception;

	public String getLocation(String attach) throws Exception;

	public void toURL(String string) throws Exception;

	public String captureSelectedOption(String attach) throws Exception;

	public List<WebElement> returnSelectOptions(String attach) throws Exception;

	public int returnSize(String attach) throws Exception;

	public void pinchZoom() throws Exception;

	public boolean setIframe(String attach) throws Exception;

	public void switchTo(String id) throws Exception;

	public void switchTo() throws Exception;

	public void waitVisibilityOfObject(String attach, boolean visibility) throws Exception;

	public void switchTab(Integer tabIndex) throws Exception;

	public void highlight(String attach) throws Exception;

	public BufferedImage takeScreenshot(String printPath) throws Exception;

	public byte[] takeScreenshotForCucumberReport();

	public boolean isAlertPresent() throws Exception;

	public void closeDriver();

	public void checkAlert(String value) throws Exception;

	public void acessaMenu(String locator) throws Exception;

	public String getAllTextFromPage() throws Exception;

	public void setWindowHandled() throws Exception;

	public void waitObjectToVanish(String locator, int timeOutInSeconds) throws Exception;

	public void WaitObjectVisibility(String locator, int timeOutInSeconds) throws Exception;

	public List<WebElement> getElements(String locator) throws Exception;

	public void saveFile() throws Exception;

	public void switchWindow() throws Exception;

	public void switchWindowSinistro() throws Exception;

	public void switchWindowSinistro2() throws Exception;

	public boolean switchWindowEfetivacao() throws Exception;

	public void getShadowRootElement(String locator) throws Exception;

	public void releaseShadowRootElement() throws Exception;

	public void submit(String locator) throws Exception;

	public void showElements() throws Exception;

	public void switchToFrameWithElement(String locator) throws Exception;

}
