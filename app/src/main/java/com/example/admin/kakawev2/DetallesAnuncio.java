package com.example.admin.kakawev2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by jose on 12/02/2018.
 */

public class DetallesAnuncio extends DialogFragment{

    View vista;
    ImageView iv_detallesAnuncio_fotoAnuncio;
    TextView tv_detallesAnuncio_tipoAnuncio, tv_detallesAnuncio_tituloAnuncio, tv_detallesAnuncio_mensajeAnuncio;
    OyenteDialog oyente;

    public interface OyenteDialog {

        public void detalleAnuncio();

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Para trabajar cn los datos pasandolos por argumetos(clave/valor)
        Bundle b=this.getArguments();
        String tipo_str=(String)b.get("TipoAnuncio");
        String titulo_str=(String)b.get("TituloAnuncio");
        String mensaje_str=(String)b.get("MensajeAnuncio");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        vista = inflater.inflate(R.layout.dialog_detalles_anuncio,null);

        iv_detallesAnuncio_fotoAnuncio = (ImageView)vista.findViewById(R.id.iv_detallesAnuncio_fotoAnuncio);
        tv_detallesAnuncio_tipoAnuncio = (TextView)vista.findViewById(R.id.tv_detallesAnuncio_tipoAnuncio);
        tv_detallesAnuncio_tituloAnuncio = (TextView)vista.findViewById(R.id.tv_detallesAnuncio_tituloAnuncio);
        tv_detallesAnuncio_mensajeAnuncio = (TextView)vista.findViewById(R.id.tv_detallesAnuncio_mensajeAnuncio);

        //seteamos los datos a al dialog
        tv_detallesAnuncio_tipoAnuncio.setText(tipo_str);
        tv_detallesAnuncio_tituloAnuncio.setText(titulo_str);
        tv_detallesAnuncio_mensajeAnuncio.setText(mensaje_str);

        builder.setView(vista);
        return builder.create();
    }
}
