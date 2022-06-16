package com.n26.functional.pets;

import com.n26.common.EndPoints;
import com.n26.common.Request;
import com.n26.core.BaseTest;

import com.n26.report.Log;
import com.n26.report.TestInfo;
import com.n26.utils.ConfigManager;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.response.Response;

public class DeletePetById extends BaseTest{
    
    public String base_URI="";
    public String jsonFile="Pet.json";
    @BeforeClass
    @Override
    public void scriptStart() {
        TestInfo.scriptInfo("DELETE - Deleting pet by Id", "Validating pet deletion by id", "Likhitha");
    }
    
    @BeforeClass
    public void setUp()
    {

        Log.scriptInfo("Setting up configurations"); 
        base_URI=ConfigManager.gsEnvironment;
        Request.setApiURI(base_URI, EndPoints.POST_PET);
        Request.setHeader("Content-Type", "application/json");   
        
    }

    @Test(priority = 1)
    public void testDeletePetByValidId()
    {
        Log.scriptInfo("TestCase 1 : Test case to validate pet deletion by id"); 

        JSONObject updatedRequest = Request.readJsonObjectFile(jsonFile);
        Response respsonse= Request.postRequest(updatedRequest.toString());
        int id=respsonse.jsonPath().getInt("id");

        //deleting the created pet record
        Request.setApiURI(base_URI, EndPoints.DETELE_BYID);
        Response deleteResponse=Request.deleteRequest("id", id);
        Log.scriptInfo("Pet delete response : "+deleteResponse.asString());
        Assert.assertEquals(deleteResponse.statusCode()==200, true);
        Assert.assertEquals(deleteResponse.asString().equals("Pet deleted"), true);

        //getting id afer deletion 
         Request.setApiURI(base_URI, EndPoints.GET_FINDBYID);
        Response getResponse=Request.getRequestUsingPath("id", id);
        Log.scriptInfo("Get response for deleted Pet : "+getResponse.asString());
        Assert.assertEquals(getResponse.statusCode()==404, true);
        Assert.assertEquals(getResponse.asString().equals("Pet not found"), true);



    }
    @Test(priority = 2)
    public void testDeletePetByInvalidId()
    {
        Log.testInfo("TestCase 2 : Test case to delete Pet by invalid id"); 

        //deleting pet by passing invalid pet id
        Request.setApiURI(base_URI, EndPoints.DETELE_BYID);
        Response deleteResponse=Request.deleteRequest("id", -1);
        Log.scriptInfo("Pet delete response : "+deleteResponse.asString());
        Assert.assertEquals(deleteResponse.statusCode()==400, true);
        Assert.assertEquals(deleteResponse.asString().equals("Invalid pet value"), true);

    }


   
}


