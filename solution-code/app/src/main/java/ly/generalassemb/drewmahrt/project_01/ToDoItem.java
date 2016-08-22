package ly.generalassemb.drewmahrt.project_01;

import java.io.Serializable;

/**
 * Created by drewmahrt on 5/2/16.
 */
public class ToDoItem{
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
