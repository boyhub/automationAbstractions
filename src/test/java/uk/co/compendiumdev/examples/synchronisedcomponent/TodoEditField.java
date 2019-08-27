package uk.co.compendiumdev.examples.synchronisedcomponent;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.SlowLoadableComponent;

import java.time.Clock;

// This implementation uses the SlowLoadableComponent
// TODO:
//      EXERCISE:
//              Make this simpler by not extending SlowLoadableComponent
//              and implementing a get method which uses a WebDriverWait
//              to wait until the field is clickable
public class TodoEditField extends SlowLoadableComponent {


    private final WebDriver driver;

    public TodoEditField(WebDriver driver) {
        super(Clock.systemDefaultZone(), 20);
        this.driver = driver;
    }

    @Override
    protected void load() {

    }

    @Override
    protected void isLoaded() throws Error {

        try {
            WebElement element = driver.findElement(By.cssSelector("li.editing input.edit"));
            if (element.isEnabled() && element.isDisplayed()) {
                return;
            }
        }catch(Exception e){

        }
        // if it isn't loaded throw an Error since
        // that is how SlowLoadableComponent works
        throw new Error("Component not loaded");
    }

    public void edit(final String editTheTitleTo) {

        WebElement editField = driver.findElement(By.cssSelector("li.editing input.edit"));
        editField.click();

        ((JavascriptExecutor)driver).executeScript(
                "arguments[0].value='';", editField);

        editField.sendKeys(editTheTitleTo);
        editField.sendKeys(Keys.ENTER);
    }
}
