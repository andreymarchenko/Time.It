package andrey.timeit.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import andrey.timeit.MainActivity;
import andrey.timeit.adapter.TAdapter;
import andrey.timeit.model.MTask;

/**
 * Created by Andrey on 11.07.2016.
 */
public abstract class TFragment extends Fragment {

    protected RecyclerView recyclerView;
    protected RecyclerView.LayoutManager layoutManager;

    protected TAdapter adapter;

    public MainActivity activity;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() != null) {
            activity = (MainActivity) getActivity();
        }
        addTaskFromDB();
    }

    public void addTask(MTask newTask, boolean saveToDB) {
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
        } else {
            adapter.addItem(newTask);
        }
        if(saveToDB) {
            activity.dbHelper.saveTask(newTask);
        }

    }


    public abstract void addTaskFromDB();

    public abstract void moveTask(MTask task);
}
