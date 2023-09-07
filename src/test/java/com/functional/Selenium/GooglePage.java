package com.functional.Selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import com.core.BaseTest;
import com.report.Log;
import com.report.TestInfo;
import org.testng.annotations.Test;

public class GooglePage extends BaseTest{
     @BeforeClass(alwaysRun = true)
    @Override
    public void scriptStart() {
        TestInfo.scriptInfo("Test google page title ", "Validating page title", "Likhitha");
    }

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        Log.scriptInfo("Setting up configurations");
       
    }

    @Test(priority = 1, groups = { "selenium" })
    public void testPageTitle() {
        Log.scriptInfo("TestCase 1 : Test case toValidate pet order creation ");
       WebDriver driver= new ChromeDriver();
       driver.get("https://www.google.co.in/");
        Assert.assertEquals(driver.getTitle().equals("Google"),true);
              
    }
    
}
