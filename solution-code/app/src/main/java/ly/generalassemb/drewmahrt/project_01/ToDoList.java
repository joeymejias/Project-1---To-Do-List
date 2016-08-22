package ly.generalassemb.drewmahrt.project_01;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Drew on 11/23/15.
 */

public class ToDoList{
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
