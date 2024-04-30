package com.webstaurantstore.demo;


import com.webstaurantstore.demo.annotations.LazyAutowired;
import com.webstaurantstore.demo.page.Search;
import com.webstaurantstore.demo.page.SearchResults;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import java.util.List;
public class WebstaurantstoreScenario2Test extends SpringBaseTestNGTest{

    @LazyAutowired
    private Search search;

    @LazyAutowired
    private SearchResults searchResults;

    private ExtentReports extent;
    private ExtentTest test;

    @BeforeSuite
    public void setUp() {
        ExtentSparkReporter spark = new ExtentSparkReporter("test-output/extent-report3.html");
        spark.config().setDocumentTitle("Your Document Titlee");
        spark.config().setReportName("Your Report Name");

        extent = new ExtentReports();
        extent.attachReporter(spark);
    }

    @Test
    public void webstaurantstoreScenario1(){
        // Create a new test in the report
        test = extent.createTest("TestNGExtentReportExample");

        // Log TestNG log messages as ExtentReports logs
        test.log(Status.INFO, "Test started...");
        test.log(Status.WARNING, "This is a warning message.");
        test.log(Status.FAIL, "This is a failed message.");
        test.log(Status.PASS, "Test passed!");


        this.search.goTo();
//        this.search.isAt();
        this.search.search("stainless work table");

        this.searchResults.isAt();
        List<WebElement> searchedElements = this.searchResults.getSearchElements();
//        System.out.println(searchedElements.get(0).getText());
        Reporter.log(searchedElements.get(0).getText(), true);

        Assert.assertTrue(searchedElements.stream().allMatch(product -> product.getText().toLowerCase().contains("table")));
//        Assert.assertFalse(searchedElements.stream().allMatch(product -> product.getText().toLowerCase().contains("table")));

//        webElements.get(59).findElement(By.xpath("//*[@data-testid=\"itemAddCart\"]")).click();
    }

    @AfterSuite
    public void tearDown() {
        extent.flush();
    }


}
