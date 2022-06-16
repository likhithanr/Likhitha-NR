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

public class DeleteUser extends BaseTest{
    
    public String base_URI="";
    public String jsonFile="User.json";
    public  Map<String,String> testData=new HashMap<String,String>();
    public static String user="user";
    public static String password="loginPassword";

    @BeforeClass
    @Override
    public void scriptStart() {
        TestInfo.scriptInfo("DELETE - User deletion ", "Validating user deletion", "Likhitha");
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
    public void testDeleteUserWithValidName()
    {
        Log.scriptInfo("TestCase 1 : Test case to validate single user creation"); 
    
        JSONObject updatedRequest = Request.readJsonObjectFile(jsonFile);
        
        //user creation
        Request.setApiURI(base_URI, EndPoints.POST_USER);
        Request.setHeader("Content-Type","application/json");
        Response response= Request.postRequest(updatedRequest.toString());
        String userName=response.jsonPath().getString("username");
        Assert.assertEquals(response.getStatusCode()==200, true);
        //retrieving created user using GET user
        Request.setApiURI(base_URI, EndPoints.GET_USERBYNAME);
         Response getResponse = Request.getRequestUsingPath("username", response.jsonPath().getString("username"));
        
         Assert.assertEquals(getResponse.asString().equals(response.asString()), true);
         Log.scriptInfo("Succesfully retrieved user details");


         //deleting the user
        Response deleteRespone=Request.deleteRequest("username", userName);
        Assert.assertEquals(deleteRespone.statusCode()==200, true);
        Log.scriptInfo("Succesfully deleted the user with status code : "+deleteRespone.statusCode());

        Log.scriptInfo("User "+userName+" deleted succesfully with message : " +deleteRespone.asString());

        }

        @Test(priority = 2)
        public void testDeleteUserWithInValidName()
        {
            Log.scriptInfo("TestCase 2 : Test case to validate single user creation"); 
        
    
             //deleting the user
            Response deleteRespone=Request.deleteRequest("username", "abcdef");
            Assert.assertEquals(deleteRespone.statusCode()==404, true);
            Log.scriptInfo("User does not exist");
        
            }
    }
