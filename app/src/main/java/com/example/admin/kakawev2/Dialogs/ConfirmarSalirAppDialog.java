package com.example.admin.kakawev2.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;


/**
 * Created by admin on 10/03/2018.
 */

public class ConfirmarSalirAppDialog extends DialogFragment {

    AvisarSalirApp oyente;
    public interface AvisarSalirApp{
        public void aceptarSalirApp();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        oyente=(AvisarSalirApp)getActivity();
        builder.setMessage("¿Realmente quieres salir de Kakawe?");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                oyente.aceptarSalirApp();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }
}
