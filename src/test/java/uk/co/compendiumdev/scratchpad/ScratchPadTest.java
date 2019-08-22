package uk.co.compendiumdev.scratchpad;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import uk.co.compendiumdev.examples.navigation.ActiveToDosPage;
import uk.co.compendiumdev.examples.navigation.AllToDosPage;
import uk.co.compendiumdev.examples.navigation.CompletedToDosPage;
import uk.co.compendiumdev.examples.navigation.TodoMVCPojoPage;
import uk.co.compendiumdev.selenium.support.webdriver.ExecutionDriver;
import uk.co.compendiumdev.todomvc.site.TodoMVCSite;

public class ScratchPadTest {

    private WebDriver driver;
    private TodoMVCSite todoMVCSite;

    private TodoMVCPojoPage todoMVC;

    @Before
    public void setup(){
        driver = new ExecutionDriver().get();
        todoMVCSite = new TodoMVCSite();

        //todoMVC = new ApplicationPage(driver, todoMVCSite);
        todoMVC = new TodoMVCPojoPage(driver, todoMVCSite.getURL());
        todoMVC.open();

     }

     // a place to experiment
    @Test
    public void experimentWithSomething(){


    }

    @After
    public void teardown(){
        ExecutionDriver.closeDriver(driver);
    }


}
