package com.webstaurantstore.demo.page;

import com.webstaurantstore.demo.annotations.Page;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Page
public class Cart extends Base{

    @FindBy(xpath = "//*[contains(@class, 'emptyCartButton')]")
    private WebElement btn_emptyCart;

    @FindBy(xpath = "//*[@data-testid=\"modal-footer\"]/button[1]")
    private WebElement btn_emptyCartPopup;
    @FindBy(xpath = "//*[@class=\"empty-cart__text\"]")
    private WebElement emptyCart;

    public void clickEmptyCart(){
        this.btn_emptyCart.click();
    }

    public void clickEmptyCartPopup(){
        this.btn_emptyCartPopup.click();
    }

    public String getEmptyBinText(){
        this.wait.until((d) -> this.emptyCart.isDisplayed());
        return this.emptyCart.getText();
    }

    @Override
    public boolean isAt() {
        return this.wait.until((d) -> this.btn_emptyCart.isDisplayed());
    }
}
