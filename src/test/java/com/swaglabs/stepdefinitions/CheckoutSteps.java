package com.swaglabs.stepdefinitions;

import com.swaglabs.utils.TestDataUtils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import com.swaglabs.utils.TestContext;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.*;


public class CheckoutSteps {

    private final TestContext testContext;

    public CheckoutSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    @When("I fill in checkout information:")
    public void i_fill_in_checkout_information(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        Map<String, String> checkoutInfo = data.get(0);

        String firstName = checkoutInfo.get("firstName");
        String lastName = checkoutInfo.get("lastName");
        String postalCode = checkoutInfo.get("postalCode");

        testContext.getCheckoutPage().fillCheckoutInformation(firstName, lastName, postalCode);
        testContext.setTestData(TestDataUtils.CHECKOUT_INFO, checkoutInfo);
    }

    @When("I continue to checkout overview")
    public void i_continue_to_checkout_overview() {
        testContext.getCheckoutPage().clickContinue();
    }

    @Then("I should see the checkout overview page")
    public void i_should_see_the_checkout_overview_page() {
        assertTrue("Should be on checkout overview page",
                testContext.getCheckoutPage().isCheckoutOverviewPageDisplayed());
    }

    @Then("I should see {string} in the order summary")
    public void i_should_see_item_in_the_order_summary(String itemName) {
        assertTrue("Item should be in order summary",
                testContext.getCheckoutPage().isItemInSummary(itemName));
    }

    @When("I finish the order")
    public void i_finish_the_order() {
        testContext.getCheckoutPage().clickFinish();
    }

    @Then("I should see the order confirmation page")
    public void i_should_see_the_order_confirmation_page() {
        assertTrue("Should be on order confirmation page",
                testContext.getCheckoutPage().isCheckoutCompletePageDisplayed());
    }

    @Then("I should see {string} message")
    public void i_should_see_message(String expectedMessage) {
        String actualMessage = testContext.getCheckoutPage().getCompletionHeader();
        assertTrue("Should see expected message",
                actualMessage.contains(expectedMessage));
    }

    @Then("I should be on the checkout information page")
    public void i_should_be_on_the_checkout_information_page() {
        assertTrue("Should be on checkout information page",
                testContext.getCheckoutPage().isCheckoutInformationPageDisplayed());
    }

    @When("I enter first name {string}")
    public void i_enter_first_name(String firstName) {
        testContext.getCheckoutPage().enterFirstName(firstName);
    }

    @When("I enter last name {string}")
    public void i_enter_last_name(String lastName) {
        testContext.getCheckoutPage().enterLastName(lastName);
    }

    @When("I enter postal code {string}")
    public void i_enter_postal_code(String postalCode) {
        testContext.getCheckoutPage().enterPostalCode(postalCode);
    }

    @When("I click continue")
    public void i_click_continue() {
        testContext.getCheckoutPage().clickContinue();
    }

    @When("I click cancel")
    public void i_click_cancel() {
        testContext.getCheckoutPage().clickCancel();
    }

    @Then("I should see checkout error message {string}")
    public void i_should_see_checkout_error_message(String expectedErrorMessage) {
        assertTrue("Error message should be displayed",
                testContext.getCheckoutPage().isErrorMessageDisplayed());

        String actualErrorMessage = testContext.getCheckoutPage().getErrorMessage();
        assertTrue("Error message should contain expected text",
                actualErrorMessage.contains(expectedErrorMessage));
    }

    @Then("the order summary should contain {int} items")
    public void the_order_summary_should_contain_items(Integer expectedCount) {
        int actualCount = testContext.getCheckoutPage().getSummaryItemCount();
        assertEquals("Order summary should contain correct number of items",
                expectedCount.intValue(), actualCount);
    }

    @When("I verify the subtotal amount")
    public void i_verify_the_subtotal_amount() {
        String subTotal = testContext.getCheckoutPage().getSubTotal();
        testContext.setTestData("subtotal", subTotal);
        assertNotNull("Subtotal should be displayed", subTotal);
    }

    @When("I verify the tax amount")
    public void i_verify_the_tax_amount() {
        String taxAmount = testContext.getCheckoutPage().getTaxAmount();
        testContext.setTestData("tax", taxAmount);
        assertNotNull("Tax amount should be displayed", taxAmount);
    }

    @When("I verify the total amount")
    public void i_verify_the_total_amount() {
        String totalAmount = testContext.getCheckoutPage().getTotalAmount();
        testContext.setTestData("total", totalAmount);
        assertNotNull("Total amount should be displayed", totalAmount);
    }

    @Then("the order should be completed successfully")
    public void the_order_should_be_completed_successfully() {
        assertTrue("Order should be completed successfully",
                testContext.getCheckoutPage().isOrderCompleted());
    }

    @When("I click back to products")
    public void i_click_back_to_products() {
        testContext.getCheckoutPage().clickBackToProducts();
    }

    @Then("I should be redirected back to products page")
    public void i_should_be_redirected_back_to_products_page() {
        assertTrue("Should be back on products page",
                testContext.getProductsPage().isProductsPageDisplayed());
    }

    @Given("I am on the checkout information page")
    public void i_am_on_the_checkout_information_page() {
        assertTrue("Should be on checkout information page",
                testContext.getCheckoutPage().isCheckoutInformationPageDisplayed());
    }

    @Given("I have items in my cart and proceed to checkout")
    public void i_have_items_in_my_cart_and_proceed_to_checkout() {
        // This assumes items are already in the cart
        testContext.getCartPage().proceedToCheckout();
        assertTrue("Should be on checkout information page",
                testContext.getCheckoutPage().isCheckoutInformationPageDisplayed());
    }

    @When("I leave the first name field empty")
    public void i_leave_the_first_name_field_empty() {
        testContext.getCheckoutPage().enterFirstName("");
    }

    @When("I leave the last name field empty")
    public void i_leave_the_last_name_field_empty() {
        testContext.getCheckoutPage().enterLastName("");
    }

    @When("I leave the postal code field empty")
    public void i_leave_the_postal_code_field_empty() {
        testContext.getCheckoutPage().enterPostalCode("");
    }
}