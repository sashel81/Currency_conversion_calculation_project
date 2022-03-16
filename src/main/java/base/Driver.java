package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.safari.SafariDriver;

public class Driver {
    private WebDriver driver;
    private static Driver instance;

    private Driver() {
    }

    public static Driver getInstance() {
        if (instance == null)
            instance = new Driver();
        return instance;
    }

    public Driver setWebDriver(BrowserName currentName) {
        switch (currentName) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case OPERA:
                WebDriverManager.operadriver().setup();
                driver = new OperaDriver();
                break;
            case EDGE:
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            case SAFARI:
                WebDriverManager.safaridriver().setup();
                driver = new SafariDriver();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + currentName);
        }
        return this;
    }

    public WebDriver getWebDriver() {
        return driver;
    }

    public void deleteWebDriver() {
        if(driver != null)
            driver.quit();
        driver = null;
    }
}
