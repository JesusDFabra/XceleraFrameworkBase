package framework.custom.procedures.fm_pattern_procedures;

import framework.custom.procedures.ClickAutoIt;
import framework.data.entities.Procedure;
import framework.helpers.ScreenshotHelper;

public class ClickAutoItFactory extends ProcedureBase {
  private final Procedure procedure;
  private final ScreenshotHelper screenshotHelper;
  private final String locator;
  
  public ClickAutoItFactory(Procedure procedure, ScreenshotHelper screenshotHelper, String locator) {
    this.procedure = procedure;
    this.screenshotHelper = screenshotHelper;
    this.locator = locator;
  }

  @Override
  public IPerformableProcedure createProcedure() throws Exception {
    return new ClickAutoIt(procedure, screenshotHelper, locator);
  }

}
