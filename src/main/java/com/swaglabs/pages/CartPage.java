package com.swaglabs.pages;

import com.swaglabs.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;


public class CartPage extends BasePage {

    // Locators
    private final By pageTitle = By.className("title");
    private final By cartItems = By.className("cart_item");
    private final By cartItemNames = By.className("inventory_item_name");
    private final By cartItemPrices = By.className("inventory_item_price");
    private final By removeButtons = By.xpath("//button[contains(text(),'Remove')]");
    private final By continueShoppingButton = By.id("continue-shopping");
    private final By checkoutButton = By.id("checkout");
    private final By emptyCartMessage = By.xpath("//div[@class='cart_list']");

    // Dynamic locators
    private final String removeItemByName = "//button[contains(@data-test, 'remove')]";
    private final String cartItemByName = "//div[@class='inventory_item_name' and text()='%s']";

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public boolean isCartPageDisplayed() {
        return isElementDisplayed(pageTitle) &&
                getCurrentUrl().contains("cart.html");
    }

    public String getPageHeader() {
        return getElementText(pageTitle);
    }


    public int getCartItemCount() {
        return findElements(cartItems).size();
    }

    public boolean isItemInCart(String itemName) {
        By itemLocator = By.xpath(String.format(cartItemByName, itemName));
        return isElementDisplayed(itemLocator);
    }

    public void removeItemFromCart(String itemName) {
        By removeButton = By.xpath(String.format(removeItemByName, itemName));
        clickElement(removeButton);
    }

    public boolean isCartEmpty() {
        return getCartItemCount() == 0;
    }

    public String getItemPrice(String itemName) {
        By priceLocator = By.xpath(String.format(
                "//div[text()='%s']/ancestor::div[@class='cart_item']//div[@class='inventory_item_price']",
                itemName));
        return getElementText(priceLocator);
    }

    public void continueShopping() {
        clickElement(continueShoppingButton);
    }

    public void proceedToCheckout() {
        clickElement(checkoutButton);
    }

    public void removeAllItems() {
        List<WebElement> removeButtonsList = findElements(removeButtons);
        for (WebElement button : removeButtonsList) {
            button.click();
            // Wait a moment for DOM to update
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public boolean isCheckoutButtonEnabled() {
        return driver.findElement(checkoutButton).isEnabled();
    }

    public boolean isContinueShoppingButtonDisplayed() {
        return isElementDisplayed(continueShoppingButton);
    }
}