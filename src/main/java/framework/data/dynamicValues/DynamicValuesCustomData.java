package framework.data.dynamicValues;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import framework.custom.utils.databases.BaseDatosAplicacion;
import framework.custom.utils.databases.ConexionDB;
import framework.dataProviders.ConfigFileReader;

public class DynamicValuesCustomData {

	private static String _ID = "";
	final static String abecedario = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	static Random random = new Random();
	public static HashMap<String, String> datosPruebas;
	static final Logger logger = Logger.getLogger(DynamicValuesCustomData.class.getName());

	ConfigFileReader f = new ConfigFileReader("configs/config.properties");

	private static String calcDigVerif(String num) {
		Integer primDig, segDig;
		int soma = 0, peso = 10;
		for (int i = 0; i < num.length(); i++)
			soma += Integer.parseInt(num.substring(i, i + 1)) * peso--;
		if (soma % 11 == 0 | soma % 11 == 1)
			primDig = new Integer(0);
		else
			primDig = new Integer(11 - (soma % 11));
		soma = 0;
		peso = 11;
		for (int i = 0; i < num.length(); i++)
			soma += Integer.parseInt(num.substring(i, i + 1)) * peso--;
		soma += primDig.intValue() * 2;
		if (soma % 11 == 0 | soma % 11 == 1)
			segDig = new Integer(0);
		else
			segDig = new Integer(11 - (soma % 11));
		return primDig.toString() + segDig.toString();
	}

	public static String geraCPF() { // Gera CPF
		String iniciais = "", cpf;
		Integer numero;
		for (int i = 0; i < 9; i++) {
			numero = new Integer((int) (Math.random() * 10));
			iniciais += numero.toString();
		}
		cpf = iniciais + calcDigVerif(iniciais);
		cpf = cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-" + cpf.substring(9, 11);
		return cpf;
	}

	public static String somaData(String params) {
		String data = "";
		params = params.substring(params.indexOf("(") + 1, params.indexOf(")"));
		Integer dias = Integer.valueOf(params);
		Calendar c = Calendar.getInstance();
		c.get(Calendar.DAY_OF_MONTH);
		c.add(Calendar.DATE, dias);
		String formato = "dd/MM/yyyy";
		SimpleDateFormat dataFormatada = new SimpleDateFormat(formato);
		data = dataFormatada.format(c.getTime());
		return data;
	}

	public static boolean validaCPF(String cpf) {
		if (cpf.length() != 11)
			return false;
		String numDig = cpf.substring(0, 9);
		return calcDigVerif(numDig).equals(cpf.substring(9, 11));
	}

	public static String geraCNPJ() {

		int digito1 = 0, digito2 = 0, resto = 0;
		String nDigResult;
		String numerosContatenados;
		String numeroGerado;
		Random numeroAleatorio = new Random();
		// numeros gerados
		int n1 = numeroAleatorio.nextInt(10);
		int n2 = numeroAleatorio.nextInt(10);
		int n3 = numeroAleatorio.nextInt(10);
		int n4 = numeroAleatorio.nextInt(10);
		int n5 = numeroAleatorio.nextInt(10);
		int n6 = numeroAleatorio.nextInt(10);
		int n7 = numeroAleatorio.nextInt(10);
		int n8 = numeroAleatorio.nextInt(10);
		int n9 = numeroAleatorio.nextInt(10);
		int n10 = numeroAleatorio.nextInt(10);
		int n11 = numeroAleatorio.nextInt(10);
		int n12 = numeroAleatorio.nextInt(10);
		int soma = n12 * 2 + n11 * 3 + n10 * 4 + n9 * 5 + n8 * 6 + n7 * 7 + n6 * 8 + n5 * 9 + n4 * 2 + n3 * 3 + n2 * 4
				+ n1 * 5;
		int valor = (soma / 11) * 11;
		digito1 = soma - valor;
		// Primeiro resto da divisão por 11.
		resto = (digito1 % 11);
		if (digito1 < 2) {
			digito1 = 0;
		} else {
			digito1 = 11 - resto;
		}
		int soma2 = digito1 * 2 + n12 * 3 + n11 * 4 + n10 * 5 + n9 * 6 + n8 * 7 + n7 * 8 + n6 * 9 + n5 * 2 + n4 * 3
				+ n3 * 4 + n2 * 5 + n1 * 6;
		int valor2 = (soma2 / 11) * 11;
		digito2 = soma2 - valor2;
		// Primeiro resto da divisão por 11.
		resto = (digito2 % 11);
		if (digito2 < 2) {
			digito2 = 0;
		} else {
			digito2 = 11 - resto;
		}
		// Conctenando os numeros
		numerosContatenados = String.valueOf(n1) + String.valueOf(n2) + "." + String.valueOf(n3) + String.valueOf(n4)
				+ String.valueOf(n5) + "." + String.valueOf(n6) + String.valueOf(n7) + String.valueOf(n8) + "/"
				+ String.valueOf(n9) + String.valueOf(n10) + String.valueOf(n11) + String.valueOf(n12) + "-";
		// Concatenando o primeiro resto com o segundo.
		nDigResult = String.valueOf(digito1) + String.valueOf(digito2);
		numeroGerado = numerosContatenados + nDigResult;
		return numeroGerado;
	}

