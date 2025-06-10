package phonebook.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;

public class TestBase {

    protected ApplicationManager app;
    Logger logger = LoggerFactory.getLogger(TestBase.class);

    @BeforeMethod
    public void setUp(Method method) {
        logger.info("Test is started: [{}]", method.getName());
        app = new ApplicationManager(System.getProperty("browser", "chrome"));
        app.init();
    }

    @AfterMethod(enabled = true)
    public void tearDown(Method method, ITestResult result) {
        if (result.isSuccess()) {
            logger.info("Test is PASSED: [{}]", method.getName());
        } else {
            logger.error("Test is FAILED: [{}], Screenshot: [{}]", method.getName(), app.getContactHelper().takeScreenshot());
        }
        logger.info("Test is ended: [{}]", method.getName());
        app.stop();
    }
}
