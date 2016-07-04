package com.example.joeymejias.listr.db;

import android.provider.BaseColumns;

/**
 * Created by joey on 7/2/16.
 */
public class TaskContract {
    public static final String DB_NAME = "com.joeymejias.listr.db";
    public static final int DB_VERSION = 1;

    public class TaskEntry implements BaseColumns {
        public static final String TABLE = "tasks";

        public static final String COL_TASK_TITLE = "title";
        public static final String COL_TASK_DETAIL = "detail";

    }
}
