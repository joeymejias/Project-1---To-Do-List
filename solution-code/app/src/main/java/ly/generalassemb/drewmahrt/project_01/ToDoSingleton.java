package ly.generalassemb.drewmahrt.project_01;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by drewmahrt on 7/7/16.
 */
public class ToDoSingleton {
    private static ToDoSingleton sToDoSingleton = null;
    private static List<ToDoList> sToDoLists;

    private ToDoSingleton(){
        sToDoLists = new ArrayList<>();
    }

    public static ToDoSingleton getInstance(){
        if(sToDoSingleton == null)
            sToDoSingleton = new ToDoSingleton();
        return sToDoSingleton;
    }

    public void addToDoList(ToDoList toDoList){
        sToDoLists.add(toDoList);
    }

    public List<ToDoList> getToDoLists(){
        return sToDoLists;
    }
}
