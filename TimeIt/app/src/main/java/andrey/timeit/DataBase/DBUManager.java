package andrey.timeit.DataBase;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import andrey.timeit.model.MTask;

/**
 * Created by Andrey on 12.07.2016.
 */
public class DBUManager {


    SQLiteDatabase database;

    DBUManager(SQLiteDatabase database) {
        this.database = database;
    }

    public void title(long timeStamp, String title) {
        update(DBManager.TASK_TITLE_COLUMN, timeStamp, title);
    }

    public void date(long timeStamp, long date) {
        update(DBManager.TASK_DATE_COLUMN, timeStamp, date);
    }

    public void priority(long timeStamp, int priority) {
        update(DBManager.TASK_CATEGORY_COLUMN, timeStamp, priority);
    }

    public void status(long timeStamp, int status) {
        update(DBManager.TASK_STATUS_COLUMN, timeStamp, status);
    }

    public void task(MTask task) {
        title(task.getTimeStamp(), task.getTitle());
        date(task.getTimeStamp(), task.getDate());
        priority(task.getTimeStamp(), task.getCategory());
        status(task.getTimeStamp(), task.getStatus());
    }

    private void update(String column, long key, String value) {
        ContentValues cv = new ContentValues();
        cv.put(column, value);
        database.update(DBManager.TASKS_TABLE, cv, DBManager.TASK_TIME_STAMP_COLUMN + " = " + key, null);
    }

    private void update (String column, long key, long value) {
        ContentValues cv = new ContentValues();
        cv.put(column, value);
        database.update(DBManager.TASKS_TABLE, cv, DBManager.TASK_TIME_STAMP_COLUMN + " = " + key, null);
    }

}
