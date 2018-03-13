package com.kakawe.admin.kakawev2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by jose on 12/02/2018.
 */

public class DetallesAnuncio extends DialogFragment{

    View vista;
    DatabaseReference reference;
    String cat,fechaCad;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    Intent intent;
    Uri uri;
    private static final int GALERY_INTENT = 1;
    private StorageReference storageReference ;
    ImageView iv_detallesAnuncio_fotoAnuncio,iv_detallesAnuncio_cerrar,iv_detallesAnuncio_autor_foto,iv_detallesAnuncio_categoria,iv_detallesAnuncio_disponibilidad;
    TextView tv_detallesAnuncio_tituloAnuncio, tv_detallesAnuncio_mensajeAnuncio,tv_detallesAnuncio_autor_domicilio,tv_detallesAnuncio_autor_nombre,tv_detallesAnuncio_disponibilidad,tv_detallesAnuncio_disponibilidad2;
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
        cat = (String)b.get("Categoria");
        fechaCad = (String)b.get("fechaCad");
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
        iv_detallesAnuncio_categoria = (ImageView)vista.findViewById(R.id.iv_detallesAnuncio_categoria);
        iv_detallesAnuncio_disponibilidad = (ImageView)vista.findViewById(R.id.iv_detallesAnuncio_disponibilidad);
        tv_detallesAnuncio_tituloAnuncio = (TextView)vista.findViewById(R.id.tv_detallesAnuncio_tituloAnuncio);
        tv_detallesAnuncio_mensajeAnuncio = (TextView)vista.findViewById(R.id.tv_detallesAnuncio_mensajeAnuncio);
        tv_detallesAnuncio_autor_domicilio = (TextView)vista.findViewById(R.id.tv_detallesAnuncio_autor_domicilio);
        tv_detallesAnuncio_autor_nombre = (TextView)vista.findViewById(R.id.tv_detallesAnuncio_autor_nombre);
        tv_detallesAnuncio_disponibilidad = (TextView)vista.findViewById(R.id.tv_detallesAnuncio_disponibilidad);
        tv_detallesAnuncio_disponibilidad2 = (TextView)vista.findViewById(R.id.tv_detallesAnuncio_disponibilidad2);


        //seteamos los datos a al dialog
        tv_detallesAnuncio_tituloAnuncio.setText(tituloCompleto);
        tv_detallesAnuncio_mensajeAnuncio.setText(mensaje_str);
        tv_detallesAnuncio_autor_domicilio.setText(direccionCompleta);
        tv_detallesAnuncio_autor_nombre.setText(anunciante);
        cargaCategoria();

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

    private void cargaCategoria() {
        if (cat.equals("Bricolaje")){
            iv_detallesAnuncio_categoria.setImageResource(R.drawable.ic_categoria_bricolaje);
        }if (cat.equals("Cocina")){
            iv_detallesAnuncio_categoria.setImageResource(R.drawable.ic_categoria_cocina);
        }if (cat.equals("Deporte y ocio")){
            iv_detallesAnuncio_categoria.setImageResource(R.drawable.ic_categoria_deporte_y_ocio);
        }if (cat.equals("Electronica")){
            iv_detallesAnuncio_categoria.setImageResource(R.drawable.ic_categoria_electronica);
        }if (cat.equals("Entretenimiento")){
            iv_detallesAnuncio_categoria.setImageResource(R.drawable.ic_categoria_entretenimiento);
        }if (cat.equals("Jardineria")){
            iv_detallesAnuncio_categoria.setImageResource(R.drawable.ic_categoria_jardineria);
        }if (cat.equals("Limpieza")){
            iv_detallesAnuncio_categoria.setImageResource(R.drawable.ic_categoria_limpieza);
        }if (cat.equals("Mascotas")){
            iv_detallesAnuncio_categoria.setImageResource(R.drawable.ic_categoria_mascotas);
        }if (cat.equals("Mobiliario")){
            iv_detallesAnuncio_categoria.setImageResource(R.drawable.ic_categoria_mobiliario);
        }if (cat.equals("Otros")){
            iv_detallesAnuncio_categoria.setImageResource(R.drawable.ic_categoria_otros);
        }if (cat.equals("Ropa")){
            iv_detallesAnuncio_categoria.setImageResource(R.drawable.ic_categoria_ropa);
        }if (cat.equals("Salud")){
            iv_detallesAnuncio_categoria.setImageResource(R.drawable.ic_categoria_salud);
        }if (cat.equals("Servicios")){
            iv_detallesAnuncio_categoria.setImageResource(R.drawable.ic_categoria_servicios);
        }if (cat.equals("Social")){
            iv_detallesAnuncio_categoria.setImageResource(R.drawable.ic_categoria_social);
        }
        if (fechaCad.equals("Permanente")){
            iv_detallesAnuncio_disponibilidad.setImageResource(R.drawable.ic_disponibilidad_chincheta);
            tv_detallesAnuncio_disponibilidad2.setText("Anuncio disponible");
            tv_detallesAnuncio_disponibilidad.setText("permanentemente");
        }else{
            iv_detallesAnuncio_disponibilidad.setImageResource(R.drawable.ic_disponibilidad_reloj);
            tv_detallesAnuncio_disponibilidad2.setText("Anuncio disponible hasta el");
            tv_detallesAnuncio_disponibilidad.setText(fechaCad);
        }
    }
}
