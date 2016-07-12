package andrey.timeit.DataBase;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import andrey.timeit.model.MTask;

/**
 * Created by Andrey on 12.07.2016.
 */
public class DBQManager {
    private SQLiteDatabase database;

    DBQManager(SQLiteDatabase database) {
        this.database = database;
    }

    public List<MTask> getTasks(String selection, String[] selectionArgs, String orderBy) {
        List<MTask> tasks = new ArrayList<>();

        Cursor c = database.query(DBManager.TASKS_TABLE, null,
                selection, selectionArgs, null, null, orderBy);

        if (c.moveToFirst()) {
            do {
                String title = c.getString(c.getColumnIndex(DBManager.TASK_TITLE_COLUMN));
                long date = c.getLong(c.getColumnIndex(DBManager.TASK_DATE_COLUMN));
                int priority = c.getInt(c.getColumnIndex(DBManager.TASK_CATEGORY_COLUMN));
                int status = c.getInt(c.getColumnIndex(DBManager.TASK_STATUS_COLUMN));
                long timeStamp = c.getLong(c.getColumnIndex(DBManager.TASK_TIME_STAMP_COLUMN));

                MTask modelTask = new MTask(title, date, priority, status, timeStamp);
                tasks.add(modelTask);
            } while (c.moveToNext());
        }
        c.close();

        return tasks;
    }
}
