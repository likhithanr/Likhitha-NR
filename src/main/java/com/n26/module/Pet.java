package com.n26.module;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.n26.common.Request;
import com.n26.utils.Excel;
import org.json.JSONObject;

/**
 * @author Likhitha NR
 */
public class Pet {

	public static Map<String, String> testData = new HashMap<String, String>();
	public static String name = "Name";
	public static String nameAction = "NameAction";
	public static String id = "Id";
	public static String idAction = "IdAction";
	public static String categoryAction = "CategoryAction";
	public static String categoryId = "CategoryId";
	public static String categoryIdAction = "CategoryIdAction";
	public static String categoryName = "CategoryName";
	public static String categoryNameAction = "CategodryNameAction";
	public static String photoUrl = "PhotoUrl";
	public static String photoUrlAction = "PhotoUrlAction";
	public static String tagsAction = "TagsAction";
	public static String tagId = "TagId";
	public static String tagIdAction = "TagIdAction";
	public static String tagName = "TagName";
	public static String tagNameAction = "TagNameAction";
	public static String status = "Status";
	public static String statusAction = "StatusAction";
	public static String searchCriteria = "searchCriteria";

	/**
	 * @param testData
	 * @param rowNum
	 * @return JSONObject
	 */
	public static JSONObject updateJsonBody(Map<String, String> testData, int rowNum) {

		// reading json structure form file
		JSONObject objectResponse = null;
		try {
			objectResponse = Request.readJsonObjectFile("Pet.json");
			String sSearchCriteria;
			sSearchCriteria = Excel.getColumnData("Pet", searchCriteria, rowNum);
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
					case "name":
						if (testData.get(nameAction).equals("Y")) {
							objectResponse.put("name", testData.get(name));
						} else if (testData.get(name).equals("N")) {
							// removing name object
							objectResponse.remove(searchCrietriaKey[i]);
						}
						break;
					case "categoryId":
						if (testData.get(categoryIdAction).equals("Y")) {
							objectResponse.getJSONObject("category").put("id", testData.get(categoryId));

						} else if (testData.get(categoryIdAction).equals("N")) {
							// removing category id object
							objectResponse.getJSONObject("category").remove("id");
						}
						break;
					case "categoryName":
						if (testData.get(categoryNameAction).equals("Y")) {
							objectResponse.getJSONObject("caegory").put("name", testData.get(categoryName));

						} else if (testData.get(categoryNameAction).equals("N")) {
							objectResponse.getJSONObject("category").remove("name");
						}
						break;
					case "category":
						if (testData.get(categoryIdAction).equals("N")) {
							objectResponse.remove("category");

						}
						break;
					case "tags":
						if (testData.get(tagIdAction).equals("N")) {
							objectResponse.remove("tags");

						}
						break;
					case "photoUrls":
						if (testData.get(photoUrlAction).equals("Y")) {

							objectResponse.getJSONArray("photoUrls").put(testData.get(photoUrl));
						} else if (testData.get(photoUrlAction).equals("N")) {

							objectResponse.remove("photoUrls");
						}
						break;
					case "status":
						if (testData.get(statusAction).equals("Y")) {

							objectResponse.put("status", testData.get(status));
						} else if (testData.get(statusAction).equals("N")) {

							objectResponse.remove("status");
						}
						break;
					case "tagId":
						if (testData.get(tagIdAction).equals("Y")) {

							objectResponse.getJSONArray("tags").getJSONObject(0).put("id", testData.get(tagId));
						} else if (testData.get(tagIdAction).equals("N")) {
							objectResponse.getJSONArray("tags").getJSONObject(0).remove("id");
						}
						break;
					case "tagName":
						if (testData.get(tagNameAction).equals("Y")) {
							objectResponse.getJSONArray("tags").getJSONObject(0).put("name", testData.get(tagName));
						} else if (testData.get(tagNameAction).equals("N")) {
							objectResponse.getJSONArray("tags").getJSONObject(0).remove("name");
						}
						break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return objectResponse;
	}
}
