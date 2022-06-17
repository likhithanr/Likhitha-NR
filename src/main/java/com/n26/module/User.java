package com.n26.module;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import io.restassured.response.Response;
import java.io.IOException;
import com.n26.common.EndPoints;
import com.n26.common.Request;
import com.n26.report.Log;
import com.n26.utils.Excel;

/**
 * @author Likhitha NR
 */

public class User {
	
	public static Map<String, String> testData = new HashMap<String, String>();
	public static String userName = "userName";
	public static String userNameAction = "userNameAction";
	public static String id = "id";
	public static String idAction = "idAction";
	public static String firstName = "firstName";
	public static String firstNameAction = "firstNameAction";
	public static String lastName = "lastName";
	public static String lastNameAction = "lastNameAction";
	public static String email = "email";
	public static String emailAction = "emailAction";
	public static String passwordAction = "passwordAction";
	public static String password = "password";
	public static String phone = "phone";
	public static String phoneAction = "phoneAction";
	public static String userStatus = "userStatus";
	public static String userStatusAction = "userStatusAction";
	public static String searchCriteria = "searchCriteria";
	public static JSONArray listUsers = new JSONArray();

	/**
	 * @param object
	 * @return JSONArray
	 */
	public static JSONArray createListOfUser(JSONObject object) {
		listUsers.put(object);
		return listUsers;
	}

	/**
	 * @param testData
	 * @param rowNum
	 * @return JSONObject
	 */
	public static JSONObject updateJsonBody(Map<String, String> testData, int rowNum) {

		// reading json structure form file
		JSONObject objectResponse = null;
		try {
			objectResponse = Request.readJsonObjectFile("User.json");
			String sSearchCriteria;
			sSearchCriteria = Excel.getColumnData("User", searchCriteria, rowNum);
			String[] searchCrietriaKey = sSearchCriteria.split(",");
			for (int i = 0; i < searchCrietriaKey.length; i++) {
				switch (searchCrietriaKey[i]) {
					case "id":
						if (testData.get(idAction).equals("Y")) {
							objectResponse.put("id", testData.get(id));
						} else if (testData.get(idAction).equals("N")) {
							// removing id object
							objectResponse.remove(searchCrietriaKey[i]);
						}
						break;
					case "userName":
						if (testData.get(userNameAction).equals("Y")) {
							objectResponse.put("userName", testData.get(userName));
						} else if (testData.get(userName).equals("N")) {
							// removing user name object
							objectResponse.remove(searchCrietriaKey[i]);
						}
						break;
					case "firstName":
						if (testData.get(firstNameAction).equals("Y")) {
							objectResponse.put("firstName", testData.get(firstName));

						} else if (testData.get(firstNameAction).equals("N")) {
							// removing first name object
							objectResponse.remove("firstName");
						}
						break;
					case "lastName":
						if (testData.get(lastNameAction).equals("Y")) {
							objectResponse.put("lastName", testData.get(lastName));

						} else if (testData.get(lastNameAction).equals("N")) {
							// removing last name object
							objectResponse.remove("lastName");
						}
						break;
					case "email":
						if (testData.get(emailAction).equals("Y")) {
							objectResponse.put("email", testData.get(email));

						} else if (testData.get(emailAction).equals("N")) {
							// removing email object
							objectResponse.remove("email");

						}
						break;
					case "password":
						if (testData.get(passwordAction).equals("Y")) {
							objectResponse.put("password", testData.get(password));
						} else if (testData.get(passwordAction).equals("N")) {
							// removing password object
							objectResponse.remove("password");
						}
						break;
					case "phone":
						if (testData.get(phoneAction).equals("Y")) {

							objectResponse.put("phone", testData.get(phone));
						} else if (testData.get(phoneAction).equals("N")) {
							// removing phone object
							objectResponse.remove("phone");
						}
						break;
					case "userStatus":
						if (testData.get(userStatusAction).equals("Y")) {

							objectResponse.put("userStatus", testData.get(userStatus));
						} else if (testData.get(userStatusAction).equals("N")) {
							// removing userStatus object
							objectResponse.remove("userStatus");
						}
						break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return objectResponse;
	}

	/**
	 * @param baseURI
	 * @param action
	 * @param userName
	 * @param pwd
	 * @return Response
	 */
	public static Response loginLogout(String baseURI, String action, String userName, String pwd) {
		Map<String, String> map = new HashMap<>();
		Response response = null;
		if (action.equals("Y")) {
			map.put("username", userName);
			map.put("password", pwd);
			Request.setApiURI(baseURI, EndPoints.GET_LOGIN);
			Request.setHeader("accept", "application/xml");
			response = Request.getRequestUsingQuery(map);
			Assert.assertEquals(response.statusCode() == 200, true);
			Log.scriptInfo("User succesfully logged in");
		} else {
			Request.setApiURI(baseURI, EndPoints.GET_LOGOUT);
			Request.setHeader("accept", "application/xml");
			response = Request.getRequest();
			Assert.assertEquals(response.statusCode() == 200, true);
			Log.scriptInfo("User succesfully logged out");
		}
		return response;
	}

}
