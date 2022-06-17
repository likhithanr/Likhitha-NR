package com.n26.report;

import java.util.List;
import java.util.Map;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

/**
 * @author Likhitha NR
 */

public class ReportListener implements IReporter {

    /**
     * @param xmlSuites,suites,outputDirectory
     */
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {

        for (ISuite suite : suites) {

            Map<String, ISuiteResult> suiteResult = suite.getResults();

            for (XmlSuite classes : xmlSuites) {
                List<XmlTest> xmlTests = classes.getTests();
                for (XmlTest test : xmlTests) {
                    Log.info("Total Class        : " + test.getClasses().size());
                }
            }
            for (ISuiteResult sr : suiteResult.values()) {
                ITestContext tc = sr.getTestContext();
                double allMethodsCount = tc.getAllTestMethods().length;
                double passCount = tc.getPassedTests().getAllResults().size();
                double failCount = tc.getFailedTests().getAllResults().size();

                // System.out.println(methodsCOunt);
                Log.info("Start Time         : " + tc.getStartDate());
                Log.info("Passed tests count : " + passCount);
                Log.info("Failed tests count : " + failCount);
                Log.info("Skipped tests count: " + tc.getSkippedTests().getAllResults().size());
                Log.info("Pass percentage    : " + ((passCount / allMethodsCount) * 100) + "%");
                Log.info("Fail percentage    : " + ((failCount / allMethodsCount) * 100) + "%");
                Log.info("End Time           : " + tc.getEndDate());

            }
        }

    }

}
