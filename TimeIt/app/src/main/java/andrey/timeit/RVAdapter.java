package andrey.timeit;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Andrey on 07.07.2016.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.TaskViewHolder> {
    public static class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        CardView cardView;
        TextView textTask;
        ImageView imageTask;
        ImageView imagePlay;
        ImageView imageStop;
        ImageView imagePause;

        TaskViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            textTask = (TextView) itemView.findViewById(R.id.taskText);
            imageTask = (ImageView) itemView.findViewById(R.id.image1);
            imagePlay = (ImageView) itemView.findViewById(R.id.startImage);
            imageStop = (ImageView) itemView.findViewById(R.id.stopImage);
            imagePause = (ImageView) itemView.findViewById(R.id.pauseImage);

            imagePlay.setOnClickListener(this);
            imageStop.setOnClickListener(this);
            imagePause.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position!=RecyclerView.NO_POSITION) {
                switch (v.getId()) {
                    case R.id.startImage:
                        startClick(position);
                        Snackbar.make(v, "Действие добавлено", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        break;
                    case R.id.stopImage:
                        stopClick(position);
                        Snackbar.make(v, "Действие завершено", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        break;
                    case R.id.pauseImage:
                        pauseClick(position);
                        Snackbar.make(v, "Действие приостановлено", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        break;
                }
            }
        }

        private void startClick(int position) {
            Log.i("СТАРТ!", " ");
        }
        private void stopClick(int position) {
            Log.i("СТОП!", " ");
        }
        private void pauseClick(int position) {
            Log.i("ПАУЗА!", " ");
        }

    }

    List<Task> tasks;

    RVAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_item, viewGroup, false);
        TaskViewHolder pvh = new TaskViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(TaskViewHolder taskViewHolder, int i) {
        taskViewHolder.textTask.setText(tasks.get(i).taskText);
        taskViewHolder.imageTask.setImageResource(tasks.get(i).mainImage);
        taskViewHolder.imagePlay.setImageResource(tasks.get(i).startImage);
        taskViewHolder.imageStop.setImageResource(tasks.get(i).stopImage);
        taskViewHolder.imagePause.setImageResource(tasks.get(i).pauseImage);
    }
}

