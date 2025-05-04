
@tag
Feature: Purchase order from Ecommerce Website
  I want to use this template for my feature file

Background:
Given I landed on Ecommerce Page

  @Regression
  Scenario Outline: Positive test of submitting the order
    Given Logged in with username <name> and password <password>
    When I add product <productName> to cart
    And Checkout <productName> and submit the order
    Then "THANKYOU FOR THE ORDER." message is displayed on ConfirmationPage

    Examples: 
      | name		  				| password 	| productName |
      | ShikhaD@gmail.com | Idiot_123 | ZARA COAT 3 |
