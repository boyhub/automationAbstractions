package uk.co.compendiumdev.todomvc.elementabstractions;

public interface Checkbox {

    public boolean isChecked();
    public Checkbox check();
    public Checkbox uncheck();
    public Checkbox toggle();
}
