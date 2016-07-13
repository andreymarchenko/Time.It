package andrey.timeit.dataBase;

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

    public MTask getTask(long timeStamp) {
        MTask modelTask = null;
        Cursor c1 = database.query(DBManager.TASKS_TABLE, null, DBManager.SELECTION_TIME_STAMP,
                new String[]{Long.toString(timeStamp)}, null, null, null);

        if (c1.moveToFirst()) {
            String title = c1.getString(c1.getColumnIndex(DBManager.TASK_TITLE_COLUMN));
            long date = c1.getLong(c1.getColumnIndex(DBManager.TASK_DATE_COLUMN));
            int priority = c1.getInt(c1.getColumnIndex(DBManager.TASK_CATEGORY_COLUMN));
            int status = c1.getInt(c1.getColumnIndex(DBManager.TASK_STATUS_COLUMN));

            modelTask = new MTask(title, date, priority, status, timeStamp);
        }
        c1.close();

        return modelTask;

    }

    public List<MTask> getTasks(String selection, String[] selectionArgs, String orderBy) {
        List<MTask> tasks = new ArrayList<>();

        Cursor c2 = database.query(DBManager.TASKS_TABLE, null,
                selection, selectionArgs, null, null, orderBy);

        if (c2.moveToFirst()) {
            do {
                String title = c2.getString(c2.getColumnIndex(DBManager.TASK_TITLE_COLUMN));
                long date = c2.getLong(c2.getColumnIndex(DBManager.TASK_DATE_COLUMN));
                int priority = c2.getInt(c2.getColumnIndex(DBManager.TASK_CATEGORY_COLUMN));
                int status = c2.getInt(c2.getColumnIndex(DBManager.TASK_STATUS_COLUMN));
                long timeStamp = c2.getLong(c2.getColumnIndex(DBManager.TASK_TIME_STAMP_COLUMN));

                MTask modelTask = new MTask(title, date, priority, status, timeStamp);
                tasks.add(modelTask);
            } while (c2.moveToNext());
        }
        c2.close();

        return tasks;
    }
}
