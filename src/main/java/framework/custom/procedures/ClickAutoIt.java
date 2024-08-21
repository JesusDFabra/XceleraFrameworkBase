package framework.custom.procedures;

import framework.custom.procedures.fm_pattern_procedures.IPerformableProcedure;
import framework.data.entities.Procedure;
import framework.enums.TypeError;
import framework.helpers.ExecutionStatusHelper;
import framework.helpers.GeneralHelper;
import framework.helpers.ScreenshotHelper;
import framework.testtools.AutoItFunctions;

public class ClickAutoIt implements IPerformableProcedure {
    private final Procedure procedure;
    private final ScreenshotHelper screenshotHelper;
    private final String locator;
    private final AutoItFunctions _autoItFunctions = new AutoItFunctions();

    public ClickAutoIt(Procedure procedure, ScreenshotHelper screenshotHelper, String locator) {
        this.procedure = procedure;
        this.screenshotHelper = screenshotHelper;
        this.locator = locator;
    }

  @Override
  public void executeCustomProcedure() throws Exception {
    try {
      _autoItFunctions.click(locator);
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
