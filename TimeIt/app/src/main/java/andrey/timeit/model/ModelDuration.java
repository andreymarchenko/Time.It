package andrey.timeit.model;

/**
 * Created by Andrey on 18.07.2016.
 */
public class ModelDuration {
    long workCategoryDuration;
    long sportCategoryDuration;
    long restCategoryDuration;
    long familyCategoryDuration;

    public ModelDuration() {
        this.workCategoryDuration = 0;
        this.sportCategoryDuration = 0;
        this.restCategoryDuration = 0;
        this.familyCategoryDuration = 0;
    }

    public ModelDuration(long work_category_duration, long sport_category_duration, long rest_category_duration, long family_category_duration) {
        this.workCategoryDuration = work_category_duration;
        this.sportCategoryDuration = sport_category_duration;
        this.restCategoryDuration = rest_category_duration;
        this.familyCategoryDuration = family_category_duration;
    }

    public long getWorkCategoryDuration() {
        return workCategoryDuration;
    }

    public void setWorkCategoryDuration(long workCategoryDuration) {
        this.workCategoryDuration = workCategoryDuration;
    }

    public long getSportCategoryDuration() {
        return sportCategoryDuration;
    }

    public void setSportCategoryDuration(long sportCategoryDuration) {
        this.sportCategoryDuration = sportCategoryDuration;
    }

    public long getRestCategoryDuration() {
        return restCategoryDuration;
    }

    public void setRestCategoryDuration(long restCategoryDuration) {
        this.restCategoryDuration = restCategoryDuration;
    }

    public long getFamilyCategoryDuration() {
        return familyCategoryDuration;
    }

    public void setFamilyCategoryDuration(long familyCategoryDuration) {
        this.familyCategoryDuration = familyCategoryDuration;
    }
}
