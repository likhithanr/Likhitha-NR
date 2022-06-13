package com.n26.functional.pets;

import com.n26.report.TestInfo;
import com.n26.utils.ConfigManager;
import com.n26.utils.Excel;

import java.util.HashMap;
import java.util.Map;

import com.n26.common.EndPoints;
import com.n26.common.Request;
import com.n26.core.BaseTest;
import com.n26.module.Pet;
import com.n26.report.Log;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.response.Response;

public class FindByTags extends BaseTest{
    
    public String base_URI="";
    public String jsonFile="Pet.json";
    public  Map<String,String> testData=new HashMap<String,String>();

    @BeforeClass
    @Override
    public void scriptStart() {
        TestInfo.scriptInfo("GET - Retrieving pet by tags", "Validating pet creation POST request", "Likhitha");
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
    public void testGetPetByValidTag()
    {
        Log.scriptInfo("TestCase 1 : Test case to retrieve valid Pet by tags"); 
        testData = Excel.getTestData("Pet", 6);
        JSONObject updatedRequest=Pet.updateJsonBody(testData,6);
        Request.postRequest(updatedRequest.toString());


        //fetching pet record using GET by status
        Request.setApiURI(base_URI, EndPoints.GET_FINDBYTAG);
        Response getResponse=Request.getRequest("tags","own");
        Log.scriptInfo("Status Code : "+getResponse.statusCode());
        String subString=getResponse.jsonPath().getString("tags.name");
        Log.scriptInfo("Pet tags : "+subString);
        Assert.assertEquals(getResponse.statusCode()==200, true);
        int matchingTagResponse=getResponse.body().jsonPath().getList("$").size();
        int extractedTaValue=getResponse.body().jsonPath().getList("tags.name").size();
        Assert.assertEquals(matchingTagResponse==extractedTaValue,true);
        Log.scriptInfo("Expected filered tags pet are displaying : "+getResponse.asString());

    }

    @Test(priority = 2)
    public void tesGetPetByInvalidTags()
    {
        Log.scriptInfo("TestCase 2 : Test case to retrieve invalid Pet by tags"); 
        testData = Excel.getTestData("Pet", 6);
        JSONObject updatedRequest=Pet.updateJsonBody(testData,6);
        Request.postRequest(updatedRequest.toString());


        //fetching pet record using GET by status
        Request.setApiURI(base_URI, EndPoints.GET_FINDBYTAG);
        Response getResponse=Request.getRequest("tags","own1");
        Log.scriptInfo("Status Code : "+getResponse.statusCode());
        boolean boolVal=getResponse.body().jsonPath().getList("$").isEmpty();
        Log.scriptInfo("There is no pet available for the tag : "+boolVal);
        Assert.assertEquals(getResponse.statusCode()==200, true);
    
        Assert.assertEquals(boolVal,true);
        Log.scriptInfo("Expected empty tag array is displaying : "+getResponse.asString());

    }

   
}