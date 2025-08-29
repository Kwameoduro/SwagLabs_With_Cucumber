package com.swaglabs.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import java.time.Duration;


public class DriverManager {

    private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();


    public static void initializeDriver(String browserType) {
        WebDriver driver = createDriver(browserType.toLowerCase());
        configureDriver(driver);
        driverThreadLocal.set(driver);
    }

    private static WebDriver createDriver(String browserType) {
        WebDriver driver;

        switch (browserType) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = getChromeOptions();
                driver = new ChromeDriver(chromeOptions);
                break;

            case "headless-chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions headlessChromeOptions = getChromeOptions();
                headlessChromeOptions.addArguments("--headless");
                driver = new ChromeDriver(headlessChromeOptions);
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = getFirefoxOptions();
                driver = new FirefoxDriver(firefoxOptions);
                break;

            case "headless-firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions headlessFirefoxOptions = getFirefoxOptions();
                headlessFirefoxOptions.addArguments("--headless");
                driver = new FirefoxDriver(headlessFirefoxOptions);
                break;

            case "edge":
//                WebDriverManager.edgedriver().setup();
//                EdgeOptions edgeOptions = getEdgeOptions();
//                driver = new EdgeDriver(edgeOptions);
                System.setProperty("webdriver.edge.driver", "resources/msedgedriver.exe");
                driver = new EdgeDriver();
                break;

            default:
                throw new IllegalArgumentException("Browser type not supported: " + browserType +
                        ". Supported browsers: chrome, headless-chrome, firefox, headless-firefox, edge");
        }

        return driver;
    }

    private static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();

        // Essential for Docker environments
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-web-security");
        options.addArguments("--allow-running-insecure-content");
        options.addArguments("--disable-background-timer-throttling");
        options.addArguments("--disable-backgrounding-occluded-windows");
        options.addArguments("--disable-renderer-backgrounding");
        options.addArguments("--disable-features=TranslateUI");
        options.addArguments("--disable-ipc-flooding-protection");

        // For Docker environments specifically
        if (isRunningInDocker()) {
            options.addArguments("--headless");
            options.addArguments("--remote-debugging-port=9222");
            options.addArguments("--disable-background-networking");
            options.addArguments("--disable-default-apps");
            options.addArguments("--disable-sync");
        }

        return options;
    }


    private static FirefoxOptions getFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        FirefoxProfile profile = new FirefoxProfile();

        // Firefox-specific configurations
        profile.setPreference("dom.webnotifications.enabled", false);
        profile.setPreference("media.volume_scale", "0.0");
        profile.setPreference("browser.safebrowsing.malware.enabled", false);
        profile.setPreference("browser.safebrowsing.phishing.enabled", false);
        profile.setPreference("browser.download.folderList", 2);
        profile.setPreference("browser.download.manager.showWhenStarting", false);
        profile.setPreference("browser.download.dir", System.getProperty("java.io.tmpdir"));
        profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
                "application/zip,application/octet-stream,application/x-zip,application/x-zip-compressed,text/css,text/html,text/plain,text/xml,text/comma-separated-values");

        // Performance optimizations
        profile.setPreference("network.http.pipelining", true);
        profile.setPreference("network.http.proxy.pipelining", true);
        profile.setPreference("network.http.pipelining.maxrequests", 8);
        profile.setPreference("content.notify.interval", 500000);
        profile.setPreference("content.notify.ontimer", true);
        profile.setPreference("content.switch.threshold", 250000);

        options.setProfile(profile);

        // For CI/Docker environments
        if (isRunningInCI() || isRunningInDocker()) {
            options.addArguments("--headless");
            options.addArguments("--width=1920");
            options.addArguments("--height=1080");
        }

        return options;
    }

    private static EdgeOptions getEdgeOptions() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-extensions");

        if (isRunningInCI() || isRunningInDocker()) {
            options.addArguments("--headless");
        }

        return options;
    }

    private static void configureDriver(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        // Don't maximize in headless mode
        if (!isRunningInDocker() && !isRunningInCI()) {
            driver.manage().window().maximize();
        }
    }

    public static WebDriver getDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver == null) {
            throw new IllegalStateException("WebDriver is not initialized. Call initializeDriver() first.");
        }
        return driver;
    }

    public static boolean isDriverInitialized() {
        return driverThreadLocal.get() != null;
    }

    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                System.err.println("Error while quitting driver: " + e.getMessage());
            } finally {
                driverThreadLocal.remove();
            }
        }
    }

    private static boolean isRunningInCI() {
        String ci = System.getenv("CI");
        String githubActions = System.getenv("GITHUB_ACTIONS");
        String jenkins = System.getenv("JENKINS_URL");

        return "true".equalsIgnoreCase(ci) ||
                "true".equalsIgnoreCase(githubActions) ||
                jenkins != null;
    }

    private static boolean isRunningInDocker() {
        String docker = System.getenv("DOCKER");
        String dockerEnv = System.getenv("DOCKERIZED");

        // Check for Docker-specific files
        java.io.File dockerEnvFile = new java.io.File("/.dockerenv");

        return "true".equalsIgnoreCase(docker) ||
                "true".equalsIgnoreCase(dockerEnv) ||
                dockerEnvFile.exists() ||
                isRunningInCI(); // Assume Docker in CI
    }

    public static String getBrowserType() {
        String browser = System.getProperty("browser");
        if (browser == null || browser.isEmpty()) {
            browser = System.getenv("BROWSER");
        }
        if (browser == null || browser.isEmpty()) {
            browser = "headless-chrome"; // default for Docker
        }
        return browser;
    }

}