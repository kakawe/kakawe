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
    ImageView iv_detallesAnuncio_fotoAnuncio,iv_detallesAnuncio_cerrar;
    TextView tv_detallesAnuncio_tituloAnuncio, tv_detallesAnuncio_mensajeAnuncio,tv_detallesAnuncio_autor_domicilio,tv_detallesAnuncio_autor_nombre,tv_detallesAnuncio_disponibilidad;
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
        String piso_str=(String)b.get("PisoAnuncio");
        String puerta_str=(String)b.get("PuertaAnuncio");
        String anunciante = (String)b.get("Anunciante");
        String categoria = (String)b.get("Categoria");
        String fechaCad = (String)b.get("fechaCad");

        String tituloCompleto = tipo_str+" "+titulo_str;
        String direccionCompleta = piso_str+" "+puerta_str;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        vista = inflater.inflate(R.layout.dialog_detalles_anuncio,null);

        iv_detallesAnuncio_cerrar = (ImageView)vista.findViewById(R.id.iv_detallesAnuncio_cerrar);
        iv_detallesAnuncio_fotoAnuncio = (ImageView)vista.findViewById(R.id.iv_detallesAnuncio_fotoAnuncio);
        tv_detallesAnuncio_tituloAnuncio = (TextView)vista.findViewById(R.id.tv_detallesAnuncio_tituloAnuncio);
        tv_detallesAnuncio_mensajeAnuncio = (TextView)vista.findViewById(R.id.tv_detallesAnuncio_mensajeAnuncio);
        tv_detallesAnuncio_autor_domicilio = (TextView)vista.findViewById(R.id.tv_detallesAnuncio_autor_domicilio);
        tv_detallesAnuncio_autor_nombre = (TextView)vista.findViewById(R.id.tv_detallesAnuncio_autor_nombre);
        tv_detallesAnuncio_disponibilidad = (TextView)vista.findViewById(R.id.tv_detallesAnuncio_disponibilidad);

        //seteamos los datos a al dialog
        tv_detallesAnuncio_tituloAnuncio.setText(tituloCompleto);
        tv_detallesAnuncio_mensajeAnuncio.setText(mensaje_str);
        tv_detallesAnuncio_autor_domicilio.setText(direccionCompleta);
        tv_detallesAnuncio_autor_nombre.setText(anunciante);
        tv_detallesAnuncio_disponibilidad.setText(fechaCad);

        iv_detallesAnuncio_cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        builder.setView(vista);
        return builder.create();
    }
}
