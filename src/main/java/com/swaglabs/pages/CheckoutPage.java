package com.swaglabs.pages;

import com.swaglabs.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;


public class CheckoutPage extends BasePage {

    // Checkout Information Page Locators
    private final By pageTitle = By.className("title");
    private final By firstNameField = By.id("first-name");
    private final By lastNameField = By.id("last-name");
    private final By postalCodeField = By.id("postal-code");
    private final By continueButton = By.id("continue");
    private final By cancelButton = By.id("cancel");
    private final By errorMessage = By.cssSelector("[data-test='error']");

    // Checkout Overview Page Locators
    private final By summaryItems = By.className("cart_item");
    private final By summaryItemNames = By.className("inventory_item_name");
    private final By summaryItemPrices = By.className("inventory_item_price");
    private final By summarySubTotal = By.className("summary_subtotal_label");
    private final By summaryTax = By.className("summary_tax_label");
    private final By summaryTotal = By.className("summary_total_label");
    private final By finishButton = By.id("finish");

    // Checkout Complete Page Locators
    private final By completeHeader = By.className("complete-header");
    private final By completeText = By.className("complete-text");
    private final By backToProductsButton = By.id("back-to-products");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public boolean isCheckoutInformationPageDisplayed() {
        return isElementDisplayed(firstNameField) &&
                getCurrentUrl().contains("checkout-step-one.html");
    }

    public boolean isCheckoutOverviewPageDisplayed() {
        return isElementDisplayed(finishButton) &&
                getCurrentUrl().contains("checkout-step-two.html");
    }

    public boolean isCheckoutCompletePageDisplayed() {
        return isElementDisplayed(completeHeader) &&
                getCurrentUrl().contains("checkout-complete.html");
    }

    public void enterFirstName(String firstName) {
        typeText(firstNameField, firstName);
    }

    public void enterLastName(String lastName) {
        typeText(lastNameField, lastName);
    }

    public void enterPostalCode(String postalCode) {
        typeText(postalCodeField, postalCode);
    }

    public void fillCheckoutInformation(String firstName, String lastName, String postalCode) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterPostalCode(postalCode);
    }

    public void clickContinue() {
        clickElement(continueButton);
    }

    public void clickCancel() {
        clickElement(cancelButton);
    }

    public String getErrorMessage() {
        return getElementText(errorMessage);
    }

    public boolean isErrorMessageDisplayed() {
        return isElementDisplayed(errorMessage);
    }

    public String getPageTitle() {
        return getElementText(pageTitle);
    }

    public List<String> getSummaryItemNames() {
        return findElements(summaryItemNames)
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    public boolean isItemInSummary(String itemName) {
        return getSummaryItemNames().contains(itemName);
    }

    public String getSubTotal() {
        return getElementText(summarySubTotal);
    }

    public String getTaxAmount() {
        return getElementText(summaryTax);
    }

    public String getTotalAmount() {
        return getElementText(summaryTotal);
    }

    public int getSummaryItemCount() {
        return findElements(summaryItems).size();
    }

    public void clickFinish() {
        clickElement(finishButton);
    }

    public String getCompletionHeader() {
        return getElementText(completeHeader);
    }

    public void clickBackToProducts() {
        clickElement(backToProductsButton);
    }

    public boolean isOrderCompleted() {
        return isCheckoutCompletePageDisplayed() &&
                getCompletionHeader().contains("Thank you for your order!");
    }
}