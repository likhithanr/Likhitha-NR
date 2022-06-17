package com.n26.report;

import java.util.HashMap;
import java.util.Map;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

/**
 * @author Likhitha NR
 */

public class TestInfo {

    static Map<Integer, ExtentTest> extentTestMap = new HashMap<Integer, ExtentTest>();
    static ExtentReports extent = ExtentManager.createExtentReports();
    private static ThreadLocal<ExtentTest> threadtest = new ThreadLocal<ExtentTest>();

    /**
     * @return ExtentTest
     */
    public static synchronized ExtentTest getTest() {
        return extentTestMap.get((int) Thread.currentThread().getId());
    }

    /**
     * @param testName,desc,author
     * @return ExtentTest
     */
    public static synchronized ExtentTest scriptInfo(String testName, String desc, String author) {
        Log.log("******************************Execution of " + testName + " started*******************************");
        ExtentTest test = extent.createTest(testName, desc).assignAuthor(author);
        threadtest.set(test);
        extentTestMap.put((int) Thread.currentThread().getId(), test);
        return test;
    }

    /**
     * @param testName
     * @return ExtentTest
     */
    public static synchronized ExtentTest testInfo(String testName) {
        ExtentTest node = threadtest.get().createNode(testName);
        extentTestMap.put((int) Thread.currentThread().getId(), node);
        return node;
    }

    /**
     * @param testDesc
     */
    public static void info(String testDesc) {

        TestInfo.getTest().info(MarkupHelper.createLabel(testDesc, ExtentColor.BLUE));
    }
}
