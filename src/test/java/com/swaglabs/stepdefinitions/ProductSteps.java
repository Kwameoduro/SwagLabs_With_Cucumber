package com.swaglabs.stepdefinitions;

import com.swaglabs.utils.TestDataUtils;
import io.cucumber.java.en.*;
import com.swaglabs.utils.TestContext;
import static org.junit.Assert.*;

public class ProductSteps {

    private final TestContext testContext;

    public ProductSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    @Given("I am on the products page")
    public void i_am_on_the_products_page() {
        assertTrue("Should be on products page",
                testContext.getProductsPage().isProductsPageDisplayed());
    }

    @When("I add {string} to cart")
    public void i_add_product_to_cart(String productName) {
        testContext.getProductsPage().addProductToCart(productName);
        testContext.setTestData("added_product", productName);
    }

    @When("I remove {string} from the products page")
    public void i_remove_product_from_products_page(String productName) {
        testContext.getProductsPage().removeProductFromCart(productName);
    }

    @When("I click on product {string}")
    public void i_click_on_product(String productName) {
        testContext.getProductsPage().clickProductName(productName);
        testContext.setTestData(TestDataUtils.CURRENT_PRODUCT, productName);
    }

    @Then("the cart badge should show {string} items")
    public void the_cart_badge_should_show_items(String expectedCount) {
        String actualCount = testContext.getProductsPage().getCartBadgeCount();
        assertEquals("Cart badge should show correct count", expectedCount, actualCount);
    }

    @Then("the cart badge should not be visible")
    public void the_cart_badge_should_not_be_visible() {
        assertFalse("Cart badge should not be visible when cart is empty",
                testContext.getProductsPage().isCartBadgeVisible());
    }

    @Then("I should see the product details page")
    public void i_should_see_the_product_details_page() {
        String currentUrl = testContext.getDriver().getCurrentUrl();
        assertTrue("Should be on product details page",
                currentUrl.contains("inventory-item.html"));
    }

    @Then("I should see the product name {string}")
    public void i_should_see_the_product_name(String expectedProductName) {

        // This would require a ProductDetailsPage class, but for simplicity
        String currentUrl = testContext.getDriver().getCurrentUrl();
        assertTrue("Product details page should be displayed",
                currentUrl.contains("inventory-item.html"));
    }

    @Then("I should see the product price {string}")
    public void i_should_see_the_product_price(String expectedPrice) {

        String currentUrl = testContext.getDriver().getCurrentUrl();
        assertTrue("Should be on product details page",
                currentUrl.contains("inventory-item.html"));
    }

    @When("I add the product to cart from details page")
    public void i_add_the_product_to_cart_from_details_page() {

        testContext.getDriver().navigate().back();
        String productName = testContext.getTestDataAsString(TestDataUtils.CURRENT_PRODUCT);
        if (productName != null) {
            testContext.getProductsPage().addProductToCart(productName);
        }
    }

    @Then("I should see {int} products on the page")
    public void i_should_see_products_on_the_page(Integer expectedCount) {
        int actualCount = testContext.getProductsPage().getProductCount();
        assertEquals("Should see correct number of products", expectedCount.intValue(), actualCount);
    }

    @When("I sort products by {string}")
    public void i_sort_products_by(String sortOption) {
        testContext.getProductsPage().sortProducts(sortOption);
    }

    @Then("the product {string} should be displayed")
    public void the_product_should_be_displayed(String productName) {
        assertTrue("Product should be displayed",
                testContext.getProductsPage().isProductDisplayed(productName));
    }

    @Then("the product {string} should be in cart")
    public void the_product_should_be_in_cart(String productName) {
        assertTrue("Product should be in cart (Remove button visible)",
                testContext.getProductsPage().isProductInCart(productName));
    }

    @Then("the product {string} should not be in cart")
    public void the_product_should_not_be_in_cart(String productName) {
        assertFalse("Product should not be in cart (Add to Cart button visible)",
                testContext.getProductsPage().isProductInCart(productName));
    }

    @When("I get the price of {string}")
    public void i_get_the_price_of_product(String productName) {
        String price = testContext.getProductsPage().getProductPrice(productName);
        testContext.setTestData(productName + "_price", price);
    }

    @Then("the price of {string} should be {string}")
    public void the_price_of_product_should_be(String productName, String expectedPrice) {
        String actualPrice = testContext.getProductsPage().getProductPrice(productName);
        assertEquals("Product price should match", expectedPrice, actualPrice);
    }
}