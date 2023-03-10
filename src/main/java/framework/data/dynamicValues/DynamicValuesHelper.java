package framework.data.dynamicValues;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

import framework.RestWebService;
import framework.custom.Custom;
import framework.custom.entities.StaticFields;
import framework.data.entities.LayoutFileData;
import framework.data.entities.Procedure;
import framework.enums.TypeError;
import framework.helpers.ExecutionStatusHelper;
import framework.helpers.GeneralHelper;

public class DynamicValuesHelper {

	public static Integer installmentDay = 0;
	public static String dateFirstPayment;
	public static String dateFirstInstallment;

	public String changeDynamicValueData(String value, Map<String, String> executionConfigs) throws Exception {
		boolean toCache = false;
		String chave = null;

		Calendar c = Calendar.getInstance();

		if (value == null || value.isEmpty())
			return "";

		if (value.toLowerCase().contains("#cached(")) {
			chave = value.replace("#cached(", "").replace(")", "");
			chave += ")";
			return StaticFields.getDataCached(chave);
		} else if (value.toLowerCase().contains("#cached")) {
			return StaticFields.getCachedQueryValue();
		} else if (value.toLowerCase().contains("#cache")) {
			value = value.replace("#cache(", "").replace(")", "") + ")";
			chave = value;
			toCache = true;
		}

		if (value.contains("#DynamicAlias=")) {
			if (value.indexOf("|") > 0)
				value = value.substring(value.indexOf("#DynamicAlias=") + 14, value.indexOf("|"));
			else
				value = value.substring(value.indexOf("#DynamicAlias=") + 14, value.length());
		}

		if (value.toLowerCase().contains("#dynamicname")) {
			UUID uuid = UUID.randomUUID();
			String myRandom = uuid.toString();
			String letras = myRandom.substring(0, 10).replace("-", "");
			value = "TESTE GRUPO HDI " + letras;
		}

		if (value.startsWith("@")) {
			DynamicValuesExcel valuesExcel = new DynamicValuesExcel();
			value = valuesExcel.getValue(1, value);
		}

		else if (value.toLowerCase().equals("#day")) { //$NON-NLS-1$
			value = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
		} else if (value.toLowerCase().equals("#month")) { //$NON-NLS-1$
			value = String.valueOf(c.get(Calendar.MONTH));
		} else if (value.toLowerCase().equals("#year")) { //$NON-NLS-1$
			value = String.valueOf(c.get(Calendar.YEAR));
		}  
		if (!value.toLowerCase().contains("<ignore>")) {
			System.err.println("valor: " + value);
		}

		if (toCache)
			StaticFields.setLIST_DATA_CACHED(chave, value);

		return value;
	}

	public void changeDynamicValue(int locatorNro, Procedure procedure, Map<String, String> executionConfigs)
			throws Exception {
		try {

			procedure.Value = changeDynamicValueData(procedure.Value, executionConfigs);
			procedure.Object.Locator1 = GeneralHelper.getLocatorProcedure(locatorNro, procedure);

		} catch (Exception e) {
			String methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();
			String comment = GeneralHelper.getCommentError(methodName, e.getMessage());

			ExecutionStatusHelper helper = new ExecutionStatusHelper();
			helper.setStatusErrorOnProcedure(procedure, TypeError.OperationalError, comment);
		}
	}

	public JSONObject changeDynamicWebServiceBody(String webServiceBody, Map<String, String> executionConfigs)
			throws Exception {

		JSONObject json = new JSONObject(webServiceBody);

		Set<String> keys = json.keySet();
		for (String key : keys) {

			Object jsonValue = json.get(key);
			if (jsonValue instanceof JSONObject) {
				JSONObject replacedJson = changeDynamicWebServiceBody(jsonValue.toString(), executionConfigs);
				json.put(key, replacedJson);
			} else if (jsonValue instanceof JSONArray) {
				JSONArray replacedJsonArray = new JSONArray();
				for (int i = 0; i <= ((JSONArray) jsonValue).length() - 1; i++) {

					if (((JSONArray) jsonValue).get(i) instanceof JSONObject) {
						JSONObject jsonObj = ((JSONArray) jsonValue).getJSONObject(i);
						replacedJsonArray.put(changeDynamicWebServiceBody(jsonObj.toString(), executionConfigs));
					} else if (((JSONArray) jsonValue).get(i) instanceof Integer) {
						Object jsonObj = ((JSONArray) jsonValue).get(i);
						replacedJsonArray
								.put(Integer.parseInt(changeDynamicValueData(jsonObj.toString(), executionConfigs)));
					} else if (((JSONArray) jsonValue).get(i) instanceof Boolean) {
						Object jsonObj = ((JSONArray) jsonValue).get(i);
						replacedJsonArray.put(
								Boolean.parseBoolean(changeDynamicValueData(jsonObj.toString(), executionConfigs)));
					} else if (((JSONArray) jsonValue).get(i) instanceof Double) {
						Object jsonObj = ((JSONArray) jsonValue).get(i);
						replacedJsonArray
								.put(Double.parseDouble(changeDynamicValueData(jsonObj.toString(), executionConfigs)));
					} else {
						Object jsonObj = ((JSONArray) jsonValue).get(i);
						replacedJsonArray.put(changeDynamicValueData(jsonObj.toString(), executionConfigs));
					}

				}

				json.put(key, new JSONArray(replacedJsonArray.toString()));
			} else if (jsonValue instanceof Integer) {
				json.put(key, Integer.parseInt(changeDynamicValueData(jsonValue.toString(), executionConfigs)));
			} else if (jsonValue instanceof Boolean) {
				json.put(key, Boolean.parseBoolean(changeDynamicValueData(jsonValue.toString(), executionConfigs)));
			} else if (jsonValue instanceof Double) {
				json.put(key, Double.parseDouble(changeDynamicValueData(jsonValue.toString(), executionConfigs)));
			} else {
				json.put(key, changeDynamicValueData(jsonValue.toString(), executionConfigs));
			}
		}

		return json;
	}

	/*
	 * This method need to be customized to get path and file name correctly
	 */
	public void changeDynamicPathAndFilename(LayoutFileData layoutFileData) throws Exception {
		if (layoutFileData.PathAndFilename.contains("#FILEPATH")) {
			if (layoutFileData.TypeFileName.contains("CSV"))
				layoutFileData.PathAndFilename = "C:\\HDI\\Temp\\caixa_2020-12-16-11-26-46.csv";
			else
				layoutFileData.PathAndFilename = "C:\\HDI\\Temp\\arquivo_2020-12-29_17_59.txt";
		}
	}

	public static String getdateFirstInstallment(int days) {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");
		c.getTime().toString();
		c.setTime(c.getTime());
		c.add(Calendar.DAY_OF_MONTH, days);
		dateFirstInstallment = formatador.format(c.getTime()).toString();
		return dateFirstInstallment;
	}

	public static String getdateFirstPayment(int days) {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");
		c.getTime().toString();
		c.setTime(c.getTime());
		c.add(Calendar.DAY_OF_MONTH, days);
		dateFirstPayment = formatador.format(c.getTime()).toString();
		return dateFirstPayment;
	}

}
