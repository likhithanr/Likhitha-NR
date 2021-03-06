package com.functional.pets;

import java.util.HashMap;
import java.util.Map;
import com.common.Constants;
import com.common.EndPoints;
import com.common.Request;
import com.core.BaseTest;
import com.report.Log;
import com.report.TestInfo;
import com.utils.ConfigManager;
import com.utils.Utility;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.response.Response;

/**
 * @author Likhitha NR
 */

public class UpdatePetById extends BaseTest {
    public String base_URI = "";
    public Map<String, String> testData = new HashMap<String, String>();

    @BeforeClass(alwaysRun = true)
    @Override
    public void scriptStart() {
        TestInfo.scriptInfo("Update existing pet (PUT)", "Validating pet updation", "Likhitha");
    }

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        Log.scriptInfo("Setting up configurations");
        base_URI = ConfigManager.gsEnvironment;
        Request.setApiURI(base_URI, EndPoints.POST_PET);
        Request.setHeader(Constants.CONTENT_TYPE, Constants.CONTENT_TYPE_JSON);
    }

    @Test(priority = 1, groups = { "smoke", "regression" })
    public void testUpdatePetById() {
        Log.scriptInfo("TestCase 1 : Test case to validate pet updation by Id ");

        // creating pet
        JSONObject updatedRequest = Request.readJsonObjectFile(Constants.PET_JSON);
        Response postRespsonse = Request.postRequest(updatedRequest.toString());
        int id = postRespsonse.jsonPath().getInt("id");
        String name = postRespsonse.jsonPath().getString("name");

        // updating id
        JSONObject updatedJson = updatedRequest.put("name", "Lab");
        System.out.println(updatedJson);
        String updatedName = updatedJson.getString("name");
        Response putRespsonse = Request.putRequest(updatedJson.toString());

        // PUT call response
        Utility.getInstance().printStatusCodeAndResp(putRespsonse);
        Assert.assertEquals(putRespsonse.jsonPath().getInt("id") == id, true);
        Assert.assertEquals((putRespsonse.jsonPath().getString("name").equals(updatedName)), true);
        Assert.assertEquals(
                !(putRespsonse.jsonPath().getString("name").equals(postRespsonse.jsonPath().getString("name"))), true);

        Assert.assertEquals(putRespsonse.statusCode() == 200, true);

        // getting updated pet data by id
        Response getResponse = Request.getRequestUsingPath("id", id);

        // com.paring PUT response and GET response for the same Id
        Assert.assertEquals(putRespsonse.asString().equals(getResponse.asString()), true);
        Log.scriptInfo("Old name ---> " + name + " and updated name-->: " + putRespsonse.jsonPath().getString("name")
                + " : Pet updated succesfully for the ID : " + id);
    }

    @Test(priority = 2, groups = { "regression" })
    public void testUpdatePetByInvaidId() {
        Log.scriptInfo("TestCase 2 : Test case to validate unsuccesful Pet updation by invalid id");
        Request.setApiURI(base_URI, EndPoints.POST_PET);
        Request.setHeader(Constants.CONTENT_TYPE, Constants.CONTENT_TYPE_JSON);
        JSONObject updatedRequest = Request.readJsonObjectFile(Constants.PET_JSON);
        Response postRespsonse = Request.postRequest(updatedRequest.toString());
        int id = postRespsonse.jsonPath().getInt("id");
        String name = postRespsonse.jsonPath().getString("name");

        // updating id
        JSONObject updatedIdJson = updatedRequest.put("id", id + 1);
        JSONObject updatedNameJson = updatedIdJson.put("name", "cow");
        Response putRespsonse = Request.putRequest(updatedNameJson.toString());

        // PUT call response
        Utility.getInstance().printStatusCodeAndResp(putRespsonse);
        Assert.assertEquals(putRespsonse.statusCode() == 404, true);

        // getting updated pet data by id
        Response getResponse = Request.getRequestUsingPath("id", id);

        // com.paring PUT response and GET response for the same Id
        Assert.assertEquals(postRespsonse.asString().equals(getResponse.asString()), true);

        // ensuring data has not updated for that id
        Log.scriptInfo("Old name ---> " + name + " and updated name-->: " + getResponse.jsonPath().getString("name")
                + " : Pet not updated as invalid ID Passed: ");
    }

}
