package andrey.timeit.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import andrey.timeit.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class DoneTasksFragment extends Fragment {

    private RecyclerView rvDoneTasks;
    private RecyclerView.LayoutManager layoutManager;

    public DoneTasksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_done_tasks, container, false);
        rvDoneTasks = (RecyclerView) rootView.findViewById(R.id.rv_done_task);

        layoutManager = new LinearLayoutManager(getActivity());

        rvDoneTasks.setLayoutManager(layoutManager);

        // Inflate the layout for this fragment
        return rootView;
    }

}
