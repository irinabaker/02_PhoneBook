package phonebook.fw;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import phonebook.core.BaseHelper;
import phonebook.model.Contact;

import java.util.List;

public class ContactHelper extends BaseHelper {
    public static final String CONTACT_NAME = "NewName2027";
    public static final String CONTACT_LOCATOR = "contact-item_card__2SOIM";
    public static final String BUTTON_REMOVE = "//button[.='Remove']";
    public static final By CONTACT = By.className(CONTACT_LOCATOR);
    public static final By CONTACT_TO_DELETE = By.xpath("//h2[contains(text(),'" + CONTACT_NAME + "')]");

    public ContactHelper(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public boolean isContactAdded(String textToFind) {
        // Собираем в коллекцию все контакты
        List<WebElement> contacts = driver.findElements(By.className(CONTACT_LOCATOR));
        // Ищем в коллекции наш контакт
        for (WebElement contact : contacts) {
            if (contact.getText().contains(textToFind))
                return true; // Контакт найден
        }
        return false; // Контакт не найден
    }

    public void addNewContactPositiveData(String name) {
        // click on ADD link
        click(By.xpath("//a[.='ADD']"));
        fillInNewContactForm(
                new Contact()
                        .setName(name)
                        .setLastName("LastName")
                        .setPhone("1234567890")
                        .setEmail("admin@gmail.com")
                        .setAddress("Germany, Berlin")
                        .setDescription("Some Description")
        );
        // click on Save button
        click(By.xpath("//b[.='Save']"));
    }

    public void addContactPositiveData(Contact contact) {
        click(By.xpath("//a[.='ADD']"));
        type(By.xpath("//input[@placeholder='Name']"), contact.getName());
        type(By.xpath("//input[@placeholder='Last Name']"), contact.getLastName());
        type(By.xpath("//input[@placeholder='Phone']"), contact.getPhone());
        type(By.xpath("//input[@placeholder='email']"), contact.getEmail());
        type(By.xpath("//input[@placeholder='Address']"), contact.getAddress());
        type(By.xpath("//input[@placeholder='description']"), contact.getDescription());
        click(By.xpath("//b[.='Save']"));
    }

    public void fillInNewContactForm(Contact contact) {
        type(By.xpath("//input[@placeholder='Name']"), contact.getName());
        type(By.xpath("//input[@placeholder='Last Name']"), contact.getLastName());
        type(By.xpath("//input[@placeholder='Phone']"), contact.getPhone());
        type(By.xpath("//input[@placeholder='email']"), contact.getEmail());
        type(By.xpath("//input[@placeholder='Address']"), contact.getAddress());
        type(By.xpath("//input[@placeholder='description']"), contact.getDescription());
    }

    public int getContactsCount() {
        pause(100);
        // 🔍 Вернёт int количество CONTACT_LOCATOR на странице
        return driver.findElements(By.className(CONTACT_LOCATOR)).size();
    }
}
