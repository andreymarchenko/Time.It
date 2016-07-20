package andrey.timeit.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import andrey.timeit.model.ModelAdvice;
import andrey.timeit.model.ModelDuration;
import andrey.timeit.model.ModelProfile;
import andrey.timeit.model.ModelTask;

/**
 * Created by Andrey on 12.07.2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "time_it_database";

    public static final String TASKS_TABLE = "tasks_table";
    public static final String PROFILE_TABLE = "profile_table";
    public static final String DURATION_CATEGORY_TABLE="duration_category_table";
    public static final String ADVICE_TABLE = "advice_table";

    public static final String TASK_TITLE_COLUMN = "task_title";
    public static final String TASK_DATE_COLUMN = "task_date";
    public static final String TASK_CATEGORY_COLUMN = "task_priority";
    public static final String TASK_STATUS_COLUMN = "task_status";
    public static final String TASK_TIME_STAMP_COLUMN = "task_time_stamp";
    public static final String TASK_TIME_START_COLUMN = "task_time_start";
    public static final String TASK_TIME_STOP_COLUMN = "task_time_stop";


    public static final String DURATION_CATEGORY_WORK_COLUMN = "duration_category_work_column";
    public static final String DURATION_CATEGORY_REST_COLUMN = "duration_category_rest_column";
    public static final String DURATION_CATEGORY_FAMILY_COLUMN = "duration_category_family_column";
    public static final String DURATION_CATEGORY_SPORT_COLUMN = "duration_category_sport_column";

    public static final String ADVICE_MESSAGE_COLUMN = "advice_message_column";
    public static final String ADVICE_KEY_COLUMN = "advice_key_column";

    public static final String PROFILE_LOGIN_COLUMN = "profile_login";
    public static final String PROFILE_PASSWORD_COLUMN = "profile_password";
    public static final String PROFILE_NAME_COLUMN = "profile_name";
    public static final String PROFILE_ACTIVITY_COLUMN = "profile_activity";
    public static final String PROFILE_AGE_COLUMN = "profile_age";
    public static final String PROFILE_GENDER_COLUMN = "profile_gender";
    public static final String PROFILE_WEIGHT_COLUMN = "profile_weight";
    public static final String PROFILE_GROWTH_COLUMN = "profile_growth";
    public static final String PROFILE_EMAIL_COLUMN = "profile_email";

    private static final String TASKS_TABLE_CREATE_SCRIPT =  "CREATE TABLE "
            + TASKS_TABLE + " (" + BaseColumns._ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TASK_TITLE_COLUMN + " TEXT NOT NULL, "
            + TASK_DATE_COLUMN + " LONG, " + TASK_CATEGORY_COLUMN + " INTEGER, "
            + TASK_STATUS_COLUMN + " INTEGER, " + TASK_TIME_START_COLUMN + " LONG, "
            + TASK_TIME_STOP_COLUMN + " LONG, "
            + TASK_TIME_STAMP_COLUMN + " LONG);";

    private static final String DURATION_TABLE_CREATE_SCRIPT =  "CREATE TABLE "
            + DURATION_CATEGORY_TABLE + " (" + BaseColumns._ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DURATION_CATEGORY_WORK_COLUMN + " LONG, "
            + DURATION_CATEGORY_REST_COLUMN + " LONG, " + DURATION_CATEGORY_FAMILY_COLUMN + " LONG, "
            + DURATION_CATEGORY_SPORT_COLUMN + " LONG);";

    private static final String ADVICE_TABLE_CREATE_SCRIPT = "CREATE TABLE "
            + ADVICE_TABLE + " (" + BaseColumns._ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ADVICE_MESSAGE_COLUMN + " TEXT, "
            + ADVICE_KEY_COLUMN + " DOUBLE);";

    private static final String PROFILE_TABLE_CREATE_SCRIPT =  "CREATE TABLE "
            + PROFILE_TABLE + " (" + BaseColumns._ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PROFILE_LOGIN_COLUMN + " TEXT, "
            + PROFILE_PASSWORD_COLUMN + " TEXT, " + PROFILE_NAME_COLUMN + " TEXT, "
            + PROFILE_ACTIVITY_COLUMN + " TEXT, " + PROFILE_AGE_COLUMN + " INTEGER, "
            + PROFILE_GENDER_COLUMN + " TEXT, " + PROFILE_WEIGHT_COLUMN + " INTEGER, "
            + PROFILE_GROWTH_COLUMN + " INTEGER, " + PROFILE_EMAIL_COLUMN + " TEXT);";

    public static final String SELECTION_STATUS = DBHelper.TASK_STATUS_COLUMN + " = ?";
    public static final String SELECTION_TIME_STAMP = TASK_TIME_STAMP_COLUMN + " = ?";
    public static final String SELECTION_LIKE_TITLE = TASK_TITLE_COLUMN + " LIKE ?";

    public DBQueryManager queryManager;
    public DBUpdateManager updateManager;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        queryManager = new DBQueryManager(getReadableDatabase());
        updateManager = new DBUpdateManager(getWritableDatabase());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TASKS_TABLE_CREATE_SCRIPT);
        db.execSQL(DURATION_TABLE_CREATE_SCRIPT);
        db.execSQL(ADVICE_TABLE_CREATE_SCRIPT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TASKS_TABLE);
        db.execSQL("DROP TABLE " + DURATION_CATEGORY_TABLE);
        db.execSQL("DROP TABLE " + ADVICE_TABLE);
        onCreate(db);
    }

    public void saveTask(ModelTask task) {
        ContentValues newValues = new ContentValues();

        newValues.put(TASK_TITLE_COLUMN, task.getTitle());
        newValues.put(TASK_DATE_COLUMN, task.getDate());
        newValues.put(TASK_STATUS_COLUMN, task.getStatus());
        newValues.put(TASK_CATEGORY_COLUMN, task.getCategory());
        newValues.put(TASK_TIME_STAMP_COLUMN, task.getTimeStamp());

        getWritableDatabase().insert(TASKS_TABLE, null, newValues);
    }

    public void saveDuration(ModelDuration duration) {
        SQLiteDatabase database = queryManager.getDatabase();
        Cursor c = database.query(DBHelper.DURATION_CATEGORY_TABLE, null, null, null, null, null, null);
        if (!c.moveToFirst()) {

            ContentValues newValues = new ContentValues();

            newValues.put(DURATION_CATEGORY_WORK_COLUMN, duration.getWorkCategoryDuration());
            newValues.put(DURATION_CATEGORY_REST_COLUMN, duration.getRestCategoryDuration());
            newValues.put(DURATION_CATEGORY_SPORT_COLUMN, duration.getSportCategoryDuration());
            newValues.put(DURATION_CATEGORY_FAMILY_COLUMN, duration.getFamilyCategoryDuration());

            getWritableDatabase().insert(DURATION_CATEGORY_TABLE, null, newValues);
        }
    }

    public void saveAdvice(ModelAdvice advice) {

            ContentValues newValues = new ContentValues();

            newValues.put(ADVICE_MESSAGE_COLUMN, advice.getText());
            newValues.put(ADVICE_KEY_COLUMN, advice.getCoeff());

            getWritableDatabase().insert(ADVICE_TABLE, null, newValues);
        }

    public void saveProfile(ModelProfile profile) {
        ContentValues newValues = new ContentValues();
        newValues.put(PROFILE_LOGIN_COLUMN, profile.getLogin());
        newValues.put(PROFILE_PASSWORD_COLUMN, profile.getPassword());
        newValues.put(PROFILE_NAME_COLUMN,profile.getName());
        newValues.put(PROFILE_ACTIVITY_COLUMN, profile.getActivity());
        newValues.put(PROFILE_AGE_COLUMN, profile.getGender());
        newValues.put(PROFILE_GENDER_COLUMN, profile.getGender());
        newValues.put(PROFILE_WEIGHT_COLUMN, profile.getWeight());
        newValues.put(PROFILE_GROWTH_COLUMN, profile.getGrowth());
        newValues.put(PROFILE_EMAIL_COLUMN, profile.getEmail());

        getWritableDatabase().insert(PROFILE_TABLE, null, newValues);
    }

    public DBQueryManager query() {
        return queryManager;
    }

    public DBUpdateManager update() {
        return updateManager;
    }

    public void removeTask(long timeStamp) {
        getWritableDatabase().delete(TASKS_TABLE, SELECTION_TIME_STAMP, new String[]{Long.toString(timeStamp)});
    }

    public void removeProfile(String login) {
        getWritableDatabase().delete(PROFILE_TABLE, null, new String[]{login}); //Изменить
    }
}
