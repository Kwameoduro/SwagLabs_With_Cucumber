package com.swaglabs.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * Firefox Test Runner - Forces Firefox browser execution
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com/swaglabs", "com/swaglabs"},
        plugin = {
                "pretty",
                "html:target/firefox-reports/html-report",
                "json:target/firefox-reports/cucumber.json",
                "junit:target/firefox-reports/cucumber.xml"
        },
        monochrome = true,
        tags = "@swag_labs"
)
public class FirefoxRunner {

    static {
        // Force Firefox browser for this runner
        System.setProperty("browser", "firefox");
    }
}