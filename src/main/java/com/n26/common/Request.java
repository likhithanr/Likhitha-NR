package com.n26.common;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.io.FileReader;
import java.util.Map;

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

public static void setHeader(Map<String,String> headers) 
{ 
    reqSpec.headers(headers);
}

//GET request without any parameter
 public static Response getRequest()
 {
    
    response = reqSpec.get();
    
    return response;
 }

 //Get request using int type path parameter
 public static Response getRequestUsingPath(String key,int value)
 {
   response= reqSpec.pathParam(key, value).when().get("/{"+key+"}");
    return response;
 }

 //Get request using String type path parameter
 public static Response getRequestUsingPath(String key,String value)
 {
   response= reqSpec.pathParam(key, value).when().get("/{"+key+"}");
    return response;
 }

  //Get request using map type query param
 public static Response getRequestUsingQuery(Map<String,String> pathParams)
 {
     response= reqSpec.queryParams(pathParams).when().get();
     return response;
 }

  public static Response getRequestUsingQuery(String key,String value)
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
 public static Response postRequest(String postBody,String pathParamKey,int pathParamValue,Map<String,String> query)
 {
     if(postBody.equals(null))
     {
      response=reqSpec.pathParam(pathParamKey, pathParamValue).queryParams(query).when().body("").post("/{"+pathParamKey+"}");
     }
     else
     response=reqSpec.pathParam(pathParamKey, pathParamValue).queryParams(query).when().body(postBody).post("/{"+pathParamKey+"}");
    
    return response;
 }

 public static Response uploadImage(String pathParamKey,int pathParamValue,File file)
 {
     if(file.equals(null))
     {
      response=reqSpec.pathParam(pathParamKey, pathParamValue).contentType(ContentType.BINARY).when().body("").post("/{"+pathParamKey+"}/uploadImage");
     }
     else{
      response=reqSpec.pathParam(pathParamKey, pathParamValue).body(file).when().post("/{"+pathParamKey+"}/uploadImage");
     //File file1 = new File("/home/likhithanr/N26/PETSTORE/petstore/Images/dog1.jpeg");
     /* Map<String,String> map=new HashMap<>();
     map.put("accept", "application/json");
     map.put("Content-Type", "application/octet-stream"); 

     String endpoint = "/api/v3/pet/1/uploadImage";    
     
        RestAssured.baseURI = "http://localhost:8080";
             Response res = RestAssured.given().header("Content-Type","application/octet-stream").body(file1)
                     .when().post(endpoint);
                     System.out.println(res.asPrettyString()); */
     }
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
 public static Response putRequest(String body,String pathParamKey,String pathParamValue)
 {
    if(body.equals(null))
     {
        response=reqSpec.pathParam(pathParamKey, pathParamValue).when().put("/{"+pathParamKey+"}");
     }
     else
        response=reqSpec.pathParam(pathParamKey, pathParamValue).when().body(body).put("/{"+pathParamKey+"}");
    
    return response;
 }
 public static Response putRequest(String body,String pathParamKey,int pathParamValue)
 {
    if(body.equals(null))
     {
        response=reqSpec.pathParam(pathParamKey, pathParamValue).when().put("/{"+pathParamValue+"}");
     }
     else
        response=reqSpec.pathParam(pathParamKey, pathParamValue).when().body(body).put("/{"+pathParamValue+"}");
    
    return response;
 }
 
 public static Response deleteRequest(String key,int value)
 {
   response= reqSpec.pathParam(key, value).when().delete("/{"+key+"}");
    return response;
 }
 public static Response deleteRequest(String key,String value)
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
    