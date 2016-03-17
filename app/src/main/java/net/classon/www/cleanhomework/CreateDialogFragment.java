package net.classon.www.cleanhomework;

/**
 * Created by classon6513 on 3/1/2016.
 */
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CreateDialogFragment extends DialogFragment {

    private EditText mEditText;
    private Button createButton;


    public interface DialogListener {
        void onFinishUserDialog(String user, int i);
    }

    // Empty constructor required for DialogFragment
    public CreateDialogFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_button_dialog, container);
        mEditText = (EditText) view.findViewById(R.id.foldername);
        createButton = (Button) view.findViewById(R.id.createButton);
        createButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DialogListener activity = (DialogListener) getActivity();
                String var = mEditText.getText().toString();
                activity.onFinishUserDialog(mEditText.getText().toString(),0);
                dismiss();
            }
        });
        // set this instance as callback for editor action
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        return view;
    }
}