package com.example.joeymejias.listr.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.joeymejias.listr.MainActivity;
import com.example.joeymejias.listr.TaskDetail;

/**
 * Created by joey on 7/2/16.
 */
public class TaskDbHelper extends SQLiteOpenHelper {

    public static TaskDbHelper mHelper;

    public TaskDbHelper(Context context) {
        super(context, TaskContract.DB_NAME, null, TaskContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TaskContract.TaskEntry.TABLE + " ( " +
                TaskContract.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TaskContract.TaskEntry.COL_TASK_TITLE + " TEXT NOT NULL, " +
                TaskContract.TaskEntry.COL_TASK_DETAIL + " TEXT NOT NULL);";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TaskContract.TaskEntry.TABLE);
        onCreate(db);
    }

    public static void updateDetail(){
        SQLiteDatabase db = TaskDbHelper.mHelper.getWritableDatabase();
        String command = ("UPDATE " + TaskContract.TaskEntry.TABLE + " SET " + TaskContract.TaskEntry.COL_TASK_DETAIL + " = \"" +  TaskDetail.detail + "\" WHERE " + TaskContract.TaskEntry.COL_TASK_DETAIL + " = \"" + TaskDetail.oldText + "\"");
        db.execSQL(command);
    }

}
