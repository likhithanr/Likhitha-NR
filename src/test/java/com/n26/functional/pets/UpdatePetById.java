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
import com.n26.utils.Utility;
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
    public String jsonFile = "Pet.json";
    public Map<String, String> testData = new HashMap<String, String>();
    public Map<String, String> queryParam = new HashMap<String, String>();

    @BeforeClass
    @Override
    public void scriptStart() {
        TestInfo.scriptInfo("POST - Update pet by ID", "Validating pet by id with name and status", "Likhitha");
    }

    @BeforeClass
    public void setUp() {

        Log.scriptInfo("Setting up configurations");
        base_URI = ConfigManager.gsEnvironment;
        Request.setApiURI(base_URI, EndPoints.POST_PET);
        Request.setHeader(Constants.CONTENT_TYPE, Constants.CONTENT_TYPE_JSON);

    }

    @Test(priority = 1,groups = {"Regression"})
    public void testValidPetUpdateWithNameStatus() {
        Log.scriptInfo("TestCase 1 : Test case to validate updating pet with valid name and status");
        // reating pet and getting the id
        JSONObject updatedRequest = Request.readJsonObjectFile(jsonFile);
        Response respsonse = Request.postRequest(updatedRequest.toString());
        int id = respsonse.jsonPath().getInt("id");
        queryParam.put("name", "Retriever");
        queryParam.put("status", "sold");
        Response postRespsonse = Request.postRequest(updatedRequest.toString(), "id", id, queryParam);

        Assert.assertEquals(postRespsonse.statusCode() == 200, true);
        Utility.getInstance().printStatusCodeAndResp(postRespsonse);
        Assert.assertEquals((postRespsonse.jsonPath().getString("name").equals("Retriever")), true);
        Assert.assertEquals((postRespsonse.jsonPath().getString("status").equals("sold")), true);

        Assert.assertEquals(!(respsonse.jsonPath().getString("name").equals("Retriever")), true);
        Log.scriptInfo("old pet name " + respsonse.jsonPath().getString("name")
                + " is successfully updated with new name as : " + postRespsonse.jsonPath().getString("name"));
        Log.scriptInfo("old pet status " + respsonse.jsonPath().getString("status")
                + " is successfully updated with new status as : " + postRespsonse.jsonPath().getString("status"));

        Log.scriptInfo(respsonse.jsonPath().getString("name") + " : Pet upated with new name");
        Log.scriptInfo(respsonse.jsonPath().getString("status") + " : Pet upated with new status");

        // getting updated pet details by id
        Response getResponse = Request.getRequestUsingPath("id", id);
        Assert.assertEquals(getResponse.jsonPath().getString("name").equals(getResponse.jsonPath().getString("name")),
                true);
        Assert.assertEquals(
                getResponse.jsonPath().getString("status").equals(getResponse.jsonPath().getString("status")), true);

    }

    @Test(priority = 2,groups = {"Regression"})
    public void testInavlidPetUpdate() {
        Log.scriptInfo("TestCase 2 : Test case to udpate invalid pet with name and status ");
        // reating pet and getting the id
        JSONObject updatedRequest = Request.readJsonObjectFile(jsonFile);
        queryParam.put("name", "Retriever");
        queryParam.put("status", "sold");
        Response postRespsonse = Request.postRequest(updatedRequest.toString(), "id", 999, queryParam);
        Assert.assertEquals(postRespsonse.statusCode() == 404, true);
        Utility.getInstance().printStatusCodeAndResp(postRespsonse);
        Assert.assertEquals((postRespsonse.asString().equals("Pet not found")), true);

    }
}
