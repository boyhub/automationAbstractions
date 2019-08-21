package uk.co.compendiumdev.examples.component;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import uk.co.compendiumdev.examples.pojo.TodoMVCPojoPage;
import uk.co.compendiumdev.selenium.support.webdriver.ExecutionDriver;
import uk.co.compendiumdev.todomvc.site.TodoMVCSite;

public class ComponentAbstractionTest {

    private WebDriver driver;
    private TodoMVCSite todoMVCSite;

    private TodoMVCPojoPage todoMVC;

    @Before
    public void setup(){
        driver = new ExecutionDriver().get();
        todoMVCSite = new TodoMVCSite();

        todoMVC = new TodoMVCPojoPage(driver, todoMVCSite.getURL());
        todoMVC.open();

        for(int todos=0; todos<5; todos++){
            todoMVC.typeIntoNewToDo("todo " + todos + Keys.ENTER);

            if (todos % 2 == 0) {
                // mark completed
                todoMVC.getTodoItems().get(todos).findElement(By.className("toggle")).click();
            }
        }
    }

    @Test
    public void canUseComponents(){

        final ItemsLeftCount itemsLeft = new ItemsLeftCount(driver);
        Assertions.assertEquals(2,itemsLeft.count());
        Assertions.assertEquals(5,countVisibleTodos());

        final FooterFilters filters = new FooterFilters(driver);
        filters.active();
        Assertions.assertEquals(2,countVisibleTodos());

        filters.completed();
        Assertions.assertEquals(3,countVisibleTodos());
    }

    // TODO:
    //      EXERCISE:
    //                refactor this method into a new component
    //                the component should be called VisibleTodoList
    //                use the component in the test
    public int countVisibleTodos(){
        return driver.findElements(By.cssSelector("ul.todo-list li:not(.hidden)")).size();
    }

    // TODO:
    //      EXERCISE:
    //                refactor the ComponentPojoPage page object
    //                to move the method editItem into the VisibleTodoList
    //                create an @Test method in this class which uses the editItem

    // TODO:
    //      EXERCISE:
    //                refactor the ComponentPojoPage page object
    //                to move the method deleteTodoItem into the VisibleTodoList
    //                create an @Test method in this class which uses the deleteTodoItem

    // TODO:
    //      QUESTION:
    //                should the ComponentPojoPage still have the methods
    //                editItem and deleteTodoItem?
    //      EXERCISE:
    //                If so, then re-implement them but use
    //                the VisibleTodoList in the methods.
    //                e.g. something like...
    //                public void editTodoItem(int item){
    //                      new VisibleTodoList(driver).editTodoItem(item);
    //                }

    // TODO:
    //      QUESTION:
    //                Should the ComponentPojoPage implement a filters()
    //                method which returns a FooterFilters object?
    //                  e.g. page.filters().active();
    //                Or should there be a new Footer Component?
    //                  e.g. page.footer().itemsLeftCount();
    //                  e.g. page.footer().clickActiveFilter();
    //      EXERCISE:
    //                Decide, and then implement.
    //                Or find a better way to model the footer.


    @After
    public void teardown(){
        ExecutionDriver.closeDriver(driver);
    }
}
