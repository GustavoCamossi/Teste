package c.e.g.v.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.example.gustavocamossi.vidafit1.R;

public class MessagemAlerta {

    public static void mensagemDialog(Context context, String title, String message, DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(context, R.style.Theme_AppCompat_Light_Dialog_Alert)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Ok", listener)
                .create().show();
    }
}
