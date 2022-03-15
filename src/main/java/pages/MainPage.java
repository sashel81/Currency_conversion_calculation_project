package pages;

import base.Driver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Actions actions;

    @FindBy(css = ".js-localization-popover")
    private WebElement changeCountry;

    @FindBy(css = "button[id = 'countries-dropdown']")
    private WebElement selectCountryButton;

    @FindBy(css = ".rate-table-filter")
    private WebElement form;

    @FindBy(css = ".transformable-table tbody tr")
    private WebElement table;

    @FindBy(css = ".popover-content")
    private WebElement country;

    @FindBy(xpath = "//button[contains(text(),'Filter')]")
    private WebElement filterButton;

    private By dropDownCountry(String country) {
        return By.xpath("//*[@aria-labelledby='countries-dropdown']/li[contains(., '" + country + "')]");
    }

    private By selectCurrency(String currency) {
        return By.xpath("//span[@class='ng-binding ng-scope'][contains(text(), '" + currency + "')]");
    }

    private By currentCurrency = By.cssSelector(".ui-select-toggle");

    private By sellAndBuyTextInput = By.cssSelector("input[class*=form-control][type=text]");

    private By tableString = By.cssSelector("tbody tr");

    private By payseraAmount = By.cssSelector("[data-title='Paysera rate']");

    private By swedbankAmount = By.cssSelector("[data-title='Swedbank amount']");

    public MainPage() {
        this.driver = Driver.getInstance().getWebDriver();
        this.wait = new WebDriverWait(driver, 25);
        this.actions = new Actions(driver);
        PageFactory.initElements(driver, this);
    }

    @Step("Load page of Paysera's currency conversion calculator")
    public MainPage loadPage() {
        driver.manage().window().maximize();
        driver.get("https://www.paysera.lt/v2/en-LT/fees/currency-conversion-calculator#/");
        return this;
    }

    @Step("Delete cookies")
    public void clearStorage() {
        driver.manage().deleteAllCookies();
    }

    @Step("Move to the footer of page")
    public MainPage moveToTheFooter() {
        actions.moveToElement(changeCountry).build().perform();
        return this;
    }
    @Step("Move to the form of calculator")
    public MainPage moveToTheForm() {
        wait.until(ExpectedConditions.visibilityOf(form));
        actions.moveToElement(form).build().perform();
        return this;
    }

    @Step("Start change country process")
    public MainPage changeCountry() {
        changeCountry.click();
        return this;
    }

    @Step("Select country from dropdown menu")
    public MainPage selectCountry(String country) {
        selectCountryButton.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated((dropDownCountry(country)))).click();
        wait.until(ExpectedConditions.visibilityOf(changeCountry));
        return this;
    }
    @Step("Wait till default currency present")
    public MainPage waitForDefaultCurrency() {
        wait.until((ExpectedCondition<Boolean>) d -> d.findElements(currentCurrency).get(0).getText().length() != 0);
        return this;
    }

    @Step("Wait till drop down menu with countries present")
    public MainPage waitForSelectCurrencyDropDown() {
        wait.until((ExpectedCondition<Boolean>) d -> d.findElements(currentCurrency).size() > 1);
        return this;
    }
    @Step("Wait for table with filter results")
    public MainPage waitForTableWithFilteredResults() {
        wait.until((ExpectedCondition<Boolean>) d -> d.findElements(tableString).size() == 1);
        return this;
    }
    @Step("Get default currency")
    public String defaultCurrencyIs() {
        return driver.findElements(currentCurrency).get(0).getText();
    }
    @Step("User fill BUY amount")
    public MainPage userFillBuyAmount(String amount) {
        driver.findElements(sellAndBuyTextInput).get(1).clear();
        driver.findElements(sellAndBuyTextInput).get(1).sendKeys(amount);
        return this;
    }
    @Step("User fill SELL amount")
    public MainPage userFillSellAmount(String amount) {
        driver.findElements(sellAndBuyTextInput).get(0).clear();
        driver.findElements(sellAndBuyTextInput).get(0).sendKeys(amount);
        return this;
    }
    @Step("Get BUY amount")
    public String getBuyAmount() {
        wait.until((ExpectedCondition<Boolean>) d -> d.findElements(sellAndBuyTextInput).size() > 1);
        return driver.findElements(sellAndBuyTextInput).get(1).getText();
    }
    @Step("Get SELL amount")
    public String getSellAmount() {
        wait.until((ExpectedCondition<Boolean>) d -> d.findElements(sellAndBuyTextInput).size() > 1);
        return driver.findElements(sellAndBuyTextInput).get(0).getText();
    }
    @Step("Set SELL currency")
    public MainPage setSellCurrency(String currency) {
        driver.findElements(currentCurrency).get(0).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(selectCurrency(currency))).click();
        return this;
    }
    @Step("Set BUY currency")
    public MainPage setBuyCurrency(String currency) {
        driver.findElements(currentCurrency).get(1).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(selectCurrency(currency))).click();
        return this;
    }
    @Step("Get Paysera amount")
    public Float getPayseraAmount() {
        return Float.parseFloat(driver.findElement(payseraAmount).getText());
    }
    @Step("Get Bank amount")
    public Float getBankAmount() {
        String[] bankAmountAndLoss = driver.findElement(swedbankAmount).getText().split("\\r?\\n");
        return Float.parseFloat(bankAmountAndLoss[0]);
    }
    @Step("Get loss")
    public Float getLoss() {
        String[] bankAmountAndLoss = driver.findElement(swedbankAmount).getText().split("\\r?\\n");
        String lossInBrackets = bankAmountAndLoss[1];
        return Float.parseFloat(lossInBrackets.substring(lossInBrackets.indexOf("(") + 1, lossInBrackets.indexOf(")")));
    }
    @Step("Press filter button")
    public MainPage pressFilterButton() {
        filterButton.click();
        return this;
    }
}
