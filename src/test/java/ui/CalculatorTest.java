package ui;

import base.BaseTest;
import base.Countries;
import base.Currency;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.*;


public class CalculatorTest extends BaseTest {

    @BeforeMethod
    public void loadCalculatorPage() {
        mainPage.loadPage()
                .moveToTheFooter()
                .changeCountry()
                .selectCountry(Countries.LITHUANIA);
    }

    @Test(description = "Check the default currency is changed when country selected")
    public void checkDefaultCurrencyWhenCountrySelected() {
        mainPage.moveToTheFooter()
                .changeCountry()
                .selectCountry(Countries.UKRAINE)
                .moveToTheForm();

        assertEquals(mainPage.waitForDefaultCurrency().defaultSellCurrencyIs(), Currency.UAH);
    }

    @Test(description = "Check that BUY amount is empty when SELL amount is set")
    public void checkBuyAmountIsEmptyWhenSellAmountIsSet() {
        mainPage.moveToTheForm()
                .waitForDefaultCurrency()
                .waitForSelectCurrencyDropDown()
                .setSellCurrency(Currency.PLN)
                .userFillSellAmount("1000")
                .setBuyCurrency(Currency.USD);

        assertEquals(mainPage.getBuyAmount(), "");
    }

    @Test(description = "Check that SELL amount is empty when BUY amount is set")
    public void checkSellAmountIsEmptyWhenBuyAmountIsSet() {
        mainPage.moveToTheForm()
                .waitForDefaultCurrency()
                .waitForSelectCurrencyDropDown()
                .setSellCurrency(Currency.DKK)
                .setBuyCurrency(Currency.EUR)
                .userFillBuyAmount("2000");

        assertEquals(mainPage.getSellAmount(), "");
    }

    @Test(description = "Check that loss amount that displayed is diff of bank amount and Paysera amount")
    public void checkTheLossAmount() {
        mainPage.moveToTheForm()
                .waitForDefaultCurrency()
                .waitForSelectCurrencyDropDown()
                .setSellCurrency(Currency.NOK)
                .setBuyCurrency(Currency.USD)
                .userFillSellAmount("1000")
                .pressFilterButton()
                .waitForTableWithFilteredResults();

        assertEquals(Float.parseFloat(df.format(mainPage.getBankAmount() - mainPage.getPayseraAmount())), mainPage.getLoss());
    }

    @Test(description = "Check non-numeric value input field validation")
    public void setNonNumericDataInSellField() {
        mainPage.moveToTheForm()
                .waitForDefaultCurrency()
                .waitForSelectCurrencyDropDown()
                .setSellCurrency(Currency.NOK)
                .setBuyCurrency(Currency.USD)
                .userFillSellAmount("ttt")
                .pressFilterButton();

        assertTrue(toast.isDisplayed());
        assertTrue(toast.getToastText().contains("Invalid parameters"));
    }
}
