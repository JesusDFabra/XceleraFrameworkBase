package framework.custom.procedures;

import framework.custom.procedures.fm_pattern_procedures.IPerformableProcedure;
import framework.data.entities.Procedure;
import framework.enums.TypeError;
import framework.helpers.ExecutionStatusHelper;
import framework.helpers.GeneralHelper;
import framework.helpers.ScreenshotHelper;
import framework.testtools.ITestToolFunctions;

public class CaptureTextFromControl implements IPerformableProcedure  {

    private final Procedure procedure;
    private final ScreenshotHelper screenshotHelper;
    private final String locator;
    private final ITestToolFunctions testToolFunctions;

    public CaptureTextFromControl(Procedure _procedure, ScreenshotHelper _screenshotHelper, String _locator, ITestToolFunctions _testToolFunctions) {
        this.procedure = _procedure;
        this.screenshotHelper = _screenshotHelper;
        this.locator = _locator;
        this.testToolFunctions = _testToolFunctions;
    }
    
    @Override
    public void executeCustomProcedure() throws Exception {
        try {
            String textSMS = testToolFunctions.captureText(locator);
			System.out.println("Texto SMS: " + textSMS);
			
			ExecutionStatusHelper helper = new ExecutionStatusHelper();
			helper.setStatusLogOkProcedure(procedure, TypeError.Sucess, "El código obtenido del autenticador es: "+textSMS);
        } catch (Exception e) {
            String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
            String mensaje = "Falla en tiempo de espera de ejecucion " 
                           + "mesaje obtenido de exception: " + e.getMessage();
            String comment = GeneralHelper.getCommentError(methodName, mensaje);
    
            screenshotHelper.takeScreenshot();
            ExecutionStatusHelper helper = new ExecutionStatusHelper();
            helper.setStatusErrorOnProcedure(procedure, TypeError.OperationalError, comment);
    
            throw e;
        }
    }
}
