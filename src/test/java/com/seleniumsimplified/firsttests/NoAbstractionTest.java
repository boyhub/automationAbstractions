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
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

public class NoAbstractionTest {

    @Test
    public void canCreateAToDo(){

        // a true test with no abstraction would use the driver directly
        // WebDriver driver = new ChromeDriver();
        WebDriver driver = new ExecutionDriver().get();

        // admittedly the line below is an abstraction but it allows me to
        // run the tests locally more easily.
        // String siteURL = "http://todomvc.com/examples/backbone/"
        String siteURL = new TodoMVCSite().getURL();

        driver.get(siteURL);

        int originalNumberOfTodos = driver.findElements(
                                        By.cssSelector("ul.todo-list li")).size();

        WebElement createTodo = driver.findElement(By.className("new-todo"));
        createTodo.click();
        createTodo.sendKeys("new task");
        createTodo.sendKeys(Keys.ENTER);

        assertThat(driver.findElement(
                        By.className("filters")).isDisplayed(), is(true));

        int newToDos = driver.findElements(
                                By.cssSelector("ul.todo-list li")).size();

        assertThat(newToDos, greaterThan(originalNumberOfTodos));

        driver.close();
        driver.quit();
    }
}
