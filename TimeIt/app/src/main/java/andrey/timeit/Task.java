package andrey.timeit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrey on 07.07.2016.
 */
public class Task {
    String taskText;
    int mainImage;
    int startImage;
    int stopImage;
    int pauseImage;

    Task(String _taskText, int _photoId, int _startImage, int _stopImage, int _pauseImage) {
        this.taskText = _taskText;
        this.mainImage = _photoId;
        this.startImage = _startImage;
        this.stopImage = _stopImage;
        this.pauseImage = _pauseImage;
    }
}
