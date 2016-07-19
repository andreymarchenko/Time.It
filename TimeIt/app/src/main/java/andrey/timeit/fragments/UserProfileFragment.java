package andrey.timeit.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import andrey.timeit.MainActivity;
import andrey.timeit.R;
import andrey.timeit.adapter.ProfileAdapter;
import andrey.timeit.model.ModelProfile;
import andrey.timeit.model.ModelProfileItem;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserProfileFragment extends Fragment {

    protected RecyclerView recyclerView;
    protected RecyclerView.LayoutManager layoutManager;

    public ProfileAdapter adapter;

    MainActivity mainActivity;

    public UserProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null) {
            mainActivity = (MainActivity) getActivity();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_user_profile, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_profile);

        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);

        adapter = new ProfileAdapter(this);

        int itemNumber = 9;

        ModelProfileItem[] modelProfileItems = new ModelProfileItem[itemNumber];

        for (int i = 0; i < itemNumber; i++) {
            adapter.addItem(modelProfileItems[i]);
        }

        ModelProfile modelProfile = new ModelProfile
                ("marchenko", "123456", "Andrey", "It-technology", "21", "male", "80", "185", "andrey-95mm@yandex.ru");


        //mainActivity.dbHelper.saveProfile(modelProfile);

        recyclerView.setAdapter(adapter);

        // Inflate the layout for this fragment
        return rootView;
    }
}
