package pages;

import base.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Actions actions;

    public BasePage() {
        this.driver = Driver.getInstance().getWebDriver();
        this.wait = new WebDriverWait(driver, 25);
        this.actions = new Actions(driver);
        PageFactory.initElements(driver, this);
    }
}
