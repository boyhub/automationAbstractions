package com.seleniumsimplified.firsttests;


import com.seleniumsimplified.selenium.support.webdriver.ExecutionDriver;
import com.seleniumsimplified.todomvc.domain.actors.TodoMVCUser;
import com.seleniumsimplified.todomvc.page.functionalvsstructural.ApplicationPageFunctional;
import com.seleniumsimplified.todomvc.site.TodoMVCSite;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

public class WithAbstractionTest {

    private WebDriver driver;
    String siteURL;

    @Before
    public void startDriver(){
        // a true test with no abstraction would use the driver directly
        // driver = new FirefoxDriver();
        driver = new ExecutionDriver().get();

        // admittedly the line below is an abstraction but it allows me to
        // run the tests locally more easily. It is the only abstraction
        // used in this test class though
        siteURL = new TodoMVCSite().getURL();

        //siteURL = "http://todomvc.com/architecture-examples/backbone/";
    }

    @Test
    public void canCreateAToDo(){
        WebDriver driver = new ExecutionDriver().get();
        TodoMVCUser user = new TodoMVCUser(driver, new TodoMVCSite());

        user.opensApplication().and().createNewToDo("new task");

        ApplicationPageFunctional page =
                new ApplicationPageFunctional(driver, new TodoMVCSite());

        assertThat(page.getCountOfTodoDoItems(), is(1));
        assertThat(page.isFooterVisible(), is(true));

        driver.close();
        driver.quit();
    }

    @After
    public void stopDriver(){
        driver.close();
        driver.quit();
    }

}
