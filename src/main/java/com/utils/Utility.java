package com.utils;

import com.report.Log;
import io.restassured.response.Response;

public class Utility {

    private static Utility utility = null;
    private static final String STATUS_CODE = "Status Code";
    private static final String RESPONSE = "Response";
    private static final String COLON = " : ";
    private static final String CODE = "code";
    private static final String MESSAGE = "message";
    private static final String INVALID_VALUE = "Invalid Value";
    private static final String BAD_REQ_CODE = "Bad Request status code";

    private Utility() {

    }

    /**
     * @return Utility
     */
    public static Utility getInstance() {
        if (utility == null)
            utility = new Utility();

        return utility;
    }

    /**
     * @param response
     */
    public void printStatusCodeAndResp(Response response) {
        Log.scriptInfo(RESPONSE + COLON + response.asString());
        Log.scriptInfo(STATUS_CODE + COLON + response.statusCode());
    }

    /**
     * @param response
     */
    public void printInvalidParamStatus(Response response) {
        Log.scriptInfo(response.jsonPath().getString(CODE) + COLON + BAD_REQ_CODE);
        Log.scriptInfo(response.jsonPath().getString(MESSAGE) + COLON + INVALID_VALUE);

    }

}
