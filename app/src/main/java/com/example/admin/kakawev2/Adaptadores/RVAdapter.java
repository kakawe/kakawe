package com.example.admin.kakawev2.Adaptadores;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.admin.kakawev2.Entidades.Anuncio2;
import com.example.admin.kakawev2.R;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import static java.security.AccessController.getContext;

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
        private ImageView iv_vista_anuncio_foto;

        public ViewHolder(View v){
            super(v);
            tv_vista_anuncio_titulo = (TextView)v.findViewById(R.id.tv_vista_anuncio_titulo);
            tv_vista_anuncio_autor_nombre = (TextView)v.findViewById(R.id.tv_vista_anuncio_autor_nombre);
            iv_vista_anuncio_foto = (ImageView)v.findViewById(R.id.iv_vista_anuncio_foto);

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
/*
    private void traerIamgenAnuncio() {
        FirebaseAuth au = FirebaseAuth.getInstance();
        String correo = au.getCurrentUser().getEmail();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        refGuardar = storage.getReferenceFromUrl("gs://kakawe-22f82.appspot.com").child("ImagenesPerfilUsuario").child(correo);

    }
*/
    @Override
    public void onBindViewHolder(RVAdapter.ViewHolder holder, int position) {
        holder.tv_vista_anuncio_titulo.setText(anuncioTablon.get(position).getTitulo());
        holder.tv_vista_anuncio_autor_nombre.setText(anuncioTablon.get(position).getNombreAnunciante());

        //holder.iv_vista_anuncio_foto.setImageURI(Uri.parse(refGuardar.toString()));
        FirebaseStorage storage=FirebaseStorage.getInstance();
        StorageReference refDescargar = storage.getReferenceFromUrl("gs://kakawe-22f82.appspot.com").child("ImagenesAnuncios").child(anuncioTablon.get(position).getKey());

        Glide.with(a).using(new FirebaseImageLoader())
                .load(refDescargar)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(holder.iv_vista_anuncio_foto);
    }

    @Override
    public int getItemCount() {
        return anuncioTablon.size();
    }
}