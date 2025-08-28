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
                "io.cucumber.core.plugin.HtmlFormatter:target/cucumber-reports/cucumber-html-report.html",
                "json:target/cucumber-reports/cucumber.json"
        },
        monochrome = true,
        tags = "@swag_labs"
)
public class TestRunner {
}