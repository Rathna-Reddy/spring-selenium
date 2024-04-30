package com.webstaurantstore.demo.config;


import com.webstaurantstore.demo.annotations.LazyConfiguration;
import com.webstaurantstore.demo.annotations.ThreadScopeBean;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
@LazyConfiguration
public class WebDriverConfig {

    @ThreadScopeBean
    @ConditionalOnProperty(name = "browser", havingValue = "chrome")
    public WebDriver chromeDriver(){
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver();
    }
}
