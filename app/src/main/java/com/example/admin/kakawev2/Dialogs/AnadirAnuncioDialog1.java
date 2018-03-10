package com.example.admin.kakawev2.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.admin.kakawev2.R;

/**
 * Created by jose on 19/02/2018.
 */

public class AnadirAnuncioDialog1 extends DialogFragment implements View.OnClickListener{

    View vista;
    String nomComunidad;
    ImageView bt_anadir_anuncio1_necesito,bt_anadir_anuncio1_ofrezco;

    @Override
    public Dialog onCreateDialog(Bundle saveIntanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        nomComunidad = getArguments().getString("nomComunidad");
        vista = inflater.inflate(R.layout.dialog_anadir_anuncio1,null);
        bt_anadir_anuncio1_necesito = (ImageView) vista.findViewById(R.id.bt_anadir_anuncio1_necesito);
        bt_anadir_anuncio1_ofrezco = (ImageView) vista.findViewById(R.id.bt_anadir_anuncio1_ofrezco);
        bt_anadir_anuncio1_necesito.setOnClickListener(this);
        bt_anadir_anuncio1_ofrezco.setOnClickListener(this);

        builder.setView(vista);
        return builder.create();
    }



    @Override
    public void onClick(View v) {
        AnadirAnuncioDialog2 ad2 = new AnadirAnuncioDialog2();
        if(v.getId() == R.id.bt_anadir_anuncio1_necesito){
            ad2.show(getFragmentManager(),"ad2");
            Bundle bundle = new Bundle();
            bundle.putString("tipo","Necesito");
            bundle.putString("nomComunidad",nomComunidad);
            ad2.setArguments(bundle);

        }else{
            ad2.show(getFragmentManager(),"ad2");
            Bundle bundle = new Bundle();
            bundle.putString("tipo","Ofrezco");
            bundle.putString("nomComunidad",nomComunidad);
            ad2.setArguments(bundle);

        }
    }
}
