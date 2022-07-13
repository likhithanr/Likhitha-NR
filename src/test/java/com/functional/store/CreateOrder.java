package com.functional.store;

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

public class CreateOrder extends BaseTest {
    public String base_URI = "";
    public Map<String, String> testData = new HashMap<String, String>();

    @BeforeClass(alwaysRun = true)
    @Override
    public void scriptStart() {
        TestInfo.scriptInfo("Create new order (POST) ", "Validating pet store order creation", "Likhitha");
    }

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        Log.scriptInfo("Setting up configurations");
        base_URI = ConfigManager.gsEnvironment;
        Request.setApiURI(base_URI, EndPoints.POST_STOREORDER);
        Request.setHeader(Constants.CONTENT_TYPE, Constants.CONTENT_TYPE_JSON);
    }

    @Test(priority = 1, groups = { "regression" })
    public void testCreatePetOrder() {
        Log.scriptInfo("TestCase 1 : Test case toValidate pet order creation ");
        JSONObject updatedRequest = Request.readJsonObjectFile(Constants.STORE_JSON);
        Response response = Request.postRequest(updatedRequest.toString());
        Assert.assertEquals(response.statusCode() == 200, true);
        Utility.getInstance().printStatusCodeAndResp(response);
        Log.scriptInfo("Order created for the pet id : " + response.jsonPath().getInt("petId") + " with quantity : "
                + response.jsonPath().getInt("quantity"));
    }

    @Test(priority = 2, groups = { "smoke", "regression" })
    public void testCreatePlacedPetOrder() {
        Log.scriptInfo("TestCase 2 : Test case to validate order creation with placed status ");
        JSONObject updatedRequest = Request.readJsonObjectFile(Constants.STORE_JSON);
        updatedRequest.put("status", "placed");
        Response response = Request.postRequest(updatedRequest.toString());
        int id = response.jsonPath().getInt("id");
        Assert.assertEquals(response.statusCode() == 200, true);
        Utility.getInstance().printStatusCodeAndResp(response);
        Assert.assertEquals(updatedRequest.getString("status").equals(response.jsonPath().getString("status")), true,
                "Order created with placed status");
        Log.scriptInfo("Order created for the pet id : " + response.jsonPath().getInt("petId") + " with status : "
                + response.jsonPath().getString("status"));

        // retrieving created order detils
        Request.setApiURI(base_URI, EndPoints.GET_ORDER);
        Response getResponse = Request.getRequestUsingPath("id", id);
        Assert.assertEquals(getResponse.jsonPath().getInt("id") == id, true);
        Log.scriptInfo("order succesfully created with id : " + id + " and with status as : "
                + getResponse.jsonPath().getString("status"));

    }

    @Test(priority = 3, groups = { "regression" })
    public void testCreateDeliveredPetOrder() {
        Log.scriptInfo("TestCase 3 : Test case to validate order creation with delivered status ");
        JSONObject updatedRequest = Request.readJsonObjectFile(Constants.STORE_JSON);
        updatedRequest.put("status", "delivered");
        Request.setApiURI(base_URI, EndPoints.POST_STOREORDER);
        Request.setHeader(Constants.CONTENT_TYPE, Constants.CONTENT_TYPE_JSON);
        Response response = Request.postRequest(updatedRequest.toString());
        int id = response.jsonPath().getInt("id");
        Assert.assertEquals(response.statusCode() == 200, true);
        Utility.getInstance().printStatusCodeAndResp(response);
        Assert.assertEquals(updatedRequest.getString("status").equals(response.jsonPath().getString("status")), true,
                "Order created with delivered status");
        Log.scriptInfo("Order created for the pet id : " + response.jsonPath().getInt("petId") + " with status : "
                + response.jsonPath().getString("status"));

        // retrieving created order detils
        Request.setApiURI(base_URI, EndPoints.GET_ORDER);
        Response getResponse = Request.getRequestUsingPath("id", id);
        Assert.assertEquals(getResponse.jsonPath().getInt("id") == id, true);
        Log.scriptInfo("order succesfully created with id : " + id + " and with status as : "
                + getResponse.jsonPath().getString("status"));

    }

    @Test(priority = 4, groups = { "regression" })
    public void testCreateOrderWithInvalidStatus() {
        Log.scriptInfo("TestCase 4 : Test case to validate order creation with invalid status ");
        JSONObject updatedRequest = Request.readJsonObjectFile(Constants.STORE_JSON);
        updatedRequest.put("status", "pending");
        Request.setApiURI(base_URI, EndPoints.POST_STOREORDER);
        Request.setHeader(Constants.CONTENT_TYPE, Constants.CONTENT_TYPE_JSON);
        Response response = Request.postRequest(updatedRequest.toString());

        // expected status code is 405 as invalid status value is passed
        Utility.getInstance().printStatusCodeAndResp(response);
        try {
            Assert.assertEquals(response.statusCode() == 400, true);
            Log.scriptInfo("Order can not be created with invalid status");
        } catch (AssertionError e) {
            Log.error(e);
        }

    }
}
