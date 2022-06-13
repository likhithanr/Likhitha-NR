 package com.n26.functional.pets;


import com.n26.common.EndPoints;
import com.n26.common.Request;
import com.n26.core.BaseTest;

import com.n26.report.Log;
import com.n26.report.TestInfo;
import com.n26.utils.ConfigManager;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.response.Response;

public class FindPetByStatus extends BaseTest{
    
    public String base_URI="";
    public String jsonFile="Pet.json";
    @BeforeClass
    @Override
    public void scriptStart() {
        TestInfo.scriptInfo("GET - UPdating existing pet", "Validating pet creation POST request", "Likhitha");
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
    public void testValidPetByAvailableStatus()
    {
        Log.scriptInfo("TestCase 1 : Test case to retrieve valid Pet by status"); 


        //fetching pet record using GET by status
        Request.setApiURI(base_URI, EndPoints.GET_FINDBYSTATUS);
        Response getResponse=Request.getRequest("status","available");
        Log.scriptInfo("Response : "+getResponse.asString());
        Log.scriptInfo("Status Code : "+getResponse.statusCode());
        String subString=getResponse.jsonPath().getString("status").substring(1, getResponse.jsonPath().getString("status").length()-1).trim();
        String[] status = subString.split(",");

        Log.scriptInfo("Pet status : "+getResponse.jsonPath().getString("status"));
        Assert.assertEquals(getResponse.statusCode()==200, true);

       
        //Ensuring proper response recieved from GET call 
        for(String val:status){
            Assert.assertEquals(val.trim().equals("available"),true);
        }

    }
    @Test(priority = 2)
    public void testValidPetByPendingStatus()
    {
        Log.scriptInfo("TestCase 2 : Test case to retrieve valid Pet by status"); 


        //fetching pet record using GET by status
        Request.setApiURI(base_URI, EndPoints.GET_FINDBYSTATUS);
        Response getResponse=Request.getRequest("status","pending");
        Log.scriptInfo("Response : "+getResponse.asString());
        Log.scriptInfo("Status Code : "+getResponse.statusCode());
        String subString=getResponse.jsonPath().getString("status").substring(1, getResponse.jsonPath().getString("status").length()-1).trim();
        String[] status = subString.split(",");

        Log.scriptInfo("Pet status : "+getResponse.jsonPath().getString("status"));
        Assert.assertEquals(getResponse.statusCode()==200, true);

       
        //Ensuring proper response recieved from GET call 
        for(String val:status){
            Assert.assertEquals(val.trim().equals("pending"),true);
        }

    }

    @Test(priority = 3)
    public void testValidPetBySoldStatus()
    {
        Log.scriptInfo("TestCase 1 : Test case to retrieve valid Pet by status"); 


        //fetching pet record using GET by status
        Request.setApiURI(base_URI, EndPoints.GET_FINDBYSTATUS);
        Response getResponse=Request.getRequest("status","sold");
        Log.scriptInfo("Response : "+getResponse.asString());
        Log.scriptInfo("Status Code : "+getResponse.statusCode());
        String subString=getResponse.jsonPath().getString("status").substring(1, getResponse.jsonPath().getString("status").length()-1).trim();
        String[] status = subString.split(",");

        Log.scriptInfo("Pet status : "+getResponse.jsonPath().getString("status"));
        Assert.assertEquals(getResponse.statusCode()==200, true);

       
        //Ensuring proper response recieved from GET call 
        for(String val:status){
            Assert.assertEquals(val.trim().equals("sold"),true);
        }

    }
     @Test(priority = 4)
    public void testGetPetByInvalidStatus()
    {
        Log.scriptInfo("TestCase 1 : Test case to validate find pet by invalid status"); 

       

        //fetching pet record using GET by status
        Request.setApiURI(base_URI, EndPoints.GET_FINDBYSTATUS);
        Response getResponse=Request.getRequest("status","Deleted");
        Log.scriptInfo("Response : "+getResponse.asString());
        Log.scriptInfo("Status Code : "+getResponse.statusCode());
        Log.scriptInfo("Inavlid status value : "+getResponse.jsonPath().getString("message"));
        Assert.assertEquals(getResponse.statusCode()==400, true);     
         
    } 


   
}
