package com.n26.functional.store;

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

public class CreateOrder extends BaseTest{
    
    public String base_URI="";
    public String jsonFile="Store.json";
    public  Map<String,String> testData=new HashMap<String,String>();
    @BeforeClass
    @Override
    public void scriptStart() {
        TestInfo.scriptInfo("POST - Order creation ", "Validating pet store order creation", "Likhitha");
    }
    
    @BeforeClass
    public void setUp()
    {

     Log.scriptInfo("Setting up configurations"); 
     base_URI=ConfigManager.gsEnvironment;
     Request.setApiURI(base_URI, EndPoints.POST_STOREORDER);
     Request.setHeader("Content-Type", "application/json");   
    
    }

    @Test(priority = 1)
    public void testCreatePetOrder()
    {
        Log.scriptInfo("TestCase 1 : Test case toValidate pet order creation "); 
        JSONObject updatedRequest = Request.readJsonObjectFile(jsonFile);

        Response respsonse= Request.postRequest(updatedRequest.toString());

        Assert.assertEquals(respsonse.statusCode()==200, true);
        Log.scriptInfo("Response : "+respsonse.asString());
        Log.scriptInfo("Status Code : "+respsonse.statusCode());
       
       // Assert.assertEquals(respsonse.jsonPath().getString("name").equals(updatedRequest.get("name")),true);
        Log.scriptInfo("Order created for the pet id : "+respsonse.jsonPath().getInt("petId")+" with quantity : "+respsonse.jsonPath().getInt("quantity"));
    }

    @Test(priority = 2)
    public void testCreatePlacedPetOrder()
    {
        Log.scriptInfo("TestCase 2 : Test case to validate order creation with pending status "); 
        JSONObject updatedRequest = Request.readJsonObjectFile(jsonFile);
        updatedRequest.put("status", "placed");
        Response respsonse= Request.postRequest(updatedRequest.toString());
        int id=respsonse.jsonPath().getInt("id");

        Assert.assertEquals(respsonse.statusCode()==200, true);
        Log.scriptInfo("Response : "+respsonse.asString());
        Log.scriptInfo("Status Code : "+respsonse.statusCode());
        Assert.assertEquals(updatedRequest.getString("status").equals(respsonse.jsonPath().getString("status")), true,"Order created with placed status");
        Log.scriptInfo("Order created for the pet id : "+respsonse.jsonPath().getInt("petId")+" with status : "+respsonse.jsonPath().getString("status"));
        
        //retrieving created order detils 
        Request.setApiURI(base_URI, EndPoints.GET_ORDER);
        Response getResponse=Request.getRequestUsingPath("id", id);
        Assert.assertEquals(getResponse.jsonPath().getInt("id")==id, true);
        Log.scriptInfo("order succesfully created with id : "+id+" and with status as : "+getResponse.jsonPath().getString("status"));

    }
    @Test(priority = 3)
    public void testCreateDeliveredPetOrder()
    {
        Log.scriptInfo("TestCase 3 : Test case to validate order creation with delivered status "); 
        JSONObject updatedRequest = Request.readJsonObjectFile(jsonFile);
        updatedRequest.put("status", "delivered");
        Request.setApiURI(base_URI, EndPoints.POST_STOREORDER);
        Request.setHeader("Content-Type", "application/json");   
        Response respsonse= Request.postRequest(updatedRequest.toString());
        int id=respsonse.jsonPath().getInt("id");

        Assert.assertEquals(respsonse.statusCode()==200, true);
        Log.scriptInfo("Response : "+respsonse.asString());
        Log.scriptInfo("Status Code : "+respsonse.statusCode());
        Assert.assertEquals(updatedRequest.getString("status").equals(respsonse.jsonPath().getString("status")), true,"Order created with delivered status");
        Log.scriptInfo("Order created for the pet id : "+respsonse.jsonPath().getInt("petId")+" with status : "+respsonse.jsonPath().getString("status"));
        
        //retrieving created order detils 
        Request.setApiURI(base_URI, EndPoints.GET_ORDER);
        Response getResponse=Request.getRequestUsingPath("id", id);
        Assert.assertEquals(getResponse.jsonPath().getInt("id")==id, true);
        Log.scriptInfo("order succesfully created with id : "+id+" and with status as : "+getResponse.jsonPath().getString("status"));

    }
    @Test(priority = 4)
    public void testCreateOrderWithInvalidStatus()
    {
        Log.scriptInfo("TestCase 4 : Test case to validate order creation with invalid status "); 
        JSONObject updatedRequest = Request.readJsonObjectFile(jsonFile);
        updatedRequest.put("status", "pending");
        Request.setApiURI(base_URI, EndPoints.POST_STOREORDER);
        Request.setHeader("Content-Type", "application/json");   

        Response respsonse= Request.postRequest(updatedRequest.toString());

        //expected status code is 405 as invalid status value is passed
        Log.scriptInfo("Response : "+respsonse.asString());
        Log.scriptInfo("Status Code : "+respsonse.statusCode());
        Assert.assertEquals(respsonse.statusCode()==400, true);
        Log.scriptInfo("Order can not be created with invalid status");
        
    }
}
