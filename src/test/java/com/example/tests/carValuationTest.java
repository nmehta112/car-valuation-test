import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CarValuationTest {

    private static final String INPUT_FILE = "car_input.txt";
    private static final String OUTPUT_FILE = "car_output.txt";
    private static final String WEBSITE_URL = "https://www.webuyanycar.com";
    private static final String REGISTRATION_PATTERN = "[A-Z]{2}\\d{2}[A-Z]{3}";

    private WebDriver driver;
    private static final Logger logger = Logger.getLogger(CarValuationTest.class.getName());

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\mehtan1\\car-valuation-test\\drivers\\chromedriver.exe");
        
        driver = new ChromeDriver();
        logger.info("WebDriver initialized.");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            logger.info("WebDriver closed.");
        }
    }

    @Test
    public void testCarValuation() throws IOException {
        List<String> registrationNumbers = getRegistrationNumbersFromFile(INPUT_FILE);
        for (String registrationNumber : registrationNumbers) {
            searchAndValidate(driver, registrationNumber);
        }
    }

    private List<String> getRegistrationNumbersFromFile(String inputFile) throws IOException {
        List<String> registrationNumbers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");
                String registrationNumber = columns[0].trim();
                if (registrationNumber.matches(REGISTRATION_PATTERN)) {
                    registrationNumbers.add(registrationNumber);
                    logger.info("Extracted registration number: " + registrationNumber);
                }
            }
        }
        return registrationNumbers;
    }

    private void searchAndValidate(WebDriver driver, String registrationNumber) {
        driver.get(WEBSITE_URL);

        WebElement searchBox = driver.findElement(By.name("car_reg_input"));
        searchBox.sendKeys(registrationNumber);
        logger.info("Searching valuation for registration: " + registrationNumber);

        WebElement searchButton = driver.findElement(By.xpath("//button[@type='submit']"));
        searchButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement valuationElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='valuation']")));

        String actualValuation = valuationElement.getText();
        String expectedValuation = getExpectedValuation(registrationNumber);

        if (expectedValuation.isEmpty()) {
            logger.warning("Expected valuation not found for registration: " + registrationNumber);
        } else if (!actualValuation.equals(expectedValuation)) {
            logger.warning("Mismatch for registration " + registrationNumber + ": expected " + expectedValuation + ", got " + actualValuation);
        } else {
            logger.info("Valuation matched for registration " + registrationNumber + ": " + actualValuation);
        }
    }

    private String getExpectedValuation(String registrationNumber) {
        try (BufferedReader reader = new BufferedReader(new FileReader(OUTPUT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(registrationNumber)) {
                    String valuation = line.split(":")[1].trim();
                    logger.info("Expected valuation retrieved for " + registrationNumber + ": " + valuation);
                    return valuation;
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error reading expected valuations from file", e);
        }
        return "";
    }
}