package framework.custom.utils.databases;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import static framework.custom.utils.databases.UtilsConstants.CONNECTION_FAILURE;
import static framework.custom.utils.databases.UtilsConstants.DRIVER_NOT_FOUND;

public class BaseDatosAplicacion {

    static final Logger logger = Logger.getLogger(BaseDatosAplicacion.class.getName());
    private ResultSet rs = null;
    private Statement smt;
    private boolean result = false;

    public BaseDatosAplicacion(){
        // Write document why this constructor is empty
    }

    public ResultSet ejecutarConsulta(String strQuery, Connection conexion) throws SQLException
    {
        try{
            smt = conexion.createStatement();
            rs = smt.executeQuery(strQuery);
        }catch(SQLException e){
            logger.log(Level.SEVERE,() -> CONNECTION_FAILURE + e.getMessage());
        }catch(Exception ex){
            logger.log(Level.SEVERE,() ->DRIVER_NOT_FOUND + ex.getMessage() );
        }
        return rs;
    }

    public boolean ejecutarUpdate(String strQuery, Connection conexion)
    {
        try{
            smt = conexion.createStatement();
            smt.executeUpdate(strQuery);
            result = true;
        }catch(SQLException e){
            logger.log(Level.SEVERE,() -> CONNECTION_FAILURE + e.getMessage());
            result = false;
        }catch(Exception ex){
            logger.log(Level.SEVERE,() -> DRIVER_NOT_FOUND + ex.getMessage());
        }
        return result;
    }
}
