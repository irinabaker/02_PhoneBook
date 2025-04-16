package phonebook.tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import phonebook.core.TestBase;

public class DeleteContactTests extends TestBase {

    @BeforeMethod
    public void precondition() {
        app.getUserHelper().login("portishead@gmail.com", "Password@1");
        app.getContactHelper().addNewContact(app.getContactHelper().CONTACT_NAME);
        app.getContactHelper().addNewContact(app.getContactHelper().CONTACT_NAME);
        app.getContactHelper().addNewContact(app.getContactHelper().CONTACT_NAME);
    }

    @Test
    public void deleteOnePositiveTest() {

        int sizeBefore = app.getContactHelper().getContactsCount();
        logger.info("Number of contacts BEFORE deletion: " + sizeBefore);
        app.getContactHelper().deleteOneContact(app.getContactHelper().CONTACT_NAME);
        app.wait.until(driver -> app.getContactHelper().getContactsCount() < sizeBefore);
        int sizeAfter = app.getContactHelper().getContactsCount();
        System.out.println("Number of contacts AFTER deletion: " + sizeAfter);
        Assert.assertEquals(sizeAfter, sizeBefore - 1);
    }

    @Test
    public void deleteAllContactsTest(){
        while(app.getContactHelper().hasContacts()){
            int sizeBefore = app.getContactHelper().getContactsCount();
            System.out.println("Number of contacts BEFORE deletion: " + sizeBefore);
            //click(By.className(CONTACT_LOCATOR));
            app.getContactHelper().click(app.getContactHelper().LOCATOR);
            app.getHomePageHelper().click(app.getContactHelper().BUTTON_REMOVE);
            app.wait.until(driver -> app.getContactHelper().getContactsCount() < sizeBefore);
        }
        if(!app.getContactHelper().hasContacts()){
            System.out.println("All contacts deleted: " + app.getContactHelper().getContactsCount());
        }
        Assert.assertEquals(app.getContactHelper().getContactsCount(),0,"Not all contacts were deleted: " + app.getContactHelper().getContactsCount());
    }


}
