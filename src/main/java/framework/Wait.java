package framework;

import java.util.Map;

import framework.data.entities.Procedure;
import framework.enums.TypeError;
import framework.helpers.ExecutionStatusHelper;
import framework.helpers.GeneralHelper;
import framework.testtools.ITestToolFunctions;

public class Wait {

	private ITestToolFunctions _testToolFunctions;
	private Map<String, String> _executionConfigs;

	private Procedure _procedure;
	private Map<String, String> _attributes;

	public Wait(ITestToolFunctions testToolFunctions, Map<String, String> executionConfigs) {
		_testToolFunctions = testToolFunctions;
		_executionConfigs = executionConfigs;
	}
	
	public Wait(ITestToolFunctions testToolFunctions) {
		_testToolFunctions = testToolFunctions;
	}

	public void executeAction(String repositoryType, Procedure procedure) throws Exception {

		_procedure = procedure;
		_attributes = GeneralHelper.getMapAttributes(_procedure);

		String locator = "";
		
		if(repositoryType.equals("S"))
			locator = GeneralHelper.getLocatorProcedure(Integer.parseInt(_executionConfigs.get("System.Locator")), procedure);
		else if(repositoryType.equals("M"))
			locator = GeneralHelper.getLocatorProcedure(Integer.parseInt(_executionConfigs.get("Mobile.Locator")), procedure);
		
		int methodId = (int) procedure.MethodId;

		switch (methodId) {
		case 74:
			waitSeconds(GeneralHelper.cNull(_attributes.get("Sec")));
			break;
		case 57:
			waitObject(
					locator, 
					GeneralHelper.cNull(_attributes.get("Sec")), 
					GeneralHelper.cNull(_attributes.get("Condition")), 
					GeneralHelper.cNull(_attributes.get("Identify"))
					);
			break;
		default:
			break;
		}
	}

	private void waitSeconds(String seconds) throws Exception {
		try {

			if (seconds == null || seconds.isEmpty())
				seconds = "5";

			System.out.println("Waiting: " + seconds + " seconds");
			Thread.sleep(Integer.parseInt(seconds) * 1000);
		} catch (Exception e) {
			e.printStackTrace();
			String methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();
			String comment = GeneralHelper.getCommentError(methodName, e.getMessage());
			ExecutionStatusHelper helper = new ExecutionStatusHelper();
			helper.setStatusErrorOnProcedure(_procedure, TypeError.OperationalError, comment);
			throw e;
		}
	}

	private void waitObject(String locator, String secondsToWait, String condition, String identify) throws Exception {
		try {


			boolean exists = true;
			if (condition.equals("Vanish"))
				exists = false;

			if (identify == null || identify.equals(""))
				identify = GeneralHelper.getLocatorProcedure(Integer.parseInt(_executionConfigs.get("System.Locator")),
						_procedure);

			if (identify.equals("//*[@id=\"btnReplicarItensPainel\"]"))
				secondsToWait = "120";

			if (secondsToWait == null || secondsToWait.equals(""))
				secondsToWait = "60";

			if (exists == true)
				_testToolFunctions.WaitObjectVisibility(identify, Integer.parseInt(secondsToWait));
			else
				_testToolFunctions.waitObjectToVanish(identify, Integer.parseInt(secondsToWait));
		} catch (Exception e) {
			e.printStackTrace();
			// String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			// String comment = GeneralHelper.getCommentError(methodName, e.getMessage());
			ExecutionStatusHelper helper = new ExecutionStatusHelper();
			helper.setStatusErrorOnProcedure(_procedure, TypeError.OperationalError, e.getMessage());
			throw e;
		}
	}
}
