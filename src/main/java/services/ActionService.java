package services;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.BasePage;
import pages.MainPage;

public class ActionService extends BasePage {

    @FindBy(css = ".rate-table-filter")
    private WebElement form;

    @FindBy(css = ".js-localization-popover")
    private WebElement changeCountry;

    private By currentCurrency = By.cssSelector(".ui-select-toggle");

    private By tableString = By.cssSelector("tbody tr");

    public ActionService() {super();}

    @Step("Wait till default currency present")
    public MainPage waitForDefaultCurrency() {
        wait.until((ExpectedCondition<Boolean>) d -> d.findElements(currentCurrency).get(0).getText().length() != 0);
        return new MainPage();
    }

    @Step("Wait till drop down menu with countries present")
    public MainPage waitForSelectCurrencyDropDown() {
        wait.until((ExpectedCondition<Boolean>) d -> d.findElements(currentCurrency).size() > 1);
        return new MainPage();
    }

    @Step("Wait for table with filter results")
    public MainPage waitForTableWithFilteredResults() {
        wait.until((ExpectedCondition<Boolean>) d -> d.findElements(tableString).size() == 1);
        return new MainPage();
    }

    @Step("Move to the footer of page")
    public MainPage moveToTheFooter() {
        actions.moveToElement(changeCountry).build().perform();
        return new MainPage();
    }

    @Step("Move to the form of calculator")
    public ActionService moveToTheForm() {
        wait.until(ExpectedConditions.visibilityOf(form));
        actions.moveToElement(form).build().perform();
        return this;
    }
}
