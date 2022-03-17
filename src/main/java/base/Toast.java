package base;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.BasePage;

public class Toast extends BasePage {
    @FindBy(css = "div[class = 'ng-toast__message ']")
    protected WebElement toast;

    public Toast() {
        super();
    }

    @Step("Get text from toast message")
    public String getToastText() {
        return toast.getText();
    }
    public Boolean isDisplayed (){
        return wait.until(ExpectedConditions.visibilityOf(toast)).isDisplayed();
    }
}
