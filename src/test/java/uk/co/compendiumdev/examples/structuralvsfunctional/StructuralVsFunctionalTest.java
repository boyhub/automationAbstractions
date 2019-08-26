package uk.co.compendiumdev.examples.structuralvsfunctional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import uk.co.compendiumdev.examples.synchronisedcomponent.SynchronisedComponentPojoPage;
import uk.co.compendiumdev.selenium.support.webdriver.ExecutionDriver;
import uk.co.compendiumdev.todomvc.site.TodoMVCSite;

public class StructuralVsFunctionalTest {

    /*
        A structural page represents the physical structure.

        It might be as simple as something which is responsible for
        managing all the selectors.

        It might also be responsible for doing stuff.

        We might choose to split it into two:

        - StructuralPageLocators - manages all the By locators
        - StructuralPage - performs actions e.g. typeIntoCreateTodo, getTodoElement,
                                                 hoverOverTodo, clickVisibleDeleteButton

        Where the Structural Page, uses the Structural Page Selectors.


        A Functional Page would model the functions that we use the page to implement
        e.g. createTodo, countNumberOfTodos, deleteTodo etc.

        A Structural page would throw exceptions, but a functional page would most likely
        trap them - particularly if the function was a boolean e.g. isFooterVisible

     */

    private WebDriver driver;
    private TodoMVCSite todoMVCSite;

    @Before
    public void setup(){
        driver = new ExecutionDriver().get();
        todoMVCSite = new TodoMVCSite();

    }

    @Test
    public void canUseFunctionalPage(){

        final ApplicationPageFunctionalExample page =
                    new ApplicationPageFunctionalExample(driver, todoMVCSite);

        page.open();

        Assert.assertFalse(page.isFooterVisible());
        Assert.assertEquals(0, page.getCountInFooter());
        Assert.assertEquals(0, page.getCountOfTodoDoItems());

        page.enterNewToDo("My Todo 1");
        page.enterNewToDo("My Todo 2");
        page.enterNewToDo("My Todo 3");
        page.enterNewToDo("My Todo 4");
        page.enterNewToDo("My Todo 5");
        page.enterNewToDo("My Todo 6");

        Assert.assertTrue(page.isFooterVisible());
        Assert.assertEquals(6, page.getCountInFooter());
        Assert.assertEquals(6, page.getCountOfTodoDoItems());

        // duplicate mark to do completed makes no difference
        page.markTodoCompleted(1);
        page.markTodoCompleted(1);
        page.markTodoCompleted(2);

        page.filterOnActive();
        Assert.assertEquals(4, page.getCountInFooter());
        Assert.assertEquals(4, page.getCountOfTodoDoItems());

        page.filterOnCompleted();
        Assert.assertEquals(4, page.getCountInFooter());
        Assert.assertEquals(2, page.getCountOfTodoDoItems());

        page.markTodoCompleted(1);
        Assert.assertEquals(4, page.getCountInFooter());
        Assert.assertEquals(2, page.getCountOfTodoDoItems());

        page.markTodoActive(1);
        Assert.assertEquals(5, page.getCountInFooter());
        Assert.assertEquals(1, page.getCountOfTodoDoItems());
    }

    @Test
    public void canUseStructuralPage(){

        final ApplicationPageStructuralExample page =
                new ApplicationPageStructuralExample(driver, todoMVCSite);

        page.open();

        try {
            Assert.assertFalse(page.isFooterVisible());
            Assert.assertEquals(0, page.getCountInFooter());
            Assert.fail("Footer was not supposed to exist");
        }catch(NoSuchElementException e){
            // there is no footer so the structural page call fails
        }
        Assert.assertEquals(0, page.getVisibleTodos().size());

        page.typeIntoNewToDo("My Todo 1", Keys.ENTER);
        page.typeIntoNewToDo("My Todo 2", Keys.ENTER);
        page.typeIntoNewToDo("My Todo 3", Keys.ENTER);
        page.typeIntoNewToDo("My Todo 4", Keys.ENTER);
        page.typeIntoNewToDo("My Todo 5", Keys.ENTER);
        page.typeIntoNewToDo("My Todo 6", Keys.ENTER);

        Assert.assertTrue(page.isFooterVisible());
        Assert.assertEquals(6, page.getCountInFooter());
        Assert.assertEquals(6, page.getVisibleTodos().size());

        // duplicate toggle here would make a difference as it would
        // move to completed and then back to active
        //page.clickStateOfItem(1);
        page.clickStateOfItem(1);
        page.clickStateOfItem(2);

        page.clickOnFilter(1);
        Assert.assertEquals(4, page.getCountInFooter());
        Assert.assertEquals(4, page.getVisibleTodos().size());

        page.clickOnFilter(2);
        Assert.assertEquals(4, page.getCountInFooter());
        Assert.assertEquals(2, page.getVisibleTodos().size());

        // Can't mark it as completed because it is already completed
        // page.clickStateOfItem(1);
        // Assert.assertEquals(4, page.getCountInFooter());
        // Assert.assertEquals(2, page.getVisibleTodos().size());

        page.clickStateOfItem(1);
        Assert.assertEquals(5, page.getCountInFooter());
        Assert.assertEquals(1, page.getVisibleTodos().size());
    }


    @After
    public void teardown(){
        ExecutionDriver.closeDriver(driver);
    }
}
