package framework.custom.procedures;

import framework.custom.procedures.fm_pattern_procedures.IPerformableProcedure;

public class HolaMundo implements IPerformableProcedure {
   
  public HolaMundo() {
  }
  
  @Override
  public void executeCustomProcedure() throws Exception {
      System.out.println("  ..........................................................");
      System.out.println("  . Ejecutando Hola mundo desde xcelera con patron factory .");
      System.out.println("  ..........................................................");
  }
}
