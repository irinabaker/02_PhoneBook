package phonebook.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import phonebook.core.TestBase;
import phonebook.data.ContactData;
import phonebook.fw.ContactHelper;
import phonebook.model.Contact;
import phonebook.utils.DataProviders;

import java.time.Duration;

import static phonebook.fw.ContactHelper.*;

public class AddContactTests extends TestBase {

    @BeforeMethod
    public void precondition(){
        //if(!isSignOutButtonPresent()){
        if(app.getUserHelper().isLoginLinkPresent()){
            app.getUserHelper().login("portishead@gmail.com", "Password@1");
        }
    }

    @Test(invocationCount = 1)
    public void addContactPositiveTest() {
        // 🔍 Шаг 1: Получаем текущее количество контактов
        int contactsBefore = app.getContactHelper().getContactsCount();
        System.out.println("Кол-во контактов ДО теста: " + contactsBefore);

        // ➕ Шаг 2: Добавляем новый контакт с валидными данными
        app.getContactHelper().addNewContactPositiveData(CONTACT_NAME);

        // 🔍 Шаг 3: Получаем количество контактов после добавления
        int contactsAfter = app.getContactHelper().getContactsCount();
        System.out.println("Кол-во контактов ПОСЛЕ теста: " + contactsAfter);

        // ✅ Шаг 4: Проверяем, что количество контактов увеличилось на 1
        Assert.assertEquals(
                contactsAfter,
                contactsBefore + 1,
                "❌ Количество контактов не увеличилось на +1 после добавления нового контакта"
        );

        // ✅ Шаг 5: Альтернативная проверка — убедиться, что контакт с заданным именем действительно появился
        Assert.assertTrue(
                app.getContactHelper().isContactAdded(CONTACT_NAME),
                "❌ Контакт с именем " + CONTACT_NAME + " не найден после добавления"
        );
    }

    @Test(invocationCount = 1)
    public void addContactWithDataPositiveTest() {
        int contactsBefore = app.getContactHelper().getContactsCount();
        System.out.println("Кол-во контактов ДО теста: " + contactsBefore);
//app.getContactHelper().addNewContactPositiveData(CONTACT_NAME);
        app.getContactHelper().click(By.xpath("//a[.='ADD']"));
        app.getContactHelper().fillInNewContactForm(
                new Contact()
                        .setName(ContactData.FIRST_NAME)
                        .setLastName(ContactData.LAST_NAME)
                        .setPhone(ContactData.PHONE)
                        .setEmail(ContactData.EMAIL)
                        .setAddress(ContactData.ADDRESS)
                        .setDescription(ContactData.DESC)
        );
        app.getContactHelper().click(By.xpath("//b[.='Save']"));
        int contactsAfter = app.getContactHelper().getContactsCount();
        System.out.println("Кол-во контактов ПОСЛЕ теста: " + contactsAfter);
        Assert.assertEquals(
                contactsAfter,
                contactsBefore + 1,
                "❌ Количество контактов не увеличилось на +1 после добавления нового контакта"
        );
    }

    @Test(dataProvider = "contactDataProvider", dataProviderClass = DataProviders.class)
    public void addContactWithDataProviderPositiveTest(
            String name,
            String lastName,
            String phone,
            String email,
            String address,
            String description
    ) {
        int contactsBefore = app.getContactHelper().getContactsCount();
        System.out.println("Кол-во контактов ДО теста: " + contactsBefore);
        app.getContactHelper().click(By.xpath("//a[.='ADD']"));
        app.getContactHelper().fillInNewContactForm(
                new Contact()
                        .setName(name)
                        .setLastName(lastName)
                        .setPhone(phone)
                        .setEmail(email)
                        .setAddress(address)
                        .setDescription(description)
        );
        app.getContactHelper().click(By.xpath("//b[.='Save']"));
        int contactsAfter = app.getContactHelper().getContactsCount();
        System.out.println("Кол-во контактов ПОСЛЕ теста: " + contactsAfter);
        Assert.assertEquals(
                contactsAfter,
                contactsBefore + 1,
                "❌ Количество контактов не увеличилось на +1 после добавления нового контакта"
        );
    }

    @Test(dataProvider = "addContactFromCsv", dataProviderClass = DataProviders.class)
    public void addContactDataProviderFromCSVPositiveTest(Contact contact) {
        int contactsBefore = app.getContactHelper().getContactsCount();
        System.out.println("Кол-во контактов ДО теста: " + contactsBefore);
        app.getContactHelper().addContactPositiveData(contact);
        app.getContactHelper().pause(500);
        int contactsAfter = app.getContactHelper().getContactsCount();
        System.out.println("Кол-во контактов ПОСЛЕ теста: " + contactsAfter);
        Assert.assertEquals(
                contactsAfter,
                contactsBefore + 1,
                "❌ Количество контактов не увеличилось на +1 после добавления нового контакта"
        );
        Assert.assertTrue(
                app.getContactHelper().isContactAdded(contact.getName()),
                "❌ Контакт с именем " + contact.getName() + " не найден после добавления"
        );
    }

    @Test(dataProvider = "objectDataProvider", dataProviderClass = DataProviders.class)
    public void addContactDataProviderFromObjectPositiveTest(Contact contact) {
        int contactsBefore = app.getContactHelper().getContactsCount();
        System.out.println("Кол-во контактов ДО теста: " + contactsBefore);
        app.getContactHelper().addContactPositiveData(contact);
        app.getContactHelper().pause(500);
        int contactsAfter = app.getContactHelper().getContactsCount();
        System.out.println("Кол-во контактов ПОСЛЕ теста: " + contactsAfter);
        Assert.assertEquals(
                contactsAfter,
                contactsBefore + 1,
                "❌ Количество контактов не увеличилось на +1 после добавления нового контакта"
        );
        Assert.assertTrue(
                app.getContactHelper().isContactAdded(contact.getName()),
                "❌ Контакт с именем " + contact.getName() + " не найден после добавления"
        );
    }

    @AfterMethod(enabled = false)
    public void postcondition(){
        int contactsBeforeDelete = app.getContactHelper().getContactsCount();
        System.out.println("Кол-во контактов ДО удаления: " + contactsBeforeDelete);

        //Если контактов нет - то нечего и делать всему методу далее
        if(contactsBeforeDelete == 0){
            System.out.println("Контактов для удаления не найдено");
            return;
        }
        app.getContactHelper().click(CONTACT_TO_DELETE);
        app.getUserHelper().click(By.xpath(BUTTON_REMOVE));

        //pause(500);
        new WebDriverWait(app.driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.numberOfElementsToBe(CONTACT,contactsBeforeDelete-1));
        //! или
        app.wait.until(driver -> app.getContactHelper().getContactsCount() < contactsBeforeDelete);

        int contactsAfterDelete = app.getContactHelper().getContactsCount();
        System.out.println("Кол-во контактов ПОСЛЕ удаления: " + contactsAfterDelete);

        Assert.assertEquals(
                contactsAfterDelete,
                contactsBeforeDelete - 1,
                "❌ Количество контактов не уменьшилось на -1 после удаления"
        );
    }
}
