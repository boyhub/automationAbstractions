package uk.co.compendiumdev.selenium.support.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

/**
 * Class to abstract the creation of the driver to make it easier to
 * configure and switch between browsers.
 */
public class ExecutionDriver {

    private WebDriver driver;

    /**
        Create a 'managed driver' i.e.

        - cached
        - closeable by the class

     */
    public WebDriver get() {

        if(driver==null){
            driver = getUncached();
        }

        return driver;
    }

    /**
        Close the cached and managed driver
     */
    public void close() {
        if(driver!=null){
            ExecutionDriver.closeDriver(driver);
        }
    }

    /**
        Allow creation of a new unmanaged driver that the
        test has to manage the closing of.

        This is not cached or remembered in any way.
     */
    public WebDriver getUncached(){

        return new ChromeDriver();
        //return new FirefoxDriver();
        //return new SafariDriver();
    }

    /*
        A convenience method to make it easier to close
        unmanaged drivers without caring if they need
        one or both close/quit methods
     */
    public static void closeDriver(WebDriver adriver){

        try {
            adriver.close();
            adriver.quit();
        }catch(Exception e){

        }
    }


}
