import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AndroidTest {
    @Test
    public void buttonClick() throws MalformedURLException, URISyntaxException {
        UiAutomator2Options options = new UiAutomator2Options()
                .setUdid("emulator-5554"); //имя девайса из шага 9
        AndroidDriver driver = new AndroidDriver(
                new URI("http://127.0.0.1:4723").toURL(), options
        );
        try {
            WebElement el = driver.findElement(
                    AppiumBy.xpath("//android.widget.TextView[@content-desc='Phone']")
            );
            el.click();
            WebElement textContainer = driver.findElement(
                    AppiumBy.xpath(
                            "//android.widget.TextView[@resource-id=\"com.google.android.dialer:id/empty_content_view_message\"]"
                    )
            );
            assertEquals("Your contacts are just a tap away here", textContainer.getText());
            WebElement textContainer2 = driver.findElement(
                    AppiumBy.xpath(
                            "//android.widget.EditText[@resource-id=\"com.google.android.dialer:id/open_search_bar\"]"
                    )
            );
            assertEquals("Search contacts & places", textContainer2.getText());

        } finally {
            driver.pressKey(new KeyEvent(AndroidKey.HOME));
            driver.quit();
        }
    }
}
