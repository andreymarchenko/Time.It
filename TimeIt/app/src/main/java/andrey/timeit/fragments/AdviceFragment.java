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
import java.util.List;

import andrey.timeit.MainActivity;
import andrey.timeit.R;
import andrey.timeit.adapter.AdviceAdapter;
import andrey.timeit.adapter.ProfileAdapter;
import andrey.timeit.model.ModelAdvice;
import andrey.timeit.model.ModelDuration;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdviceFragment extends Fragment {

    public MainActivity activity;

    protected RecyclerView recyclerView;
    protected RecyclerView.LayoutManager layoutManager;

    protected AdviceAdapter adapter;

    public AdviceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_advice, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_advice);

        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);

        adapter = new AdviceAdapter(this);

        activity = (MainActivity) getActivity();

        ModelAdvice modelAdvice1 = new ModelAdvice("Совет 1",0.3);
        ModelAdvice modelAdvice2 = new ModelAdvice("Cовет 2", 0.3);
        ModelAdvice modelAdvice3 = new ModelAdvice("Совет 3", 0.3);
        ModelAdvice modelAdvice4 = new ModelAdvice("Совет 4",0.2);
        ModelAdvice modelAdvice5 = new ModelAdvice("Cовет 5", 0.8);
        ModelAdvice modelAdvice6 = new ModelAdvice("Совет 6", 1.4);
        ModelAdvice modelAdvice7 = new ModelAdvice("Совет 7",0.2);
        ModelAdvice modelAdvice8 = new ModelAdvice("Cовет 8", 0.8);
        ModelAdvice modelAdvice9 = new ModelAdvice("Совет 9", 1.4);

        //activity.dbHelper.saveAdvice(modelAdvice1);
        //activity.dbHelper.saveAdvice(modelAdvice2);
        //activity.dbHelper.saveAdvice(modelAdvice3);

//        List<ModelAdvice> modelAdvice4 = activity.dbHelper.queryManager.getAdvice(2);

        View secondView = inflater.inflate(R.layout.fragment_statistic, container, false);

        TextView workView = (TextView) secondView.findViewById(R.id.work_category_view);
        TextView familyView = (TextView) secondView.findViewById(R.id.family_category_view);
        TextView restView = (TextView) secondView.findViewById(R.id.rest_category_view);
        TextView sportView = (TextView) secondView.findViewById(R.id.sport_category_view);
        TextView coefficientView = (TextView) secondView.findViewById(R.id.coefficient_instance);

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

        //BigDecimal newCoeff = new BigDecimal(coeff);
        //newCoeff = newCoeff.setScale(1, BigDecimal.ROUND_DOWN);


        if (coeff < 0.5) {
            adapter.addItem(modelAdvice1);
            adapter.addItem(modelAdvice2);
            adapter.addItem(modelAdvice3);
        }
        if (coeff >= 0.5 && coeff < 1) {
            adapter.addItem(modelAdvice4);
            adapter.addItem(modelAdvice5);
            adapter.addItem(modelAdvice6);
        }
        if (coeff >= 1) {
            adapter.addItem(modelAdvice7);
            adapter.addItem(modelAdvice8);
            adapter.addItem(modelAdvice9);

        }

        recyclerView.setAdapter(adapter);

        return rootView;
    }
}