package andrey.timeit.adapter;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import andrey.timeit.R;
import andrey.timeit.Utils;
import andrey.timeit.fragments.CurrentTasksFragment;
import andrey.timeit.model.ModelSeparator;
import andrey.timeit.model.ModelTask;
import andrey.timeit.model.item;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Andrey on 10.07.2016.
 */
public class CurrentTasksAdapter extends TaskAdapter {

    private static final int TYPE_TASK = 0;
    private static final int TYPE_SEPARATOR = 1;

    Calendar calendar = null;

    public CurrentTasksAdapter(CurrentTasksFragment taskFragment) {
        super(taskFragment);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case TYPE_TASK:
                View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.model_task, viewGroup, false);
                TextView title = (TextView) v.findViewById(R.id.TaskTitle);
                TextView date = (TextView) v.findViewById(R.id.TaskDate);
                CircleImageView category = (CircleImageView) v.findViewById(R.id.cvTaskCategory);

                return new TaskViewHolder(v, title, date, category);

            case TYPE_SEPARATOR:
                View separator = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.model_separator, viewGroup, false);
                TextView type = (TextView) separator.findViewById(R.id.tvSeparatorName);

                return new SeparatorViewHolder(separator, type);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        item item = items.get(position);

        final Resources resources = viewHolder.itemView.getResources();

        if (item.isTask()) {
            viewHolder.itemView.setEnabled(true);
            final ModelTask task = (ModelTask) item;
            final TaskViewHolder taskViewHolder = (TaskViewHolder) viewHolder;

            final View itemView = taskViewHolder.itemView;


            taskViewHolder.title.setText(task.getTitle());
            if (task.getDate() != 0) {
                taskViewHolder.date.setText(Utils.getFullDate(task.getDate()));
            } else {
                taskViewHolder.date.setText(null);
            }
            itemView.setVisibility(View.VISIBLE);

            taskViewHolder.category.setEnabled(true);

            if (task.getDate() != 0 && task.getDate() < Calendar.getInstance().getTimeInMillis()) {
                itemView.setBackgroundColor(resources.getColor(R.color.gray_200));
            } else {
                itemView.setBackgroundColor(resources.getColor(R.color.gray_50));
            }

            taskViewHolder.title.setTextColor(resources.getColor(R.color.primary_text_default_material_light));
            taskViewHolder.date.setTextColor(resources.getColor(R.color.secondary_text_default_material_light));
            taskViewHolder.category.setColorFilter(resources.getColor(task.getCategoryColor()));
            taskViewHolder.category.setImageResource(R.drawable.ic_checkbox_blank_circle_white_48dp);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getTaskFragment().showTaskEditDialog(task);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                Handler handler = new Handler();

                @Override
                public boolean onLongClick(View v) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getTaskFragment().removeTaskDialog(taskViewHolder.getLayoutPosition());
                        }
                    }, 300);
                    return true;
                }
            });

            taskViewHolder.category.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    calendar = Calendar.getInstance();
                    long a = calendar.getTimeInMillis();
                    //Date date = new Date();
                    taskViewHolder.category.setEnabled(false);
                    task.setStatus(ModelTask.STATUS_DONE);
                    task.setTimeStop(calendar.getTimeInMillis());

                    getTaskFragment().activity.dbHelper.update().status(task.getTimeStamp(), ModelTask.STATUS_DONE, 0);

                    getTaskFragment().activity.dbHelper.update().durationCategory(task.getTimeStamp(),
                            task.getTimeStart(), task.getTimeStop(), task.getCategory(), 1);

                    taskViewHolder.title.setTextColor(resources.getColor(R.color.primary_text_disabled_material_light));
                    taskViewHolder.date.setTextColor(resources.getColor(R.color.secondary_text_disabled_material_light));
                    taskViewHolder.category.setColorFilter(resources.getColor(task.getCategoryColor()));

                    ObjectAnimator flipIn = ObjectAnimator.ofFloat(taskViewHolder.category, "rotationY", -180f, 0f);

                    flipIn.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            if (task.getStatus() == ModelTask.STATUS_DONE) {
                                taskViewHolder.category.setImageResource(R.drawable.ic_check_circle_white_24dp);
                            }
                            ObjectAnimator translationX = ObjectAnimator.ofFloat(itemView,
                                    "translationX", 0f, itemView.getWidth());

                            ObjectAnimator translationXBack = ObjectAnimator.ofFloat(itemView,
                                    "translationXBack", itemView.getWidth(), 0f);

                            translationX.addListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    itemView.setVisibility(View.GONE);
                                    getTaskFragment().moveTask(task);
                                    removeItem(taskViewHolder.getLayoutPosition());
                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animation) {

                                }
                            });

                            AnimatorSet translationSet = new AnimatorSet();
                            translationSet.play(translationX).before(translationXBack);
                            translationSet.start();

                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    flipIn.start();
                }
            });
        } else {

            ModelSeparator separator = (ModelSeparator) item;
            SeparatorViewHolder separatorViewHolder = (SeparatorViewHolder) viewHolder;

            separatorViewHolder.type.setText(resources.getString(separator.getType()));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position).isTask()) {
            return TYPE_TASK;
        } else return TYPE_SEPARATOR;
    }
}
