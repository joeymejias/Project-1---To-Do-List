package com.joeymejias.listr2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private MySingleton mMySingleton;
    private CustomAdapter mCustomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialog(); //show dialog box for the title of the todo list
            }
        });

        mMySingleton = MySingleton.getInstance();

        mCustomAdapter = new CustomAdapter(this,mMySingleton.getToDoLists());

        ListView listView = (ListView)findViewById(R.id.to_do_list_view);
        TextView emptyTextView = (TextView)findViewById(R.id.empty_list_text_view);
        listView.setAdapter(mCustomAdapter);
        listView.setEmptyView(emptyTextView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long id) {
                Intent intent = new Intent(MainActivity.this, ToDoActivity.class);
                intent.putExtra("index", position);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mMySingleton.getToDoLists().remove(position);
                mCustomAdapter.notifyDataSetChanged();
                return false;
            }
        });
    }

    //method for showing the dialog box
    public void showInputDialog(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.list_text_dialog, null); //inflate view for text dialog
        dialogBuilder.setView(dialogView);

        final EditText editText = (EditText) dialogView.findViewById(R.id.list_text);

        dialogBuilder.setTitle("List Title"); //title of dialog box
        dialogBuilder.setMessage("Enter title below"); //prompt of dialog box
        // confirmation button
        dialogBuilder.setPositiveButton("Add List", new DialogInterface.OnClickListener(){
           public void onClick(DialogInterface dialogInterface, int button){
               if(editText.getText().toString().length() == 0){
                   Toast.makeText(MainActivity.this, "Cannot add blank list!", Toast.LENGTH_SHORT).show();
               } else {
                   mMySingleton.addToDoList(new ToDoList(editText.getText().toString()));
                   mCustomAdapter.notifyDataSetChanged();
                   Intent intent = new Intent(MainActivity.this, ToDoActivity.class);
                   intent.putExtra("index", mMySingleton.getToDoLists().size() - 1);
                   startActivity(intent);
               }
           }
        });
        // rejection button
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int button){
            }
        });
        AlertDialog dialog = dialogBuilder.create(); //creates the dialog
        dialog.show(); // shows the dialog
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
