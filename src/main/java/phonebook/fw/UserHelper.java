package phonebook.fw;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import phonebook.core.BaseHelper;
import phonebook.model.User;

public class UserHelper extends BaseHelper {
    Logger logger = LoggerFactory.getLogger(UserHelper.class);

    public UserHelper(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public boolean isSignOutButtonPresent() {
        return isElementPresent(By.xpath("//button[.='Sign Out']"));
    }

    public boolean isLoginLinkPresent() {
        return isElementPresent(By.xpath("//a[.='LOGIN']"));
    }

    public boolean isRegistrationFailed409TextPresent() {
        return isElementPresent(By.xpath("//div[.='Registration failed with code 409']"));
    }

    public void login(String email, String password) {
        click(By.xpath("//a[.='LOGIN']"));
        type(By.name("email"), email);
        type(By.name("password"), password);
        click(By.name("login"));
    }

    public void login(User user) {
        click(By.xpath("//a[.='LOGIN']"));
        logger.info("Typing email: " + user.getEmail());
        type(By.name("email"), user.getEmail());
        logger.info("Typing password: " + user.getPassword());
        type(By.name("password"), user.getPassword());
        click(By.name("login"));
    }

    public void logout() {
        click(By.xpath("//button[.='Sign Out']"));
    }
}
