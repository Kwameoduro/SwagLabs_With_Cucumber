package com.swaglabs.pages;

import com.swaglabs.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class ProductsPage extends BasePage {

    // Locators
    private final By pageTitle = By.className("title");
    private final By productItems = By.className("inventory_item");
    private final By productNames = By.className("inventory_item_name");
    private final By productPrices = By.className("inventory_item_price");
    private final By addToCartButtons = By.xpath("//button[contains(text(),'Add to cart')]");
    private final By removeFromCartButtons = By.xpath("//button[contains(text(),'Remove')]");
    private final By sortDropdown = By.className("product_sort_container");

    // Dynamic locators - will be formatted with product name
    private final String addToCartByProductName = "//div[text()='%s']/ancestor::div[@class='inventory_item']//button[text()='Add to cart']";
    private final String removeFromCartByProductName = "//div[text()='%s']/ancestor::div[@class='inventory_item']//button[text()='Remove']";
    private final String productLinkByName = "//div[text()='%s']";

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    public String getPageHeader() {
        return getElementText(pageTitle);
    }

    public boolean isProductsPageDisplayed() {
        return isElementDisplayed(pageTitle) &&
                getPageHeader().equals("Products");
    }

    public int getProductCount() {
        return findElements(productItems).size();
    }

    public void addProductToCart(String productName) {
        By addToCartButton = By.xpath(String.format(addToCartByProductName, productName));
        clickElement(addToCartButton);
    }

    public void removeProductFromCart(String productName) {
        By removeButton = By.xpath(String.format(removeFromCartByProductName, productName));
        clickElement(removeButton);
    }

    public void clickProductName(String productName) {
        By productLink = By.xpath(String.format(productLinkByName, productName));
        clickElement(productLink);
    }

    public boolean isProductInCart(String productName) {
        By removeButton = By.xpath(String.format(removeFromCartByProductName, productName));
        return isElementDisplayed(removeButton);
    }

    public String getProductPrice(String productName) {
        By priceLocator = By.xpath(String.format(
                "//div[text()='%s']/ancestor::div[@class='inventory_item']//div[@class='inventory_item_price']",
                productName));
        return getElementText(priceLocator);
    }

    public void sortProducts(String sortOption) {
        WebElement dropdown = driver.findElement(sortDropdown);
        dropdown.click();
        By option = By.xpath(String.format("//option[text()='%s']", sortOption));
        clickElement(option);
    }

    public boolean isProductDisplayed(String productName) {
        By productLocator = By.xpath(String.format(productLinkByName, productName));
        return isElementDisplayed(productLocator);
    }
}