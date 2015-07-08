package com.gameclicker.automation;

import com.gameclicker.utils.WebDriverController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestResult;
import org.testng.annotations.*;
import ru.yandex.qatools.allure.annotations.Attachment;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Random;

public class BaseTest implements IHookable {
    private static WebDriver wd = null;
    protected final Logger log = LogManager.getLogger(this);

    @BeforeClass
    public static void beforeClass() {
        wd = WebDriverController.getDriver();
    }

    @AfterClass
    public static void afterClass() {
        if (wd != null) {
            wd.quit();
        }
    }

    protected static WebDriver getWdInstance() {
        return wd;
    }

    @BeforeMethod()
    public void beforeMethodInit() throws MalformedURLException {
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethodStop(ITestResult testResult) throws IOException {
        log.info("Test '{}' finished its execution", testResult.getMethod().getMethodName());
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuiteStop() {
    }

    @Override
    public void run(IHookCallBack callBack, ITestResult testResult) {
        callBack.runTestMethod(testResult);

        if (testResult.getThrowable() != null) {
            try {
                takeScreenShot();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Attachment(value = "Failure in method {0}", type = "image/png")
    private byte[] takeScreenShot() throws IOException {
        return ((TakesScreenshot) getWdInstance()).getScreenshotAs(OutputType.BYTES);
    }


    protected String getRandomString(int length) {
        final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rnd = new Random();

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }
}
