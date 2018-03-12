package com.example.admin.kakawev2.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.kakawev2.Adaptadores.RVAdapterAnuncioCategoria;

import java.util.ArrayList;
import java.util.List;

import com.example.admin.kakawev2.Entidades.Categoria;
import com.example.admin.kakawev2.R;

/**
 * Created by jose on 22/02/2018.
 */

public class AnadirAnuncioCategoriaDialog2 extends DialogFragment {
    View vista;
    List<Categoria> categorias;
    TextView tv_daa2_categoria_item;
    ImageView iv_anadir_anuncio2_imgen_categoria;
    private RecyclerView rv_dg_anadir_categoria;
    private RVAdapterAnuncioCategoria adaptadorTabla;
    private CategoriaSeleccionada claseLlamadora;


    //creamos un interfaz para poder setear el dato categoria en en añadirAnuncioDialog2
    CategoriaSeleccionada cat_selected;
    public interface CategoriaSeleccionada
    {
        public void seleccionada(String categoria);
    }

    @Override
    public Dialog onCreateDialog(Bundle saveIntanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final LayoutInflater inflater = getActivity().getLayoutInflater();
        vista = inflater.inflate(R.layout.dialog_anadir_anuncio2_categoria, null);

        rv_dg_anadir_categoria = (RecyclerView) vista.findViewById(R.id.rv_dg_anadir_categoria);
        rv_dg_anadir_categoria.setLayoutManager((new LinearLayoutManager(getActivity())));

        String categoriaSeleccionada = getArguments().getString("categoria2");
        Log.v("categoriaDialogo",categoriaSeleccionada);

        adaptadorTabla = new RVAdapterAnuncioCategoria(ObtenerCategorias(), categoriaSeleccionada);
        rv_dg_anadir_categoria.setAdapter(adaptadorTabla);
        rv_dg_anadir_categoria.setClickable(true);
        final GestureDetector mGestureDetector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        //poder seleccionar las card dentro del recyclerView
        rv_dg_anadir_categoria.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                try {

                    View child = rv.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && mGestureDetector.onTouchEvent(e)) {
                        int posicion = rv.getChildAdapterPosition(child);

                        //obtenemos los datos segun su posicion en el array
                        String cat = categorias.get(posicion).getCategoria();
                        Log.v("clicado",String.valueOf(cat));
                        //cat_selected=(CategoriaSeleccionada)getActivity();
                        claseLlamadora=(CategoriaSeleccionada)getTargetFragment();
                        claseLlamadora.seleccionada(String.valueOf(cat));
                        Log.v("categoria", cat);
                        Toast.makeText(getActivity(), "Clicado " + cat, Toast.LENGTH_LONG).show();
                        cerrar();
                        builder.setView(vista).create();
                        return true;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                return false;

            }


            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }

        });
        builder.setView(vista);
        return builder.create();
    }

    private void cerrar() {
        this.dismiss();
    }


    private List<Categoria> ObtenerCategorias() {
        categorias = new ArrayList<>();
        categorias.add(new Categoria("Bricolaje", R.drawable.ic_categoria_bricolaje));
        categorias.add(new Categoria("Cocina", R.drawable.ic_categoria_cocina));
        categorias.add(new Categoria("Deporte y Ocio", R.drawable.ic_categoria_deporte_y_ocio));
        categorias.add(new Categoria("Electricidad", R.drawable.ic_categoria_electronica));
        categorias.add(new Categoria("Entretenimiento", R.drawable.ic_categoria_entretenimiento));
        categorias.add(new Categoria("Jardineria", R.drawable.ic_categoria_jardineria));
        categorias.add(new Categoria("Limpieza", R.drawable.ic_categoria_limpieza));
        categorias.add(new Categoria("Mascotas", R.drawable.ic_categoria_mascotas));
        categorias.add(new Categoria("Mobiliario", R.drawable.ic_categoria_mobiliario));
        categorias.add(new Categoria("Ropa", R.drawable.ic_categoria_ropa));
        categorias.add(new Categoria("Salud", R.drawable.ic_categoria_salud));
        categorias.add(new Categoria("Servicios", R.drawable.ic_categoria_servicios));
        categorias.add(new Categoria("Social", R.drawable.ic_categoria_social));
        categorias.add(new Categoria("Otros", R.drawable.ic_categoria_otros));

        return categorias;
    }
}