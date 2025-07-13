# DemoWebShop Automation Testing Project
This is a personal test automation project built to demonstrate my skills in testing a sample e-commerce application – Demo Web Shop. The project covers both positive and negative test cases using a modular, scalable, and maintainable structure.

## Tools & Technologies Used
Java

Selenium WebDriver

TestNG

Maven

ExtentReports

Page Object Model (POM)

Log4j

DataProvider

RetryAnalyzer

Custom Utils

External properties files (for messages and test data)

## What’s Tested?
### Positive Test Scenarios
Successful registration

Login with valid credentials

Add product to cart after login

Full shopping flow after registration

Full shopping flow after login

### Negative Test Scenarios
Registration with missing fields (first name, last name, email, password, confirm password)

Login with invalid or empty credentials

## Project Structure
base/ – base test and page classes, browser setup

pages/ – Page Object Model classes

tests/ – test scenarios (split into positive/negative packages)

utils/ – common helpers (e.g., email generator, retry logic)

helpers/ – additional logic (e.g., logout flow)

resources/ – config and test data .properties files

## Reporting
Integrated with ExtentReports

Detailed logs and test statuses

Future plans to integrate Allure for advanced visual reporting

## How to Run the Tests
Open the project in your IDE

Make sure chromedriver is configured in your system PATH

Use testng.xml to run the full test suite

Or execute with Maven: mvn clean test
### Personal Note
This project represents my understanding of real-world test automation structure. I built it from scratch, made mistakes, debugged through failures (yes, even misidentified bugs 😄), and learned a lot. It’s far from final – I’ll keep improving it as I grow.

If you’re reviewing this as part of my portfolio – welcome! I’m happy to show what I know and I’m even more excited about what’s next.
