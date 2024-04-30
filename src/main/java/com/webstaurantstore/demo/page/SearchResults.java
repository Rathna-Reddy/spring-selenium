package com.webstaurantstore.demo.page;

import com.webstaurantstore.demo.annotations.Page;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

@Page
public class SearchResults extends Base{

    @FindBy(id = "ProductBoxContainer")
    private List<WebElement> searchResultsElements;

    @FindBy(id = "cartItemCountSpan")
    private WebElement cartItem;

    @FindBy(id = "paging")
    private WebElement btn_paging;

    public List<WebElement> getSearchElements(){
        return this.searchResultsElements;
    }

    public void clickCartItem(){
    /*    // Create WebDriverWait instance with a timeout of 10 seconds
        WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(15));

        // Wait until the element with data-role="notification" is visible
        WebElement notificationElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-role='notification']")));
        wait.until(ExpectedConditions.invisibilityOf(notificationElement));

        this.wait.until((d) -> ExpectedConditions.elementToBeClickable(this.cartItem));
        this.cartItem.click(); */

        WebDriverWait waitHandler = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement notificationElement = waitHandler.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-role='notification']")));
        // Switch to the pop-up window
        for (String windowHandle : driver.getWindowHandles()) {
            driver.switchTo().window(windowHandle);
        }
        WebElement viewCartButton = notificationElement.findElement(By.xpath(".//*[contains(text(), 'View Cart')]"));
        viewCartButton.click();
    }

    public WebElement getBtn_paging(){
        return this.btn_paging;
    }

    @Override
    public boolean isAt() {
        return this.wait.until((d) -> this.searchResultsElements.get(0).isDisplayed());
    }
}
