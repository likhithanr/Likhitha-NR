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

public class UpdatePet extends BaseTest {
    public String base_URI = "";
    // public String jsonFile = "Pet.json";
    public Map<String, String> testData = new HashMap<String, String>();
    public Map<String, String> queryParam = new HashMap<String, String>();

    @BeforeClass(alwaysRun = true)
    @Override
    public void scriptStart() {
        TestInfo.scriptInfo("Update existing pet (POST)", "Validating pet updation with name and status", "Likhitha");
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
        Log.scriptInfo("TestCase 1 : Test case to validate updating pet by id with valid name and status");

        // creating new pet
        JSONObject updatedRequest = Request.readJsonObjectFile(Constants.PET_JSON);
        Response respsonse = Request.postRequest(updatedRequest.toString());

        // retrieving pet id from the previous response and updating pet using that id
        int id = respsonse.jsonPath().getInt("id");
        queryParam.put("name", "Retriever");
        queryParam.put("status", "sold");
        Response postRespsonse = Request.postRequest(updatedRequest.toString(), "id", id, queryParam);
        Assert.assertEquals(postRespsonse.statusCode() == 200, true);
        Utility.getInstance().printStatusCodeAndResp(postRespsonse);
        // verifying updated values
        Assert.assertEquals((postRespsonse.jsonPath().getString("name").equals("Retriever")), true);
        Assert.assertEquals((postRespsonse.jsonPath().getString("status").equals("sold")), true);
        Assert.assertEquals(!(respsonse.jsonPath().getString("name").equals("Retriever")), true);
        Log.scriptInfo("old pet name " + respsonse.jsonPath().getString("name")
                + " is successfully updated with new name as : " + postRespsonse.jsonPath().getString("name"));
        Log.scriptInfo("old pet status " + respsonse.jsonPath().getString("status")
                + " is successfully updated with new status as : " + postRespsonse.jsonPath().getString("status"));
        // getting updated pet details by id
        Response getResponse = Request.getRequestUsingPath("id", id);
        Assert.assertEquals(getResponse.jsonPath().getString("name").equals(getResponse.jsonPath().getString("name")),
                true);
        Assert.assertEquals(
                getResponse.jsonPath().getString("status").equals(getResponse.jsonPath().getString("status")), true);

    }

    @Test(priority = 2, groups = { "regression" })
    public void testUpdatePetByInvalidId() {
        Log.scriptInfo("TestCase 2 : Test case to udpate invalid pet with name and status ");

        // reating pet and getting the id
        JSONObject updatedRequest = Request.readJsonObjectFile(Constants.PET_JSON);
        queryParam.put("name", "Retriever");
        queryParam.put("status", "sold");
        Response postRespsonse = Request.postRequest(updatedRequest.toString(), "id", 999, queryParam);
        Assert.assertEquals(postRespsonse.statusCode() == 404, true);
        Utility.getInstance().printStatusCodeAndResp(postRespsonse);
        Assert.assertEquals((postRespsonse.asString().equals("Pet not found")), true);

    }
}
