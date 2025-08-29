package com.swaglabs.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com/swaglabs", "com/swaglabs"},
        plugin = {
                "pretty",
                "html:target/edge-reports/html-report.tml",
                "json:target/edge-reports/cucumber.json",
                "junit:target/edge-reports/cucumber.xml"
        },
        monochrome = true,
        tags = "@swag_labs"
)
public class EdgeRunner {

    static {
        // Force edge browser for this runner
        System.setProperty("browser", "edge");
    }
}