package com.n26.report;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener  implements ITestListener {
public static String suiteName;
public static String className;


    public void onStart(ITestContext iTestContext) {
    
        suiteName=iTestContext.getCurrentXmlTest().getSuite().getName();
        className=iTestContext.getCurrentXmlTest().getClasses().stream()
               .findFirst().get().getName().substring(iTestContext.getCurrentXmlTest().getClasses().stream()
               .findFirst().get().getName().lastIndexOf(".")+1);

    }
   
    public void onFinish(ITestContext iTestContext) {  
        ExtentManager.extentReports.flush();
        Log.endTestCase();
        
    }
    public void onTestStart(ITestResult result) {
        startMethod( result);
    }
    
    public void onTestSuccess(ITestResult iTestResult) {
        
        TestInfo.getTest().log(Status.PASS,MarkupHelper.createLabel("Test PASSED",ExtentColor.GREEN));
        Log.log(iTestResult.getMethod().getMethodName()+" : PASSED");
    }

    public void onTestFailure(ITestResult iTestResult) {
       
        TestInfo.getTest().fail(MarkupHelper.createLabel("Test FAILED",ExtentColor.RED));
        TestInfo.getTest().fail(iTestResult.getThrowable());
        Log.error(iTestResult, iTestResult.getThrowable());
    }

    public void onTestSkipped(ITestResult iTestResult) {
       
        TestInfo.getTest().log(Status.SKIP,MarkupHelper.createLabel("Test SKIPPED",ExtentColor.YELLOW));
        Log.log(iTestResult.getMethod().getMethodName()+" : SKIPPED");

    }

    public void startMethod(ITestResult result)
	{
		TestInfo.testInfo(result.getMethod().getMethodName());
	}

}
