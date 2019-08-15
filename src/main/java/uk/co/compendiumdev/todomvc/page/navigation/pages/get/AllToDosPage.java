package uk.co.compendiumdev.todomvc.page.navigation.pages.get;


import uk.co.compendiumdev.todomvc.page.structural.slowloadablecomponent.ApplicationPageStructuralSlowLoadable;
import uk.co.compendiumdev.todomvc.site.TodoMVCSite;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.SlowLoadableComponent;

import java.time.Clock;

public class AllToDosPage extends SlowLoadableComponent<AllToDosPage> {

    private final WebDriver driver;
    private final TodoMVCSite site;
    private final ApplicationPageStructuralSlowLoadable page;

    public AllToDosPage(WebDriver driver, TodoMVCSite site){
        super(Clock.systemDefaultZone(), 10);
        this.driver = driver;
        this.site = site;

        // delegate to the main page
        // normally we would do this for components, but since this is a
        // single page app, the 'pages' are somewhat artificial and this
        // avoids duplicated code
        page = new ApplicationPageStructuralSlowLoadable(driver, site);
    }

    @Override
    protected void load() {
        driver.get(site.getURL() + "/#/");
    }

    @Override
    protected void isLoaded() throws Error {

        // are the components loaded?
        page.isLoaded();

        try{
            if(!page.getSelectedFilterText().contentEquals("All")){
                throw new Error("Selected Filter is not All");
            }
        }catch(Exception e){
            throw new Error("All ToDos Page Not loaded " + e.getMessage());
        }
    }

    public ApplicationPageStructuralSlowLoadable body(){
        return page;
    }
}
