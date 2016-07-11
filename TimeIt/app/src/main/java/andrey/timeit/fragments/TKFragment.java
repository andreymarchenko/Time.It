package andrey.timeit.fragments;

import android.app.Fragment;
import android.support.v7.widget.RecyclerView;

import andrey.timeit.adapter.TAdapter;
import andrey.timeit.model.MTask;

/**
 * Created by Andrey on 11.07.2016.
 */
public abstract class TKFragment extends Fragment {

    protected RecyclerView recyclerView;
    protected RecyclerView.LayoutManager layoutManager;

    protected TAdapter adapter;

    public void addTask(MTask newTask) {
        int position = -1;

        for (int i = 0; i < adapter.getItemCount(); i++) {
            if (adapter.getItem(i).IsTask()) {
                MTask task = (MTask) adapter.getItem(i);
                if (newTask.getDate() < task.getDate()) {
                    position = i;
                    break;
                }
            }
        }
        if (position != -1) {
            adapter.addItem(position, newTask);
        } else adapter.addItem(newTask);
    }

    public abstract void moveTask(MTask task);
}
