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

public class GetOrders extends BaseTest{
    
    public String base_URI="";
    public String jsonFile="Store.json";
    public  Map<String,String> testData=new HashMap<String,String>();
    @BeforeClass
    @Override
    public void scriptStart() {
        TestInfo.scriptInfo("GET - Get order details  ", "Validating get order details", "Likhitha");
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
    public void testGetOrderByInvalidId()
    {
        Log.scriptInfo("TestCase 1 : Test case to validate get order by invalid id "); 
        JSONObject updatedRequest = Request.readJsonObjectFile(jsonFile);
        Response respsonse= Request.postRequest(updatedRequest.toString());
        int id = respsonse.jsonPath().getInt("id");
        Assert.assertEquals(respsonse.statusCode()==200, true);
        Log.scriptInfo("Pet created sucesfully with id : "+id);
       
        Response getResponse=Request.getRequestUsingPath("id",id+111);
        Assert.assertEquals(getResponse.statusCode()==404, true);
        Log.scriptInfo("Response : "+getResponse.asString());
        Log.scriptInfo("Status Code : "+getResponse.statusCode());
        Assert.assertEquals(getResponse.asString().equals("Order not found"), true);

        Log.scriptInfo("Order does not exist for the id :  "+(id+111)+" says "+getResponse.asString());
    }
}