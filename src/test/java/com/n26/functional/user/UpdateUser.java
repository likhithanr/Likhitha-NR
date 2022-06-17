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
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.response.Response;

/**
 * @author Likhitha NR
 */

public class UpdateUser extends BaseTest {
    public String base_URI = "";
    public Map<String, String> testData = new HashMap<String, String>();
    public static String user = "user";
    public static String password = "loginPassword";

    @BeforeClass(alwaysRun = true)
    @Override
    public void scriptStart() {
        TestInfo.scriptInfo("Update existing user (PUT)", "Validating  user updation", "Likhitha");
    }

    @BeforeClass(alwaysRun = true)
    public void setUp() {

        Log.scriptInfo("Setting up configurations");
        base_URI = ConfigManager.gsEnvironment;
        testData = Excel.getTestData("User", 1);
        User.loginLogout(base_URI, "Y", testData.get(user), testData.get(password));
    }

    @Test(priority = 1, groups = { "smoke", "regression" })
    public void testUpdateUserWithValidName() {
        Log.scriptInfo("TestCase 1 : Test case to validate update user with valid name");
        JSONObject updatedRequest = Request.readJsonObjectFile(Constants.USER_JSON);

        // user creation
        Request.setApiURI(base_URI, EndPoints.POST_USER);
        Request.setHeader(Constants.CONTENT_TYPE, Constants.CONTENT_TYPE_JSON);
        Response response = Request.postRequest(updatedRequest.toString());
        String userName = response.jsonPath().getString("username");
        Assert.assertEquals(response.getStatusCode() == 200, true);

        // update user name
        testData = Excel.getTestData("User", 2);
        updatedRequest = User.updateJsonBody(testData, 2);
        Request.setApiURI(base_URI, EndPoints.PUT_USERBYNAME);
        Request.setHeader(Constants.CONTENT_TYPE, Constants.CONTENT_TYPE_JSON);
        Response putResponse = Request.putRequest(updatedRequest.toString(), "username", userName);
        Log.scriptInfo("Get user response : " + putResponse.asString());
        Assert.assertEquals(putResponse.getStatusCode() == 200, true);

        // retrieving created user using GET user
        Request.setApiURI(base_URI, EndPoints.GET_USERBYNAME);
        Response getResponse = Request.getRequestUsingPath("username", response.jsonPath().getString("username"));
        Log.scriptInfo("Get user response : " + getResponse.asString());
        Assert.assertEquals(putResponse.asString().equals(getResponse.asString()), true);
        Log.scriptInfo("Succesfully retrieved user details");
    }

    @Test(priority = 2, groups = { "regression" })
    public void testUpdateUserWIthInvalidUserName() {
        Log.scriptInfo("TestCase 2 : Test case to valdiate update user with invalid user name");
        JSONObject updatedRequest = Request.readJsonObjectFile(Constants.USER_JSON);
        Request.setApiURI(base_URI, EndPoints.PUT_USERBYNAME);
        Request.setHeader(Constants.CONTENT_TYPE, Constants.CONTENT_TYPE_JSON);
        Response putResponse = Request.putRequest(updatedRequest.toString(), "username", "abcdef");
        Log.scriptInfo("response : " + putResponse.asString());
        Assert.assertEquals(putResponse.getStatusCode() == 404, true);
    }
}