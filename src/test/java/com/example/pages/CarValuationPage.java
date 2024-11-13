package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CarValuationPage {

    private WebDriver driver;

    private By searchBox = By.name("car_reg_input");
    private By searchButton = By.xpath("//button[@type='submit']");
    private By valuationElement = By.xpath("//div[@class='valuation']");

    public CarValuationPage(WebDriver driver) {
        this.driver = driver;
    }

    public void searchCarValuation(String registrationNumber) {
        WebElement searchTextBoxElement = driver.findElement(searchBox);
        searchTextBoxElement.sendKeys(registrationNumber);
        WebElement searchButtonElement = driver.findElement(searchButton);
        searchButtonElement.click();
    }

    public String getCarValuation() {
        return driver.findElement(this.valuationElement).getText();
    }
}