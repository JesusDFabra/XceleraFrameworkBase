package framework.custom;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
//import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.MaskFormatter;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.xb.xsdschema.FieldDocument.Field.Xpath;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

//import autoitx4java.AutoItX;
import framework.data.entities.Procedure;
import framework.enums.TypeError;
import framework.helpers.ExecutionStatusHelper;
import framework.helpers.GeneralHelper;
import framework.helpers.ScreenshotHelper;
import framework.testtools.AutoItFunctions;
import framework.testtools.ITestToolFunctions;
import framework.testtools.SeleniumFunctions;
import framework.testtools.utils.SeleniumUtils;
import io.cucumber.core.exception.ExceptionUtils;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Custom {
	private ITestToolFunctions _testToolFunctions;
	private AutoItFunctions _autoItFunctions;

	private Map<String, String> _executionConfigs;
	private ScreenshotHelper _screenshotHelper;

	private Procedure _procedure;
	private Map<String, String> _attributes;
	private String _valueFromTestProcedure;
	private String _locator;
	boolean isWindows = System.getProperty("os.name").toLowerCase().contains("windows");


	public Custom(ITestToolFunctions testToolFunctions, Map<String, String> executionConfigs,
			ScreenshotHelper screenshotHelper) {
		_testToolFunctions = testToolFunctions;
		_autoItFunctions = new AutoItFunctions();
		_executionConfigs = executionConfigs;
		_screenshotHelper = screenshotHelper;

	}

	public void executeAction(String repositoryType, Procedure procedure) throws Exception {
		_procedure = procedure;
		_attributes = GeneralHelper.getMapAttributes(_procedure);
		_valueFromTestProcedure = _procedure.Value != null && !_procedure.Value.isEmpty() ? _procedure.Value : "";
		_locator = GeneralHelper.getLocatorProcedure(Integer.parseInt(_executionConfigs.get("System.Locator")),
				_procedure);

		switch ((int) procedure.MethodId) {
		case 448:
			acceptAlert();
			break;
		case 449:
			toUrl();
			break;
		case 531:
			clickAutoIt();
			break;
		case 532:
			setValueAutoIt();
		default:
			break;
		}
	}

	private void setValueAutoIt() throws Exception {
		try {
			_autoItFunctions.setValue(_locator, _valueFromTestProcedure);
		} catch (Exception e) {
			String methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();
			String comment = GeneralHelper.getCommentError(methodName, e.getMessage());

			ExecutionStatusHelper helper = new ExecutionStatusHelper();
			helper.setStatusErrorOnProcedure(_procedure, TypeError.OperationalError, comment);

			throw e;
		}
		
	}

	private void clickAutoIt() throws Exception {
		try {
			_autoItFunctions.click(_locator);
		} catch (Exception e) {
			String methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();
			String comment = GeneralHelper.getCommentError(methodName, e.getMessage());

			ExecutionStatusHelper helper = new ExecutionStatusHelper();
			helper.setStatusErrorOnProcedure(_procedure, TypeError.OperationalError, comment);

			throw e;
		}
	}

	private void acceptAlert() throws Exception {
		try {
			_testToolFunctions.acceptAlert();
		} catch (Exception e) {
			String methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();
			String comment = GeneralHelper.getCommentError(methodName, e.getMessage());

			ExecutionStatusHelper helper = new ExecutionStatusHelper();
			helper.setStatusErrorOnProcedure(_procedure, TypeError.OperationalError, comment);

			throw e;
		}
	}

	private void toUrl() throws Exception {
		try {
			String url = _attributes.get("URL");
			_testToolFunctions.toURL(url);
			_testToolFunctions.showElements();

		} catch (Exception e) {
			String methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();
			String comment = GeneralHelper.getCommentError(methodName, e.getMessage());

			ExecutionStatusHelper helper = new ExecutionStatusHelper();
			helper.setStatusErrorOnProcedure(_procedure, TypeError.OperationalError, comment);

			throw e;
		}
	}

}
