package org.example;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

public class WebViewPage {
    protected final AndroidDriver driver;

    public WebViewPage(AppiumDriver driver) {
        this.driver = (AndroidDriver) driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void switchToWebView() {
        Set<String> contextNames = driver.getContextHandles();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        wait.until(d -> contextNames.size() > 1);
        //driver.context("WEBVIEW");
        driver.context(contextNames.toArray()[1].toString());
    }

    public void switchToNativeApp() {
        driver.context("NATIVE_APP");
    }

    public String getMainPageText() {
        HomePage homePage = new HomePage(driver);
        homePage.openMenu("Webview");
        switchToWebView();
        return driver.findElement(By.cssSelector("header > div > p"))
                .getText();
    }
}
