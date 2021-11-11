package com.example.chatbot.actividades.tareas;

import android.provider.BaseColumns;

public class TaskContract {
    public static final String DB_NAME = "com.example.crm.actividades.tareas.task_database";
    public static final int DB_VERSION = 1;

    public class TaskEntry implements BaseColumns {
        public static final String TABLE = "tasks";

        public static final String COL_TASK_TITLE = "title";
    }
}
