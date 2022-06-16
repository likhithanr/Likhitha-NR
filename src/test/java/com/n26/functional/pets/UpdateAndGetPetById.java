 package com.n26.functional.pets;
import java.util.HashMap;
import java.util.Map;

import com.n26.common.EndPoints;
import com.n26.common.Request;
import com.n26.core.BaseTest;
import com.n26.report.Log;
import com.n26.report.TestInfo;
import com.n26.utils.ConfigManager;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.response.Response;

public class UpdateAndGetPetById extends BaseTest{
    
    public String base_URI="";
    public String jsonFile="Pet.json";
    public  Map<String,String> testData=new HashMap<String,String>();
    @BeforeClass
    @Override
    public void scriptStart() {
        TestInfo.scriptInfo("PUT - UPdating existing pet", "Validating pet creation POST request", "Likhitha");
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
    public void testUpdatePetById()
    {
        Log.scriptInfo("TestCase 1 : Test case to validate successful pet creation "); 

        //creating pet
        JSONObject updatedRequest = Request.readJsonObjectFile(jsonFile);
        Response postRespsonse= Request.postRequest(updatedRequest.toString());
        int id=postRespsonse.jsonPath().getInt("id");
        String name=postRespsonse.jsonPath().getString("name");


        //updating id
        JSONObject updatedJson=updatedRequest.put("name", "Lab");
        System.out.println(updatedJson);
        String updatedName=updatedJson.getString("name");
        Response putRespsonse= Request.putRequest(updatedJson.toString());

        //PUT call response 
        Log.scriptInfo("Response : "+putRespsonse.asString());
        Log.scriptInfo("Status Code : "+putRespsonse.statusCode());
        Assert.assertEquals(putRespsonse.jsonPath().getInt("id")==id,true);
        Assert.assertEquals((putRespsonse.jsonPath().getString("name").equals(updatedName)),true);
        Assert.assertEquals(!(putRespsonse.jsonPath().getString("name").equals(postRespsonse.jsonPath().getString("name"))),true);

        Assert.assertEquals(putRespsonse.statusCode()==200, true);

        //getting updated pet data by id
        Response getResponse=Request.getRequestUsingPath("id",id);
       
        //comparing PUT response and GET response for the same Id
        Assert.assertEquals(putRespsonse.asString().equals(getResponse.asString()),true);

        Log.scriptInfo("Old name ---> "+name+" and updated name-->: "+putRespsonse.jsonPath().getString("name")+" : Pet updated succesfully for the ID : "+id);
    }

    @Test(priority = 1)
    public void testUpdatePetByInvaidId()
    {
        Log.scriptInfo("TestCase 2 : Test case to validate unsuccesful Pet updation for invalid id"); 
        Request.setApiURI(base_URI, EndPoints.POST_PET);
        Request.setHeader("Content-Type", "application/json");   
        JSONObject updatedRequest = Request.readJsonObjectFile(jsonFile);
        Response postRespsonse= Request.postRequest(updatedRequest.toString());
        int id=postRespsonse.jsonPath().getInt("id");
        String name=postRespsonse.jsonPath().getString("name");


        //updating id
        JSONObject updatedIdJson=updatedRequest.put("id", id+1);
        JSONObject updatedNameJson=updatedIdJson.put("name", "cow");

        System.out.println(updatedNameJson);
        Response putRespsonse= Request.putRequest(updatedNameJson.toString());

        //PUT call response 
        Log.scriptInfo("Response : "+putRespsonse.asString());
        Log.scriptInfo("Status Code : "+putRespsonse.statusCode());
        Assert.assertEquals(putRespsonse.statusCode()==404, true);

        //getting updated pet data by id
        Response getResponse=Request.getRequestUsingPath("id",id);
       
        //comparing PUT response and GET response for the same Id
        Assert.assertEquals(postRespsonse.asString().equals(getResponse.asString()),true);

        //ensuring data has not updated for that id
        Log.scriptInfo("Old name ---> "+name+" and updated name-->: "+getResponse.jsonPath().getString("name")+" : Pet not updated as invalid ID Passed: ");
    }
   
}

