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

public class GetOrder extends BaseTest {
    public String base_URI = "";
    public Map<String, String> testData = new HashMap<String, String>();

    @BeforeClass(alwaysRun = true)
    @Override
    public void scriptStart() {
        TestInfo.scriptInfo("Get existing orders (GET)", "Validating get order details", "Likhitha");
    }

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        Log.scriptInfo("Setting up configurations");
        base_URI = ConfigManager.gsEnvironment;
        Request.setApiURI(base_URI, EndPoints.POST_STOREORDER);
        Request.setHeader(Constants.CONTENT_TYPE, Constants.CONTENT_TYPE_JSON);

    }

    @Test(priority = 1, groups = { "regression" })
    public void testGetOrderByInvalidId() {
        Log.scriptInfo("TestCase 1 : Test case to validate get order by invalid id ");
        JSONObject updatedRequest = Request.readJsonObjectFile(Constants.STORE_JSON);
        Response respsonse = Request.postRequest(updatedRequest.toString());
        int id = respsonse.jsonPath().getInt("id");
        Assert.assertEquals(respsonse.statusCode() == 200, true);
        Log.scriptInfo("Pet created sucesfully with id : " + id);
        Response getResponse = Request.getRequestUsingPath("id", id + 111);
        Assert.assertEquals(getResponse.statusCode() == 404, true);
        Utility.getInstance().printStatusCodeAndResp(getResponse);
        Assert.assertEquals(getResponse.asString().equals("Order not found"), true);
        Log.scriptInfo("Order does not exist for the id :  " + (id + 111) + " says " + getResponse.asString());
    }

}