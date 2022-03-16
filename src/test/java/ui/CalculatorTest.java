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
        mainPage.loadPage();
        actionService.moveToTheFooter()
                .changeCountry()
                .selectCountry(Countries.LITHUANIA);
    }

    @Test(description = "Check the default currency is changed when country selected")
    public void checkDefaultCurrencyWhenCountrySelected() {
        actionService.moveToTheFooter()
                .changeCountry()
                .selectCountry(Countries.UKRAINE);
        actionService.moveToTheForm();

        assertEquals(actionService.waitForDefaultCurrency().defaultCurrencyIs(), Currency.UAH);
    }

    @Test(description = "Check that BUY amount is empty when SELL amount is set")
    public void checkBuyAmountIsEmptyWhenSellAmountIsSet() {
        actionService.moveToTheForm()
                .waitForDefaultCurrency();
        actionService.waitForSelectCurrencyDropDown()
                .setSellCurrency(Currency.PLN)
                .userFillSellAmount("1000")
                .setBuyCurrency(Currency.USD);

        assertEquals(mainPage.getBuyAmount(), "");
    }

    @Test(description = "Check that SELL amount is empty when BUY amount is set")
    public void checkSellAmountIsEmptyWhenBuyAmountIsSet() {
        actionService.moveToTheForm()
                .waitForDefaultCurrency();
        actionService.waitForSelectCurrencyDropDown()
                .setSellCurrency(Currency.DKK)
                .setBuyCurrency(Currency.EUR)
                .userFillBuyAmount("2000");

        assertEquals(mainPage.getSellAmount(), "");
    }

    @Test(description = "Check that loss amount that displayed is diff of bank amount and Paysera amount")
    public void checkTheLossAmount() {
        actionService.moveToTheForm()
                .waitForDefaultCurrency();
        actionService.waitForSelectCurrencyDropDown()
                .setSellCurrency(Currency.NOK)
                .setBuyCurrency(Currency.USD)
                .userFillSellAmount("1000")
                .pressFilterButton();
        actionService.waitForTableWithFilteredResults();

        assertEquals(Float.parseFloat(df.format(mainPage.getBankAmount() - mainPage.getPayseraAmount())), mainPage.getLoss());
    }
}
