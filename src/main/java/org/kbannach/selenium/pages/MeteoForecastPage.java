package org.kbannach.selenium.pages;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.kbannach.data.scraper.City;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.net.URL;

@Component
@RequiredArgsConstructor
public class MeteoForecastPage {

    private static final String METEOROGRAM_IMG_ELEMENT_ID = "meteorogram";
    private static final String METEOROGRAM_IMAGE_FORMAT_NAME = "png";

    // TODO automate opening different cities(?) (cities list could be fetched from DB)
    public byte[] readMeteogram(City city) {
        WebDriverManager.chromedriver().setup();
        ChromeDriver driver = new ChromeDriver(); // TODO abstract

        try {
            return read(driver, city.getMeteorogramUrl());
        } finally {
            driver.quit();
        }
    }

    @SneakyThrows // TODO throw dedicated exception
    private byte[] read(ChromeDriver driver, String meteorogramUrl) {
        driver.get(meteorogramUrl);
        WebElement meteorogramElement = driver.findElement(By.id(METEOROGRAM_IMG_ELEMENT_ID));

        String imgSrcUrl = meteorogramElement.getAttribute("src");
        BufferedImage image = ImageIO.read(new URL(imgSrcUrl));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, METEOROGRAM_IMAGE_FORMAT_NAME, outputStream);

        return outputStream.toByteArray();
    }
}
