package com.n26.functional.user;

import java.util.HashMap;
import java.util.Map;

import com.n26.common.EndPoints;
import com.n26.common.Request;
import com.n26.core.BaseTest;
import com.n26.module.User;
import com.n26.report.Log;
import com.n26.report.TestInfo;
import com.n26.utils.ConfigManager;
import com.n26.utils.Excel;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.response.Response;

public class UpdateUser extends BaseTest{
    
    public String base_URI="";
    public String jsonFile="User.json";
    public  Map<String,String> testData=new HashMap<String,String>();
    public static String user="user";
    public static String password="loginPassword";

    @BeforeClass
    @Override
    public void scriptStart() {
        TestInfo.scriptInfo("POST - User creation ", "Validating single and multiple creation", "Likhitha");
    }
    
    @BeforeClass
    public void setUp()
    {

        Log.scriptInfo("Setting up configurations"); 
        base_URI=ConfigManager.gsEnvironment;
        testData=Excel.getTestData("User", 1);
        User.loginLogout(base_URI, "Y",testData.get(user), testData.get(password));
    
    }

    @Test(priority = 1)
    public void testSingleUserCreation()
    {
        Log.scriptInfo("TestCase 1 : Test case to validate single user creation"); 
    
        JSONObject updatedRequest = Request.readJsonObjectFile(jsonFile);
        //user creation
        Request.setApiURI(base_URI, EndPoints.POST_USER);
        Request.setHeader("Content-Type","application/json");
        Response response= Request.postRequest(updatedRequest.toString());
        String userName=response.jsonPath().getString("username");
        Assert.assertEquals(response.getStatusCode()==200, true);
        
        //update user name
        testData=Excel.getTestData("User", 2);
        updatedRequest=User.updateJsonBody(testData, 2);
        Request.setApiURI(base_URI, EndPoints.PUT_USERBYNAME);
        Request.setHeader("Content-Type","application/json");
        Response putResponse=Request.putRequest(updatedRequest.toString(),"username",userName);
        Log.scriptInfo("Get user response : "+putResponse.asString());

        Assert.assertEquals(putResponse.getStatusCode()==200, true);


        //retrieving created user using GET user
        Request.setApiURI(base_URI, EndPoints.GET_USERBYNAME);
         Response getResponse = Request.getRequestUsingPath("username", response.jsonPath().getString("username"));
         Log.scriptInfo("Get user response : "+getResponse.asString());
        
         Assert.assertEquals(putResponse.asString().equals(getResponse.asString()), true);
         Log.scriptInfo("Succesfully retrieved user details");
        }




    @Test(priority = 2)
    public void testUpdateUserWIthInvalidUserName()
    {
        Log.scriptInfo("TestCase 2 : Test case to valdiate update user with invalid user name"); 
    
        JSONObject updatedRequest = Request.readJsonObjectFile(jsonFile);
        Request.setApiURI(base_URI, EndPoints.PUT_USERBYNAME);
        Request.setHeader("Content-Type","application/json");
        Response putResponse=Request.putRequest(updatedRequest.toString(),"username","abcdef");
        Log.scriptInfo("response : "+putResponse.asString());
        Assert.assertEquals(putResponse.getStatusCode()==404, true);


        //retrieving created user using GET user
         Log.scriptInfo("Succesfully retrieved user details");
        }
    }