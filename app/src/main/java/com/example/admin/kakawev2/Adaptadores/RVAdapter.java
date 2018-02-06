package com.example.admin.kakawev2.Adaptadores;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.kakawev2.Entidades.Anuncio;
import com.example.admin.kakawev2.R;

import java.util.List;

/**
 * Created by admin on 03/02/2018.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder>{

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_vista_anuncio_titulo;
        private TextView tv_vista_anuncio_descripcion;
        //private ImageView iv_vista_anuncio_foto;

        public ViewHolder(View v){
            super(v);
            tv_vista_anuncio_titulo = (TextView)v.findViewById(R.id.tv_vista_anuncio_titulo);
            tv_vista_anuncio_descripcion = (TextView)v.findViewById(R.id.tv_vista_anuncio_descripcion);
            //iv_vista_anuncio_foto = (ImageView)v.findViewById(R.id.iv_vista_anuncio_foto);
        }
    }
    public List<Anuncio> anuncioTablon;

    public RVAdapter(List<Anuncio> anuncioTablon) {
        this.anuncioTablon = anuncioTablon;
    }

    @Override
    public RVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vista_anuncio_tablon,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RVAdapter.ViewHolder holder, int position) {
        holder.tv_vista_anuncio_titulo.setText(anuncioTablon.get(position).getTitulo());
        holder.tv_vista_anuncio_descripcion.setText(anuncioTablon.get(position).getMensaje());
        //holder.iv_vista_anuncio_foto.setImageResource(anuncioLista.get(position).getFoto());
    }

    @Override
    public int getItemCount() {
        return anuncioTablon.size();
    }
}