package uk.co.compendiumdev.selenium.support.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Class to abstract the creation of the driver to make it easier to
 * configure and switch between browsers.
 */
public class ExecutionDriver {

    private WebDriver driver;

    public WebDriver get() {

        return new ChromeDriver();
    }

}
