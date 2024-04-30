package com.webstaurantstore.demo;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.webstaurantstore.demo.annotations.LazyAutowired;
import com.webstaurantstore.demo.page.Cart;
import com.webstaurantstore.demo.page.Search;
import com.webstaurantstore.demo.page.SearchResults;
import org.openqa.selenium.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.util.FileCopyUtils;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import org.apache.commons.io.FileUtils;
public class Example5 extends SpringBaseTestNGTest{
    @LazyAutowired
    private Search search;

    @LazyAutowired
    private SearchResults searchResults;

    @LazyAutowired
    private Cart cart;

    @Autowired
    private ApplicationContext context;

//    @Value("${screenshot.path}")
//    private Path path;




//    @LazyAutowired
//    private WebDriver driver;

    private ExtentReports extent;
    private ExtentTest test;

    @BeforeMethod
    public void setUp() {
//        ExtentSparkReporter spark = new ExtentSparkReporter("test-output/extent-report.html");
        ExtentSparkReporter spark = new ExtentSparkReporter(new File("test-output/extent-report.html"));
        spark.config().setDocumentTitle("Webstaurantstore Scenario 1 Test Report");
        spark.config().setReportName("Test Add and Delete item from the cart.");

        extent = new ExtentReports();
        extent.attachReporter(spark);
    }

    @Test
    public void webstaurantstoreScenario1() throws IOException {
        // Create a new test in the report
        test = extent.createTest("Webstaurantstore Scenario 1 Test");

        try {
            this.search.goTo();
            this.search.isAt();
            takeScreenshot("Step 1: Navigated to search page");

            this.search.search("stainless work table");
            takeScreenshot("Step 2: Searched for 'stainless work table'");

            this.searchResults.isAt();
            takeScreenshot("Step 3: Search results page displayed");

            List<WebElement> searchedElements = this.searchResults.getSearchElements();
            Reporter.log(searchedElements.get(0).getText(), true);
            takeScreenshot("Step 4: First search result displayed");

            Assert.assertTrue(searchedElements.stream().allMatch(product -> product.getText().toLowerCase().contains("table")));
            test.log(Status.PASS, "All search results contain 'table'");
            takeScreenshot("Step 5: All search results contain 'table'");

            //get the last item in the page and add it to the cart
            WebElement parentElement = searchedElements.get(59);
            try {
                // to get the add to cart button option
                WebElement childElement = parentElement.findElement(By.name("addToCartButton"));
                this.cart.waitToBeClickableAndClick(childElement);
                test.log(Status.PASS, "Item added to cart successfully");
                takeScreenshot("Step 6: Item added to cart successfully");
            } catch (org.openqa.selenium.NoSuchElementException e) {
                // Handle if element not found and check if the item is out of stock
                WebElement outOfStockElement = parentElement.findElement(By.className("add-to-cart"));
                if(outOfStockElement.getText().contains("Out of Stock")){
                    Reporter.log("This item is out of stock..", true);
                    Assert.fail("This item is out of stock..");
                }else{
                    Assert.fail("Failed with some other technical reason..need to investigate...");
                }
            }

            this.searchResults.clickCartItem();

            this.cart.isAt();
            this.cart.clickEmptyCart();
            this.cart.clickEmptyCartPopup();

            String emptyBinText = this.cart.getEmptyBinText();
            // Assert and log to Extent report
            Assert.assertTrue(emptyBinText.contains("Your cart is empty."));
            test.log(Status.PASS, "Cart is empty: " + emptyBinText);
            takeScreenshot("Step 7: Cart is empty");
        } catch (AssertionError | Exception e) {
            // Log test failure
            test.log(Status.FAIL, "Webstaurantstore Scenario 1 Test failed: " + e.getMessage());

            // Capture screenshot and add to Extent report
            takeScreenshot("Test failed");
            throw e; // Rethrow the exception to mark the test as failed
        }
    }

    @AfterMethod
    public void tearDown() {
        // Flush ExtentReports
        extent.flush();
    }

    private void takeScreenshot(String screenshotName) throws IOException {

      /*  File screenshotFile = this.context.getBean(TakesScreenshot.class).getScreenshotAs(OutputType.FILE);
//        String currentDirectory = System.getProperty("user.dir");

        String destinationPath = "screenshots/" + System.currentTimeMillis() + ".png";
//        FileUtils.copyFile(screenshotFile, new File(destinationPath));
        screenshotFile.renameTo(new File(destinationPath));
        File screenshotPath = new File(destinationPath);
        test.log(Status.INFO, screenshotName);
//        test.addScreenCaptureFromPath(screenshotPath, screenshotName);
        test.log(Status.INFO, "Screenshot captured:", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath.getAbsolutePath()).build()); */

        File sourceFile = this.context.getBean(TakesScreenshot.class).getScreenshotAs(OutputType.FILE);
        File destFile = new File("./Screenshots/"+System.currentTimeMillis() + ".png");
        try {
            FileUtils.copyFile(sourceFile, destFile);
        }catch (IOException e){
            e.printStackTrace();
        }

        String absPath = destFile.getAbsolutePath();
//        test.addScreenCaptureFromPath(absPath, screenshotName);
        test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(absPath, screenshotName).build());


    }

}
