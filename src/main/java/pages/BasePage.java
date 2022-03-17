package pages;

import base.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import services.ActionService;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected ActionService actionService;

    public BasePage() {
        this.driver = Driver.getInstance().getWebDriver();
        this.wait = new WebDriverWait(driver, 25);
        PageFactory.initElements(driver, this);
        actionService = new ActionService();
    }
}
