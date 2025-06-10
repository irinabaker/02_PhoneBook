package phonebook.core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import phonebook.fw.ContactHelper;
import phonebook.fw.HomePageHelper;
import phonebook.fw.UserHelper;

import java.time.Duration;

public class ApplicationManager {
    public WebDriver driver;
    public WebDriverWait wait;
    private final String browser;
    UserHelper userHelper;
    ContactHelper contactHelper;
    HomePageHelper homePageHelper;

    public ApplicationManager(String browser) {
        this.browser = browser;
    }

    protected void init() {
        switch (browser.toLowerCase()){
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "chrome_headless":
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("headless");
                options.addArguments("--disable-search-engine-choice-screen");
                options.addArguments("windows-size=1920x1080");
                driver = new ChromeDriver(options);
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "safari":
                WebDriverManager.safaridriver().setup();
                driver = new SafariDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            default:
                throw new IllegalArgumentException("\n❌ Некорректный браузер: " + browser +". Допустимые варианты: \n`chrome`\n`firefox`\n`safari`\n`edge`");
        }
        //WebDriverManager.chromedriver().setup();
        //driver = new ChromeDriver();
        driver.get("https://telranedu.web.app/");
        driver.manage().window().setPosition(new Point(2500, 0)); // Сдвиг браузера на второй монитор
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5)); // Неявное ожидание
        wait =  new WebDriverWait(driver, Duration.ofSeconds(5)); // явное ожидание
        userHelper = new UserHelper(driver,wait);
        contactHelper = new ContactHelper(driver,wait);
        homePageHelper = new HomePageHelper(driver,wait);
    }

    public UserHelper getUserHelper() {
        return userHelper;
    }

    public ContactHelper getContactHelper() {
        return contactHelper;
    }

    public HomePageHelper getHomePageHelper() {
        return homePageHelper;
    }

    protected void stop() {
        driver.quit();
    }

}
