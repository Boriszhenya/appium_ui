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
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SunflowerAppTest extends BaseTest {
    private static final String APP_PATH = String.valueOf (
            Path.of (System.getProperty ("user.dir"), "/src/main/resources", "test.apk"));
    @BeforeEach
    public void setUp() throws URISyntaxException, MalformedURLException {
        super.setUp(APP_PATH);
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

    private void printElementInfo(WebElement webElement) {
        System.out.println("Tag name: " + webElement.getTagName());
        System.out.println("Text: " + webElement.getText());
        System.out.println("Location: " + webElement.getLocation());
        System.out.println("Displayed: " + webElement.isDisplayed());
        System.out.println("Size: " + webElement.getSize() + "\r\n\n");
    }
}
