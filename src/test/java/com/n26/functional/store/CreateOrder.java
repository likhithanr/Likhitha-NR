package com.n26.functional.store;

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

public class CreateOrder extends BaseTest {

    public String base_URI = "";
    public Map<String, String> testData = new HashMap<String, String>();

    @BeforeClass
    @Override
    public void scriptStart() {
        TestInfo.scriptInfo("POST - Order creation ", "Validating pet store order creation", "Likhitha");
    }

    @BeforeClass
    public void setUp() {

        Log.scriptInfo("Setting up configurations");
        base_URI = ConfigManager.gsEnvironment;
        Request.setApiURI(base_URI, EndPoints.POST_STOREORDER);
        Request.setHeader(Constants.CONTENT_TYPE, Constants.CONTENT_TYPE_JSON);

    }

    @Test(priority = 1,groups = {"Regression"})
    public void testCreatePetOrder() {
        Log.scriptInfo("TestCase 1 : Test case toValidate pet order creation ");
        JSONObject updatedRequest = Request.readJsonObjectFile(Constants.STORE_JSON);
        Response response = Request.postRequest(updatedRequest.toString());
        Assert.assertEquals(response.statusCode() == 200, true);
        Utility.getInstance().printStatusCodeAndResp(response);
        Log.scriptInfo("Order created for the pet id : " + response.jsonPath().getInt("petId") + " with quantity : "
                + response.jsonPath().getInt("quantity"));
    }

    @Test(priority = 2,groups = {"Smoke","Regression"})
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

    @Test(priority = 3,groups = {"Regression"})
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

    @Test(priority = 4,groups = {"Regression"})
    public void testCreateOrderWithInvalidStatus() {
        Log.scriptInfo("TestCase 4 : Test case to validate order creation with invalid status ");
        JSONObject updatedRequest = Request.readJsonObjectFile(Constants.STORE_JSON);
        updatedRequest.put("status", "pending");
        Request.setApiURI(base_URI, EndPoints.POST_STOREORDER);
        Request.setHeader(Constants.CONTENT_TYPE, Constants.CONTENT_TYPE_JSON);
        Response response = Request.postRequest(updatedRequest.toString());

        // expected status code is 405 as invalid status value is passed
        Utility.getInstance().printStatusCodeAndResp(response);
        Assert.assertEquals(response.statusCode() == 400, true);
        Log.scriptInfo("Order can not be created with invalid status");

    }
}
