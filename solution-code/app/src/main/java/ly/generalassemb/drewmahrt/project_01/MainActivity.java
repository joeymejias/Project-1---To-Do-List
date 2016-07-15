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
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ToDoSingleton mToDoSingleton;
    private CustomListAdapter mCustomListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_new_list);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialog();
            }
        });

        mToDoSingleton = ToDoSingleton.getInstance();

        mCustomListAdapter = new CustomListAdapter(this,mToDoSingleton.getToDoLists());

        ListView listView = (ListView)findViewById(R.id.to_do_list_view);
        TextView emptyTextView = (TextView)findViewById(R.id.empty_list_text_view);
        listView.setAdapter(mCustomListAdapter);
        listView.setEmptyView(emptyTextView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long id) {
                Intent intent = new Intent(MainActivity.this, ToDoListActivity.class);
                intent.putExtra("index", position);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mToDoSingleton.getToDoLists().remove(position);
                mCustomListAdapter.notifyDataSetChanged();
                return false;
            }
        });
    }

    public void showInputDialog(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.input_text_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editText = (EditText) dialogView.findViewById(R.id.title_text);

        dialogBuilder.setTitle("List Title");
        dialogBuilder.setMessage("Enter title below");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if(editText.getText().toString().length() == 0) {
                    Toast.makeText(MainActivity.this, "Please enter a list title", Toast.LENGTH_LONG).show();
                } else {
                    mToDoSingleton.addToDoList(new ToDoList(editText.getText().toString()));
                    mCustomListAdapter.notifyDataSetChanged();
                    Intent intent = new Intent(MainActivity.this, ToDoListActivity.class);
                    intent.putExtra("index", mToDoSingleton.getToDoLists().size() - 1);
                    startActivity(intent);
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
