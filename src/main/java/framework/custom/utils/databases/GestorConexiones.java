package framework.custom.utils.databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static framework.custom.utils.databases.UtilsConstants.CONNECTION_FAILURE;
import static framework.custom.utils.databases.UtilsConstants.CONNECTION_NOT_CLOSED;


public class GestorConexiones {

    static final Logger logger = Logger.getLogger(GestorConexiones.class.getName());
    private Connection conexion;

    public static GestorConexiones util(){return new GestorConexiones();}

    public Connection getConnection() {
        return conexion;
    }

    public void setConnection(Connection con) {
        conexion = con;
    }
    
    public void crearConexionDB(String strCon) {
        try {
            String[] parts = strCon.split(":");
            String driver = parts[1];
            
            Class.forName(AppProperties.getDriverConexion(driver));
            
            conexion = DriverManager.getConnection(strCon);
            
        } catch (ClassNotFoundException | SQLException ex) {
            logger.log(Level.SEVERE,() -> CONNECTION_FAILURE + ex.getMessage());
        }
    }

    public void closeConnection( Connection connection){
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE,() ->CONNECTION_NOT_CLOSED + e.getMessage());
        }
    }
}
