package org.example;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    protected final AppiumDriver driver;
    private final WebDriverWait wait;

    public HomePage(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        wait = new WebDriverWait(this.driver, Duration.ofSeconds(10));
    }

    public String getTitle() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated
                (AppiumBy.androidUIAutomator("new UiSelector().text(\"WEBDRIVER\")"))).getText();
    }

    void openMenu(String menuName) {
        driver.findElement(AppiumBy.accessibilityId(menuName)).click();
    }

    public String tagLine() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.androidUIAutomator("new UiSelector().text(\"Demo app for the appium-boilerplate\")"))
        ).getText();
    }

}
