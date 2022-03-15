package ui;

import base.BaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;


public class CalculatorTest extends BaseTest {

    @BeforeMethod
    public void loadCalculatorPage() {
        mainPage.loadPage();
        mainPage.moveToTheFooter()
                .changeCountry()
                .selectCountry("Lithuania");
    }

    @Test(description = "Check the default currency is changed when country selected")
    public void checkDefaultCurrencyWhenCountrySelected() {
        mainPage.moveToTheFooter()
                .changeCountry()
                .selectCountry("Ukraine")
                .moveToTheForm();

        assertEquals(mainPage.waitForDefaultCurrency().defaultCurrencyIs(), "UAH");
    }

    @Test(description = "Check that BUY amount is empty when SELL amount is set")
    public void checkBuyAmountIsEmptyWhenSellAmountIsSet() {
        mainPage.moveToTheForm()
                .waitForDefaultCurrency()
                .waitForSelectCurrencyDropDown()
                .setSellCurrency("PLN")
                .userFillSellAmount("1000")
                .setBuyCurrency("USD");

        assertEquals(mainPage.getBuyAmount(), "");
    }

    @Test(description = "Check that SELL amount is empty when BUY amount is set")
    public void checkSellAmountIsEmptyWhenBuyAmountIsSet() {
        mainPage.moveToTheForm()
                .waitForDefaultCurrency()
                .waitForSelectCurrencyDropDown()
                .setSellCurrency("DKK")
                .setBuyCurrency("EUR")
                .userFillBuyAmount("2000");

        assertEquals(mainPage.getSellAmount(), "");
    }

    @Test(description = "Check that loss amount that displayed is diff of bank amount and Paysera amount")
    public void checkTheLossAmount(){
        mainPage.moveToTheForm()
                .waitForDefaultCurrency()
                .waitForSelectCurrencyDropDown()
                .setSellCurrency("NOK")
                .setBuyCurrency("USD")
                .userFillSellAmount("1001")
                .pressFilterButton()
                .waitForTableWithFilteredResults();
        assertEquals(Float.parseFloat(df.format(mainPage.getBankAmount() - mainPage.getPayseraAmount())), mainPage.getLoss());
    }
}