	public static String geraEmail() { // Gera Email Ex: sfsdf@dsf.com
		UUID uuid = UUID.randomUUID();
		String myRandom = uuid.toString();
		String email = myRandom.substring(0, 8) + "@" + myRandom.substring(0, 5) + ".com";
		return email;
	}

	public static String getDynamicDataFromTDM(String tdmData, Map<String, String> executionConfigs) throws Exception {
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;

		String dynamicData = "";
		try {
			tdmData = tdmData.replace("#TDM(", "").replace(")", "");

			String[] splittedData = tdmData.split(",");
			String tableAndFieldNameValue = splittedData[0].replace(".", "SEPARATOR");
			String[] tableAndFieldName = tableAndFieldNameValue.split("SEPARATOR");

			String tableName = tableAndFieldName[0];
			String fieldName = tableAndFieldName[1];
			Boolean isUnique = Boolean.parseBoolean(splittedData[1].trim());
			Boolean isLastFieldInTheRow = Boolean.parseBoolean(splittedData[2].trim());

			String query = "";
			if (!_ID.isEmpty())
				query = "SELECT TOP 1 ID," + fieldName + " FROM " + tableName + " WHERE ID = " + _ID;
			else {
				if (isUnique)
					query = "SELECT TOP 1 ID," + fieldName + " FROM " + tableName + " WHERE ISNULL(Used,0) = 0";
				else
					query = "SELECT TOP 1 ID," + fieldName + " FROM " + tableName;
			}
			// String url = String.format("jdbc:sqlserver://%s/%s",
			// executionConfigs.get("TDM.Hostname"),
			// executionConfigs.get("TDM.DatabaseName"));
			// connection = DriverManager.getConnection(url, executionConfigs.get("TDM.Username"),
			// executionConfigs.get("TDM.Password"));
			String url = String.format("jdbc:sqlserver://%s;databaseName=%s;user=%s;password=%s;",
					executionConfigs.get("TDM.Hostname"), executionConfigs.get("TDM.DatabaseName"),
					executionConfigs.get("TDM.Username"), executionConfigs.get("TDM.Password"));
			conn = DriverManager.getConnection(url);

			statement = conn.createStatement();
			rs = statement.executeQuery(query);

			if (rs.next()) {
				_ID = rs.getString("ID");
				dynamicData = rs.getString(fieldName);
			}

			if (isLastFieldInTheRow) {
				query = "UPDATE " + tableName + " SET Used = 1 WHERE ID = " + rs.getString("ID");
				statement.execute(query);

				_ID = "";
			}

			return dynamicData;

		} catch (Exception e) {
			throw e;
		} finally {
			rs.close();
			statement.close();
			conn.close();
		}
	}

	public static String getRenavam() {
		Random random = new Random();
		String renavam = "";
		for (int i = 0; i < 10; i++) {
			renavam += Math.abs(random.nextInt(8));
		}
		String renavamSemDigito = renavam.substring(0, 10);
		String renavamReversoSemDigito = new StringBuffer(renavamSemDigito).reverse().toString();

		int soma = 0;
		for (int i = 0; i < 8; i++) {
			Integer algarismo = Integer.parseInt(renavamReversoSemDigito.substring(i, i + 1));
			Integer multiplicador = i + 2;
			soma += algarismo * multiplicador;
		}

		soma += Character.getNumericValue(renavamReversoSemDigito.charAt(8)) * 2;
		soma += Character.getNumericValue(renavamReversoSemDigito.charAt(9)) * 3;

		int mod11 = soma % 11;
		int ultimoDigitoCalculado = 11 - mod11;
		ultimoDigitoCalculado = (ultimoDigitoCalculado >= 10 ? 0 : ultimoDigitoCalculado);

		return renavam + ultimoDigitoCalculado;
	}

