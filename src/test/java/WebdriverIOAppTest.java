import org.example.DragPage;
import org.example.FormPage;
import org.example.SwipePage;
import org.example.WebViewPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WebdriverIOAppTest extends BaseTest {
    private static final String APP_PATH = String.valueOf(
            Path.of(
                    System.getProperty("user.dir"),
                    "/src/main/resources", "webdriverio-app.apk"
            )
    );
    @BeforeEach
    public void setUp() throws URISyntaxException, MalformedURLException {
        super.setUp(APP_PATH);
    }

    @Test
    public void formPageTest() {
        final String inputText = "This is Appium Test";
        final FormPage formPage = new FormPage(driver);
        formPage.fillForm(inputText, 2);
        assertEquals(inputText, formPage.getInputText());
        assertEquals("Click to turn the switch OFF", formPage.getSwitchText());
        assertEquals("Appium is awesome", formPage.getSelectedDropdownValue());
        formPage.submitForm();

        assertEquals("This button is", formPage.getActiveMessageTitle());
        assertEquals("This button is active", formPage.getActiveMessage());
        formPage.closeMessage();

        assertEquals("false", formPage.checkInActiveBtn());
    }

    @Test
    public void testWebView () {
        final WebViewPage webViewPage = new WebViewPage(driver);

        assertEquals (
                "Next-gen browser and mobile automation test framework for Node.js",
                webViewPage.getMainPageText()
        );

        webViewPage.switchToNativeApp();
    }

    @Test
    public void testDragAndDrop() {
        final DragPage dragPage = new DragPage(driver);
        dragPage.dragAndDropPieces();
        assertEquals("Congratulations", dragPage.congratulationsText());
    }

    @Test
    public void testSwipeHorizontal() {
        final SwipePage swipePage = new SwipePage(driver);
        swipePage.open();
        swipePage.performHorizontalSwipe();
        assertTrue(swipePage.lastCardText.isDisplayed());
        assertEquals(
                "WebdriverIO works in combination with most of the TDD and BDD test frameworks in the JavaScript world",
                swipePage.lastCardText.getText()
        );
    }

    @Test
    public void testSwipeVertical() {
        final SwipePage swipePage = new SwipePage(driver);
        swipePage.open();
        //Сделайте swipe вниз
        assertTrue(swipePage.foundText.isDisplayed());
        assertEquals("You found me!!!", swipePage.foundText.getText());
    }
}
