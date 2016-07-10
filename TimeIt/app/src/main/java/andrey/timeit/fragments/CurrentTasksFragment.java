package andrey.timeit.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import andrey.timeit.R;
import andrey.timeit.adapter.CurrentTasksAdapter;
import andrey.timeit.model.ModelTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentTasksFragment extends Fragment {


    private RecyclerView rvCurrentTasks;
    private RecyclerView.LayoutManager layoutManager;

    private CurrentTasksAdapter adapter;

    public CurrentTasksFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_current_tasks, container, false);
        rvCurrentTasks = (RecyclerView) rootView.findViewById(R.id.rv_current_task);

        layoutManager = new LinearLayoutManager(getActivity());

        rvCurrentTasks.setLayoutManager(layoutManager);

        adapter = new CurrentTasksAdapter();
        rvCurrentTasks.setAdapter(adapter);

        // Inflate the layout for this fragment
        return rootView;
    }

    public void addTask(ModelTask newTask) {
        int position = -1;

        for (int i = 0; i < adapter.getItemCount(); i++) {
            if (adapter.getItem(i).IsTask()) {
                ModelTask task = (ModelTask) adapter.getItem(i);
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

}
