package com.webstaurantstore.demo.page;


import com.webstaurantstore.demo.annotations.Page;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Value;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

@Page
public class Search extends Base{

    @Value("${url}")
    private String url;

    @FindBy(xpath = "(//*[@id=\"searchval\"])[1]")
    private WebElement searchBox;

    public void search(String keyword){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement searchInput = wait.until(ExpectedConditions.elementToBeClickable(this.searchBox));
        searchInput.sendKeys(keyword);
        searchInput.submit();
    }

    public void goTo(){
        this.driver.get(url);
        this.driver.manage().window().maximize();
    }

    @Override
    public boolean isAt() {
        return this.wait.until((d) -> this.searchBox.isDisplayed());
    }
}
