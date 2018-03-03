package com.example.admin.kakawev2.Adaptadores;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.kakawev2.Categoria;
import com.example.admin.kakawev2.R;

import java.util.List;


/**
 * Created by jose on 26/02/2018.
 */

public class RVAdapterAnuncioCategoria extends RecyclerView.Adapter<RVAdapterAnuncioCategoria.ViewHolder>{
    public List<Categoria> listaCategoria;
    public String categoriaSeleccionada;
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_daa2_categoria_item;
        private ImageView iv_daa2_categoria_item;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_daa2_categoria_item = (TextView)itemView.findViewById(R.id.tv_daa2_categoria_item);
            iv_daa2_categoria_item = (ImageView)itemView.findViewById(R.id.iv_daa2_categoria_item);
        }
    }



    public RVAdapterAnuncioCategoria(List<Categoria> crearLista,String categoriaSeleccionada) {
        this.listaCategoria = crearLista;
        this.categoriaSeleccionada = categoriaSeleccionada;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_anadir_anuncio2_categoria_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(vista);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (categoriaSeleccionada.equals(listaCategoria.get(position).getCategoria())){
            holder.tv_daa2_categoria_item.setText(listaCategoria.get(position).getCategoria());
            holder.iv_daa2_categoria_item.setImageResource(listaCategoria.get(position).getFotoCategoria());
            holder.tv_daa2_categoria_item.setSelected(true);
            holder.tv_daa2_categoria_item.setTextColor(Color.RED);

        }else{
            holder.tv_daa2_categoria_item.setText(listaCategoria.get(position).getCategoria());
            holder.iv_daa2_categoria_item.setImageResource(listaCategoria.get(position).getFotoCategoria());
        }


    }

    @Override
    public int getItemCount() {
        return listaCategoria.size();
    }



}
