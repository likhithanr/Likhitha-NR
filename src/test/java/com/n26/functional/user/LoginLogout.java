package com.n26.functional.user;

import java.util.HashMap;
import java.util.Map;
import com.n26.core.BaseTest;
import com.n26.module.User;
import com.n26.report.Log;
import com.n26.report.TestInfo;
import com.n26.utils.ConfigManager;
import com.n26.utils.Excel;
import com.n26.utils.Utility;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.response.Response;

/**
 * @author Likhitha NR
 */

public class LoginLogout extends BaseTest {

    public String base_URI = "";
    public Map<String, String> testData = new HashMap<String, String>();
    public static String user = "user";
    public static String password = "loginPassword";

    @BeforeClass
    @Override
    public void scriptStart() {
        TestInfo.scriptInfo("GET - User login-logout ", "Validating login and logout", "Likhitha");
    }

    @BeforeClass
    public void setUp() {

        Log.scriptInfo("Setting up configurations");
        base_URI = ConfigManager.gsEnvironment;

    }

    @Test(priority = 1,groups = {"Smoke","Regression"})
    public void testUserLogin() {
        Log.scriptInfo("TestCase 1 : Test case to Validate user login ");
        testData = Excel.getTestData("User", 1);
        Response response = User.loginLogout(base_URI, "Y", testData.get(user), testData.get(password));
        Assert.assertEquals(response.statusCode() == 200, true);
        Utility.getInstance().printStatusCodeAndResp(response);

    }

    @Test(priority = 2)
    public void testUserLoginwithOnlyUserName() {
        Log.scriptInfo("TestCase 2 : Test case to Validate error message when only user name is passed");
        testData = Excel.getTestData("User", 1);
        Response response = User.loginLogout(base_URI, "Y", testData.get(user), "");

        // expected error message for invalid user name and password
        Assert.assertEquals(response.statusCode() == 400, true);
        Utility.getInstance().printStatusCodeAndResp(response);
        Log.scriptInfo("user succesfully logged out");

    }

    @Test(priority = 3,groups = {"Regression"})
    public void testUserLoginwithOnlyPassword() {
        Log.scriptInfo("TestCase 3 : Test case to Validate error message when only password is passed");
        testData = Excel.getTestData("User", 1);
        Response response = User.loginLogout(base_URI, "Y", "", testData.get(password));

        // expected error message for invalid user name and password
        Assert.assertEquals(response.statusCode() == 400, true);
        Utility.getInstance().printStatusCodeAndResp(response);

    }

    @Test(priority = 4,groups = {"Smoke","Regression"})
    public void testUserLogout() {
        Log.scriptInfo("TestCase 4 : Test case to Validate succesfull logout");
        Response response = User.loginLogout(base_URI, "N", "", "");

        // expected error message for invalid user name and password
        Assert.assertEquals(response.statusCode() == 200, true);
        Utility.getInstance().printStatusCodeAndResp(response);

    }

}