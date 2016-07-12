package andrey.timeit.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import andrey.timeit.DataBase.DBManager;
import andrey.timeit.R;
import andrey.timeit.adapter.CTAdapter;
import andrey.timeit.model.MTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class CTFragment extends TFragment {

    public CTFragment() {
        // Required empty public constructor
    }

    OnTaskDoneListener onTaskDoneListener;

    public interface OnTaskDoneListener {
        void onTaskDone(MTask task);
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            onTaskDoneListener = (OnTaskDoneListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + "need implement OnTaskDoneListener");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_current_tasks, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_current_task);

        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);

        adapter = new CTAdapter(this);
        recyclerView.setAdapter(adapter);

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void addTaskFromDB() {
        List<MTask> tasks = new ArrayList<>();
        tasks.addAll(activity.dbHelper.query().getTasks(DBManager.SELECTION_STATUS + " OR "
                + DBManager.SELECTION_STATUS, new String[]{Integer.toString(MTask.STATUS_CURRENT),
                Integer.toString(MTask.STATUS_OVERDUE)}, DBManager.TASK_DATE_COLUMN));
        for (int i = 0; i < tasks.size(); i++) {
            addTask(tasks.get(i), false);
        }
    }

    @Override
    public void moveTask(MTask task) {
        onTaskDoneListener.onTaskDone(task);
    }
}
