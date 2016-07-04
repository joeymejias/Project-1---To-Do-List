package com.example.joeymejias.listr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.joeymejias.listr.db.TaskContract;

public class TaskDetail extends AppCompatActivity {

    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        Intent intent = getIntent();

        String details = intent.getStringExtra("Task Details");

        mTextView = (TextView) findViewById(R.id.taskDetails);

        mTextView.setText(details);

        final Button button = (Button) findViewById(R.id.goBack);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                startActivity(new Intent(v.getContext(),MainActivity.class));

            }
        });
    }
}
