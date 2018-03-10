package com.example.admin.kakawev2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;


/**
 * Created by admin on 10/03/2018.
 */

public class DialogConfirmarSalirApp extends DialogFragment {

    AvisarSalirApp oyente;
    public interface AvisarSalirApp{
        public void aceptarSalirApp();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        oyente=(AvisarSalirApp)getActivity();
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                oyente.aceptarSalirApp();
            }
        });
        return builder.create();
    }
}
