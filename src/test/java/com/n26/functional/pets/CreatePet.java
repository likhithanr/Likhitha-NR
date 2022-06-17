package com.n26.functional.pets;

import java.util.HashMap;
import java.util.Map;
import com.n26.common.Constants;
import com.n26.common.EndPoints;
import com.n26.common.Request;
import com.n26.core.BaseTest;
import com.n26.report.Log;
import com.n26.report.TestInfo;
import com.n26.utils.ConfigManager;
import com.n26.utils.Excel;
import com.n26.utils.Utility;
import com.n26.module.*;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.response.Response;

/**
 * @author Likhitha NR
 */

public class CreatePet extends BaseTest {
    public String base_URI = "";
    public Map<String, String> testData = new HashMap<String, String>();

    @BeforeClass(alwaysRun = true)
    @Override
    public void scriptStart() {
        TestInfo.scriptInfo("Add a new pet to the store (POST)", "Validating pet creation POST request", "Likhitha");
    }

    @BeforeClass(alwaysRun = true)
    public void setUp() {

        Log.scriptInfo("Setting up configurations");
        base_URI = ConfigManager.gsEnvironment;
        Request.setApiURI(base_URI, EndPoints.POST_PET);
        Request.setHeader(Constants.CONTENT_TYPE, Constants.CONTENT_TYPE_JSON);

    }

    @Test(priority = 1, groups = { "regression" })
    public void testCreatePet() {
        Log.scriptInfo("TestCase 1 : Test case to validate successful pet creation ");
        JSONObject updatedRequest = Request.readJsonObjectFile(Constants.PET_JSON);
        Response respsonse = Request.postRequest(updatedRequest.toString());
        Assert.assertEquals(respsonse.statusCode() == 200, true);
        Utility.getInstance().printStatusCodeAndResp(respsonse);
        Assert.assertEquals(respsonse.jsonPath().getString("name").equals(updatedRequest.get("name")), true);
        Log.scriptInfo(respsonse.jsonPath().getString("name") + " : Pet created");
    }

    @Test(priority = 2, groups = { "regression" })
    public void testCreatePetwWithoutMandatoryParams() {
        Log.scriptInfo("TestCase 2: Test case to validate bad request when request sent without mandatory field");
        testData = Excel.getTestData("Pet", 2);
        JSONObject updatedRequest = Pet.updateJsonBody(testData, 2);
        Response response = Request.postRequest(updatedRequest.toString());
        try {
            Assert.assertEquals(response.statusCode() == 400, true);
            Utility.getInstance().printInvalidParamStatus(response);
        } catch (AssertionError e) {
            Log.error(e);
        }
    }

    @Test(priority = 3, groups = { "regression" })
    public void testCreatePetWithInvalidRequestMethod() {
        Log.scriptInfo("TestCase 3: Test case to validate error response when invalid request method used");
        JSONObject updatedRequest = Request.readJsonObjectFile(Constants.PET_JSON);
        Response response = Request.patchRequest(updatedRequest.toString());
        Assert.assertEquals(response.statusCode() == 405, true);
        Utility.getInstance().printInvalidParamStatus(response);

    }

    @Test(priority = 4, groups = { "regression" })
    public void testCreatePetWithInvalidDataType_Id() {
        Log.scriptInfo("TestCase 4: Test case to validate ID param with Inavalid data types");
        testData = Excel.getTestData("Pet", 3);
        JSONObject updatedRequest = Pet.updateJsonBody(testData, 3);
        Log.scriptInfo(updatedRequest);
        Response response = Request.postRequest(updatedRequest.toString());
        try {
            Assert.assertEquals(response.statusCode() == 400, true);
            Utility.getInstance().printInvalidParamStatus(response);
        } catch (AssertionError e) {
            Log.error(e);
        }
    }

    @Test(priority = 5, groups = { "regression" })
    public void testCreatePetWithInvalidDataType_name() {
        Log.scriptInfo("TestCase 5: Test case to validate Name param with Inavalid data types");
        JSONObject updatedJson = Request.readJsonObjectFile(Constants.PET_JSON);
        updatedJson.put("name", 123456);
        Response response = Request.postRequest(updatedJson.toString());
        try {
            Assert.assertEquals(response.statusCode() == 400, true);
            Utility.getInstance().printInvalidParamStatus(response);
        } catch (AssertionError e) {
            Log.error(e);
        }

    }

}
