package org.example;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class SunflowerPage {
    protected final AppiumDriver driver;
    @AndroidFindBy(xpath = "(//android.widget.TextView)[3]")
    private WebElement firstPageText;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Plant list']")
    private WebElement plantListButton;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='My garden']")
    private WebElement myGardenButton;

    public SunflowerPage(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public String getFirstPageText() {
        return firstPageText.getText();
    }

    public void openPlantListView() {
        plantListButton.click();
    }

    public void openMyGardenView() {
        myGardenButton.click();
    }
}
