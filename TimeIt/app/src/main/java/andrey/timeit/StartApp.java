package andrey.timeit;

import android.app.Application;

/**
 * Created by Andrey on 17.07.2016.
 */
public class StartApp extends Application {
    private static boolean activityVisible;

    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
    }
}
