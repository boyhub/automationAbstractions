package uk.co.compendiumdev.examples.synchronisedcomponent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import uk.co.compendiumdev.examples.component.FooterFilters;
import uk.co.compendiumdev.examples.component.ItemsLeftCount;
import uk.co.compendiumdev.examples.pojo.TodoMVCPojoPage;
import uk.co.compendiumdev.selenium.support.webdriver.ExecutionDriver;
import uk.co.compendiumdev.todomvc.site.TodoMVCSite;

public class SynchronisedComponentAbstractionTest {

    private WebDriver driver;
    private TodoMVCSite todoMVCSite;

    private SynchronisedComponentPojoPage todoMVC;

    @Before
    public void setup(){
        driver = new ExecutionDriver().get();
        todoMVCSite = new TodoMVCSite();

        todoMVC = new SynchronisedComponentPojoPage(driver, todoMVCSite.getURL());
        todoMVC.open();

    }

    @Test
    public void canUseSynchronisedComponents(){

        todoMVC.typeIntoNewToDo("Edit Me" + Keys.ENTER);

        todoMVC.editItem(0, "Edited Todo");

        Assertions.assertEquals("Edited Todo", todoMVC.getToDoText(0));
    }


    @After
    public void teardown(){
        ExecutionDriver.closeDriver(driver);
    }
}
