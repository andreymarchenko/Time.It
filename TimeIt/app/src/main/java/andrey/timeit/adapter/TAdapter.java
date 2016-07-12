package andrey.timeit.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import andrey.timeit.fragments.TFragment;
import andrey.timeit.model.item;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Andrey on 11.07.2016.
 */
public abstract class TAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<item> items;

    TFragment taskFragment;

    public TAdapter(TFragment taskFragment) {
        this.taskFragment = taskFragment;
        items = new ArrayList<>();
    }

    public item getItem(int position) {
        return items.get(position);
    }

    public void addItem(item item) {
        items.add(item);
        notifyItemInserted(getItemCount()-1);
    }

    public void addItem(int location, item item) {
        items.add(location, item);
        notifyItemInserted(location);
    }

    public void removeItem(int location) {
        if(location >=0 && location <=getItemCount()-1) {
            items.remove(location);
            notifyItemRemoved(location);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    protected class TaskViewHolder extends RecyclerView.ViewHolder {
        protected TextView title;
        protected TextView date;
        protected CircleImageView category;

        public TaskViewHolder(View itemView, TextView title, TextView date, CircleImageView category) {
            super(itemView);
            this.title = title;
            this.date = date;
            this.category = category;
        }
    }

    public TFragment getTaskFragment() {
        return taskFragment;
    }
}
