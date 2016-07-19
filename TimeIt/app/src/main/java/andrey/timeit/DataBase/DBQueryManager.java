package andrey.timeit.dataBase;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import andrey.timeit.model.ModelAdvice;
import andrey.timeit.model.ModelDuration;
import andrey.timeit.model.ModelProfile;
import andrey.timeit.model.ModelTask;

/**
 * Created by Andrey on 12.07.2016.
 */
public class DBQueryManager {
    private SQLiteDatabase database;

    public SQLiteDatabase getDatabase() {
        return database;
    }

    public void setDatabase(SQLiteDatabase database) {
        this.database = database;
    }

    DBQueryManager(SQLiteDatabase database) {
        this.database = database;
    }

    public ModelTask getTask(long timeStamp) {
        ModelTask modelTask = null;
        Cursor c1 = database.query(DBHelper.TASKS_TABLE, null, DBHelper.SELECTION_TIME_STAMP,
                new String[]{Long.toString(timeStamp)}, null, null, null);

        if (c1.moveToFirst()) {
            String title = c1.getString(c1.getColumnIndex(DBHelper.TASK_TITLE_COLUMN));
            long date = c1.getLong(c1.getColumnIndex(DBHelper.TASK_DATE_COLUMN));
            int priority = c1.getInt(c1.getColumnIndex(DBHelper.TASK_CATEGORY_COLUMN));
            int status = c1.getInt(c1.getColumnIndex(DBHelper.TASK_STATUS_COLUMN));
            long timeStart = c1.getLong(c1.getColumnIndex(DBHelper.TASK_TIME_START_COLUMN));
            long timeStop = c1.getLong(c1.getColumnIndex(DBHelper.TASK_TIME_STOP_COLUMN));

            modelTask = new ModelTask(title, date, priority, status, timeStamp, timeStart, timeStop);
        }
        c1.close();

        return modelTask;
    }

    public ModelDuration getDuration() {
        ModelDuration modelDuration = null;
        Cursor c3 = database.query(DBHelper.DURATION_CATEGORY_TABLE, null, null, null, null, null, null);

        if (c3.moveToFirst()) {
            long workCategoryDuration = c3.getLong(c3.getColumnIndex((DBHelper.DURATION_CATEGORY_WORK_COLUMN)));
            long sportCategoryDuration = c3.getLong(c3.getColumnIndex((DBHelper.DURATION_CATEGORY_SPORT_COLUMN)));
            long restCategoryDuration = c3.getLong(c3.getColumnIndex((DBHelper.DURATION_CATEGORY_REST_COLUMN)));
            long familyCategoryDuration = c3.getLong(c3.getColumnIndex((DBHelper.DURATION_CATEGORY_FAMILY_COLUMN)));

            modelDuration = new ModelDuration(workCategoryDuration, sportCategoryDuration, restCategoryDuration, familyCategoryDuration);
        }

        c3.close();

        return modelDuration;
    }

    public ModelAdvice getAdvice() {
        ModelAdvice modelAdvice = null;
        Cursor c4 = database.query(DBHelper.ADVICE_TABLE, null, null, null, null, null, null);

        if (c4.moveToFirst()) {
            String message = c4.getString(c4.getColumnIndex((DBHelper.ADVICE_MESSAGE_COLUMN)));
            int key = c4.getInt(c4.getColumnIndex((DBHelper.ADVICE_KEY_COLUMN)));

            modelAdvice = new ModelAdvice(message, key);
        }

        c4.close();

        return modelAdvice;
    }

    public List<ModelAdvice> getAdvice(int key1) {
        List<ModelAdvice> advice = new ArrayList();

        Cursor c5 = database.query(DBHelper.TASKS_TABLE, null, null, null, null, null, null);

        if (c5.moveToFirst()) {
            do {
                String message = c5.getString(c5.getColumnIndex(DBHelper.ADVICE_MESSAGE_COLUMN));
                int key = c5.getInt(c5.getColumnIndex((DBHelper.ADVICE_KEY_COLUMN)));

                ModelAdvice modelAdvice = new ModelAdvice(message, key);
                advice.add(modelAdvice);
            } while (c5.moveToNext());

        }
        c5.close();

        return advice;
    }

    public List<ModelTask> getTasks(String selection, String[] selectionArgs, String orderBy) {
        List<ModelTask> tasks = new ArrayList<>();

        Cursor c2 = database.query(DBHelper.TASKS_TABLE, null,
                selection, selectionArgs, null, null, orderBy);

        if (c2.moveToFirst()) {
            do {
                String title = c2.getString(c2.getColumnIndex(DBHelper.TASK_TITLE_COLUMN));
                long date = c2.getLong(c2.getColumnIndex(DBHelper.TASK_DATE_COLUMN));
                int priority = c2.getInt(c2.getColumnIndex(DBHelper.TASK_CATEGORY_COLUMN));
                int status = c2.getInt(c2.getColumnIndex(DBHelper.TASK_STATUS_COLUMN));
                long timeStamp = c2.getLong(c2.getColumnIndex(DBHelper.TASK_TIME_STAMP_COLUMN));
                long timeStart = c2.getLong(c2.getColumnIndex(DBHelper.TASK_TIME_START_COLUMN));
                long timeStop = c2.getLong(c2.getColumnIndex(DBHelper.TASK_TIME_STOP_COLUMN));

                ModelTask modelTask = new ModelTask(title, date, priority, status, timeStamp, timeStart, timeStop);
                tasks.add(modelTask);
            } while (c2.moveToNext());
        }
        c2.close();

        return tasks;
    }

    public ModelProfile getProfile(String login) {
        ModelProfile modelProfile = null;

        Cursor c3 = database.query(DBHelper.PROFILE_TABLE, null, null, null, null, null, null);

        if (c3.moveToFirst()) {
            String password = c3.getString(c3.getColumnIndex(DBHelper.PROFILE_PASSWORD_COLUMN));
            String name = c3.getString(c3.getColumnIndex(DBHelper.PROFILE_NAME_COLUMN));
            String activity = c3.getString(c3.getColumnIndex(DBHelper.PROFILE_ACTIVITY_COLUMN));
            String age = c3.getString(c3.getColumnIndex(DBHelper.PROFILE_AGE_COLUMN));
            String gender = c3.getString(c3.getColumnIndex(DBHelper.PROFILE_GENDER_COLUMN));
            String weight = c3.getString(c3.getColumnIndex(DBHelper.PROFILE_WEIGHT_COLUMN));
            String growth = c3.getString(c3.getColumnIndex(DBHelper.PROFILE_GROWTH_COLUMN));
            String email = c3.getString(c3.getColumnIndex(DBHelper.PROFILE_EMAIL_COLUMN));

            modelProfile = new ModelProfile(login, password, name, activity, age, gender, weight, growth, email);
        }
        c3.close();
        return modelProfile;
    }
}
