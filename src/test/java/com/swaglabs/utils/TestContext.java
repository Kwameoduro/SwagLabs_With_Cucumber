package com.swaglabs.utils;

import com.swaglabs.pages.CartPage;
import com.swaglabs.pages.CheckoutPage;
import com.swaglabs.pages.LoginPage;
import com.swaglabs.pages.ProductsPage;
import org.openqa.selenium.WebDriver;
import java.util.HashMap;
import java.util.Map;

public class TestContext {

    private WebDriver driver;
    private Map<String, Object> testData;

    // Page Objects
    private LoginPage loginPage;
    private ProductsPage productsPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;

    public TestContext() {
        this.testData = new HashMap<>();
    }

    public void initializePages() {
        this.driver = DriverManager.getDriver();
        this.loginPage = new LoginPage(driver);
        this.productsPage = new ProductsPage(driver);
        this.cartPage = new CartPage(driver);
        this.checkoutPage = new CheckoutPage(driver);
    }

    // Getters for Page Objects
    public LoginPage getLoginPage() {
        if (loginPage == null) {
            initializePages();
        }
        return loginPage;
    }

    public ProductsPage getProductsPage() {
        if (productsPage == null) {
            initializePages();
        }
        return productsPage;
    }

    public CartPage getCartPage() {
        if (cartPage == null) {
            initializePages();
        }
        return cartPage;
    }

    public CheckoutPage getCheckoutPage() {
        if (checkoutPage == null) {
            initializePages();
        }
        return checkoutPage;
    }

    public WebDriver getDriver() {
        return DriverManager.getDriver();
    }

    // Test Data Management
    public void setTestData(String key, Object value) {
        testData.put(key, value);
    }

    public String getTestDataAsString(String key) {
        Object value = testData.get(key);
        return value != null ? value.toString() : null;
    }

    public void clearTestData() {
        testData.clear();
    }
}