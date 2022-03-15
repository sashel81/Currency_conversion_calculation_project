package base;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import pages.MainPage;

import java.text.DecimalFormat;

public class BaseTest {
    protected static MainPage mainPage;
    protected static DecimalFormat df = new DecimalFormat("0.00");

    @BeforeTest
    public void setUp() {
        Driver.getInstance().setWebDriver(BrowserName.CHROME);
        mainPage = new MainPage();
    }

    @AfterTest
    public void tearDown() {
        Driver.getInstance().deleteWebDriver();
    }

}

