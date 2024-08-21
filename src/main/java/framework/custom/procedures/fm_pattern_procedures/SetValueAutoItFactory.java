package framework.custom.procedures.fm_pattern_procedures;

import framework.custom.procedures.SetValueAutoIt;
import framework.data.entities.Procedure;
import framework.helpers.ScreenshotHelper;

public class SetValueAutoItFactory  extends ProcedureBase {
    private final Procedure procedure;
    private final ScreenshotHelper screenshotHelper;
    private final String locator;
    private final String valueFromTestProcedure;

    public SetValueAutoItFactory(Procedure procedure, ScreenshotHelper screenshotHelper, String locator, String valueFromTestProcedure) {

    this.procedure = procedure;
    this.screenshotHelper = screenshotHelper;
    this.locator = locator;
    this.valueFromTestProcedure = valueFromTestProcedure;
  }

    @Override
    public IPerformableProcedure createProcedure() throws Exception {
        return new SetValueAutoIt(procedure, screenshotHelper, locator, valueFromTestProcedure);
    }
}
