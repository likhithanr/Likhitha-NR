 package com.n26.functional.pets;

import java.util.HashMap;
import java.util.Map;

import com.n26.common.EndPoints;
import com.n26.common.Request;
import com.n26.core.BaseTest;
import com.n26.report.Log;
import com.n26.report.TestInfo;
import com.n26.utils.ConfigManager;
import com.n26.utils.Excel;
import com.n26.module.*;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.response.Response;

public class CreatePet_POST extends BaseTest{
    
    public String base_URI="";
    public String jsonFile="Pet.json";
    public  Map<String,String> testData=new HashMap<String,String>();
    @BeforeClass
    @Override
    public void scriptStart() {
        TestInfo.scriptInfo("POST - Pet creation", "Validating pet creation POST request", "Likhitha");
    }
    
    @BeforeClass
    public void setUp()
    {

     Log.scriptInfo("Setting up configurations"); 
     base_URI=ConfigManager.gsEnvironment;
     Request.setApiURI(base_URI, EndPoints.POST_PET);
     Request.setHeader("Content-Type", "application/json");   
    
    }

    @Test(priority = 1)
    public void testCreatePet()
    {
        Log.scriptInfo("TestCase 1 : Test case to validate successful pet creation "); 
        JSONObject updatedRequest = Request.readJsonObjectFile(jsonFile);

        Response respsonse= Request.postRequest(updatedRequest.toString());

        Assert.assertEquals(respsonse.statusCode()==200, true);
        Log.scriptInfo("Response : "+respsonse.asString());
        Log.scriptInfo("Status Code : "+respsonse.statusCode());
        //System.out.println(obj.getJSONArray("errors").getJSONObject(0).getString("msg"));
        Assert.assertEquals(respsonse.jsonPath().getString("name").equals(updatedRequest.get("name")),true);
        Log.scriptInfo(respsonse.jsonPath().getString("name")+" : Pet created");
    }

    @Test(priority = 2)
    public void testCreatePetwWithoutMandatoryParams()
    {
        Log.scriptInfo("TestCase 2: Test case to validate bad request when request sent without mandatory field"); 
            testData = Excel.getTestData("Pet", 2);
            JSONObject updatedRequest=Pet.updateJsonBody(testData,2);
            Response respsonse= Request.postRequest(updatedRequest.toString());

            Assert.assertEquals(respsonse.statusCode()==400, true);
            Log.scriptInfo("Response : "+respsonse.asString());
            Log.scriptInfo("Status Code : "+respsonse.statusCode());
            Log.scriptInfo(respsonse.jsonPath().getString("code")+" : Bad Request status code");
            Log.scriptInfo(respsonse.jsonPath().getString("message")+" : Invalid Value");

    }

    @Test(priority = 3)
    public void testCreatePetWithInvalidRequestMethod()
    {
        Log.scriptInfo("TestCase 3: Test case to validate 405 bad request"); 
        JSONObject updatedRequest = Request.readJsonObjectFile(jsonFile);
        Response respsonse= Request.patchRequest(updatedRequest.toString());

        Assert.assertEquals(respsonse.statusCode()==405, true);
        Log.scriptInfo("Response : "+respsonse.asString());
        Log.scriptInfo("Status Code : "+respsonse.statusCode());
        Log.scriptInfo(respsonse.jsonPath().getString("code")+" : Bad Request status code");
        Log.scriptInfo(respsonse.jsonPath().getString("message")+" : Invalid Value");

    }
    @Test(priority = 4)
    public void testCreatePetWithInvalidDataType_Id()
    {
        Log.scriptInfo("TestCase 4: Test case to validate ID param with Inavalid data types"); 
        testData = Excel.getTestData("Pet", 3);
        JSONObject updatedRequest=Pet.updateJsonBody(testData,3);
        Log.scriptInfo(updatedRequest);;
        Response respsonse= Request.postRequest(updatedRequest.toString());

        Assert.assertEquals(respsonse.statusCode()==400, true);
        Log.scriptInfo("Response : "+respsonse.asString());
        Log.scriptInfo("Status Code : "+respsonse.statusCode());
        Log.scriptInfo(respsonse.jsonPath().getString("code")+" : Bad Request status code");
        Log.scriptInfo(respsonse.jsonPath().getString("message")+" : Invalid Value");

    }
    @Test(priority = 4)
    public void testCreatePetWithInvalidDataType_name()
    {
        Log.scriptInfo("TestCase 5: Test case to validate Name param with Inavalid data types"); 
       JSONObject updatedJson= Request.readJsonObjectFile(jsonFile);
       updatedJson.put("name", 123456);
        Response respsonse= Request.postRequest(updatedJson.toString());

        Assert.assertEquals(respsonse.statusCode()==400, true);
        Log.scriptInfo(respsonse.jsonPath().getString("code")+" : Bad Request status code");
        Log.scriptInfo(respsonse.jsonPath().getString("message")+" : Invalid Value");

    }
   
    
}
  