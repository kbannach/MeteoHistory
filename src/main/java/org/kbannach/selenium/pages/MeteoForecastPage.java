package org.kbannach.selenium.pages;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kbannach.city.City;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class MeteoForecastPage implements MeteoForecastReader {

    private static final String METEOROGRAM_IMG_ELEMENT_ID = "meteorogram";

    private final WebDriverFactory webDriverFactory;
    private final ImageReader imageReader;

    public Optional<byte[]> readMeteogram(City city) {
        WebDriver driver = webDriverFactory.get();
        try {
            return Optional.ofNullable(read(driver, city.getMeteorogramUrl()));
        } catch (Exception e) {
            log.info("exception during meteorogram image reading", e);
            return Optional.empty();
        } finally {
            // TODO kbannach quit after every city finishes
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
