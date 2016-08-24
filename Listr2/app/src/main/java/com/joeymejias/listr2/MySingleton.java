package com.joeymejias.listr2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joey on 8/23/16.
 */
public class MySingleton {
    private static MySingleton sMySingleton = null;
    private static List<ToDoList> sToDoLists;

    private MySingleton(){
        sToDoLists = new ArrayList<>();
    }

    public static MySingleton getInstance(){
        if(sMySingleton == null)
            sMySingleton = new MySingleton();
        return sMySingleton;
    }

    public void addToDoList(ToDoList toDoList){
        sToDoLists.add(toDoList);
    }

    public List<ToDoList> getToDoLists(){
        return sToDoLists;
    }
}
