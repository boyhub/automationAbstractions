package uk.co.compendiumdev.examples.firsttests;


import uk.co.compendiumdev.examples.domain.actors.TodoMVCUser;
import uk.co.compendiumdev.selenium.support.webdriver.ExecutionDriver;
import uk.co.compendiumdev.todomvc.page.functionalvsstructural.ApplicationPageFunctional;
import uk.co.compendiumdev.todomvc.site.TodoMVCSite;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class WithAbstractionTest {

    private WebDriver driver;
    String siteURL;

    @Before
    public void startDriver(){
        driver = new ExecutionDriver().get();
        siteURL = new TodoMVCSite().getURL();
    }

    @Test
    public void canCreateAToDo(){
        TodoMVCUser user = new TodoMVCUser(driver, new TodoMVCSite());

        user.opensApplication().and().createNewToDo("new task");

        ApplicationPageFunctional page =
                new ApplicationPageFunctional(driver, new TodoMVCSite());

        assertThat(page.getCountOfTodoDoItems(), is(1));
        assertThat(page.isFooterVisible(), is(true));
    }

    @After
    public void stopDriver(){
        ExecutionDriver.closeDriver(driver);
    }
}
