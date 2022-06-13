package com.n26.common;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import java.io.FileReader;

import com.n26.utils.ConfigManager;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

public class Request {

    public static final String JSON_FILE_PATH = System.getProperty("user.dir")+ConfigManager.gsJSONFilePath;  
 public static RequestSpecification reqSpec;
 public static RestAssured request;
 public static Response response;
 public static ValidatableResponse valResponse;



 public static RequestSpecification setApiURI(String sBase_URI,String sEndPoint)
 {
    request.baseURI=sBase_URI+sEndPoint;
    reqSpec = RestAssured.given();

    return reqSpec;
 }

 public static void setHeaders(String[][] head) {
    for (int row = 0; row < head.length; row++)
        for (int col = 0; col < head[row].length; col++)
            reqSpec.header(head[row][col], head[row][col + 1]);
}

public static void setHeader(String head, String val) 
{ 
    reqSpec.header(head, val);
}

 public static Response getRequest()
 {
    
    response = reqSpec.get();
    
    return response;
 }
 public static Response getRequest(String key,int value)
 {
   response= reqSpec.pathParam(key, value).when().get("/{"+key+"}");
    return response;
 }
 public static Response getRequest(String key,String value)
 {
     response= reqSpec.queryParam(key, value).when().get();
    
    return response;
 }
 public static Response postRequest(String postBody)
 {
     if(postBody.equals(null))
     {
        response=reqSpec.body("").post();
     }
     else
        response=reqSpec.body(postBody).post();
    
    return response;
 }

 public static Response patchRequest(String body)
 {
    if(body.equals(null))
     {
        response=reqSpec.body("").patch();
     }
     else
        response=reqSpec.body(body).patch();
    
    return response;
 }
 public static Response putRequest(String body)
 {
    if(body.equals(null))
     {
        response=reqSpec.body("").put();
     }
     else
        response=reqSpec.body(body).put();
    
    return response;
 }
 public static Response deleteRequest(String key,int value)
 {
   response= reqSpec.pathParam(key, value).when().delete("/{"+key+"}");
    return response;
 }

	/**
	 * @method : readJsonObjectFile()
	 * @Description : method to read file data which holds JSON standard formatted data
	 * @author : Likhitha Raghavendra
	 */
	public static JSONObject readJsonObjectFile(String fileName)
	{
		JSONObject objectResponse=null;
		try
		{
			JSONParser parser = new JSONParser();

			Object object = parser.parse(new FileReader(JSON_FILE_PATH+fileName));

			//convert Object to JSONObject
			objectResponse = new JSONObject(object.toString());
		}
		catch(Exception e)
		{
			 e.getStackTrace();
		}
		return objectResponse;
	}



}
    