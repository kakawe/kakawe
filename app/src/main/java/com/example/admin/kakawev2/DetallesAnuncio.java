package com.example.admin.kakawev2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.admin.kakawev2.Dialogs.AnadirAnuncioDialog2;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

/**
 * Created by jose on 12/02/2018.
 */

public class DetallesAnuncio extends DialogFragment{

    View vista;
    DatabaseReference reference;
    String key;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    Intent intent;
    Uri uri;
    private static final int GALERY_INTENT = 1;
    private StorageReference storageReference ;
    ImageView iv_detallesAnuncio_fotoAnuncio,iv_detallesAnuncio_cerrar,iv_detallesAnuncio_autor_foto;
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
        String key = (String)b.get("key");
        String CorreoAnunciante = (String)b.get("CorreoAnunciante");

        String tituloCompleto = tipo_str+" "+titulo_str;
        String direccionCompleta = piso_str+" "+puerta_str;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        vista = inflater.inflate(R.layout.dialog_detalles_anuncio,null);

        iv_detallesAnuncio_cerrar = (ImageView)vista.findViewById(R.id.iv_detallesAnuncio_cerrar);
        iv_detallesAnuncio_fotoAnuncio = (ImageView)vista.findViewById(R.id.iv_detallesAnuncio_fotoAnuncio);
        iv_detallesAnuncio_autor_foto = (ImageView)vista.findViewById(R.id.iv_detallesAnuncio_autor_foto);
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


        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference refGuardar = storage.getReferenceFromUrl("gs://kakawe-22f82.appspot.com").child("ImagenesAnuncios").child(key);

        Glide.with(getActivity()).using(new FirebaseImageLoader())
                .load(refGuardar)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(iv_detallesAnuncio_fotoAnuncio);
        StorageReference refGuardar2 = storage.getReferenceFromUrl("gs://kakawe-22f82.appspot.com").child("ImagenesPerfilUsuario").child(CorreoAnunciante);

        Glide.with(getActivity()).using(new FirebaseImageLoader())
                .load(refGuardar2)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(iv_detallesAnuncio_autor_foto);


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
