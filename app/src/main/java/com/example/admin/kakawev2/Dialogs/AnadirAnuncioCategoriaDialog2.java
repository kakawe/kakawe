package com.example.admin.kakawev2.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.admin.kakawev2.R;

/**
 * Created by jose on 22/02/2018.
 */

public class AnadirAnuncioCategoriaDialog2 extends DialogFragment{
    View vista;
    @Override
    public Dialog onCreateDialog(Bundle saveIntanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        vista = inflater.inflate(R.layout.dialog_anadir_anuncio2_categoria, null);

        builder.setView(vista);
        return builder.create();
    }


}
