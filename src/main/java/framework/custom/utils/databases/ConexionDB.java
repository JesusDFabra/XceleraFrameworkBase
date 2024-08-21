package framework.custom.utils.databases;

import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

import static framework.custom.utils.databases.UtilsConstants.CONNECTION_FAILURE;
import framework.dataProviders.ConfigFileReader;

public class ConexionDB {
    static ConfigFileReader reader = new ConfigFileReader("configs/config.properties");
    static final Logger logger = Logger.getLogger(ConexionDB.class.getName());
    public static ConexionDB util(){
        return new ConexionDB();
    }
    
    public Connection conectar(String claveStrCon){
        Connection connection = null;
        try{
            String stringConexion = reader.getPropertyByKey(claveStrCon);
            GestorConexiones gc = new GestorConexiones();
            gc.crearConexionDB(stringConexion);
            connection = gc.getConnection();
        }catch(Exception e){
            logger.log(Level.SEVERE,() ->CONNECTION_FAILURE + e.getMessage());
        }
        return connection;
    }

    public void desconectar(Connection connection){
        GestorConexiones.util().closeConnection(connection);
    }
}
