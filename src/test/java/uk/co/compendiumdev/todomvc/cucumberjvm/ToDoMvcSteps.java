package uk.co.compendiumdev.todomvc.cucumberjvm;

import uk.co.compendiumdev.selenium.support.webdriver.ExecutionDriver;
import uk.co.compendiumdev.todomvc.domain.actors.TodoMVCUser;
import uk.co.compendiumdev.todomvc.site.TodoMVCSite;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ToDoMvcSteps {

    private WebDriver driver;
    private TodoMVCSite todoMVCSite;
    private TodoMVCUser user;

    @Given("^a user opens a blank ToDoMVC page$")
    public void a_user_opens_a_blank_ToDoMVC_page() throws Throwable {

        driver = new ExecutionDriver().get();
        todoMVCSite = new TodoMVCSite();
        user = new TodoMVCUser(driver, todoMVCSite);

        user.opensApplication();
    }

    @When("^the user creates a todo \"([^\"]*)\"$")
    public void the_user_creates_a_todo(String todoName) throws Throwable {
        user.createNewToDo(todoName);
    }


    @Then("^they see (\\d+) todo item on the page$")
    public void they_see_todo_item_on_the_page(int expectedToDoCount) throws Throwable {
        assertThat(user.getTodoList().size(), is(expectedToDoCount));
    }


    @After
    public void closeBrowser(){
        driver.close();
        driver.quit();
    }
}
