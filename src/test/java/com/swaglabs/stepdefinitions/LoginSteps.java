package com.swaglabs.stepdefinitions;

import io.cucumber.java.en.*;
import com.swaglabs.utils.TestContext;
import static org.junit.Assert.*;
import com.swaglabs.utils.TestDataUtils;

public class LoginSteps {

    private final TestContext testContext;

    public LoginSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    @Given("I am on the Swag Labs login page")
    public void i_am_on_the_swag_labs_login_page() {
        testContext.getLoginPage().navigateToLoginPage();
        assertTrue("Login page should be displayed",
                testContext.getLoginPage().isLoginPageDisplayed());
    }

    @Given("I login with username {string} and password {string}")
    public void i_login_as_given_step(String username, String password) {
        testContext.getLoginPage().navigateToLoginPage();
        testContext.getLoginPage().login(username, password);
        testContext.setTestData(TestDataUtils.CURRENT_USER, username);

        // Verify successful login
        assertTrue("Should be redirected to products page after login",
                testContext.getProductsPage().isProductsPageDisplayed());
    }

    @Then("I should be redirected to the products page")
    public void i_should_be_redirected_to_the_products_page() {
        assertTrue("Should be redirected to products page",
                testContext.getProductsPage().isProductsPageDisplayed());
    }

    @Then("I should see the products page header {string}")
    public void i_should_see_the_products_page_header(String expectedHeader) {
        String actualHeader = testContext.getProductsPage().getPageHeader();
        assertEquals("Products page header should match", expectedHeader, actualHeader);
    }

    @Then("I should see an error message {string}")
    public void i_should_see_an_error_message(String expectedErrorMessage) {
        assertTrue("Error message should be displayed",
                testContext.getLoginPage().isErrorMessageDisplayed());

        String actualErrorMessage = testContext.getLoginPage().getErrorMessage();
        assertEquals("Error message should match", expectedErrorMessage, actualErrorMessage);
    }

    @Then("I should remain on the login page")
    public void i_should_remain_on_the_login_page() {
        assertTrue("Should remain on login page",
                testContext.getLoginPage().isLoginPageDisplayed());
    }

    @When("I enter username {string}")
    public void i_enter_username(String username) {
        testContext.getLoginPage().enterUsername(username);
    }

    @When("I enter password {string}")
    public void i_enter_password(String password) {
        testContext.getLoginPage().enterPassword(password);
    }

    @When("I click the login button")
    public void i_click_the_login_button() {
        testContext.getLoginPage().clickLoginButton();
    }

    @Then("I should see the login button")
    public void i_should_see_the_login_button() {
        assertTrue("Login button should be visible",
                testContext.getLoginPage().isLoginPageDisplayed());
    }

    @When("I clear the error message")
    public void i_clear_the_error_message() {
        testContext.getLoginPage().clearErrorMessage();
    }

    @Then("the error message should not be displayed")
    public void the_error_message_should_not_be_displayed() {
        assertFalse("Error message should not be displayed",
                testContext.getLoginPage().isErrorMessageDisplayed());
    }
}