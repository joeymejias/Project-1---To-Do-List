package ly.generalassemb.drewmahrt.project_01;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Drew on 11/23/15.
 */
public class CustomListAdapter extends ArrayAdapter<ToDoList>{
    private List<ToDoList> mList;

    public CustomListAdapter(Context context, List<ToDoList> list) {
        super(context,android.R.layout.simple_list_item_1, list);

        mList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        TextView itemTitleView = (TextView) convertView.findViewById(android.R.id.text1);

        itemTitleView.setText( mList.get(position).getTitle());

        return convertView;
    }
}
