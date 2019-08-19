package uk.co.compendiumdev.todomvc.element;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.co.compendiumdev.selenium.support.webdriver.ExecutionDriver;
import uk.co.compendiumdev.todomvc.elementabstractions.Checkbox;
import uk.co.compendiumdev.todomvc.elementabstractions.CheckboxElement;
import uk.co.compendiumdev.todomvc.page.functionalvsstructural.ApplicationPageFunctional;
import uk.co.compendiumdev.todomvc.page.structural.pojo.ApplicationPageStructural;
import uk.co.compendiumdev.todomvc.site.TodoMVCSite;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static uk.co.compendiumdev.todomvc.page.structural.pojo.StructuralEnums.ItemsInState.VISIBLE;

public class ElementWrapperTest {

    private WebDriver driver;
    private TodoMVCSite todoMVCSite;

    private ApplicationPageFunctional todoMVC;

    @Before
    public void setup(){
        driver = new ExecutionDriver().get();
        todoMVCSite = new TodoMVCSite();

        todoMVC = new ApplicationPageFunctional(driver, todoMVCSite);
        todoMVC.open();
    }

    @Test
    public void canMarkAnItemCompleted(){
        todoMVC.enterNewToDo("First Added Item");
        todoMVC.enterNewToDo("Second Added Item");
        todoMVC.enterNewToDo("Third Added Item");

        assertThat(todoMVC.getCountOfTodoDoItems(), is(3));

        markItemCompleted(todoMVC.getCountOfTodoDoItems()-1);

        assertThat(todoMVC.getCountOfTodoDoItems(), is(3));
        assertThat(todoMVC.getCountOfCompletedTodoDoItems(), is(1));
        assertThat(todoMVC.getCountOfActiveTodoDoItems(), is(2));

        todoMVC.filterOnCompleted();

        assertThat(todoMVC.getCountOfTodoDoItems(), is(1));

        markItemActive(todoMVC.getCountOfTodoDoItems() - 1);

        assertThat(todoMVC.getCountOfTodoDoItems(), is(0));

        todoMVC.filterOnActive();
        assertThat(todoMVC.getCountOfTodoDoItems(), is(3));
        assertThat(todoMVC.getCountOfCompletedTodoDoItems(), is(0));
        assertThat(todoMVC.getCountOfActiveTodoDoItems(), is(3));
    }

    private void markItemActive(int itemIndex) {
        WebElement todoListItem = getWebElementAt(itemIndex);
        Checkbox checkBox = new CheckboxElement(todoListItem);

        checkBox.uncheck();
    }

    public void markItemCompleted(int itemIndex) {

        WebElement todoListItem = getWebElementAt(itemIndex);

        Checkbox checkBox = new CheckboxElement(todoListItem);

        checkBox.check();
    }

    private WebElement getWebElementAt(int itemIndex) {
        ApplicationPageStructural page = new ApplicationPageStructural(driver, todoMVCSite);

        List<WebElement> items = page.getTodoItems(VISIBLE);
        WebElement todoListItem = items.get(itemIndex);
        new WebDriverWait(driver,10).until(ExpectedConditions.elementToBeClickable(todoListItem));
        return todoListItem.findElement(By.cssSelector("input.toggle"));
    }


    @After
    public void teardown(){

        driver.close();
        driver.quit();
    }
}
