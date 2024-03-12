package org.example;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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


    public String getText() {
        HomePage homePage = new HomePage(driver);
        homePage.openMenu("Webview");
        switchToWebView();


        //Внутри страницы открыть меню
        driver.findElement(By.xpath("//nav/div[1]/div[1]/button")).click();
        //Выбрать пункт “Community”
        driver.findElement(By.xpath("//nav/div[3]/div[2]/div[1]/ul/li[5]/a")).click();
        //Прокрутить вниз страницы
        //driver.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//article/div[2]/ul[1]/li[1]/a")));

        //Открыть ссылку “Office hours”
        driver.findElement(By.xpath("//main/div/div/div/div/nav/a/div[2]")).click();

        System.out.println(driver.findElement(By.xpath("//article/div/p[1]")).getText());
        return driver.findElement(By.xpath("//article/div/p[1]")).getText();
    }
}
