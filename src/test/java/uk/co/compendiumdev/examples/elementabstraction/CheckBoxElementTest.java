package uk.co.compendiumdev.examples.elementabstraction;

import org.openqa.selenium.Keys;
import uk.co.compendiumdev.selenium.support.webdriver.ExecutionDriver;
import uk.co.compendiumdev.todomvc.site.TodoMVCSite;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CheckBoxElementTest {

    private WebDriver driver;
    private TodoMVCSite todoMVCSite;

    @Before
    public void setup(){
        driver = new ExecutionDriver().get();
        todoMVCSite = new TodoMVCSite();

        driver.get(todoMVCSite.getURL());
    }

    // NOTE: using local methods as DSL rather than Page object
    //       this is partly to keep the test separate from other tests
    //       but also to demonstrate this local abstraction concept.

    @Test
    public void canMarkAnItemCompleted(){
        createTodo("First Added Item");
        createTodo("Second Added Item");
        createTodo("Third Added Item");

        assertThat(getCountOfTodoItems(), is(3));
        assertThat(getCountOfCompletedItems(), is(0));

        // TODO:
        //       EXERCISE: the next 3 lines mean - mark Item Active
        //       - refactor the next 3 lines into a local markItemCompleted(int) method
        WebElement todoListItem1 = getCheckboxWebElementAt(1);
        CheckboxHTMLElement checkBox = new CheckboxHTMLElement(todoListItem1);
        checkBox.check();

        assertThat(getCountOfCompletedItems(), is(1));

        // Exercise: the next 3 lines mean - mark Item Active
        // - refactor the next 3 lines into a local markItemActive(int) method
        WebElement todoListItem = getCheckboxWebElementAt(1);
        checkBox = new CheckboxHTMLElement(todoListItem);
        checkBox.uncheck();

        assertThat(getCountOfCompletedItems(), is(0));
    }

    public void createTodo(CharSequence... keysToSend) {
        WebElement createTodo = driver.findElement(By.className("new-todo"));
        createTodo.click();
        createTodo.sendKeys(keysToSend);
        createTodo.sendKeys(Keys.ENTER);
    }

    private WebElement getCheckboxWebElementAt(int itemIndex) {
        return driver.findElements(By.cssSelector("input.toggle")).get(itemIndex);
    }

    private int getCountOfTodoItems(){
        return driver.findElements(By.cssSelector("ul.todo-list div.view")).size();
    }

    private int getCountOfCompletedItems(){
        return driver.findElements(By.cssSelector("ul.todo-list div.view input.toggle[checked]")).size();
    }

    // TODO:
    //       EXERCISE: Convert this test to use a page object

    // - optional - should the markItemActive be refactored into a page object?
    // - optional - should the markItemCompleted be refactored into a page object?
    // - optional - should the markItemCompleted/Active be refactored into a Todo object?

    // - question - should the CheckBoxHTMLElement implement a CheckBox interface?
    //   would that help? if it would help should we create that now or later?

    // - question - CheckboxHTMLElement implements WrapsElement
    //   does that help us? are there any risks to doing that?

    @After
    public void teardown(){

        try {
            driver.close();
            driver.quit();
        }catch(Exception e){

        }
    }
}
