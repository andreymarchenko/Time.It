package andrey.timeit;

import java.text.SimpleDateFormat;
import java.util.SimpleTimeZone;

/**
 * Created by Andrey on 09.07.2016.
 */
public class Utils {
    public static String getDate(long date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
        return dateFormat.format(date);
    }

    public static String getTime(long time) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH.mm");
        return timeFormat.format(time);

    }


}
