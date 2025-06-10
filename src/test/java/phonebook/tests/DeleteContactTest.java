package phonebook.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import phonebook.core.TestBase;

import java.util.List;

import static phonebook.fw.ContactHelper.*;

public class DeleteContactTest extends TestBase {

    @BeforeMethod
    public void precondition(){
        app.getUserHelper().login("portishead@gmail.com", "Password@1");
        app.getContactHelper().addNewContactPositiveData(CONTACT_NAME);
    }

    @Test
    public void createOneAndDeleteOneContactPositiveTest(){
        int contactsBeforeDelete = app.getContactHelper().getContactsCount();
        System.out.println("Кол-во контактов ДО удаления: " + contactsBeforeDelete);
        app.getContactHelper().click(CONTACT_TO_DELETE);
        app.getContactHelper().click(By.xpath(BUTTON_REMOVE));
        app.wait.until(driver -> app.getContactHelper().getContactsCount() < contactsBeforeDelete);
        int contactsAfterDelete = app.getContactHelper().getContactsCount();
        System.out.println("Кол-во контактов ПОСЛЕ удаления: " + contactsAfterDelete);
        Assert.assertEquals(
                contactsAfterDelete,
                contactsBeforeDelete - 1,
                "❌ Количество контактов не уменьшилось на -1 после удаления"
        );
    }

    @Test
    public void deleteAllContacts1Test(){
        while(hasContacts()){
            List<WebElement> contacts = app.driver.findElements(CONTACT);
            if(contacts.isEmpty()){
                // break;
                app.getContactHelper().addNewContactPositiveData(CONTACT_NAME);
            }
            WebElement contactToDelete = contacts.get(0);
            contactToDelete.click();
            app.getContactHelper().click(By.xpath(BUTTON_REMOVE));

            app.wait.until(ExpectedConditions.numberOfElementsToBeLessThan(CONTACT, contacts.size()));
            //wait.until(ExpectedConditions.stalenessOf(contactToDelete));
        }
        Assert.assertEquals(
                app.getContactHelper().getContactsCount(),
                0,
                "❌ Не все контакты были удалены");
    }

    @Test
    public void deleteAllContacts2Test(){
        while (hasContacts()){
            int contactsBefore = app.getContactHelper().getContactsCount();
            app.getContactHelper().click(CONTACT);
            app.getContactHelper().click(By.xpath(BUTTON_REMOVE));
            app.wait.until(driver -> app.getContactHelper().getContactsCount() < contactsBefore);
        }
        if(!hasContacts()){
            System.out.println("Все контакты были удалены");
        }
        Assert.assertEquals(
                app.getContactHelper().getContactsCount(),
                0,
                "❌ Не все контакты были удалены");
    }

    private boolean hasContacts() {
        return app.getContactHelper().isElementPresent(CONTACT);
    }
}
