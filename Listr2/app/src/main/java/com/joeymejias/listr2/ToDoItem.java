package com.joeymejias.listr2;

/**
 * Created by joey on 8/23/16.
 */
public class ToDoItem {
    private String title;
    private String description;

    public ToDoItem(String title, String description){
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }
}
