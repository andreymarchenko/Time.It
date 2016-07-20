package andrey.timeit.model;

import java.util.Date;

import andrey.timeit.R;

/**
 * Created by Andrey on 10.07.2016.
 */
public class ModelTask implements item {

    public static final int CATEGORY_WORK = 0;
    public static final int CATEGORY_FAMILY = 1;
    public static final int CATEGORY_REST = 2;
    public static final int CATEGORY_SPORT = 3;

    public static final String[] CATEGORY_LEVELS = {"Работа", "Семья", "Отдых", "Спорт"};

    public static final int STATUS_OVERDUE = 0;
    public static final int STATUS_CURRENT = 1;
    public static final int STATUS_DONE = 2;

    private int category;
    private int status;

    private String title;
    private long date;
    private long timeStamp;
    private long timeStop;
    private long timeStart;
    private int dateStatus;

    public ModelTask() {
        this.status = -1;
        this.timeStamp = new Date().getTime();
    }

    public static int getCategoryFamily() {
        return CATEGORY_FAMILY;
    }

    public long getTimeStop() {
        return timeStop;
    }

    public void setTimeStop(long timeStop) {
        this.timeStop = timeStop;
    }

    public long getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(long timeStart) {
        this.timeStart = timeStart;
    }

    public ModelTask(String title, long date, int category, int status, long timeStamp, long timeStart, long timeStop) {
        this.title = title;
        this.date = date;
        this.category = category;
        this.status = status;
        this.timeStamp = timeStamp;

        this.timeStart = timeStart;
        this.timeStop = timeStop;
    }

    public int getCategoryColor() {
        switch (getCategory()) {
            case CATEGORY_FAMILY:
                if (getStatus() == STATUS_CURRENT || getStatus() == STATUS_OVERDUE) {
                    return R.color.category_family;
                } else return R.color.category_family_selected;
            case CATEGORY_WORK:
                if (getStatus() == STATUS_CURRENT || getStatus() == STATUS_OVERDUE) {
                    return R.color.category_work;
                } else return R.color.category_work_selected;
            case CATEGORY_REST:
                if (getStatus() == STATUS_CURRENT || getStatus() == STATUS_OVERDUE) {
                    return R.color.category_rest;
                } else return R.color.category_rest_selected;
            case CATEGORY_SPORT:
                if (getStatus() == STATUS_CURRENT || getStatus() == STATUS_OVERDUE) {
                    return R.color.category_sport;
                } else return R.color.category_sport_selected;
            default:
                return 0;

        }
    }

    @Override
    public boolean isTask() {
        return true;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public static int getCategoryWork() {
        return CATEGORY_WORK;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getDateStatus() {
        return dateStatus;
    }

    public void setDateStatus(int dateStatus) {
        this.dateStatus = dateStatus;
    }
}
