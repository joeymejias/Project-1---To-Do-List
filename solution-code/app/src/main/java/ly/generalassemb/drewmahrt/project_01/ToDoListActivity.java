package ly.generalassemb.drewmahrt.project_01;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ToDoListActivity extends AppCompatActivity {
    private ToDoListAdapter mItemListAdapter;
    private ToDoSingleton mToDoSingleton;
    private int mListIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_new_item);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialog();
            }
        });

        mToDoSingleton = ToDoSingleton.getInstance();

        mListIndex = getIntent().getIntExtra("index",-1);

        toolbar.setTitle(mToDoSingleton.getToDoLists().get(mListIndex).getTitle());

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mItemListAdapter = new ToDoListAdapter(this,mToDoSingleton.getToDoLists().get(mListIndex).getToDoItems());
        ListView listView = (ListView)findViewById(R.id.items_list_view);
        TextView emptyItemTextView = (TextView)findViewById(R.id.empty_item_text_view);
        listView.setAdapter(mItemListAdapter);
        listView.setEmptyView(emptyItemTextView);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mToDoSingleton.getToDoLists().get(mListIndex).getToDoItems().remove(position);
                mItemListAdapter.notifyDataSetChanged();
                return false;
            }
        });
    }

    public void showInputDialog(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.item_text_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText titleEditText = (EditText) dialogView.findViewById(R.id.title_text);
        final EditText descriptionEditText = (EditText) dialogView.findViewById(R.id.description_text);


        dialogBuilder.setTitle("To-Do Item");
        dialogBuilder.setMessage("Enter item title and description below");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if(titleEditText.getText().toString().length() == 0) {
                    Toast.makeText(ToDoListActivity.this, "Please enter an item title", Toast.LENGTH_LONG).show();
                } else {
                    mToDoSingleton.getToDoLists().get(mListIndex).getToDoItems().add(new ToDoItem(titleEditText.getText().toString(),descriptionEditText.getText().toString()));
                    mItemListAdapter.notifyDataSetChanged();
                }
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }
}
