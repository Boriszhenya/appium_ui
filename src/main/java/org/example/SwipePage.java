package org.example;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;
import java.util.List;

public class SwipePage {
    private static final int CAROUSEL_CARDS = 5;
    protected final AppiumDriver driver;

    @AndroidFindBy(xpath = "//android.widget.ScrollView[@content-desc=\"Swipe-screen\"]/android.view.ViewGroup/android.widget.TextView")
    public WebElement foundText;

    @AndroidFindBy(xpath = "(//android.view.ViewGroup[@content-desc=\"slideTextContainer\"]/android.widget.TextView)[2]")
    public WebElement lastCardText;

    public SwipePage(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void open() {
        HomePage homePage = new HomePage(driver);
        homePage.openMenu("Swipe");
    }

    public void performHorizontalSwipe() {
        WebElement sourceElement = driver.findElement(AppiumBy.xpath("(//android.view.ViewGroup[@content-desc=\"card\"])[1]"));
        Point source = sourceElement.getLocation();
        for (int i = 0; i < CAROUSEL_CARDS; i++) {
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence swipe = new Sequence(finger, 1);
            swipe.addAction(
                    finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), source.x, source.y));
            swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            swipe.addAction(
                    finger.createPointerMove(Duration.ofMillis(1000), PointerInput.Origin.viewport(), source.x - (source.x * 5), source.y));
            swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
            swipe.addAction(new Pause(finger, Duration.ofMillis(600)));
            driver.perform(List.of(swipe));
        }
    }
}
