package com.webstaurantstore.demo.page;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.annotation.PostConstruct;
public abstract class Base {

    @Autowired
    protected WebDriver driver;

    @Autowired
    protected WebDriverWait wait;

    @PostConstruct
    private void init(){
        PageFactory.initElements(this.driver, this);
    }

    public void waitToBeClickableAndClick(WebElement webElement){
        /*this.wait.until((d) -> ExpectedConditions.elementToBeClickable(webElement));
        Actions actions = new Actions(this.driver);
        actions.moveToElement(webElement).build().perform();
        webElement.click(); */

    /*    this.wait.until((d) -> ExpectedConditions.elementToBeClickable(webElement));
        Actions actions = new Actions(this.driver);


        int offset = this.driver.manage().window().getSize().getHeight()/2;
//        int prevPosY = this.driver.manage().window().getPosition().getY();
        int posxScroll = webElement.getLocation().getX();
        int posyScroll = webElement.getLocation().getY() - offset;
        if (posyScroll < 0){ posyScroll=0; }
        actions.moveToLocation(posxScroll, posyScroll);
        webElement.click();  */

        Actions actions = new Actions(this.driver);
        ((JavascriptExecutor) this.driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", webElement);
        actions.click(webElement).perform();
    }

    public abstract boolean isAt();
}
