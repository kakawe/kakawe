package com.kakawe.admin.kakawev2.Adaptadores;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kakawe.admin.kakawev2.Entidades.Anuncio2;
import com.kakawe.admin.kakawev2.R;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

/**
 * Created by admin on 03/02/2018.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder>{
    ImageView iv_vista_anuncio_foto;
    StorageReference refGuardar;
    Activity a;
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_vista_anuncio_titulo;
        private TextView tv_vista_anuncio_autor_nombre;
        private ImageView iv_vista_anuncio_foto,iv_vista_anuncio_categoria,iv_vista_anuncio_autor_foto,iv_vista_anuncio_disponibilidad;

        public ViewHolder(View v){
            super(v);
            tv_vista_anuncio_titulo = (TextView)v.findViewById(R.id.tv_vista_anuncio_titulo);
            tv_vista_anuncio_autor_nombre = (TextView)v.findViewById(R.id.tv_vista_anuncio_autor_nombre);
            iv_vista_anuncio_foto = (ImageView)v.findViewById(R.id.iv_vista_anuncio_foto);
            iv_vista_anuncio_categoria = (ImageView)v.findViewById(R.id.iv_vista_anuncio_categoria);
            iv_vista_anuncio_autor_foto = (ImageView)v.findViewById(R.id.iv_vista_anuncio_autor_foto);
            iv_vista_anuncio_disponibilidad = (ImageView)v.findViewById(R.id.iv_vista_anuncio_disponibilidad);

        }
    }
    public List<Anuncio2> anuncioTablon;

    public RVAdapter(List<Anuncio2> anuncioTablon, Activity a) {
        this.anuncioTablon = anuncioTablon;
        this.a=a;
    }

    @Override
    public RVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vista_anuncio_tablon,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

       // traerIamgenAnuncio();
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(RVAdapter.ViewHolder holder, int position) {
        holder.tv_vista_anuncio_titulo.setText(anuncioTablon.get(position).getTitulo());
        holder.tv_vista_anuncio_autor_nombre.setText(anuncioTablon.get(position).getNombreAnunciante());
        String cat= anuncioTablon.get(position).getCategoria();
        Log.v("CategoriaAqui",cat);
        if (cat.equals("Bricolaje")){
            holder.iv_vista_anuncio_categoria.setImageResource(R.drawable.ic_categoria_bricolaje);
        }if (cat.equals("Cocina")){
            holder.iv_vista_anuncio_categoria.setImageResource(R.drawable.ic_categoria_cocina);
        }if (cat.equals("Deporte y ocio")){
            holder.iv_vista_anuncio_categoria.setImageResource(R.drawable.ic_categoria_deporte_y_ocio);
        }if (cat.equals("Electronica")){
            holder.iv_vista_anuncio_categoria.setImageResource(R.drawable.ic_categoria_electronica);
        }if (cat.equals("Entretenimiento")){
            holder.iv_vista_anuncio_categoria.setImageResource(R.drawable.ic_categoria_entretenimiento);
        }if (cat.equals("Jardineria")){
            holder.iv_vista_anuncio_categoria.setImageResource(R.drawable.ic_categoria_jardineria);
        }if (cat.equals("Limpieza")){
            holder.iv_vista_anuncio_categoria.setImageResource(R.drawable.ic_categoria_limpieza);
        }if (cat.equals("Mascotas")){
            holder.iv_vista_anuncio_categoria.setImageResource(R.drawable.ic_categoria_mascotas);
        }if (cat.equals("Mobiliario")){
            holder.iv_vista_anuncio_categoria.setImageResource(R.drawable.ic_categoria_mobiliario);
        }if (cat.equals("Otros")){
            holder.iv_vista_anuncio_categoria.setImageResource(R.drawable.ic_categoria_otros);
        }if (cat.equals("Ropa")){
            holder.iv_vista_anuncio_categoria.setImageResource(R.drawable.ic_categoria_ropa);
        }if (cat.equals("Salud")){
            holder.iv_vista_anuncio_categoria.setImageResource(R.drawable.ic_categoria_salud);
        }if (cat.equals("Servicios")){
            holder.iv_vista_anuncio_categoria.setImageResource(R.drawable.ic_categoria_servicios);
        }if (cat.equals("Social")){
            holder.iv_vista_anuncio_categoria.setImageResource(R.drawable.ic_categoria_social);
        }
        String caducidad = anuncioTablon.get(position).getHoraCaducidad();
        if (caducidad.equals("Permanente")){
            holder.iv_vista_anuncio_disponibilidad.setImageResource(R.drawable.ic_disponibilidad_chincheta);
        }else{
            holder.iv_vista_anuncio_disponibilidad.setImageResource(R.drawable.ic_disponibilidad_reloj);
        }


        FirebaseStorage storage=FirebaseStorage.getInstance();
        StorageReference refDescargar = storage.getReferenceFromUrl("gs://kakawe-22f82.appspot.com").child("ImagenesAnuncios").child(anuncioTablon.get(position).getKey());

        Glide.with(a).using(new FirebaseImageLoader())
                .load(refDescargar)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(holder.iv_vista_anuncio_foto);
        FirebaseStorage storage1=FirebaseStorage.getInstance();
        StorageReference refDescargar1 = storage1.getReferenceFromUrl("gs://kakawe-22f82.appspot.com").child("ImagenesPerfilUsuario").child(anuncioTablon.get(position).getCorreoAnunciante());

        Glide.with(a).using(new FirebaseImageLoader())
                .load(refDescargar1)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(holder.iv_vista_anuncio_autor_foto);
    }

    @Override
    public int getItemCount() {
        return anuncioTablon.size();
    }
}