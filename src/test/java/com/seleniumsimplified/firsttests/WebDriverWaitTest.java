package com.seleniumsimplified.firsttests;


import com.seleniumsimplified.selenium.support.webdriver.ExecutionDriver;
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

public class WebDriverWaitTest {

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

    @After
    public void stopDriver(){
        driver.close();
        driver.quit();
    }


    @Test
    public void implicitWaitExample(){

        driver.manage().timeouts().implicitlyWait(15L, TimeUnit.SECONDS);

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
    }

    @Test
    public void explicitWaitExample(){

        driver.manage().timeouts().implicitlyWait(0L, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver,15);

        driver.get(siteURL);

        int originalNumberOfTodos = driver.findElements(
                By.cssSelector("ul.todo-list li")).size();

        WebElement createTodo = driver.findElement(By.className("new-todo"));
        createTodo.click();
        createTodo.sendKeys("new task");
        createTodo.sendKeys(Keys.ENTER);

        wait.until(ExpectedConditions.elementToBeClickable(By.className("filters")));

        int newToDos = driver.findElements(
                By.cssSelector("ul.todo-list li")).size();

        assertThat(newToDos, greaterThan(originalNumberOfTodos));
    }

}
