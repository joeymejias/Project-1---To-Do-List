package com.joeymejias.listr2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joey on 8/23/16.
 */
public class ToDoList {
    private String mTitle;
    private List<ToDoItem> mToDoItems;

    public ToDoList(String title){
        mTitle = title;
        mToDoItems = new ArrayList<>();
    }

    @Override
    public String toString(){
        return mTitle;
    }

    public String getTitle(){
        return mTitle;
    }

    public List<ToDoItem> getToDoItems(){
        return mToDoItems;
    }
}
