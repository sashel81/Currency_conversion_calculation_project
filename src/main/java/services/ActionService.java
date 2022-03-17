package services;

import base.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ActionService {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Actions actions;

    public ActionService() {
        this.driver = Driver.getInstance().getWebDriver();
        this.wait = new WebDriverWait(driver, 25);
        PageFactory.initElements(driver, this);
        actions = new Actions(driver);
    }

    public void waitVisibilityAndClick(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated((locator))).click();
    }

    public void waitVisibilityOf(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void moveToElement(WebElement element){
        actions.moveToElement(element).build().perform();
    }
}
