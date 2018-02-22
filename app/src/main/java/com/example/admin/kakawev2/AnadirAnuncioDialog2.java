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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by jose on 19/02/2018.
 */

public class AnadirAnuncioDialog2 extends DialogFragment implements View.OnClickListener {

    View vista;
    TextView tv_anadir_anuncio2_tipo;
    EditText et_anadir_anuncio2_titulo, ed_anadir_anuncio2_descripcion, ed_anadir_anuncio2_categoria;
    ImageView iv_anadir_anuncio2_imagen;
    Button bt_anadir_anuncio2_atras, bt_anadir_anuncio2_adelante;
    private String tv_ruta_imagen;
    private String tipo, tipo1;

    @Override
    public Dialog onCreateDialog(Bundle saveIntanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        vista = inflater.inflate(R.layout.dialog_anadir_anuncio2, null);

        tipo = getArguments().getString("tipo") + " ...";
        tipo1 = getArguments().getString("tipo");
        Log.v("datos", tipo);

        //declaramos las vistas del dialog2
        tv_anadir_anuncio2_tipo = (TextView) vista.findViewById(R.id.tv_anadir_anuncio2_tipo);
        et_anadir_anuncio2_titulo = (EditText) vista.findViewById(R.id.et_anadir_anuncio2_titulo);
        ed_anadir_anuncio2_descripcion = (EditText) vista.findViewById(R.id.ed_anadir_anuncio2_descripcion);
        ed_anadir_anuncio2_categoria = (EditText) vista.findViewById(R.id.ed_anadir_anuncio2_categoria);
        iv_anadir_anuncio2_imagen = (ImageView) vista.findViewById(R.id.iv_anadir_anuncio2_imagen);
        bt_anadir_anuncio2_atras = (Button) vista.findViewById(R.id.bt_anadir_anuncio2_atras);
        bt_anadir_anuncio2_adelante = (Button) vista.findViewById(R.id.bt_anadir_anuncio2_adelante);
        //damos valor a la etique tipo
        tv_anadir_anuncio2_tipo.setText(tipo);
        //onclick listener
        iv_anadir_anuncio2_imagen.setOnClickListener(this);
        ed_anadir_anuncio2_descripcion.setOnClickListener(this);
        ed_anadir_anuncio2_categoria.setOnClickListener(this);
        bt_anadir_anuncio2_atras.setOnClickListener(this);
        bt_anadir_anuncio2_adelante.setOnClickListener(this);


        builder.setView(vista);
        return builder.create();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_anadir_anuncio2_imagen) {
            obtenerImagen();

        } else if (v.getId() == R.id.ed_anadir_anuncio2_categoria) {
            obtenerCategoria();

        } else if (v.getId() == R.id.bt_anadir_anuncio2_atras) {
            atrasDialog();

        } else if (v.getId() == R.id.bt_anadir_anuncio2_adelante) {
            adelanteDialog();
        }
    }

    private void obtenerCategoria() {
        //metodo que envia todos los datos al siguiente dialog


    }


    //eventos
    private void obtenerImagen() {
        Intent i = new Intent();
        i.setType("image/*");
        i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        i.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), 1);
        Log.v("Añadir", "funciona");
    }

    //ESTE MÉTODO SERÁ LLAMADO CUANDO SE HAYA ELEGIDO UNA IMAGEN Y HAYA UN RESULTADO
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {//Para recuperar los datos de llamar a la galeria

        if (requestCode == 1 && resultCode == RESULT_OK) {
            //Aquí sólo se recoge la URI. No se grabará hasta que no se haya grabado el contacto
            Uri path = data.getData();
            tv_ruta_imagen = (path.toString());
            iv_anadir_anuncio2_imagen.setImageURI(path);
            Log.v("ruta", tv_ruta_imagen.toString());

        }
    }

    private void adelanteDialog() {
        String titulo = et_anadir_anuncio2_titulo.getText().toString();
        String descripcionAnuncio = ed_anadir_anuncio2_descripcion.getText().toString();
        String categoria = ed_anadir_anuncio2_categoria.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString("tipo2", tipo1);
        bundle.putString("titulo2", titulo);
        //bundle.putString("ruta_imagen2",tv_ruta_imagen);
        bundle.putString("descripcionAnuncio2", descripcionAnuncio);
        //bundle.putString("categoria2", categoria);

        //Log.v("dialog2", tipo1);
        //Log.v("dialog2", titulo);
        //Log.v("dialog2", tv_ruta_imagen);
        //Log.v("dialog2", descripcionAnuncio);
        AnadirAnuncioDialog3 ad3 = new AnadirAnuncioDialog3();

        ad3.show(getFragmentManager(), "ad3");
        ad3.setArguments(bundle);

    }

    private void atrasDialog() {
        AnadirAnuncioDialog1 ad1 = new AnadirAnuncioDialog1();
        ad1.show(getFragmentManager(), "ad1");
    }


}
