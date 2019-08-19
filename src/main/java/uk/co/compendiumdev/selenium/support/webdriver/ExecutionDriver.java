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

        if(driver==null){
            driver = getUncached();
        }

        return driver;
    }

    private WebDriver getUncached(){
        return new ChromeDriver();
    }

    public static void closeDriver(WebDriver adriver){

        try {
            adriver.close();
            adriver.quit();
        }catch(Exception e){

        }
    }

}
