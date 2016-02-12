package net.classon.www.cleanhomework;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CreateButtonDialog {

    String txt;
    public void showDialog(Activity activity, String msg){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.create_button_dialog);

        TextView text = (TextView) dialog.findViewById(R.id.createTV);
        text.setText(msg);

        Button dialogButton = (Button) dialog.findViewById(R.id.);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edit = (EditText) dialog.findViewById(R.id.foldername);
                txt = edit.getText().toString();
                activity
                dialog.dismiss();
            }
        });

        dialog.show();

    }
}
