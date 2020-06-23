package org.kbannach.selenium.pages;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kbannach.city.City;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.util.AbstractMap.SimpleEntry;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class MeteoForecastPage implements MeteoForecastReader {

    private static final String METEOROGRAM_IMG_ELEMENT_ID = "meteorogram";

    private final WebDriverFactory webDriverFactory;
    private final ImageReader imageReader;

    @Override
    public Map<City, byte[]> readAllMeteograms(Collection<City> cities) {
        WebDriver driver = webDriverFactory.get();
        try {
            return cities.stream()
                    .map(c -> readForCity(driver, c))
                    .flatMap(Optional::stream)
                    .collect(Collectors.toMap(
                            Entry::getKey,
                            Entry::getValue
                    ));
        } finally {
            driver.quit();
        }
    }

    private Optional<Entry<City, byte[]>> readForCity(WebDriver driver, City city) {
        try {
            byte[] meteorogramBytes = read(driver, city.getMeteorogramUrl());
            return Optional.of(new SimpleEntry<>(city, meteorogramBytes));
        } catch (Exception e) {
            log.info("exception during meteorogram image reading", e);
            return Optional.empty();
        }
    }

    private byte[] read(WebDriver driver, String meteorogramUrl) {
        driver.get(meteorogramUrl);
        WebElement meteorogramElement = driver.findElement(By.id(METEOROGRAM_IMG_ELEMENT_ID));
        String imgSrcUrl = meteorogramElement.getAttribute("src");
        return imageReader.readFromUrl(imgSrcUrl);
    }
}
