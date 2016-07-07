package com.example.joeymejias.listr;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joeymejias.listr.db.TaskContract;
import com.example.joeymejias.listr.db.TaskDbHelper;

public class TaskDetail extends AppCompatActivity {

    TextView mTextView;
    public static String detail;
    public static String oldText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        Intent intent = getIntent();

        String details = intent.getStringExtra("Task Details"); //Want to return string item from DB corresponding to place in list.

        mTextView = (TextView) findViewById(R.id.taskDetails);

        mTextView.setText(details);

        final Button editButton = (Button) findViewById(R.id.editTask);
        editButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                oldText = (String) mTextView.getText();
                LinearLayout layout = new LinearLayout(TaskDetail.this);
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText detailEditText = new EditText(TaskDetail.this);
                detailEditText.setText(mTextView.getText());
                layout.addView(detailEditText);

                AlertDialog dialog = new AlertDialog.Builder(TaskDetail.this)
                        .setTitle("Edit Task Details")
                        .setView(layout)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                detail = String.valueOf(detailEditText.getText());
                                if (!detail.equals(mTextView.getText())){
                                    mTextView.setText(detail);
                                    try {
                                        TaskDbHelper.updateDetail();
                                    } catch (SQLiteException e){
                                        e.printStackTrace();
                                    }
//                                    SQLiteDatabase db = TaskDbHelper.mHelper.getWritableDatabase();
//                                    String command = ("UPDATE" + TaskContract.TaskEntry.TABLE + "SET" + detail + "WHERE" + TaskContract.TaskEntry.COL_TASK_DETAIL + " = \"" + oldText + "\"");
//                                    db.execSQL(command);
                                }
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();

//                Toast.makeText(TaskDetail.this, "You clicked edit button!", Toast.LENGTH_SHORT).show();
            }
        });

            final Button doneButton = (Button) findViewById(R.id.done);
            doneButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    deleteTask();
                }
            });

        final Button goBackButton = (Button) findViewById(R.id.goBack);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                startActivity(new Intent(v.getContext(),MainActivity.class));
            }
        });
    }
    public void deleteTask() {
        SQLiteDatabase db = TaskDbHelper.mHelper.getWritableDatabase();
        db.delete(TaskContract.TaskEntry.TABLE,
                TaskContract.TaskEntry.COL_TASK_DETAIL + " = \"" + mTextView.getText() + "\"" ,
                new String[]{});
        db.close();
        startActivity(new Intent(this,MainActivity.class));
        //Toast.makeText(TaskDetail.this, "You clicked the done button!", Toast.LENGTH_SHORT).show();
    }
}
