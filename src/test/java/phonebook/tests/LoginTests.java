package phonebook.tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import phonebook.data.UserData;
import phonebook.model.User;
import phonebook.core.TestBase;
import phonebook.utils.DataProviders;

public class LoginTests extends TestBase {
    @Test
    public void loginExistedUserPositiveTest(){
        //login("portishead@gmail.com", "Password@1");
        app.getUserHelper().login(new User()
                .setEmail("portishead@gmail.com")
                .setPassword("Password@1"));
        Assert.assertTrue( app.getUserHelper().isSignOutButtonPresent());
    }

    @Test
    public void loginExistedUserWithDataPositiveTest(){
        app.getUserHelper().login(new User()
                .setEmail(UserData.EMAIL)
                .setPassword(UserData.PASSWORD));
        Assert.assertTrue( app.getUserHelper().isSignOutButtonPresent());
    }

    @Test
    public void loginNegativeTest(){
        app.getUserHelper().login(
                "portishead122321@gmail.com",
                "Password@1"
        );
        Assert.assertTrue( app.getUserHelper().isAlertPresent());
    }

    @Test
    public void loginWOEmailNegativeTest(){
        app.getUserHelper().login(new User()
                //.setEmail("portishead@gmail.com")
                .setPassword("Password@1")
        );
        Assert.assertTrue( app.getUserHelper().isAlertPresent());
    }

    @AfterMethod
    public void postcondition(){
        if (app.getUserHelper().isSignOutButtonPresent()) {
            app.getUserHelper().logout();
        }
    }

    @Test(dataProvider =  "loginDataProvider",dataProviderClass = DataProviders.class)
    public void loginExistedUserDataProviderPositiveTest(String email, String password){
        app.getUserHelper().login(
                new User()
                .setEmail(email)
                .setPassword(password));
        Assert.assertTrue( app.getUserHelper().isSignOutButtonPresent());
    }
}