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

public class DeleteOrder extends BaseTest{
    
    public String base_URI="";
    public String jsonFile="Store.json";
    public  Map<String,String> testData=new HashMap<String,String>();
    @BeforeClass
    @Override
    public void scriptStart() {
        TestInfo.scriptInfo("DELETE - Deleting created order ", "Validating store order deletion", "Likhitha");
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
    public void testDeleteOrderByValidId()
    {
        Log.scriptInfo("TestCase 1 : Test case to validate deleting order from store with valid id "); 
        JSONObject updatedRequest = Request.readJsonObjectFile(jsonFile);
       // updatedRequest.put("id", 222);
        Response respsonse= Request.postRequest(updatedRequest.toString());
        int id = respsonse.jsonPath().getInt("id");
        Assert.assertEquals(respsonse.statusCode()==200, true);
        Log.scriptInfo("Pet created sucesfully with id : "+id);
       
        Request.setApiURI(base_URI, EndPoints.GET_ORDER);
        Response getResponse=Request.getRequestUsingPath("id",id);
        System.out.println(getResponse.asPrettyString());
        Assert.assertEquals(getResponse.statusCode()==200, true);
        Log.scriptInfo("Retrieved order succesfully for the id : "+id);
       
        //deleting created order
         //retriving created order
        Request.setApiURI(base_URI, EndPoints.DELETE_ORDER);
        Response deleteResponse=Request.deleteRequest("id",id);
        Log.scriptInfo("Response : "+deleteResponse.asString());
        Log.scriptInfo("Status Code : "+deleteResponse.statusCode());
        Assert.assertEquals(deleteResponse.statusCode()==200, true);
        Log.scriptInfo("Order deleted succesfully for the id : "+id);
       
        //retrieving deleted order
        Request.setApiURI(base_URI, EndPoints.GET_ORDER);
         getResponse=Request.getRequestUsingPath("id",id);
        Assert.assertEquals(getResponse.statusCode()==404, true);
        Log.scriptInfo("Order does not exist for deleted id :  "+id+" says "+getResponse.asString());
    }

    @Test(priority = 2)
    public void testDeleteOrderByInValidId()
    {
        Log.scriptInfo("TestCase 2 : Test case to validate deleting order from store with invalid id "); 
      
        Request.setApiURI(base_URI, EndPoints.GET_ORDER);
        Response getResponse=Request.getRequestUsingPath("id",55);
        Assert.assertEquals(getResponse.asString().equals("Order not found"), true);

        //there is not orderexist for the is 55 so deletion should be succesfull
        if(getResponse.asString().equals("Order not found")){
            {
                Request.setApiURI(base_URI, EndPoints.DELETE_ORDER);
                Response deleteResponse=Request.deleteRequest("id",55555);
                Log.scriptInfo("Status Code : "+deleteResponse.statusCode());
                Assert.assertEquals(deleteResponse.statusCode()==404, true);
            }

        }
    }
}