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

import java.util.List;

import static java.time.Duration.ofMillis;

public class DragPage {
    protected final AppiumDriver driver;

    @AndroidFindBy(accessibility = "drag-l1")
    private WebElement dragLeftFirst;
    @AndroidFindBy(accessibility = "drop-l1")
    private WebElement dropLeftFirst;
    @AndroidFindBy(accessibility = "drag-l2")
    private WebElement dragLeftSecond;
    @AndroidFindBy(accessibility = "drop-l2")
    private WebElement dropLeftSecond;

    public DragPage(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    private Point getElementCenter(WebElement element) {
        var location = element.getLocation();
        var size = element.getSize();

        return new Point(
                location.getX() + (size.getWidth() / 2),
                location.getY() + (size.getHeight() / 2)
        );
    }

    public void dragAndDropPieces() {
        HomePage homePage = new HomePage(driver);
        homePage.openMenu("Drag");
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence dragAndDrop = new Sequence(finger, 1);
        dragAndDrop.addAction(
                finger.createPointerMove(
                        ofMillis(100),
                        PointerInput.Origin.viewport(),
                        getElementCenter(dragLeftFirst).x,
                        getElementCenter(dragLeftFirst).y
                )
        );
        dragAndDrop.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        dragAndDrop.addAction(new Pause(finger, ofMillis(600)));
        dragAndDrop.addAction(
                finger.createPointerMove(
                        ofMillis(600),
                        PointerInput.Origin.viewport(),
                        getElementCenter(dropLeftFirst).x,
                        getElementCenter(dropLeftFirst).y
                )
        );
        dragAndDrop.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(List.of(dragAndDrop));
        dragAndDrop = new Sequence(finger, 1);
        dragAndDrop.addAction(
                finger.createPointerMove(
                        ofMillis(100),
                        PointerInput.Origin.viewport(),
                        getElementCenter(dragLeftSecond).x,
                        getElementCenter(dragLeftSecond).y
                )
        );
        dragAndDrop.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        dragAndDrop.addAction(new Pause(finger, ofMillis(600)));
        dragAndDrop.addAction(
                finger.createPointerMove(
                        ofMillis(600),
                        PointerInput.Origin.viewport(),
                        getElementCenter(dropLeftSecond).x,
                        getElementCenter(dropLeftSecond).y
                )
        );
        dragAndDrop.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(List.of(dragAndDrop));
        System.out.println("Первая часть домашнего задания =)");
    }

    public String congratulationsText() {
        return driver.findElement(AppiumBy.xpath("//android.widget.TextView")).getText();
    }
}
