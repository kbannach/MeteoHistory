package org.kbannach.selenium.pages;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class WebDriverFactory {

    WebDriver get() {
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver();
    }
}
