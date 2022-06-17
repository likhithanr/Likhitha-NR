package com.n26.report;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * @author Likhitha NR
 */

public class TestListener implements ITestListener {
    public static String suiteName;
    public static String className;

    /**
     * @param iTestContext
     */
    public void onStart(ITestContext iTestContext) {

        suiteName = iTestContext.getCurrentXmlTest().getSuite().getName();
        className = iTestContext.getCurrentXmlTest().getClasses().stream()
                .findFirst().get().getName().substring(iTestContext.getCurrentXmlTest().getClasses().stream()
                        .findFirst().get().getName().lastIndexOf(".") + 1);

    }

    /**
     * @param iTestContext
     */
    public void onFinish(ITestContext iTestContext) {
        ExtentManager.extentReports.flush();
        Log.endTestCase();
    }

    /**
     * @param result
     */
    public void onTestStart(ITestResult result) {
        Log.log("**************************************Test case started**************************");
        startMethod(result);
    }

    /**
     * @param iTestResult
     */
    public void onTestSuccess(ITestResult iTestResult) {

        TestInfo.getTest().log(Status.PASS, MarkupHelper.createLabel("Test PASSED", ExtentColor.GREEN));
        Log.log(iTestResult.getMethod().getMethodName() + " : PASSED");
    }

    /**
     * @param iTestResult
     */
    public void onTestFailure(ITestResult iTestResult) {

        TestInfo.getTest().fail(MarkupHelper.createLabel("Test FAILED", ExtentColor.RED));
        TestInfo.getTest().fail(iTestResult.getThrowable());
        Log.error(iTestResult, iTestResult.getThrowable());
    }

    /**
     * @param iTestResult
     */
    public void onTestSkipped(ITestResult iTestResult) {

        TestInfo.getTest().log(Status.SKIP, MarkupHelper.createLabel("Test SKIPPED", ExtentColor.YELLOW));
        Log.log(iTestResult.getMethod().getMethodName() + " : SKIPPED");
    }

    /**
     * @param result
     */
    public void startMethod(ITestResult result) {
        TestInfo.testInfo(result.getMethod().getMethodName());
    }

}
