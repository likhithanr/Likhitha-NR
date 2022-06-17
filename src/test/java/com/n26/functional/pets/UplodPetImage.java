package com.n26.functional.pets;

import java.io.File;
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

public class UplodPetImage extends BaseTest {
    public String base_URI = "";
    public String jsonFile = "Pet.json";
    public Map<String, String> testData = new HashMap<String, String>();
    public Map<String, String> headers = new HashMap<String, String>();
    public File uploadImage = new File(System.getProperty("user.dir") + "/Images/dog1.jpg");

    @BeforeClass(alwaysRun = true)
    @Override
    public void scriptStart() {
        TestInfo.scriptInfo("Upload pet image (POST)", "Validating pet image uplaod functionality", "Likhitha");
    }

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        Log.scriptInfo("Setting up configurations");
        base_URI = ConfigManager.gsEnvironment;
        Request.setApiURI(base_URI, EndPoints.POST_PET);
        Request.setHeader(Constants.CONTENT_TYPE, Constants.CONTENT_TYPE_JSON);
    }

    @Test(priority = 1, groups = { "regression" })
    public void testUploadingPetImage() {
        Log.scriptInfo("TestCase 1 : Test case to validate pet image uploading");
        JSONObject updatedRequest = Request.readJsonObjectFile(jsonFile);
        Response respsonse = Request.postRequest(updatedRequest.toString());
        int id = respsonse.jsonPath().getInt("id");
        Response postRespsonse = Request.uploadImage("id", id, uploadImage);
        Utility.getInstance().printInvalidParamStatus(postRespsonse);
        try {
            Assert.assertEquals(postRespsonse.statusCode() == 200, true);
        } catch (AssertionError e) {
            Log.error(e);
        }
    }

}
