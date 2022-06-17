package com.n26.functional.pets;

import com.n26.common.Constants;
import com.n26.common.EndPoints;
import com.n26.common.Request;
import com.n26.core.BaseTest;
import com.n26.report.Log;
import com.n26.report.TestInfo;
import com.n26.utils.ConfigManager;
import com.n26.utils.Utility;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.response.Response;

/**
 * @author Likhitha NR
 */

public class FindPetByStatus extends BaseTest {

    public String base_URI = "";

    @BeforeClass
    @Override
    public void scriptStart() {
        TestInfo.scriptInfo("GET - Retrieving pet by status", "Validating pet retrieval by status", "Likhitha");
    }

    @BeforeClass
    public void setUp() {

        Log.scriptInfo("Setting up configurations");
        base_URI = ConfigManager.gsEnvironment;
        Request.setApiURI(base_URI, EndPoints.POST_PET);
        Request.setHeader(Constants.CONTENT_TYPE, Constants.CONTENT_TYPE_JSON);

    }

    @Test(priority = 1,groups = {"Smoke","Regression"})
    public void testGetPetByAvailableStatus() {
        Log.scriptInfo("TestCase 1 : Test case to retrieve valid Pet by available status");

        // fetching pet record using GET by status
        Request.setApiURI(base_URI, EndPoints.GET_FINDBYSTATUS);
        Response getResponse = Request.getRequestUsingQuery("status", "available");
        Utility.getInstance().printStatusCodeAndResp(getResponse);
        String subString = getResponse.jsonPath().getString("status")
                .substring(1, getResponse.jsonPath().getString("status").length() - 1).trim();
        String[] status = subString.split(",");
        Log.scriptInfo("Pet status : " + getResponse.jsonPath().getString("status"));
        Assert.assertEquals(getResponse.statusCode() == 200, true);

        // Ensuring proper response recieved from GET call
        for (String val : status) {
            Assert.assertEquals(val.trim().equals("available"), true);
        }

    }

    @Test(priority = 2,groups = {"Regression"})
    public void testValidPetByPendingStatus() {
        Log.scriptInfo("TestCase 2 : Test case to retrieve valid Pet by pending status");

        // fetching pet record using GET by status
        Request.setApiURI(base_URI, EndPoints.GET_FINDBYSTATUS);
        Response getResponse = Request.getRequestUsingQuery("status", "pending");
        Log.scriptInfo("Response : " + getResponse.asString());
        Log.scriptInfo("Status Code : " + getResponse.statusCode());
        String subString = getResponse.jsonPath().getString("status")
                .substring(1, getResponse.jsonPath().getString("status").length() - 1).trim();
        String[] status = subString.split(",");
        Log.scriptInfo("Pet status : " + getResponse.jsonPath().getString("status"));
        Assert.assertEquals(getResponse.statusCode() == 200, true);

        // Ensuring proper response recieved from GET call
        for (String val : status) {
            Assert.assertEquals(val.trim().equals("pending"), true);
        }

    }

    @Test(priority = 3,groups = {"Regression"})
    public void testValidPetBySoldStatus() {
        Log.scriptInfo("TestCase 1 : Test case to retrieve valid Pet by sold status");

        // fetching pet record using GET by status
        Request.setApiURI(base_URI, EndPoints.GET_FINDBYSTATUS);
        Response getResponse = Request.getRequestUsingQuery("status", "sold");
        Utility.getInstance().printStatusCodeAndResp(getResponse);
        String subString = getResponse.jsonPath().getString("status")
                .substring(1, getResponse.jsonPath().getString("status").length() - 1).trim();
        String[] status = subString.split(",");
        Log.scriptInfo("Pet status : " + getResponse.jsonPath().getString("status"));
        Assert.assertEquals(getResponse.statusCode() == 200, true);

        // Ensuring proper response recieved from GET call
        for (String val : status) {
            Assert.assertEquals(val.trim().equals("sold"), true);
        }

    }

    @Test(priority = 4,groups = {"Regression"})
    public void testGetPetByInvalidStatus() {
        Log.scriptInfo("TestCase 1 : Test case to validate find pet by invalid status");

        // fetching pet record using GET by status
        Request.setApiURI(base_URI, EndPoints.GET_FINDBYSTATUS);
        Response getResponse = Request.getRequestUsingQuery("status", "Deleted");
        Utility.getInstance().printStatusCodeAndResp(getResponse);
        Log.scriptInfo("Inavlid status value : " + getResponse.jsonPath().getString("message"));
        Assert.assertEquals(getResponse.statusCode() == 400, true);

    }

}
