package andrey.timeit.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import andrey.timeit.MainActivity;
import andrey.timeit.R;
import andrey.timeit.adapter.CurrentTasksAdapter;
import andrey.timeit.dataBase.DBHelper;
import andrey.timeit.model.ModelAdvice;
import andrey.timeit.model.ModelDuration;
import andrey.timeit.model.ModelTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatisticTasksFragment extends Fragment {

    public MainActivity activity;

    public StatisticTasksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_statistic, container, false);

        SearchView searchView = (SearchView) rootView.findViewById(R.id.search_view);

        TextView workView = (TextView) rootView.findViewById(R.id.work_category_view);
        TextView familyView = (TextView) rootView.findViewById(R.id.family_category_view);
        TextView restView = (TextView) rootView.findViewById(R.id.rest_category_view);
        TextView sportView = (TextView) rootView.findViewById(R.id.sport_category_view);
        TextView coefficientView = (TextView) rootView.findViewById(R.id.coefficient_instance);

        activity = (MainActivity) getActivity();

        ModelDuration modelDuration = activity.dbHelper.queryManager.getDuration();

        double allTime = modelDuration.getWorkCategoryDuration() + modelDuration.getFamilyCategoryDuration()
                + modelDuration.getRestCategoryDuration() + modelDuration.getSportCategoryDuration();

        double workTime = modelDuration.getWorkCategoryDuration();
        double familyTime = modelDuration.getFamilyCategoryDuration();
        double restTime = modelDuration.getRestCategoryDuration();
        double sportTime = modelDuration.getSportCategoryDuration();

        double coeffWork = workTime / allTime;
        double coeffFamily = familyTime / allTime;
        double coeffRest = restTime / allTime;
        double coeffSport = sportTime / allTime;

        double coeff = Math.abs(0.4 - coeffWork) + Math.abs(0.15 - coeffFamily) +
                Math.abs(0.3 - coeffRest) + Math.abs(0.15 - coeffSport);

        BigDecimal newWork = new BigDecimal(coeffWork);
        newWork = newWork.setScale(3, BigDecimal.ROUND_DOWN);

        BigDecimal newFamily = new BigDecimal(coeffFamily);
        newFamily = newFamily.setScale(3, BigDecimal.ROUND_DOWN);

        BigDecimal newRest = new BigDecimal(coeffRest);
        newRest = newRest.setScale(3, BigDecimal.ROUND_DOWN);

        BigDecimal newSport = new BigDecimal(coeffSport);
        newSport = newSport.setScale(3, BigDecimal.ROUND_DOWN);

        BigDecimal newCoeff = new BigDecimal(coeff);
        newCoeff = newCoeff.setScale(3, BigDecimal.ROUND_DOWN);

        workView.setText(String.valueOf(newWork));
        familyView.setText(String.valueOf(newFamily));
        restView.setText(String.valueOf(newRest));
        sportView.setText(String.valueOf(newSport));
        coefficientView.setText(String.valueOf(newCoeff));

        return rootView;

    }

}
