package andrey.timeit.fragments;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import andrey.timeit.alarm.AlarmHelper;
import andrey.timeit.MainActivity;
import andrey.timeit.R;
import andrey.timeit.adapter.TaskAdapter;
import andrey.timeit.dataBase.DBHelper;
import andrey.timeit.dialog.EditTaskDialogFragment;
import andrey.timeit.model.ModelDuration;
import andrey.timeit.model.ModelTask;
import andrey.timeit.model.item;

/**
 * Created by Andrey on 11.07.2016.
 */
public abstract class TaskFragment extends Fragment {

    protected RecyclerView recyclerView;
    protected RecyclerView.LayoutManager layoutManager;

    public ModelDuration modelDuration = new ModelDuration();

    protected TaskAdapter adapter;

    public MainActivity activity;

    public AlarmHelper alarmHelper;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() != null) {
            activity = (MainActivity) getActivity();
        }

        alarmHelper = AlarmHelper.getInstance();

        addTaskFromDB();
    }

    public abstract void addTask(ModelTask newTask, boolean saveToDB);

    public void updateTask(ModelTask task) {
        adapter.updateTask(task);
    }

    public void removeTaskDialog(final int location) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

        dialogBuilder.setMessage(R.string.dialog_removing_message);

        item item = adapter.getItem(location);

        if (item.isTask()) {

            ModelTask removingTask = (ModelTask) item;

            final long timeStamp = removingTask.getTimeStamp();
            final boolean[] isRemoved = {false};

            dialogBuilder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    adapter.removeItem(location);
                    isRemoved[0] = true;
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.container),
                            R.string.task_removed, Snackbar.LENGTH_LONG);
                    snackbar.setAction(R.string.repeal_adding_task, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            addTask(activity.dbHelper.query().getTask(timeStamp), false);
                            isRemoved[0] = false;
                        }
                    });
                    snackbar.getView().addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                        @Override
                        public void onViewAttachedToWindow(View v) {

                        }

                        @Override
                        public void onViewDetachedFromWindow(View v) {
                            if (isRemoved[0]) {
                                alarmHelper.removeAlarm(timeStamp);
                                activity.dbHelper.removeTask(timeStamp);
                            }
                        }
                    });

                    snackbar.show();

                    dialog.dismiss();
                }
            });

            dialogBuilder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

        }

        dialogBuilder.show();
    }

    public void showTaskEditDialog(ModelTask task) {
        DialogFragment editingTaskDialog = EditTaskDialogFragment.newInstance(task);
        editingTaskDialog.show(getActivity().getFragmentManager(), "EditTaskDialogFragment");
    }

    public abstract void findTasks(String title);

    public abstract void addTaskFromDB();

    public abstract void moveTask(ModelTask task);
}
