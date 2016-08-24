package com.joeymejias.listr2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by joey on 8/23/16.
 */
public class CustomAdapter extends ArrayAdapter<ToDoList> {
    private List<ToDoList> mList;

    public CustomAdapter(Context context, List<ToDoList> list) {
        super(context,android.R.layout.simple_list_item_1, list);
        mList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        TextView itemTitleView = (TextView) convertView.findViewById(android.R.id.text1);

        itemTitleView.setText( mList.get(position).getTitle());

        return convertView;
    }
}
