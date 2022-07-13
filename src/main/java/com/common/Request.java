package com.common;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import java.io.File;
import java.io.FileReader;
import java.util.Map;
import com.utils.ConfigManager;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * @author Likhitha NR
 */

public class Request {

   public static final String JSON_FILE_PATH = System.getProperty("user.dir") + ConfigManager.gsJSONFilePath;
   public static RequestSpecification reqSpec;
   public static RestAssured request;
   public static Response response;
   public static ValidatableResponse valResponse;
   private static final String forwardSlash = "/";
   private static final String openBracket = "{";
   private static final String closeBracket = "}";

   /**
    * @param sBase_URI,sEndPoint
    * @return RequestSpecification
    */
   public static RequestSpecification setApiURI(String sBase_URI, String sEndPoint) {
      request.baseURI = sBase_URI + sEndPoint;
      reqSpec = RestAssured.given();

      return reqSpec;
   }

   /**
    * @param head
    */
   public static void setHeaders(String[][] head) {
      for (int row = 0; row < head.length; row++)
         for (int col = 0; col < head[row].length; col++)
            reqSpec.header(head[row][col], head[row][col + 1]);
   }

   /**
    * @param head,val
    */
   public static void setHeader(String head, String val) {
      reqSpec.header(head, val);
   }

   /**
    * @param headers
    */
   public static void setHeader(Map<String, String> headers) {
      reqSpec.headers(headers);
   }

   /**
    * @return Response
    */
   // GET request without any parameter
   public static Response getRequest() {

      response = reqSpec.get();

      return response;
   }

   /**
    * @param key,value
    * @return Response
    */
   // Get request using int type path parameter
   public static Response getRequestUsingPath(String key, int value) {
      response = reqSpec.pathParam(key, value).when().get(forwardSlash + openBracket + key + closeBracket);
      return response;
   }

   /**
    * @param key,value
    * @return Response
    */
   public static Response getRequestUsingPath(String key, String value) {
      response = reqSpec.pathParam(key, value).when().get(forwardSlash + openBracket + key + closeBracket);
      return response;
   }

   /**
    * @param pathParams
    * @return Response
    */
   // Get request using map type query param
   public static Response getRequestUsingQuery(Map<String, String> pathParams) {
      response = reqSpec.queryParams(pathParams).when().get();
      return response;
   }

   /**
    * @param key,value
    * @return Response
    */
   public static Response getRequestUsingQuery(String key, String value) {
      response = reqSpec.queryParam(key, value).when().get();

      return response;
   }

   /**
    * @param postBody
    * @return Response
    */
   public static Response postRequest(String postBody) {
      if (postBody.equals(null)) {
         response = reqSpec.body("").post();
      } else
         response = reqSpec.body(postBody).post();

      return response;
   }

   /**
    * @param postBody,pathParamKey,pathParamValue,query
    * @return Response
    */
   public static Response postRequest(String postBody, String pathParamKey, int pathParamValue,
         Map<String, String> query) {
      if (postBody.equals(null)) {
         response = reqSpec.pathParam(pathParamKey, pathParamValue).queryParams(query).when().body("")
               .post(forwardSlash + openBracket + pathParamKey + closeBracket);
      } else
         response = reqSpec.pathParam(pathParamKey, pathParamValue).queryParams(query).when().body(postBody)
               .post(forwardSlash + openBracket + pathParamKey + closeBracket);

      return response;
   }

   /**
    * @param pathParamKey,pathParamValue,file
    * @return Response
    */
   public static Response uploadImage(String pathParamKey, int pathParamValue, File file) {
      if (file.equals(null)) {
         response = reqSpec.pathParam(pathParamKey, pathParamValue).contentType(ContentType.BINARY).when().body("")
               .post(forwardSlash + openBracket + pathParamKey + closeBracket + "/uploadImage");
      } else {
         response = reqSpec.pathParam(pathParamKey, pathParamValue).body(file).when()
               .post(forwardSlash + openBracket + pathParamKey + closeBracket + "/uploadImage");
      }
      return response;
   }

   /**
    * @param body
    * @return Response
    */
   public static Response patchRequest(String body) {
      if (body.equals(null)) {
         response = reqSpec.body("").patch();
      } else
         response = reqSpec.body(body).patch();

      return response;
   }

   /**
    * @param body
    * @return Response
    */
   public static Response putRequest(String body) {
      if (body.equals(null)) {
         response = reqSpec.body("").put();
      } else
         response = reqSpec.body(body).put();

      return response;
   }

   /**
    * @param body,pathParamKey,pathParamValue
    * @return Response
    */
   public static Response putRequest(String body, String pathParamKey, String pathParamValue) {
      if (body.equals(null)) {
         response = reqSpec.pathParam(pathParamKey, pathParamValue).when()
               .put(forwardSlash + openBracket + pathParamKey + closeBracket);
      } else
         response = reqSpec.pathParam(pathParamKey, pathParamValue).when().body(body)
               .put(forwardSlash + openBracket + pathParamKey + closeBracket);

      return response;
   }

   /**
    * @param body,pathParamKey,pathParamValue
    * @return Response
    */
   public static Response putRequest(String body, String pathParamKey, int pathParamValue) {
      if (body.equals(null)) {
         response = reqSpec.pathParam(pathParamKey, pathParamValue).when()
               .put(forwardSlash + openBracket + pathParamValue + closeBracket);
      } else
         response = reqSpec.pathParam(pathParamKey, pathParamValue).when().body(body)
               .put(forwardSlash + openBracket + pathParamValue + closeBracket);

      return response;
   }

   /**
    * @param key,value
    * @return Response
    */
   public static Response deleteRequest(String key, int value) {
      response = reqSpec.pathParam(key, value).when().delete(forwardSlash + openBracket + key + closeBracket);
      return response;
   }

   /**
    * @param key,value
    * @return Response
    */
   public static Response deleteRequest(String key, String value) {
      response = reqSpec.pathParam(key, value).when().delete(forwardSlash + openBracket + key + closeBracket);
      return response;
   }

   /**
    * @param fileName
    * @return JSONObject
    */
   public static JSONObject readJsonObjectFile(String fileName) {
      JSONObject objectResponse = null;
      try {
         JSONParser parser = new JSONParser();

         Object object = parser.parse(new FileReader(JSON_FILE_PATH + fileName));

         // convert Object to JSONObject
         objectResponse = new JSONObject(object.toString());
      } catch (Exception e) {
         e.getStackTrace();
      }
      return objectResponse;
   }

}
