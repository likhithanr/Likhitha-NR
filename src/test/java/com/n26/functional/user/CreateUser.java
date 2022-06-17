package com.n26.functional.user;

import java.util.HashMap;
import java.util.Map;

import com.n26.common.Constants;
import com.n26.common.EndPoints;
import com.n26.common.Request;
import com.n26.core.BaseTest;
import com.n26.module.User;
import com.n26.report.Log;
import com.n26.report.TestInfo;
import com.n26.utils.ConfigManager;
import com.n26.utils.Excel;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.response.Response;

/**
 * @author Likhitha NR
 */

public class CreateUser extends BaseTest {
    public String base_URI = "";
    public Map<String, String> testData = new HashMap<String, String>();
    public static String user = "user";
    public static String password = "loginPassword";

    @BeforeClass(alwaysRun = true)
    @Override
    public void scriptStart() {
        TestInfo.scriptInfo("Create new user (POST)", "Validating single and multiple user creation", "Likhitha");
    }

    @BeforeClass(alwaysRun = true)
    public void setUp() {

        Log.scriptInfo("Setting up configurations");
        base_URI = ConfigManager.gsEnvironment;
        testData = Excel.getTestData("User", 1);
        User.loginLogout(base_URI, "Y", testData.get(user), testData.get(password));

    }

    @Test(priority = 1,groups = {"regression"})
    public void testSingleUserCreation() {
        Log.scriptInfo("TestCase 1 : Test case to validate single user creation");
        JSONObject updatedRequest = Request.readJsonObjectFile(Constants.USER_JSON);

        // user creation
        Request.setApiURI(base_URI, EndPoints.POST_USER);
        Request.setHeader(Constants.CONTENT_TYPE, Constants.CONTENT_TYPE_JSON);
        Response response = Request.postRequest(updatedRequest.toString());
        Log.scriptInfo("Created user response : " + response.asString());
        Assert.assertEquals(response.getStatusCode() == 200, true);
        Log.scriptInfo("User succesfully created with status code : " + response.getStatusCode());
        Log.scriptInfo("Created user response : " + response.asString());
        Log.scriptInfo("username : " + response.jsonPath().getString("username"));
        
        // retrieving created user using GET user
        Request.setApiURI(base_URI, EndPoints.GET_USERBYNAME);
        Response getResponse = Request.getRequestUsingPath("username", response.jsonPath().getString("username"));
        Log.scriptInfo("Get user response : " + getResponse.asString());
        Assert.assertEquals(getResponse.asString().equals(response.asString()), true);
        Log.scriptInfo("Succesfully retrieved user details");
    }

    @Test(priority = 2,groups = {"smoke","regression"})
    public void testMultipleUserCreation() {
        Log.scriptInfo("TestCase 2 : Test case to validate multiple user creation");
        JSONArray userList = new JSONArray();
        for (int i = 2; i <= 3; i++) {
            testData = Excel.getTestData("user", 1);
            JSONObject updatedRequest = Request.readJsonObjectFile(Constants.USER_JSON);
            userList = User.createListOfUser(updatedRequest);
        }
        // user creation
        Request.setApiURI(base_URI, EndPoints.POST_USERLIST);
        Request.setHeader(Constants.CONTENT_TYPE, Constants.CONTENT_TYPE_JSON);
        Response response = Request.postRequest(userList.toString());
        Log.scriptInfo("Created list of users  : " + response.asString());
        Assert.assertEquals(response.getStatusCode() == 200, true);
        Log.scriptInfo("User succesfully created with status code : " + response.getStatusCode());

    }

}