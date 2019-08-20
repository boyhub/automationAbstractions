package uk.co.compendiumdev.examples.drivercontrol;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import uk.co.compendiumdev.selenium.support.webdriver.ExecutionDriver;
import uk.co.compendiumdev.todomvc.site.TodoMVCSite;

public class DriverManagerTest {

    /*
        An early abstraction that we often add is the Driver Manager

        This helps us avoid coding into our test:

        ~~~~~~~~
        WebDriver driver = new FirefoxDriver();
        ~~~~~~~~

        Which can then make it hard to run the tests on different browsers.

        We delegate initiation of the correct driver to the Driver Manager.

        People often use a base test class to control this type of behaviour.

        I tend not to use a base test class e.g. `extends BaseWebDriverTest`
        because this makes my code feel more like a framework than a library
        and I value flexibility.
     */

    @Test
    public void canUseADefaultBrowser(){

        ExecutionDriver manager = new ExecutionDriver();

        // TODO:
        //       EXERCISE : change the code in the ExecutionDriver to open Firefox instead of Chrome
        //                  the driver is created in the `getUncached` method
        WebDriver driver = manager.get();

        final TodoMVCSite todoMVCSite = new TodoMVCSite();

        driver.get(todoMVCSite.getURL());

        Assertions.assertTrue(driver.getTitle().contains("TodoMVC"));

        manager.close();
    }

    /*
        Driver Manager classes can become very complicated.

        I tend not to make them generic, instead I build a Driver Manager specific to the needs
        of my project. Otherwise there is a tendency to make them do everything.

        Because they could:

        - manage multiple browsers,
        - share browsers but support a unique browser per Test
            - this requires using Thread identifiers when tests are run in parallel
        - configure themselves from System Properties e.g. JVM -D parameters
        - configure themselves from Environment Variables
        - be configured dynamically e.g. manager.setDefault("Firefox")

        Here is an example of an older 'generic' driver manager that I started creating.

        https://github.com/eviltester/selenium-driver-manager

        It hasn't been maintained for a while but you can see how complicated it can become.

     */
}
