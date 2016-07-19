package andrey.timeit.adapter;

import android.content.res.Resources;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import andrey.timeit.R;
import andrey.timeit.fragments.AdviceFragment;
import andrey.timeit.model.ModelAdvice;
import andrey.timeit.model.item;

/**
 * Created by Andrey on 18.07.2016.
 */
public class AdviceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ModelAdvice> advices;

    AdviceFragment adviceFragment;

    public AdviceAdapter(AdviceFragment adviceFragment) {
        this.adviceFragment = adviceFragment;
        advices = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_advice, parent, false);
        TextView message = (TextView) v.findViewById(R.id.advice_message);
        int key = 0;

        return new AdviceViewHolder(v, message, key);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ModelAdvice modelAdvice = advices.get(position);
        AdviceViewHolder adviceViewHolder = (AdviceViewHolder) holder;

        final View itemView = adviceViewHolder.itemView;

        adviceViewHolder.message.setText(modelAdvice.getText());
        adviceViewHolder.key = modelAdvice.getCoeff();

    }

    @Override
    public int getItemCount() {
        return advices.size();
    }

    public void addItem(ModelAdvice modelAdvice) {
        advices.add(modelAdvice);
        notifyItemInserted(getItemCount()-1);
    }

    public class AdviceViewHolder extends RecyclerView.ViewHolder {
        protected TextView message;
        protected double key;

        public AdviceViewHolder(View itemView, TextView message, double key) {
            super(itemView);
            this.message = message;
            this.key = key;
        }

    }
}
