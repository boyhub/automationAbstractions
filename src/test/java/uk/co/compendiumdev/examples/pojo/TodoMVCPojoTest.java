package uk.co.compendiumdev.examples.pojo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import uk.co.compendiumdev.selenium.support.webdriver.ExecutionDriver;
import uk.co.compendiumdev.todomvc.domain.objects.ToDoItem;
import uk.co.compendiumdev.todomvc.site.TodoMVCSite;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

/**
 * This is a basic example of a test that uses a very simple page object
 */
public class TodoMVCPojoTest {

    private WebDriver driver;
    private TodoMVCSite todoMVCSite;

    private TodoMVCPojoPage todoMVC;

    @Before
    public void setup(){
        driver = new ExecutionDriver().get();
        todoMVCSite = new TodoMVCSite();

        todoMVC = new TodoMVCPojoPage(driver, todoMVCSite.getURL());
        todoMVC.open();
    }

    // TODO: convert to use JUnit asserts not hamcrest to keep cognitive load lower

    @Test
    public void canCreateAToDo(){

        ToDoItem todo = new ToDoItem("New Task");

        // Question: should the page object have a count todos method?
        //           rather than return a list of WebElements?
        int originalNumberOfTodos = todoMVC.getTodoItems().size();

        // Question: should the ENTER be handled by the page object?
        // Question: should the page object have a createTodo method?
        todoMVC.typeIntoNewToDo("New Task" + Keys.ENTER);

        int newToDos = todoMVC.getTodoItems().size();

        assertThat(newToDos, greaterThan(originalNumberOfTodos));
        assertThat(newToDos, is(originalNumberOfTodos + 1));

        // Question: Should getToDoText be zero indexed?
        //           Does that make the test easier to understand?
        assertThat(todo.getText(), is(todoMVC.getToDoText(newToDos-1)));
    }

    @Test
    public void canDeleteATodo(){

        int originalNumberOfTodos = todoMVC.getTodoItems().size();

        todoMVC.typeIntoNewToDo("Delete Me" + Keys.ENTER);

        int addedATodoCount = todoMVC.getTodoItems().size();

        assertThat(addedATodoCount, is(originalNumberOfTodos + 1));

        // Question: Should delete be zero indexed?
        //           Is that how people think?
        //           Does that make the test easier to understand?
        todoMVC.deleteTodoItem(addedATodoCount-1);

        int afterDeleteCount = todoMVC.getTodoItems().size();
        assertThat(afterDeleteCount, is(originalNumberOfTodos));
    }

    @Test
    public void canEditATodo(){

        int originalNumberOfTodos = todoMVC.getTodoItems().size();

        todoMVC.typeIntoNewToDo("Edit Me" + Keys.ENTER);

        int addedATodoCount = todoMVC.getTodoItems().size();

        assertThat(addedATodoCount, is(originalNumberOfTodos + 1));

        // Question: should editItem be zero indexed?
        todoMVC.editItem(addedATodoCount-1, "Edited Todo");

        int afterEditCount = todoMVC.getTodoItems().size();
        assertThat(afterEditCount, is(addedATodoCount));

        assertThat(todoMVC.getToDoText(afterEditCount-1), is("Edited Todo"));
    }

    // TODO: trim everything else out of the page object and add as exercises



    @After
    public void teardown(){
        ExecutionDriver.closeDriver(driver);
    }
}
