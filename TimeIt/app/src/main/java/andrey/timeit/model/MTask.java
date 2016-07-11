package andrey.timeit.model;

import andrey.timeit.R;

/**
 * Created by Andrey on 10.07.2016.
 */
public class MTask implements item {

    public static final int CATEGORY_WORK = 0;
    public static final int CATEGORY_FAMILY = 1;
    public static final int CATEGORY_REST = 2;
    public static final int CATEGORY_SPORT = 3;

    public static final String[] CATEGORY_LEVELS = {"Work", "Family", "Rest", "Sport"};

    public static final int STATUS_OVERDUE = 0;
    public static final int STATUS_CURRENT = 1;
    public static final int STATUS_DONE = 2;

    private int category;
    private int status;

    private String title;
    private long date;

    public MTask() {
        this.status = -1;
    }

    public MTask(String title, long date, int category, int status) {
        this.title = title;
        this.date = date;
        this.category = category;
        this.status = status;
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
    public boolean IsTask() {
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

}
