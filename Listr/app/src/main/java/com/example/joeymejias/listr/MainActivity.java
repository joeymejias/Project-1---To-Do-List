package com.example.joeymejias.listr;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joeymejias.listr.db.TaskContract;
import com.example.joeymejias.listr.db.TaskDbHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static String task;

    //    private TaskDbHelper mHelper;
    private GridView mTaskListView;
    private ArrayAdapter<String> mAdapter;
    public static String detail;
    private String taskTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TaskDbHelper.mHelper = new TaskDbHelper(this);
        mTaskListView = (GridView) findViewById(R.id.list_todo);

        SQLiteDatabase db = TaskDbHelper.mHelper.getReadableDatabase();
        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE,
                new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COL_TASK_TITLE, TaskContract.TaskEntry.COL_TASK_DETAIL},
                null, null, null, null, null);
        cursor.moveToFirst();
        while(cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE);
            Log.d(TAG, "Task: " + cursor.getString(idx));
            int idx2 = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_DETAIL);
            Log.d(TAG, "Detail: " + cursor.getString(idx2));
//
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createTask();
            }
        });

        mTaskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,TaskDetail.class);
                intent.putExtra("Task Details", getDetail(position)); //Want to query db for the details of the task selected
                startActivity(intent);
            }
        });

        mTaskListView.setLongClickable(true);
        mTaskListView.setClickable(true);
        mTaskListView.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                Toast.makeText(MainActivity.this, "You long clicked!", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        cursor.close();
        db.close();
        updateUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//          //Code for when task(s) long clicked to deleteTask()
//    }

    public void createTask() {
//              final EditText taskEditText = new EditText(this);
//              final EditText detailEditText = new EditText(this);
                LinearLayout layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText taskEditText = new EditText(this);
                taskEditText.setHint("Title");
                layout.addView(taskEditText);

                final EditText detailEditText = new EditText(this);
                detailEditText.setHint("Description (Tip: use enter for lists)");
                layout.addView(detailEditText);

                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Add a new task")
//                       .setMessage("What do you want to do next?")
//                        .setView(taskEditText)
//                        .setTitle("Add task detail")
//                        .setMessage("How do you want to do it?")
//                        .setView(detailEditText)
                        .setView(layout)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String task = String.valueOf(taskEditText.getText());
                                String detail = String.valueOf(detailEditText.getText());
                                SQLiteDatabase db = TaskDbHelper.mHelper.getWritableDatabase();
                                ContentValues values = new ContentValues();
                                values.put(TaskContract.TaskEntry.COL_TASK_TITLE, task);
                                values.put(TaskContract.TaskEntry.COL_TASK_DETAIL, detail);
                                db.insertWithOnConflict(TaskContract.TaskEntry.TABLE,
                                        null,
                                        values,
                                        SQLiteDatabase.CONFLICT_REPLACE);
                                db.close();
                                updateUI();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
    }

    public void createTask(MenuItem item) {
        createTask();
    }

//    public String getTaskDetail(){
//        return TaskContract.TaskEntry.COL_TASK_DETAIL; //Query DB and return detail of task
//    }

    public static String getDetail(int position){
        SQLiteDatabase db = TaskDbHelper.mHelper.getReadableDatabase();
        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE,
                new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COL_TASK_TITLE, TaskContract.TaskEntry.COL_TASK_DETAIL},
                null, null, null, null, null);
        cursor.moveToPosition(position);
        detail = cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_DETAIL));
        task = cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE));
        cursor.close();
        db.close();
        return detail;
    }

//    public void deleteTask(View view) {
////        View parent = (View) view.getParent();
////        TextView taskTextView = (TextView) parent.findViewById(R.id.task_title);
////        String task = String.valueOf(taskTextView.getText());
//        String task = taskTitle;
//        SQLiteDatabase db = TaskDbHelper.mHelper.getWritableDatabase();
//        db.delete(TaskContract.TaskEntry.TABLE,
//                TaskContract.TaskEntry.COL_TASK_TITLE + " = ?",
//                new String[]{task});
//        db.close();
//        updateUI();
//    }

    public void updateUI() {
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db = TaskDbHelper.mHelper.getReadableDatabase();
        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE,
                new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COL_TASK_TITLE, TaskContract.TaskEntry.COL_TASK_DETAIL},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE);
            int idx2 = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_DETAIL);
            taskTitle = cursor.getString(idx);
            String taskDetail = cursor.getString(idx2);
            String taskCard = (taskTitle + "\n" + taskDetail);
            taskList.add(taskCard);
        }

        if (mAdapter == null) {
            mAdapter = new ArrayAdapter<>(this,
                    R.layout.item_todo,
                    R.id.task_title,
                    taskList);
            mTaskListView.setAdapter(mAdapter);
        } else {
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();
        }

        cursor.close();
        db.close();
    }
}
