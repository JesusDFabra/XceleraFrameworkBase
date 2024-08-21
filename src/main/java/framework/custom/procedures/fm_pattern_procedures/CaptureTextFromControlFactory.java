package framework.custom.procedures.fm_pattern_procedures;

import framework.custom.procedures.CaptureTextFromControl;
import framework.data.entities.Procedure;
import framework.helpers.ScreenshotHelper;
import framework.testtools.ITestToolFunctions;

public class CaptureTextFromControlFactory extends ProcedureBase {
    private final Procedure procedure;
    private final ScreenshotHelper screenshotHelper;
    private final String locator; 
    private final ITestToolFunctions testToolFunctions;

  public CaptureTextFromControlFactory(Procedure procedure, ScreenshotHelper screenshotHelper, String locator, ITestToolFunctions testToolFunctions) {
    this.procedure = procedure;
    this.screenshotHelper = screenshotHelper;
    this.locator = locator;
    this.testToolFunctions = testToolFunctions;
  }

    @Override
    public IPerformableProcedure createProcedure() throws Exception {
      return new CaptureTextFromControl(procedure, screenshotHelper, locator, testToolFunctions);
    }
}
