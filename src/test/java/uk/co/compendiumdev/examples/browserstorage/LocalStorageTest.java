package uk.co.compendiumdev.examples.browserstorage;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.html5.LocalStorage;
import uk.co.compendiumdev.examples.browserstorage.html5.Storage;
import uk.co.compendiumdev.selenium.support.webdriver.ExecutionDriver;
import uk.co.compendiumdev.todomvc.site.TodoMVCSite;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

public class LocalStorageTest {


    private WebDriver driver;
    private TodoMVCSite todoMVCSite;
    private TodoMVCStoragePojoPage todoMVC;

    @BeforeEach
    public void setup(){
        driver = new ExecutionDriver().get();
        todoMVCSite = new TodoMVCSite();

        todoMVC = new TodoMVCStoragePojoPage(driver, todoMVCSite.getURL());
        todoMVC.open();
    }

    @Test
    public void updatesLocalStorageInitiallyUglyCode(){

        // initially code for backbone
        // the spec is loosely interpreted by different frameworks
        // so this will not be generic
        // todos-[framework]

        String storage_namespace = "todos-" + todoMVCSite.getName();
        LocalStorage storage = new Storage((JavascriptExecutor)driver);

        int initialSize = 0;
        String[] keys;

        try{
            keys = storage.getItem(storage_namespace).split(",");
            initialSize = keys.length;
        }catch(NullPointerException e){
            // the key might not exist
        }

        todoMVC.enterNewToDo("First Added Item");

        keys = storage.getItem(storage_namespace).split(",");
        int newSize = keys.length;

        assertThat(newSize, greaterThan(initialSize));

        boolean foundit = false;

        // has title
        keys = storage.getItem(storage_namespace).split(",");
        for(String key : keys){
            String itemKey = storage_namespace + "-" + key;
            String item = storage.getItem(itemKey);
            // should really parse json
            if(item.contains("\"" + "First Added Item" + "\""))
                foundit = true;
        }

        assertThat(foundit, is(true));
    }

    @Test
    public void updatesLocalStorageRefactored(){

        TodoMvcLocalStorage todoStorage = new BackBoneTodoMVCLocalStorage(
                                                    (JavascriptExecutor) driver);

        assertThat(todoStorage.length(), is(0L));

        todoMVC.enterNewToDo("First Added Item");

        assertThat(todoStorage.length(), is(1L));

        assertThat(todoStorage.containsTitle("First Added Item"), is(true));

        todoMVC.enterNewToDo("Next Added Item");
        assertThat(todoStorage.length(), is(2L));

        todoMVC.enterNewToDo("Third Added Item");
        assertThat(todoStorage.length(), is(3L));
    }

    @AfterEach
    public void teardown(){

        ExecutionDriver.closeDriver(driver);
    }
}
