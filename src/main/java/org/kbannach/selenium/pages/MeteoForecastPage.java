package org.kbannach.selenium.pages;

import lombok.RequiredArgsConstructor;
import org.kbannach.data.scraper.City;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MeteoForecastPage implements MeteoForecastReader {

    private static final String METEOROGRAM_IMG_ELEMENT_ID = "meteorogram";

    private final WebDriverFactory webDriverFactory;
    private final ImageReader imageReader;

    // TODO automate opening different cities(?) (cities list could be fetched from DB)
    public byte[] readMeteogram(City city) {
        WebDriver driver = webDriverFactory.get();
        try {
            return read(driver, city.getMeteorogramUrl());
        } finally {
            driver.quit();
        }
    }

    private byte[] read(WebDriver driver, String meteorogramUrl) {
        driver.get(meteorogramUrl);
        WebElement meteorogramElement = driver.findElement(By.id(METEOROGRAM_IMG_ELEMENT_ID));
        String imgSrcUrl = meteorogramElement.getAttribute("src");
        return imageReader.readFromUrl(imgSrcUrl);
    }
}
