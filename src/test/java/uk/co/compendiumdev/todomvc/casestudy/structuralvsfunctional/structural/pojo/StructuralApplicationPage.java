package uk.co.compendiumdev.todomvc.casestudy.structuralvsfunctional.structural.pojo;


public interface StructuralApplicationPage {
    int getCountOfTodo(StructuralEnums.ItemsInState state);

    String getToDoText(int itemIndex);

    void typeIntoNewToDo(CharSequence... keysToSend);

    void open();

    boolean isFooterVisible();
    boolean isMainVisible();
    void deleteTodoItem(int todoIndex);
    void editItem(int itemIndex, String editTheTitleTo);
    Integer getCountInFooter();
    String getCountTextInFooter();
    void clickOnFilter(StructuralEnums.Filter filter);
    void toggleCompletionOfItem(int itemIndex);
    boolean isClearCompletedVisible();
    Integer getClearCompletedCount();
    void clickClearCompleted();

}
