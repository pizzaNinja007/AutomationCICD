@tag
Feature: Purchase Order from an Ecommerce Site
	I want to use this template for my feature file
	
	Background: 
	Given I landed on Ecommerce Page

	
	@tag2
	Scenario Outline: Positive test for submitting the order
	Given Logged in with username <name> and password <password> 
	When  I add product <productName> to cart
	And   Checkout <productName> and submit the order
	Then  "THANKYOU FOR THE ORDER." message is displayed on ConfirmationPage
	
	Examples:
	|name		  |password |productName |
	|test@anay.com|Abcd1234!|ZARA COAT 3 | 

