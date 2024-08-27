package framework.custom.utils.databases;

public class AppProperties {

  private AppProperties(){}

  public static String getDriverConexion(String gestorDB){

    switch(gestorDB){
      case "sqlserver":
        return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
      case "oracle":
        return "oracle.jdbc.OracleDriver";
      case "h2":
        return "org.h2.Driver";
      ////////////////////////////
      case "as400":
        return "com.ibm.as400.access.AS400JDBCDriver";
      case "iseries":
        return "com.ibm.as400.access.AS400JDBCDriver";
      default:
        throw new IllegalArgumentException("No se reconoce el driver SQL: " + gestorDB);
    }
  }
}