import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.PowerACState;
import io.appium.java_client.android.connection.ConnectionStateBuilder;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.battery.BatteryInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class AndroidTest {
    AndroidDriver driver;
    WebDriverWait wait;

    @BeforeEach
    public void setUp() throws URISyntaxException, MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options()
                .setUdid("emulator-5554"); //имя девайса из шага 9
        driver = new AndroidDriver(
                new URI("http://127.0.0.1:4723").toURL(), options
        );
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @Test
    public void buttonClick() {
        WebElement el = driver.findElement(
                AppiumBy.xpath("//android.widget.TextView[@content-desc='Phone']")
        );
        el.click();
        WebElement textContainer = driver.findElement(
                AppiumBy.xpath(
                        "//android.widget.TextView[@resource-id='com.google.android.dialer:id/empty_content_view_message']"
                )
        );
        assertEquals("Call your favorite contacts with just one tap", textContainer.getText());
    }

    @Test
    public void batteryLevelLow() {
        driver.setPowerCapacity(10);
        //System.out.println("Battery should be at 10%");
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();

        assertTrue(alert.getText().contains("Turn on Extreme Battery Saver?"));
        assertTrue(alert.getText().contains("10% battery left"));
        assertTrue(alert.getText().contains("This will reduce visual effects and background activity, and pause all non-essential apps"));
        //alert.accept();// принять системное сообщение
        //alert.sendKeys("Something");// написать что-то в него
        alert.dismiss();
    }

    @Test
    public void batteryLevelLowPlugIn() throws InterruptedException {
        driver.setPowerCapacity(10);
        //System.out.println("Battery should be at 10%");
        wait.until(ExpectedConditions.alertIsPresent());
        driver.setPowerAC(PowerACState.ON);
        Thread.sleep(1500);
        //wait.until(ExpectedConditions.not(ExpectedConditions.alertIsPresent()));
        Map<String, Object> bt = new HashMap<>();
        bt.put("command", "dumpsys");
        bt.put("args", "battery");
        Object executeScript = driver.executeScript("mobile: shell", bt);
        System.out.println(executeScript.toString());

        BatteryInfo batteryInfo = driver.getBatteryInfo();
        System.out.println(batteryInfo.getLevel());
        System.out.println(batteryInfo.getState().toString());

        assertTrue(executeScript.toString().contains("AC powered: true"));
        //assertEquals(AndroidBatteryInfo.BatteryState.CHARGING.name(), driver.getBatteryInfo().getState().name());
        assertEquals(0.1, driver.getBatteryInfo().getLevel());
    }

    @Test
    public void clipboardTest() {
        String text = "ВАШ ДРУГОЙ ТЕКСТ";
        driver.findElement(AppiumBy.accessibilityId("Messages")).click();
        driver.setClipboardText("что угодно", text);
//        driver.findElement(AppiumBy.xpath("//android.widget.TextView[@resource-id=\"com.google.android.apps.messaging:id/open_search_bar_text_view\"]")).click();
        WebElement textInput = driver.findElement(AppiumBy.id("com.google.android.apps.messaging:id/zero_state_search_box_auto_complete"));
        textInput.click();
        driver.pressKey(new KeyEvent(AndroidKey.PASTE));
        assertEquals(text, textInput.getText());
        textInput.clear();
    }

    @Test
    public void networkLevelTest() throws InterruptedException {
        //driver.toggleAirplaneMode(); //Для старых версий Android, на новых ещё не работает
        driver.setConnection(
                new ConnectionStateBuilder()
                        .withWiFiDisabled()
                        .withDataDisabled()
                        .withAirplaneModeEnabled()
                        .build()
        );
        Thread.sleep(2500);
        assertFalse(driver.getConnection().isWiFiEnabled());
        assertFalse(driver.getConnection().isDataEnabled());
    }

    @Test
    public void darkThemeTest() throws InterruptedException {
        driver.findElement(AppiumBy.accessibilityId("Messages")).click();
        Map<String, Object> bt = new HashMap<>();
        bt.put("command", "cmd uimode night yes");
        driver.executeScript("mobile: shell", bt);
        Thread.sleep(5000);
        bt.clear();
        bt.put("command", "cmd uimode night no");
        driver.executeScript("mobile: shell", bt);
        Thread.sleep(5000);
    }

    @Test
    public void screenOrientationTest() throws InterruptedException {
        driver.findElement(AppiumBy.accessibilityId("Messages")).click();
        driver.rotate(ScreenOrientation.LANDSCAPE);
        Thread.sleep(1500);
        assertEquals(ScreenOrientation.LANDSCAPE, driver.getOrientation());
        driver.rotate(ScreenOrientation.PORTRAIT);
    }

    @Test
    public void sensorsTest() throws InterruptedException {
        // Пример с акселерометром
        int numIterations = 10;
        int multiplier = 5;
        for (int i = 0; i <= numIterations; i++) {
            double val = Math.sin((Math.PI / numIterations) * i) * multiplier;
            String command = "sensor set acceleration " + val + ":" + val + ":" + val;
            driver.executeScript("mobile: execEmuConsoleCommand", ImmutableMap.of("command", command));
            Thread.sleep(1000);
        }
    }

    @AfterEach
    public void tearDown() {
        driver.setPowerCapacity(100);
        driver.setPowerAC(PowerACState.OFF);
        driver.setConnection(
                new ConnectionStateBuilder()
                        .withAirplaneModeDisabled()
                        .withDataEnabled()
                        .withWiFiEnabled()
                        .build()
        );
        driver.pressKey(new KeyEvent(AndroidKey.HOME));
        driver.quit();
    }
}
