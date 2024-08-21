package framework.custom;

import java.util.Map;

import framework.custom.procedures.fm_pattern_procedures.CaptureTextFromControlFactory;
import framework.custom.procedures.fm_pattern_procedures.ClickAutoItFactory;
import framework.custom.procedures.fm_pattern_procedures.HolaMundoFactory;
import framework.custom.procedures.fm_pattern_procedures.ProcedureBase;
import framework.custom.procedures.fm_pattern_procedures.SetValueAutoItFactory;
import framework.data.entities.Procedure;
import framework.helpers.GeneralHelper;
import framework.helpers.ScreenshotHelper;
import framework.testtools.ITestToolFunctions;

public class Custom {
	private ITestToolFunctions _testToolFunctions;
	private ProcedureBase _procedureBase;
	private Map<String, String> _executionConfigs;
	private ScreenshotHelper _screenshotHelper;

	private Procedure _procedure;
	private Map<String, String> _attributes;
	private String _valueFromTestProcedure;
	private String _locator;
	


	public Custom(ITestToolFunctions testToolFunctions, Map<String, String> executionConfigs,
			ScreenshotHelper screenshotHelper) {
		_testToolFunctions = testToolFunctions;
		_executionConfigs = executionConfigs;
		_screenshotHelper = screenshotHelper;
	}
	
	//creado temporalmente
	public Custom(ITestToolFunctions testToolFunctions) {
		_testToolFunctions = testToolFunctions;
	}

	public void executeAction(String repositoryType, Procedure procedure) throws Exception {
		_procedure = procedure;
		_attributes = GeneralHelper.getMapAttributes(_procedure);
		_valueFromTestProcedure = _procedure.Value != null && !_procedure.Value.isEmpty() ? _procedure.Value : "";
		_locator = GeneralHelper.getLocatorProcedure(Integer.parseInt(_executionConfigs.get("System.Locator")),
				_procedure);

		switch ((int) procedure.MethodId) {

		case 531:
			clickAutoIt();
			break;
		case 532:
			setValueAutoIt();
			break;
		case 626:
			captureTextFromControl();
			break;
		case 905:
			holaMundoPrinter();
			break;
		default:
			break;
		}
	}
	private void holaMundoPrinter() throws Exception {
		_procedureBase = new HolaMundoFactory();
		_procedureBase.executeCustomProcedure();
	}

	private void clickAutoIt() throws Exception {
		_procedureBase = new ClickAutoItFactory(_procedure, _screenshotHelper, _locator) ;
		_procedureBase.executeCustomProcedure();
	}

	private void captureTextFromControl() throws Exception {
		_procedureBase = new CaptureTextFromControlFactory(_procedure, _screenshotHelper, _locator, _testToolFunctions) ;
		_procedureBase.executeCustomProcedure();
	}

	private void setValueAutoIt() throws Exception {
		_procedureBase = new SetValueAutoItFactory(_procedure, _screenshotHelper, _locator, _valueFromTestProcedure) ;
		_procedureBase.executeCustomProcedure();
	}
}
 