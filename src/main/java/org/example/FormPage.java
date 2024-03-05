package org.example;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class FormPage {
    protected final AppiumDriver driver;

    @AndroidFindBy(accessibility = "text-input")
    private WebElement inputField;

    @AndroidFindBy(accessibility = "input-text-result")
    private WebElement inputResultField;

    @AndroidFindBy(accessibility = "switch")
    private WebElement switchBtn;

    @AndroidFindBy(accessibility = "Dropdown")
    private WebElement dropdownField;

    @AndroidFindBy(id = "android:id/text1")
    private List<WebElement> dropdownOptions;

    @AndroidFindBy(className = "android.widget.EditText")
    private List<WebElement> selectedDropdown;

    @AndroidFindBy(accessibility = "button-Active")
    private WebElement activeBtn;

    @AndroidFindBy(id = "android:id/button1")
    private WebElement okBtn;

    @AndroidFindBy(accessibility = "button-Inactive")
    private WebElement inActiveBtn;

    @AndroidFindBy(accessibility = "switch-text")
    private WebElement switchText;

    @AndroidFindBy(id = "android:id/message")
    private WebElement message;

    @AndroidFindBy(id = "android:id/alertTitle")
    private WebElement messageTitle;

    public FormPage(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void fillForm(String input, int option) {
        HomePage homePage = new HomePage(driver);
        homePage.openMenu("Forms");
        inputField.sendKeys(input);
        switchBtn.click();
        dropdownField.click();
        selectDropdownValue(option);
    }

    public void submitForm() {
        activeBtn.click();
    }

    public String checkInActiveBtn() {
        return inActiveBtn.getAttribute("long-clickable");
    }

    public void closeMessage() {
        okBtn.click();
    }

    public String getInputText() {
        return inputResultField.getText();
    }

    public String getSwitchText() {
        return switchText.getText();
    }

    private void selectDropdownValue(int option) {
        dropdownOptions.get(option).click();
    }

    public String getSelectedDropdownValue() {
        return selectedDropdown.get(1).getText();
    }


    public String getActiveMessageTitle() {
        return messageTitle.getText();
    }

    public String getActiveMessage() {
        return message.getText();
    }

}
