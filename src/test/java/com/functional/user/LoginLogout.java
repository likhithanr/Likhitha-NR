package com.functional.user;

import java.util.HashMap;
import java.util.Map;
import com.core.BaseTest;
import com.module.User;
import com.report.Log;
import com.report.TestInfo;
import com.utils.ConfigManager;
import com.utils.Excel;
import com.utils.Utility;
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

    @BeforeClass(alwaysRun = true)
    @Override
    public void scriptStart() {
        TestInfo.scriptInfo(" User login-logout (GET)", "Validating login and logout", "Likhitha");
    }

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        Log.scriptInfo("Setting up configurations");
        base_URI = ConfigManager.gsEnvironment;
    }

    @Test(priority = 1, groups = { "smoke", "regression" })
    public void testUserLogin() {
        Log.scriptInfo("TestCase 1 : Test case to Validate user login ");
        testData = Excel.getTestData("User", 1);
        Response response = User.loginLogout(base_URI, "Y", testData.get(user), testData.get(password));
        Assert.assertEquals(response.statusCode() == 200, true);
        Utility.getInstance().printStatusCodeAndResp(response);
    }

    @Test(priority = 2, groups = { "regression" })
    public void testUserLoginwithOnlyUserName() {
        Log.scriptInfo("TestCase 2 : Test case to Validate error message when only user name is passed");
        testData = Excel.getTestData("User", 1);
        Response response = User.loginLogout(base_URI, "Y", testData.get(user), "");

        // expected error message for invalid user name and password
        try {
            Assert.assertEquals(response.statusCode() == 400, true);
            Utility.getInstance().printInvalidParamStatus(response);
        } catch (AssertionError e) {
            Log.error(e);
        }

    }

    @Test(priority = 3, groups = { "regression" })
    public void testUserLoginwithOnlyPassword() {
        Log.scriptInfo("TestCase 3 : Test case to Validate error message when only password is passed");
        testData = Excel.getTestData("User", 1);
        Response response = User.loginLogout(base_URI, "Y", "", testData.get(password));

        // expected error message for invalid user name and password
        try {
            Assert.assertEquals(response.statusCode() == 400, true);
            Utility.getInstance().printInvalidParamStatus(response);
        } catch (AssertionError e) {
            Log.error(e);
        }
    }

    @Test(priority = 4, groups = { "smoke", "regression" })
    public void testUserLogout() {
        Log.scriptInfo("TestCase 4 : Test case to Validate succesfull logout");
        Response response = User.loginLogout(base_URI, "N", "", "");

        // expected error message for invalid user name and password
        Assert.assertEquals(response.statusCode() == 200, true);
        Utility.getInstance().printStatusCodeAndResp(response);
    }

}