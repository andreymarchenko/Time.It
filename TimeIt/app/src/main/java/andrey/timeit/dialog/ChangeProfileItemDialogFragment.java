package andrey.timeit.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import andrey.timeit.R;

/**
 * Created by Andrey on 14.07.2016.
 */
public class ChangeProfileItemDialogFragment extends DialogFragment {
    TextInputLayout textInputLayout;
    EditText textDialog;
    int id;
    String title;
    String text;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setId(int id) {
        this.id=id;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(title);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View container = inflater.inflate(R.layout.dialog_profile, null);

        textInputLayout = (TextInputLayout) container.findViewById(R.id.profile_dialog_input);
        textDialog = textInputLayout.getEditText();

        builder.setView(container);

        builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();

        return alertDialog;
    }

}
