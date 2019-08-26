package uk.co.compendiumdev.examples.browserstorage;

import java.util.Collection;

public interface TodoMvcLocalStorage {

    public long length();
    public Collection<String> itemTitles();
    public String titleAt(int index);
    public boolean containsTitle(String title);
}
