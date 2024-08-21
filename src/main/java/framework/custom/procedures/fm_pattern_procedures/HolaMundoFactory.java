package framework.custom.procedures.fm_pattern_procedures;

import framework.custom.procedures.HolaMundo;

public class HolaMundoFactory extends ProcedureBase {
  
  public HolaMundoFactory() {
  }

  @Override
  public IPerformableProcedure createProcedure() throws Exception {
    return new HolaMundo();
  }
}
