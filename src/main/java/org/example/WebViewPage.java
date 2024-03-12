package org.example;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
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
       //driver.findElement(AppiumBy.xpath("//android.widget.Button[@text='Toggle navigation bar']")).click();
        switchToWebView();
           //Внутри страницы открыть меню
        //driver.findElement(By.xpath("//nav/div[1]/div[1]/button/svg/path")).click();
           //Выбрать пункт “Community”
       // driver.findElement(By.xpath("//android.view.View[@content-desc='\"'Community']")).click();
           //Прокрутить вниз страницы

           //Открыть ссылку “Office hours”
      //  driver.findElement(By.xpath("//android.view.View[@resource-id=\"__docusaurus_skipToContent_fallback\"]/android.view.View/android.view.View/android.view.View[3]/android.view.View/android.widget.TextView")).click();

        return driver.findElement(By.cssSelector("header > div > p"))
                .getText();
    }
//    public void openMenu1() {
//        By webButtonLocator = By.xpath("//android.widget.Button[@text=\"Toggle navigation bar\"]");
//        WebElement menuButton = driver.findElement(webButtonLocator);
//        menuButton.click();
//    }

}
