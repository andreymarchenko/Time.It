package andrey.timeit.adapter;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import andrey.timeit.R;
import andrey.timeit.Utils;
import andrey.timeit.fragments.TKFragment;
import andrey.timeit.model.MTask;
import andrey.timeit.model.item;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Andrey on 11.07.2016.
 */
public class DTAdapter extends TAdapter {


    public DTAdapter(TKFragment taskFragment) {
        super(taskFragment);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.model_task, viewGroup, false);
        TextView title = (TextView) v.findViewById(R.id.TaskTitle);
        TextView date = (TextView) v.findViewById(R.id.TaskDate);
        CircleImageView category = (CircleImageView) v.findViewById(R.id.cvTaskCategory);

        return new TaskViewHolder(v, title, date, category);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        item item = items.get(position);

        if (item.IsTask()) {
            viewHolder.itemView.setEnabled(true);
            final MTask task = (MTask) item;
            final TaskViewHolder taskViewHolder = (TaskViewHolder) viewHolder;

            final View itemView = taskViewHolder.itemView;
            final Resources resources = itemView.getResources();

            taskViewHolder.title.setText(task.getTitle());
            if (task.getDate() != 0) {
                taskViewHolder.date.setText(Utils.getFullDate(task.getDate()));
            } else {
                taskViewHolder.date.setText(null);
            }
            itemView.setVisibility(View.VISIBLE);

            itemView.setBackgroundColor(resources.getColor(R.color.gray_200));

            taskViewHolder.title.setTextColor(resources.getColor(R.color.primary_text_disabled_material_light));
            taskViewHolder.date.setTextColor(resources.getColor(R.color.secondary_text_disabled_material_light));
            taskViewHolder.category.setColorFilter(resources.getColor(task.getCategoryColor()));
            taskViewHolder.category.setImageResource(R.drawable.ic_checkbox_blank_circle_white_48dp);

            taskViewHolder.category.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    task.setStatus(MTask.STATUS_CURRENT);

                    itemView.setBackgroundColor(resources.getColor(R.color.gray_50));

                    taskViewHolder.title.setTextColor(resources.getColor(R.color.primary_text_default_material_light));
                    taskViewHolder.date.setTextColor(resources.getColor(R.color.secondary_text_default_material_light));
                    taskViewHolder.category.setColorFilter(resources.getColor(task.getCategoryColor()));

                    ObjectAnimator flipIn = ObjectAnimator.ofFloat(taskViewHolder.category, "rotationY", 180f, 0f);
                    taskViewHolder.category.setImageResource(R.drawable.ic_checkbox_blank_circle_white_48dp);

                    flipIn.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            if (task.getStatus() != MTask.STATUS_DONE) {
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
        }
    }
}
