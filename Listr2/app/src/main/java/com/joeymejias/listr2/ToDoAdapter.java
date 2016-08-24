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
public class ToDoAdapter extends ArrayAdapter<ToDoItem> {
    List<ToDoItem> items;

    public ToDoAdapter(Context context, List<ToDoItem> items) {
        super(context, android.R.layout.simple_list_item_2, items);
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ToDoItem item = items.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        }

        TextView itemName = (TextView) convertView.findViewById(android.R.id.text1);
        TextView itemDescription = (TextView) convertView.findViewById(android.R.id.text2);

        itemName.setText(item.getTitle());
        itemDescription.setText(item.getDescription());

        return convertView;
    }
}
