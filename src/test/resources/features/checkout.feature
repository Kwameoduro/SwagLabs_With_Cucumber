@swag_labs @e2e_flow @shopping_flow
Feature: Swag Labs Checkout
  As a customer
  I want to checkout my selected items
  So that I can complete a purchase

  Background:
    Given I am on the Swag Labs login page

  @critical @shopping_flow
  Scenario Outline: Complete shopping flow - Add item to cart and checkout
    Given I login with username "<username>" and password "<password>"
    And I am on the products page
    When I add "Sauce Labs Backpack" to cart
    And I add "Sauce Labs Bike Light" to cart
    Then the cart badge should show "2" items
    When I click on the shopping cart
    Then I should see "Sauce Labs Backpack" in the cart
    And I should see "Sauce Labs Bike Light" in the cart
    When I proceed to checkout
    And I fill in checkout information with valid dta:
      | firstName | lastName | postalCode |
      | Kofi      | Peter    | 0242       |
    And I continue to checkout overview
    Then I should see the checkout overview page
    And I should see "Sauce Labs Backpack" in the order summary
    And I should see "Sauce Labs Bike Light" in the order summary
    When I finish the order
    Then I should see the order confirmation page
    And I should see "Thank you for your order!" message

    Examples:
      | username      | password     |
      | standard_user | secret_sauce |
