package com.swaglabs.hooks;

import io.cucumber.java.*;
import com.swaglabs.utils.DriverManager;
import com.swaglabs.utils.TestContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Hooks {

    private final TestContext testContext;

    public Hooks(TestContext testContext) {
        this.testContext = testContext;
    }

    @Before
    public void setUp(Scenario scenario) {
        String browserType = DriverManager.getBrowserType();
        System.out.println("Starting test: " + scenario.getName());
        System.out.println("Browser: " + browserType);

        DriverManager.initializeDriver(browserType);
        testContext.initializePages();
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            takeScreenshot(scenario);
        }

        testContext.clearTestData();
        DriverManager.quitDriver();

        System.out.println("Test completed: " + scenario.getName());
        System.out.println("Status: " + (scenario.isFailed() ? "FAILED" : "PASSED"));
    }

    private void takeScreenshot(Scenario scenario) {
        if (DriverManager.isDriverInitialized()) {
            try {
                WebDriver driver = DriverManager.getDriver();
                TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
                byte[] screenshot = takesScreenshot.getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Screenshot");
            } catch (Exception e) {
                System.err.println("Failed to take screenshot: " + e.getMessage());
            }
        }
    }
}