package phonebook.tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import phonebook.core.TestBase;

public class CreateAccountTests extends TestBase {

    @Test
    public void creatAccountPositiveTest(){
        // click on `LOGIN` link //a[.='LOGIN']
        app.driver.findElement(By.xpath("//a[.='LOGIN']")).click();
        // enter email "email"
        app.driver.findElement(By.name("email")).click();
        app.driver.findElement(By.name("email")).clear();
        app.driver.findElement(By.name("email")).sendKeys(System.currentTimeMillis()+"@qa_test.com");
        // enter password "password"
        app.driver.findElement(By.name("password")).click();
        app.driver.findElement(By.name("password")).clear();
        app.driver.findElement(By.name("password")).sendKeys("Password@1");
        // click on `REGISTRATION` button "registration"
        app.driver.findElement(By.name("registration")).click();
        // assert that SignOut button is Present //button[.='Sign Out']
        Assert.assertTrue(app.getUserHelper().isSignOutButtonPresent());
    }

    @Test
    public void creatExistedAccountNegativeTest(){
        SoftAssert softAssert = new SoftAssert();
        app.driver.findElement(By.xpath("//a[.='LOGIN']")).click();
        app.driver.findElement(By.name("email")).click();
        app.driver.findElement(By.name("email")).clear();
        app.driver.findElement(By.name("email")).sendKeys("portishead@gmail.com");
        app.driver.findElement(By.name("password")).click();
        app.driver.findElement(By.name("password")).clear();
        app.driver.findElement(By.name("password")).sendKeys("Password@1");
        app.driver.findElement(By.name("registration")).click();
        softAssert.assertTrue(app.getUserHelper().isAlertPresent());
        softAssert.assertTrue(app.getUserHelper().isRegistrationFailed409TextPresent());
        softAssert.assertFalse(app.getUserHelper().isSignOutButtonPresent()); // 10 секунд неявного ожидания
        softAssert.assertAll(); // Итоговый ассерт-месседж
    }

    @Test
    public void creatAccountPositiveRefactorTest(){
        app.getUserHelper().click(By.xpath("//a[.='LOGIN']"));
        app.getUserHelper().type(By.name("email"),"portishead5487@gmail.com");
        app.getUserHelper().type(By.name("password"),"Password@1");
        app.getUserHelper().click(By.name("registration"));
        Assert.assertTrue(app.getUserHelper().isSignOutButtonPresent());
    }
}