	public static String getPlacaCarro() {
		StringBuilder builder = new StringBuilder();
		StringBuilder builderNum = new StringBuilder();
		String letras = "";
		String numeros = "";
		for (int i = 0; i < 3; i++) {
			builder.append(abecedario.charAt(random.nextInt(25)));
		}
		for (int i = 0; i < 4; i++) {
			builderNum.append(String.valueOf(random.nextInt(9)));
		}
		numeros = builderNum.toString();
		letras = builder.toString();

		return letras.concat(numeros);
	}

	private static StringBuilder getLetraAlfabeto(StringBuilder builder) {
		builder.append(abecedario.charAt(random.nextInt(25)));
		return builder;
	}

	private static StringBuilder getRandomNumeroChar(StringBuilder builder) {
		builder.append(String.valueOf(random.nextInt(9)));
		return builder;
	}

	private static StringBuilder getRandomNumeroChar(StringBuilder builder, Integer num) {
		builder.append(String.valueOf(random.nextInt(num)));
		return builder;
	}

	public static String getChassi() {
		final String region = "9G";
		StringBuilder builder = new StringBuilder();
		builder.append(region);
		builder = getLetraAlfabeto(builder); // WMI

		builder = getLetraAlfabeto(builder);
		builder = getLetraAlfabeto(builder);
		builder = getRandomNumeroChar(builder);
		builder = getRandomNumeroChar(builder);
		builder = getRandomNumeroChar(builder); // VDS

		builder = getRandomNumeroChar(builder, 1);
		builder = getRandomNumeroChar(builder); // Ano

		builder = getLetraAlfabeto(builder); // Código da Planta

		for (int i = 0; i < 6; i++) {
			builder = getRandomNumeroChar(builder);
		}
		return builder.toString();
	}
	
	// ====== FUNCIONES CLIENTE
	
	public static void setIdPrueba(int id) throws SQLException {

		String tabla = "Register";
		String query = "SELECT * FROM " + tabla +" WHERE Id = " + id;

		datosPruebas = new HashMap<>();
		datosPruebas = getDatosRecordTabla(query);
	}

	////////////////////////////////////////////////////////////////////////
	public static String getFirstName() {
		return datosPruebas.get("first_name");
	}
	public static String getLastName() {
		return datosPruebas.get("last_name");
	}
	public static String getUsername() {
		return datosPruebas.get("username");
	}
	public static String getPassword() {
		return datosPruebas.get("password");
	}
	/////////////////////////////////////////////////////////////////////////

	public static HashMap<String, String> getDatosRecordTabla(String query) throws SQLException {
        ResultSet resultSet;
        Connection connection;
        HashMap<String, String> map = new HashMap<>();

        try{
			connection = ConexionDB.util().conectar("connectionStringH2");
			
			BaseDatosAplicacion bda = new BaseDatosAplicacion();		
        	resultSet = bda.ejecutarConsulta(query, connection);
			map = llenarHashConResultSet(resultSet);
        	ConexionDB.util().desconectar(connection);
        	
        	return map;
        }catch(Exception ex){
        	logger.log(Level.SEVERE,ex.getMessage());
            return map;
        }
    }
	
	private static HashMap<String,String> llenarHashConResultSet(ResultSet resultSet) throws SQLException {
        HashMap<String,String> hashMap = new HashMap<>();
        if(resultSet != null) {
            while(resultSet.next()) {
                for(int i=1 ; i <= resultSet.getMetaData().getColumnCount() ; i++){
					String nombreCampo = resultSet.getMetaData().getColumnName(i).toLowerCase();
                    String valorCampo = resultSet.getObject(i)== null?"":resultSet.getObject(i).toString();

                    hashMap.put(nombreCampo, valorCampo);
                }
            }
		}
        return hashMap;
    }
}
