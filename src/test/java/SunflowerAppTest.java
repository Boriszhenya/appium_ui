import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumBy.ByAccessibilityId;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.example.SunflowerPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SunflowerAppTest {
    AndroidDriver driver;

    @BeforeEach
    public void setUp() throws URISyntaxException, MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options()
                .setApp("C:\\Users\\guest_h0xcuas\\Documents\\appium_example\\src\\main\\resources\\test.apk") // Путь к приложению
                .setUdid("emulator-5554");
        driver = new AndroidDriver(
                new URI("http://127.0.0.1:4723").toURL(), options
        );
    }

    @Test
    public void customAppTest() {
        SunflowerPage sp = new SunflowerPage(driver);
        assertEquals("Your garden is empty", sp.getFirstPageText());
        sp.openPlantListView();
        assertEquals("Apple", sp.getFirstPageText());
    }

    @Test
    public void locatorsTest() {
        List<WebElement> locatedList = new ArrayList<>();
        locatedList.add(driver.findElement(By.id("android:id/content"))); // свойство resource-id
        locatedList.add(driver.findElement(By.className("android.widget.TextView"))); // фактически имя тэга в структуре страницы приложения
        locatedList.add(driver.findElement(AppiumBy.accessibilityId("My garden"))); // свойство content-desc
        locatedList.add(driver.findElement(By.xpath("//android.view.View[@content-desc='Plant list']"))); // (//android.view.View)[3]
        String selector = "new UiSelector().text(\"Add plant\").className(\"android.widget.TextView\")";
        locatedList.add(driver.findElement(AppiumBy.androidUIAutomator(selector)));
        locatedList.forEach(this::printElementInfo);
    }

    @Test
    public void locatorsListTest() {
        List<WebElement> locatedList = driver.findElements(By.className("android.widget.TextView"));
        locatedList.forEach(this::printElementInfo);
    }

    @AfterEach
    public void tearDown() {
        driver.pressKey(new KeyEvent(AndroidKey.HOME));
        driver.quit();
    }

    private void printElementInfo(WebElement webElement) {
        System.out.println("Tag name: " + webElement.getTagName());
        System.out.println("Text: " + webElement.getText());
        System.out.println("Location: " + webElement.getLocation());
        System.out.println("Displayed: " + webElement.isDisplayed());
        System.out.println("Size: " + webElement.getSize() + "\r\n\n");
    }
}
