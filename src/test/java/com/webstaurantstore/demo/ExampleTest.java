package com.webstaurantstore.demo;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class ExampleTest extends SpringBaseTestNGTest{

   private ExtentReports extent;
    private ExtentTest test;

    @BeforeSuite
    public void setUp() {
        ExtentSparkReporter spark = new ExtentSparkReporter("test-output/extent-report.html");
        spark.config().setDocumentTitle("Add item to the cart and delete it.");
        spark.config().setReportName("Test Add and Delete item from the cart.");

        extent = new ExtentReports();
        extent.attachReporter(spark);
    }

    @Test
    public void exampleTest() {
        test = extent.createTest("Example Test"); // Creating a test
        test.log(Status.INFO, "This is an example test.");
        // Your test code here
        test.log(Status.PASS, "Test passed.");
    }

    @AfterSuite
    public void tearDown() {
        extent.flush();
    }


}
