package andrey.timeit.dataBase;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import andrey.timeit.model.ModelDuration;
import andrey.timeit.model.ModelTask;

/**
 * Created by Andrey on 12.07.2016.
 */
public class DBUpdateManager {

    SQLiteDatabase database;
    DBQueryManager dbQueryManager = null;

    DBUpdateManager(SQLiteDatabase database) {
        this.database = database;
    }

    public void title(long timeStamp, String title) {
        update(DBHelper.TASK_TITLE_COLUMN, timeStamp, title);
    }

    public void date(long timeStamp, long date, int val) {
        update(DBHelper.TASK_DATE_COLUMN, timeStamp, date, val);
    }

    public void priority(long timeStamp, int priority, int val) {
        update(DBHelper.TASK_CATEGORY_COLUMN, timeStamp, priority, val);
    }

    public void status(long timeStamp, int status, int val) {
        update(DBHelper.TASK_STATUS_COLUMN, timeStamp, status, val);
    }

    public void timeStart(long timeStamp, long timeStart) {
        update(DBHelper.TASK_TIME_START_COLUMN, timeStamp, timeStart);
    }

    public void durationCategory(long timeStamp, long timeStart, long timeStop, int category, int val) {
        dbQueryManager = new DBQueryManager(database);
        ModelDuration modelDuration = dbQueryManager.getDuration();

        if (category == 0) {
            update(DBHelper.DURATION_CATEGORY_WORK_COLUMN, timeStamp,
                    modelDuration.getWorkCategoryDuration() + (timeStop - timeStart), val);

        }
        if (category == 1) {
            update(DBHelper.DURATION_CATEGORY_FAMILY_COLUMN, timeStamp,
                    modelDuration.getFamilyCategoryDuration() + (timeStop - timeStart), val);
        }
        if (category == 2) {
            update(DBHelper.DURATION_CATEGORY_REST_COLUMN, timeStamp,
                    modelDuration.getRestCategoryDuration() + (timeStop - timeStart), val);
            long gg=(timeStop - timeStart);
        }
        if (category == 3) {
            update(DBHelper.DURATION_CATEGORY_SPORT_COLUMN, timeStamp,
                    modelDuration.getSportCategoryDuration() + (timeStop - timeStart), val);
        }
    }

    public void task(ModelTask task) {
        title(task.getTimeStamp(), task.getTitle());
        date(task.getTimeStamp(), task.getDate(), 0);
        priority(task.getTimeStamp(), task.getCategory(), 0);
        status(task.getTimeStamp(), task.getStatus(), 0);
    }

    public void name(String login, String name) {
        update(DBHelper.PROFILE_NAME_COLUMN, login, name);
    }

    public void activity(String login, String activity) {
        update(DBHelper.PROFILE_ACTIVITY_COLUMN, login, activity);
    }

    private void update(String column, long key, String value) {
        ContentValues cv = new ContentValues();
        cv.put(column, value);
        database.update(DBHelper.TASKS_TABLE, cv, DBHelper.TASK_TIME_STAMP_COLUMN + " = " + key, null);
    }

    public void update(String column, long key, long timeStart) {
        ContentValues cv = new ContentValues();
        cv.put(column, timeStart);
        database.update(DBHelper.TASKS_TABLE, cv, DBHelper.TASK_TIME_STAMP_COLUMN + " = " + key, null);
    }

    private void update(String column, long key, long value, int index) {
        if (index == 0) {
            ContentValues cv = new ContentValues();
            cv.put(column, value);
            database.update(DBHelper.TASKS_TABLE, cv, DBHelper.TASK_TIME_STAMP_COLUMN + " = " + key, null);
        }
        if (index == 1) {
            ContentValues cv = new ContentValues();
            cv.put(column, value);
            database.update(DBHelper.DURATION_CATEGORY_TABLE, cv, null, null);
        }
    }

    private void update(String column, String key, String value) {
        ContentValues cv = new ContentValues();
        cv.put(column, value);
        database.update(DBHelper.PROFILE_TABLE, cv, DBHelper.PROFILE_LOGIN_COLUMN + " = " + key, null);
    }

    private void update(String column, String key, int value) {
        ContentValues cv = new ContentValues();
        cv.put(column, value);
        database.update(DBHelper.PROFILE_TABLE, cv, DBHelper.PROFILE_LOGIN_COLUMN + " = " + key, null);
    }

}
