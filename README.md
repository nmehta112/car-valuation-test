Car Valuation Test Project
This project automates the process of testing the car valuation feature on the website We Buy Any Car using Selenium WebDriver, TestNG, and Maven. It reads car registration numbers from an input file, performs a search on the website, and validates the valuation against expected results stored in an output file.

This project automates car valuation tests on We Buy Any Car using Selenium WebDriver and TestNG.

Setup
Clone the repo:

bash
Copy code
git clone https://github.com/nmehta112/car-valuation-tests.git
Install dependencies:

bash
Copy code
mvn install
Download WebDriver: Place chromedriver.exe in the drivers/ directory (or use WebDriverManager).

Prepare input/output files:

car_input.txt: Contains car registration numbers.
car_output.txt: Contains expected valuations.
Run Tests
Run the tests with Maven:

bash
Copy code
mvn clean test

Files
CarValuationPage.java: Page Object Model for interacting with the website.
CarValuationTest.java: Test case logic.
car_input.txt: Input data (car registration numbers).
car_output.txt: Expected valuations.
License
MIT License.
