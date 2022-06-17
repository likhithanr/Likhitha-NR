package com.n26.report;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.n26.utils.*;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.ViewName;

/**
 * @author Likhitha NR
 */

public class ExtentManager extends TestListener {

    public static final ExtentReports extentReports = new ExtentReports();

    /**
     * @return ExtentReports
     */
    // Description: method to intialize and create extent report
    public synchronized static ExtentReports createExtentReports() {

        String reportFolderPath = createExtentReportFileName();
        String htmlReportPath = reportFolderPath + "/" + getSuiteName() + "_" + ConfigManager.getDate();

        ExtentSparkReporter reporter = new ExtentSparkReporter(htmlReportPath + ".html").viewConfigurer().viewOrder()
                .as(new ViewName[] { ViewName.DASHBOARD, ViewName.TEST, ViewName.CATEGORY, ViewName.AUTHOR,
                        ViewName.EXCEPTION })
                .apply();

        reporter.config().setDocumentTitle(ConfigManager.gsDocumentTitle);
        reporter.config().setReportName(ConfigManager.gsReportName);
        Log.info("Project Name        : " + ConfigManager.gsProjectName);

        extentReports.attachReporter(reporter);

        try {
            extentReports.setSystemInfo("Environment", ConfigManager.gsEnvironment);
            Log.info("Test Environment    : " + ConfigManager.gsEnvironment);
        } catch (Exception e) {
            e.printStackTrace();
        }
        extentReports.setSystemInfo("Executed on OS", System.getProperty("os.name"));
        Log.info("Executed on OS      : " + System.getProperty("os.name"));

        extentReports.setSystemInfo("Executed By", System.getProperty("user.name"));
        Log.info("Executed By         : " + System.getProperty("user.name"));

        return extentReports;
    }

    /**
     * @return String
     */
    // description: method to create file name for extent report
    public static String createExtentReportFileName() {
        SimpleDateFormat dateFromat = new SimpleDateFormat("dd_MM_YYYY");
        Date date = new Date();
        String dateString = dateFromat.format(date);
        File dir = new File(System.getProperty("user.dir") + ConfigManager.gsAutomationAutoResultPath + dateString);
        if (!dir.exists())
            dir.mkdir();
        String path = dir.getAbsolutePath();
        return path;
    }

    /**
     * @return String
     */
    // description: method to create file name for extent report
    public static String getSuiteName() {
        if (TestListener.suiteName.contains("Default")) {
            return TestListener.className;
        } else
            return TestListener.suiteName;
    }
}
