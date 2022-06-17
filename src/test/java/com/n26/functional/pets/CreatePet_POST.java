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

public class CreatePet_POST extends BaseTest {

    public String base_URI = "";
    // public String jsonFile = "Pet.json";
    public Map<String, String> testData = new HashMap<String, String>();

    @BeforeClass
    @Override
    public void scriptStart() {
        TestInfo.scriptInfo("POST - Pet creation", "Validating pet creation POST request", "Likhitha");
    }

    @BeforeClass
    public void setUp() {

        Log.scriptInfo("Setting up configurations");
        base_URI = ConfigManager.gsEnvironment;
        Request.setApiURI(base_URI, EndPoints.POST_PET);
        Request.setHeader(Constants.CONTENT_TYPE, Constants.CONTENT_TYPE_JSON);

    }

    @Test(priority = 1,groups = {"Regression"})
    public void testCreatePet() {
        Log.scriptInfo("TestCase 1 : Test case to validate successful pet creation ");
        JSONObject updatedRequest = Request.readJsonObjectFile(Constants.PET_JSON);
        Response respsonse = Request.postRequest(updatedRequest.toString());
        Assert.assertEquals(respsonse.statusCode() == 200, true);
        Utility.getInstance().printStatusCodeAndResp(respsonse);
        Assert.assertEquals(respsonse.jsonPath().getString("name").equals(updatedRequest.get("name")), true);
        Log.scriptInfo(respsonse.jsonPath().getString("name") + " : Pet created");
    }

    @Test(priority = 2,groups = {"Regression"})
    public void testCreatePetwWithoutMandatoryParams() {
        Log.scriptInfo("TestCase 2: Test case to validate bad request when request sent without mandatory field");
        testData = Excel.getTestData("Pet", 2);
        JSONObject updatedRequest = Pet.updateJsonBody(testData, 2);
        Response response = Request.postRequest(updatedRequest.toString());
        Assert.assertEquals(response.statusCode() == 400, true);
        Utility.getInstance().printStatusCodeAndResp(response);
        Utility.getInstance().printInvalidParamStatus(response);

    }

    @Test(priority = 3,groups = {"Regression"})
    public void testCreatePetWithInvalidRequestMethod() {
        Log.scriptInfo("TestCase 3: Test case to validate error response when invalid request method used");
        JSONObject updatedRequest = Request.readJsonObjectFile(Constants.PET_JSON);
        Response response = Request.patchRequest(updatedRequest.toString());
        Assert.assertEquals(response.statusCode() == 405, true);
        Utility.getInstance().printStatusCodeAndResp(response);
        Utility.getInstance().printInvalidParamStatus(response);

    }

    @Test(priority = 4,groups = {"Regression"})
    public void testCreatePetWithInvalidDataType_Id() {
        Log.scriptInfo("TestCase 4: Test case to validate ID param with Inavalid data types");
        testData = Excel.getTestData("Pet", 3);
        JSONObject updatedRequest = Pet.updateJsonBody(testData, 3);
        Log.scriptInfo(updatedRequest);
        ;
        Response response = Request.postRequest(updatedRequest.toString());
        Assert.assertEquals(response.statusCode() == 400, true);
        Utility.getInstance().printStatusCodeAndResp(response);
        Utility.getInstance().printInvalidParamStatus(response);

    }

    @Test(priority = 5,groups = {"Regression"})
    public void testCreatePetWithInvalidDataType_name() {
        Log.scriptInfo("TestCase 5: Test case to validate Name param with Inavalid data types");
        JSONObject updatedJson = Request.readJsonObjectFile(Constants.PET_JSON);
        updatedJson.put("name", 123456);
        Response response = Request.postRequest(updatedJson.toString());
        Assert.assertEquals(response.statusCode() == 400, true);
        Utility.getInstance().printInvalidParamStatus(response);

    }

}
