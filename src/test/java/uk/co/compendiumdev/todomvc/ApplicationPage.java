package uk.co.compendiumdev.todomvc;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.co.compendiumdev.selenium.support.webelement.EnsureWebElementIs;
import uk.co.compendiumdev.todomvc.site.TodoMVCSite;

import java.util.List;

public class ApplicationPage {
    private final WebDriver driver;
    private final TodoMVCSite todoMVCSite;
    private final WebDriverWait wait;

    public ApplicationPage(WebDriver driver, TodoMVCSite todoMVCSite) {
        this.driver = driver;
        this.todoMVCSite = todoMVCSite;
        wait = new WebDriverWait(driver,10);
    }

    public int getCountOfTodoDoItems() {
        return getTodoItems().size();
    }

    public String getLastToDoText() {
        List<WebElement> items = getTodoItems();
        return items.get(items.size()-1).getText();
    }

    private List<WebElement> getTodoItems() {
        // visible ones
        return driver.findElements(By.cssSelector("ul.todo-list li:not(.hidden)"));
    }

    public void enterNewToDo(String todoTitle) {
        WebElement createTodo = driver.findElement(By.className("new-todo"));
        createTodo.click();
        createTodo.sendKeys(todoTitle);
        createTodo.sendKeys(Keys.RETURN);
    }

    public void get() {
        driver.get(todoMVCSite.getURL());
    }

    public boolean isFooterVisible() {
        return driver.findElement(By.className("footer")).isDisplayed();
    }

    public boolean isMainVisible() {
        return driver.findElement(By.className("main")).isDisplayed();
    }

    public void deleteLastToDo() {
        List<WebElement> items = getTodoItems();
        WebElement todoListItem = items.get(items.size()-1);

        wait.until(ExpectedConditions.elementToBeClickable(todoListItem));

        // on my mac, sometimes this fails because
        // the default size of the window is small so
        // the element abstraction is off screen,
        // I used to do this with an extra todoListItem.click()
        // where the first click brings it on to screen
        // but by scrolling, the button is lost
        // I decided to use JavaScript to scroll it into view instead
        EnsureWebElementIs.inViewOnThePage(driver, todoListItem);

        // todoListItem.click(); // enable the destroy button
        // show the destroy button
        new Actions(driver).moveToElement(todoListItem).perform();

        WebElement destroyButton = todoListItem.findElement(By.cssSelector("button.destroy"));
        wait.until(ExpectedConditions.elementToBeClickable(destroyButton));

        destroyButton.click();
    }


    public void editLastItem(String editTheTitleTo) {
        List<WebElement> items = getTodoItems();
        WebElement todoListItem = items.get(items.size()-1);
        wait.until(ExpectedConditions.elementToBeClickable(todoListItem));

        // find the label
        // have no choice but to use actions here
        final WebElement fieldLabel = todoListItem.findElement(By.cssSelector("div > label"));

        String textToEdit = fieldLabel.getText();

        // double click to edit and reveal the input field
        new Actions(driver).doubleClick(fieldLabel).perform();

        WebElement editField = todoListItem.findElement(By.cssSelector("input.edit"));
        wait.until(ExpectedConditions.elementToBeClickable(editField));
        editField.click();

        // clearing the field causes the entire field to disappear again
        // and then we have a stale element exception when we send keys
        // does field lose control?
        // Note: this used to work - did GUI change? or did webdriver change?
        // editField.clear();
        // editField.sendKeys(editTheTitleTo);
        // editField.sendKeys(Keys.ENTER);

        // OPTION: could find the length of the text and issue that many backspace
        StringBuilder seq = new StringBuilder();
        for(int backspaceToAdd = 0; backspaceToAdd<textToEdit.length(); backspaceToAdd++){
            seq.append(Keys.BACK_SPACE);
        }
        seq.append(editTheTitleTo);
        seq.append(Keys.ENTER);
        editField.sendKeys(seq.toString());

        // OPTION: could use actions to do the same thing? which might simulate human more
        // OPTION: could use JavaScript to change the values of the field?
        // OPTION: is there a way to retain focus between clear and sendKeys?

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

    public void filterOnAll() {
        clickOnFilter(0);
    }

    public void filterOnActive() {
        clickOnFilter(1);
    }

    public void filterOnCompleted() {
        clickOnFilter(2);
    }

    private void clickOnFilter(int filterIndex) {
        List<WebElement> filters = driver.findElements(By.cssSelector(".filters li a"));
        filters.get(filterIndex).click();
    }

    public void toggleCompletionOfLastItem() {
        List<WebElement> items = getTodoItems();
        WebElement todoListItem = items.get(items.size()-1);
        wait.until(ExpectedConditions.elementToBeClickable(todoListItem));

        WebElement checkbox = todoListItem.findElement(By.cssSelector("input.toggle"));
        checkbox.click();
    }

    public int getCountOfCompletedTodoDoItems() {
        return driver.findElements(By.cssSelector("ul.todo-list li.completed:not(.hidden)")).size();
    }

    public int getCountOfActiveTodoDoItems() {
        return driver.findElements(By.cssSelector("ul.todo-list li:not(.completed):not(.hidden)")).size();
    }

    public boolean isClearCompletedVisible() {
        // it may or may not be in the dom
        List<WebElement> clearCompleted = driver.findElements(By.className("clear-completed"));
        if(clearCompleted.size()==0){return false;}

        return clearCompleted.get(0).isDisplayed();
    }

    public Integer getClearCompletedCount() {

        // the clear completed button used to show the count in the button test

//        Integer clearCompletedCount=0;
//
//        if(isClearCompletedVisible()){
//            WebElement clearCompletedButton = driver.findElement(By.className("clear-completed"));
//            String clearCompletedText = clearCompletedButton.getText();
//            Pattern completedText = Pattern.compile("Clear completed \\((.+)\\)");
//            Matcher matcher = completedText.matcher(clearCompletedText);
//
//            if(matcher.matches()){
//                return Integer.valueOf(matcher.group(1));
//            }
//        }
//
//        return clearCompletedCount;

        // the button no longer shows the completed count so count the list items
        // ul.todo-list li.completed
        return driver.findElements(By.cssSelector("ul.todo-list li.completed")).size();


    }

    public void clearCompleted() {
        WebElement clearCompletedButton = driver.findElement(By.className("clear-completed"));
        clearCompletedButton.click();
    }
}
