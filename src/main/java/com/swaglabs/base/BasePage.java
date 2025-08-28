package com.swaglabs.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.List;


public abstract class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    // Common locators
    protected By cartBadge = By.className("shopping_cart_badge");
    protected By cartIcon = By.className("shopping_cart_link");
    protected By menuButton = By.id("react-burger-menu-btn");

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected WebElement waitForElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitForClickableElement(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected boolean isElementDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected String getElementText(By locator) {
        return waitForElement(locator).getText();
    }

    protected void clickElement(By locator) {
        waitForClickableElement(locator).click();
    }

    protected void typeText(By locator, String text) {
        WebElement element = waitForElement(locator);
        element.clear();
        element.sendKeys(text);
    }

    public String getCartBadgeCount() {
        try {
            return getElementText(cartBadge);
        } catch (Exception e) {
            return "0";
        }
    }

    public boolean isCartBadgeVisible() {
        return isElementDisplayed(cartBadge);
    }

    public void clickCartIcon() {
        clickElement(cartIcon);
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    protected List<WebElement> findElements(By locator) {
        return driver.findElements(locator);
    }
}