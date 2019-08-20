package uk.co.compendiumdev.examples.pojo;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.co.compendiumdev.selenium.support.webelement.EnsureWebElementIs;
import uk.co.compendiumdev.todomvc.page.structural.pojo.StructuralApplicationPage;
import uk.co.compendiumdev.todomvc.page.structural.pojo.StructuralEnums.Filter;
import uk.co.compendiumdev.todomvc.page.structural.pojo.StructuralEnums.ItemsInState;
import uk.co.compendiumdev.todomvc.site.TodoMVCSite;

import java.awt.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TodoMVCPojoPage {

    private static final By CLEAR_COMPLETED = By.id("clear-completed");

    private final WebDriver driver;
    private final String url;
    private final WebDriverWait wait;

    public TodoMVCPojoPage(WebDriver driver, String url) {
        this.driver = driver;
        this.url = url;
        wait = new WebDriverWait(driver,10);

        // move the mouse out of the way so it
        // doesn't interfere with the test
        // if this is required then it should be in an abstraction rather than repeated in different objects
//        try {
//            new Robot().mouseMove(0,0);
//        } catch (AWTException e) {
//            e.printStackTrace();
//        }
    }

    // TODO: EXERCISE: refactor the class so that By selectors are static final fields
    public List<WebElement> getTodoItems() {
        return driver.findElements(By.cssSelector("ul.todo-list li div.view"));
    }


    public String getToDoText(int itemIndex) {
        List<WebElement> items = getTodoItems();
        return items.get(itemIndex).getText();
    }


    public void typeIntoNewToDo(CharSequence... keysToSend) {
        WebElement createTodo = driver.findElement(By.className("new-todo"));
        createTodo.click();
        createTodo.sendKeys(keysToSend);
    }

    public void open() {
        driver.get(url);
    }


    public void deleteTodoItem(int todoIndex) {
        List<WebElement> items = getTodoItems();
        WebElement todoListItem = items.get(todoIndex);

        wait.until(ExpectedConditions.elementToBeClickable(todoListItem));

        // on my mac, sometimes this fails because
        // the default size of the window is small so
        // the element is off screen,
        // I used to do this with an extra todoListItem.click()
        // where the first click brings it on to screen
        // but by scrolling, the button is lost
        // I decided to use JavaScript to scroll it into view instead

        // TODO: EXERCISE: I created a support abstraction object called EnsureWebElementIs
        //      refactor the line of code below to use the EnsureWebElementIs object
        ((JavascriptExecutor) driver).executeScript(
                "window.scrollTo(0," + todoListItem.getLocation().getY() + ")");

        todoListItem.click(); // enable the destroy button

        WebElement destroyButton = todoListItem.findElement(By.cssSelector("button.destroy"));
        wait.until(ExpectedConditions.elementToBeClickable(destroyButton));

        destroyButton.click();
    }

    public void editItem(int itemIndex, String editTheTitleTo) {
        List<WebElement> items = getTodoItems();
        WebElement todoListItem = items.get(itemIndex);
        wait.until(ExpectedConditions.elementToBeClickable(todoListItem));

        // used actions here because WebElement supports click only
        new Actions(driver).doubleClick(todoListItem).perform();

        WebElement editfield = wait.until(ExpectedConditions.
                                        elementToBeClickable(
                                                By.cssSelector("input.edit")));
        editfield.click();

        // clear causes the javascript on the field to trigger and close the input
        // perhaps it loses focus? Use JS instead to empty field
        //editfield.clear();
        // TODO: EXERCISE: refactor this into a 'cleared' method on EnsureWebElementIs
        //       and use the EnsureWebElementIs in the page object
        ((JavascriptExecutor)driver).executeScript(
                "arguments[0].value='';", editfield);

        editfield.sendKeys(editTheTitleTo);
        editfield.sendKeys(Keys.ENTER);
    }


    public Integer getCountInFooter() {
        WebElement countElement = driver.findElement(By.cssSelector(".todo-count strong"));
        return Integer.valueOf(countElement.getText());
    }

    public String getCountTextInFooter() {
        WebElement countElement = driver.findElement(By.cssSelector(".todo-count"));
        String countText = countElement.getText();

        // remove the number from the string
        return countText.replace(getCountInFooter() + " ", "");
    }

    public void clickOnFilter(Filter filter) {
        List<WebElement> filters = driver.findElements(By.cssSelector(".filters li a"));
        filters.get(filter.index()).click();
    }

    public void toggleCompletionOfItem(int itemIndex) {

        List<WebElement> items = getTodoItems();
        WebElement todoListItem = items.get(itemIndex);
        wait.until(ExpectedConditions.elementToBeClickable(todoListItem));

        WebElement checkbox = todoListItem.findElement(By.cssSelector("input.toggle"));
        checkbox.click();
    }

    public boolean isClearCompletedVisible() {
        // it may or may not be in the dom
        List<WebElement> clearCompleted = driver.findElements(CLEAR_COMPLETED);
        if(clearCompleted.size()==0){return false;}

        return clearCompleted.get(0).isDisplayed();
    }

    public Integer getClearCompletedCount() {
        Integer clearCompletedCount=0;

        if(isClearCompletedVisible()){
            WebElement clearCompletedButton = driver.findElement(CLEAR_COMPLETED);
            String clearCompletedText = clearCompletedButton.getText();
            Pattern completedText = Pattern.compile("Clear completed \\((.+)\\)");
            Matcher matcher = completedText.matcher(clearCompletedText);

            if(matcher.matches()){
                return Integer.valueOf(matcher.group(1));
            }
        }

        return clearCompletedCount;
    }

    public void clickClearCompleted() {
        WebElement clearCompletedButton = driver.findElement(CLEAR_COMPLETED);
        clearCompletedButton.click();
    }

}
