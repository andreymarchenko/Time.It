package andrey.timeit.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.Calendar;

import andrey.timeit.R;
import andrey.timeit.Utils;
import andrey.timeit.model.MTask;

/**
 * Created by Andrey on 09.07.2016.
 */
public class ATDialog extends DialogFragment {

    private AddingTaskListener addingTaskListener;

    public interface AddingTaskListener {
        void onTaskAdded(MTask newTask);

        void onTaskAddingCancel();
    }

    @Override
    public void onAttach(Activity activity) { //Проверяем, выполняется ли интерфейс или кидается исключение
        super.onAttach(activity);
        try {
            addingTaskListener = (AddingTaskListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + "must implement AddingTaskListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.dialog_title);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View container = inflater.inflate(R.layout.dialog_task, null);

        final TextInputLayout title = (TextInputLayout) container.findViewById(R.id.DialogTaskTitle);
        final EditText edTitle = title.getEditText();

        TextInputLayout date = (TextInputLayout) container.findViewById(R.id.DialogTaskDate);
        final EditText edDate = date.getEditText();

        TextInputLayout time = (TextInputLayout) container.findViewById(R.id.DialogTaskTime);
        final EditText edTime = time.getEditText();

        Spinner spinnerCategory = (Spinner) container.findViewById(R.id.spDialogTaskCategory);

        title.setHint(getResources().getString(R.string.task_title));
        date.setHint(getResources().getString(R.string.task_date));
        time.setHint(getResources().getString(R.string.task_time));

        builder.setView(container);

        final MTask task = new MTask();

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, MTask.CATEGORY_LEVELS);

        spinnerCategory.setAdapter(categoryAdapter);

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                task.setCategory(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 1);

        edDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edDate.length() == 0) {
                    edDate.setText(" ");
                }
                DialogFragment datePickerFragment = new DatePickerFragment() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        edDate.setText(Utils.getDate(calendar.getTimeInMillis()));
                    }

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        edDate.setText(null);
                    }
                };
                datePickerFragment.show(getFragmentManager(), "DatePickerFragment");
            }
        });


        edTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edTime.length() == 0) {
                    edTime.setText(" ");
                }
                DialogFragment timePickerFragment = new TimePickerFragment() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        calendar.set(Calendar.SECOND, 0);
                        edTime.setText(Utils.getTime(calendar.getTimeInMillis()));
                    }

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        edTime.setText(null);
                    }
                };
                timePickerFragment.show(getFragmentManager(), "TimePickerFragment");
            }
        });

        builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                task.setTitle(edTitle.getText().toString());
                if (edDate.length() != 0 || edTime.length() != 0) {
                    task.setDate(calendar.getTimeInMillis());
                }
                task.setStatus(MTask.STATUS_CURRENT);
                addingTaskListener.onTaskAdded(task);
                dialog.dismiss();
            }
        });

        builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addingTaskListener.onTaskAddingCancel();
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                final Button positiveButton = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE);
                if (edTitle.length() == 0) {
                    positiveButton.setEnabled(false);
                    title.setError(getResources().getString(R.string.dialog_error_empty_title));
                }
                edTitle.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.length() == 0) {
                            positiveButton.setEnabled(false);
                            title.setError(getResources().getString(R.string.dialog_error_empty_title));
                        } else {
                            positiveButton.setEnabled(true);
                            title.setErrorEnabled(false);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        });

        return alertDialog;
    }
}
