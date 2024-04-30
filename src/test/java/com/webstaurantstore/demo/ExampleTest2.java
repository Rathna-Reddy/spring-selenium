package com.webstaurantstore.demo;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.annotations.*;

public class ExampleTest2 extends SpringBaseTestNGTest{
    private ExtentReports extent;
    private ExtentTest test;

    @BeforeMethod
    public void setUp() {
        // Initialize ExtentReports
        ExtentSparkReporter spark = new ExtentSparkReporter("test-output/extent-report.html");
        spark.config().setDocumentTitle("Add item to the cart and delete it.");
        spark.config().setReportName("Test Add and Delete item from the cart.");
        extent = new ExtentReports();
        extent.attachReporter(spark);
    }

    @Test
    public void exampleTest() {
        // Create a new test in the report
        test = extent.createTest("Example Test");

        // Log test steps
        test.log(Status.INFO, "This is an example test.");
        // Your test code here

        // Log test status
        test.log(Status.PASS, "Test passed.");
    }

    @AfterMethod
    public void tearDown() {
        // Flush ExtentReports
        extent.flush();
    }

}
