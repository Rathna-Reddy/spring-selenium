package com.webstaurantstore.demo;

import com.webstaurantstore.demo.annotations.LazyAutowired;
import com.webstaurantstore.demo.page.Cart;
import com.webstaurantstore.demo.page.Search;
import com.webstaurantstore.demo.page.SearchResults;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.util.List;

public class WebstaurantstoreScenario3Test extends SpringBaseTestNGTest{

    @LazyAutowired
    private Search search;
    @LazyAutowired
    private SearchResults searchResults;
    @LazyAutowired
    private Cart cart;

    @Test
    public void webstaurantstoreScenario1(){
        this.search.goTo();
        this.search.isAt();
        this.search.search("stainless work table");

        ////////////////////////
        //*[@id="paging"]//li[a[contains(@aria-label, 'page 5')]]
        WebElement pagingElement = this.searchResults.getBtn_paging();
        this.searchResults.isAt();
        for (int i = 1; i <= 9; i++) {
            String pageNumberXPath = String.format(".//li[a[contains(@aria-label, 'page %d')]]", i);
            WebElement pageElement = pagingElement.findElement(By.xpath(pageNumberXPath));
//            pageElement.click();
            this.searchResults.waitToBeClickableAndClick(pageElement);
            this.searchResults.isAt();
            List<WebElement> searchedElements = this.searchResults.getSearchElements();
            Assert.assertTrue(searchedElements.stream().allMatch(product -> product.getText().toLowerCase().contains("table")));
        }
        ////////////////////////

        this.searchResults.isAt();
        List<WebElement> searchedElements = this.searchResults.getSearchElements();
        Reporter.log(searchedElements.get(0).getText(), true);

        Assert.assertTrue(searchedElements.stream().allMatch(product -> product.getText().toLowerCase().contains("table")));

        //get the last item in the page and add it to the cart
        WebElement parentElement = searchedElements.get(59);
        try {
            // to get the add to cart button option
            WebElement childElement = parentElement.findElement(By.name("addToCartButton"));
            this.cart.waitToBeClickableAndClick(childElement);
        } catch (org.openqa.selenium.NoSuchElementException e) {
            // Handle if element not found and check if the item is out of stock
            WebElement outOfStockElement = parentElement.findElement(By.className("add-to-cart"));
            if(outOfStockElement.getText().contains("Out of Stock")){
                Reporter.log("This item is out of stock..", true);
                Assert.fail("This item is out of stock..");
            }else{
                Assert.fail("Failed with some other technical reason..nee to investigate...");
            }

        }

        this.searchResults.clickCartItem();

        this.cart.isAt();
        this.cart.clickEmptyCart();
        this.cart.clickEmptyCartPopup();

        String emptyBinText = this.cart.getEmptyBinText();
        Assert.assertTrue(emptyBinText.contains("Your cart is empty."));

        // Close the browser
//        driver.quit();


    }
}
