package com.kakawe.admin.kakawev2.Dialogs;

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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kakawe.admin.kakawev2.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static android.app.Activity.RESULT_OK;

/**
 * Created by jose on 19/02/2018.
 */

public class AnadirAnuncioDialog2 extends DialogFragment implements View.OnClickListener, AnadirAnuncioCategoriaDialog2.CategoriaSeleccionada {
    DatabaseReference reference;
    String nomComunidad,key;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private StorageReference storageReference ;
    Intent intent;
    Uri uri;
    private static final int GALERY_INTENT = 1;
    View vista;
    TextView tv_anadir_anuncio2_tipo;
    EditText et_anadir_anuncio2_titulo, ed_anadir_anuncio2_descripcion, ed_anadir_anuncio2_categoria;
    ImageView iv_anadir_anuncio2_imagen, iv_anadir_anuncio2_imgen_categoria, iv_anuncio2_cerrar;
    ImageButton bt_anadir_anuncio2_atras, bt_anadir_anuncio2_adelante;
    private String tv_ruta_imagen;
    private String tipo, tipo1;
    String categoria;


    public void setearCategoria(String categoria) {
        ed_anadir_anuncio2_categoria.setText(categoria);
        Log.v("desdeAnadir", categoria);
    }

    @Override
    public Dialog onCreateDialog(Bundle saveIntanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        vista = inflater.inflate(R.layout.dialog_anadir_anuncio2, null);
        storageReference = FirebaseStorage.getInstance().getReference();
        nomComunidad = getArguments().getString("nomComunidad");
        tipo = getArguments().getString("tipo") + " ...";
        tipo1 = getArguments().getString("tipo");
        Log.v("clicados", tipo);
        categoria = getArguments().getString("categoria");
        Log.v("clicados", categoria);

        //declaramos las vistas del dialog2
        iv_anuncio2_cerrar = (ImageView) vista.findViewById(R.id.iv_anuncio2_cerrar);
        tv_anadir_anuncio2_tipo = (TextView) vista.findViewById(R.id.tv_anadir_anuncio2_tipo);
        et_anadir_anuncio2_titulo = (EditText) vista.findViewById(R.id.et_anadir_anuncio2_titulo);
        ed_anadir_anuncio2_descripcion = (EditText) vista.findViewById(R.id.ed_anadir_anuncio2_descripcion);
        ed_anadir_anuncio2_categoria = (EditText) vista.findViewById(R.id.ed_anadir_anuncio2_categoria);
        iv_anadir_anuncio2_imagen = (ImageView) vista.findViewById(R.id.iv_anadir_anuncio2_imagen);
        bt_anadir_anuncio2_atras = (ImageButton) vista.findViewById(R.id.bt_anadir_anuncio2_atras);
        bt_anadir_anuncio2_adelante = (ImageButton) vista.findViewById(R.id.bt_anadir_anuncio2_adelante);

        //damos valor a la etique tipo
        tv_anadir_anuncio2_tipo.setText(tipo);

        iv_anuncio2_cerrar.setOnClickListener(this);
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
        } else if (v.getId() == R.id.ed_anadir_anuncio2_categoria) {//|| v.getId() == R.id.iv_anadir_anuncio2_imgen_categoria
            obtenerCategoria();
        } else if (v.getId() == R.id.bt_anadir_anuncio2_atras) {
            atrasDialog();
        } else if (v.getId() == R.id.bt_anadir_anuncio2_adelante) {
            adelanteDialog();
        } else if (v.getId() == R.id.iv_anuncio2_cerrar) {
            cerrarAnuncio();
        }
    }

    //cargamos la imagen desde firebase
    //eventos
    private void obtenerImagen() {
        Intent i = new Intent();
        i.setType("image/*");
        i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        i.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), 1);
        Log.v("Añadir", "funciona");
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String correo = "adri1@mail.com";
        Log.v("Entra", "1");
        //verificamos si obtenemos la imagen de la galeria
        if (requestCode == GALERY_INTENT && resultCode == RESULT_OK) {
            Log.v("Entra", "2");
            //Aquí sólo se recoge la URI. No se grabará hasta que no se haya grabado el contacto
            uri = data.getData();
            subirFoto();
        }

    }

    private void subirFoto() {
        reference = FirebaseDatabase.getInstance().getReference().child("comunidades");
        key = reference.push().getKey();
        StorageReference rutaCarpetaImg = storageReference.child("ImagenesAnuncios").child(key);
        //subimos la imagen y verificamos mediante un toast que se subio la foto
        rutaCarpetaImg.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //descargar imagen de firebase
                Log.v("Entra", "3");

                Uri descargarFoto = taskSnapshot.getDownloadUrl();
                Glide.with(AnadirAnuncioDialog2.this)
                        .load(descargarFoto)
                        .into(iv_anadir_anuncio2_imagen);

            }
        });
    }

    private void adelanteDialog() {

        String titulo = et_anadir_anuncio2_titulo.getText().toString();
        String descripcionAnuncio = ed_anadir_anuncio2_descripcion.getText().toString();
        String categoria = ed_anadir_anuncio2_categoria.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString("nomComunidad", nomComunidad);
        bundle.putString("tipo2", tipo1);
        bundle.putString("titulo2", titulo);
        bundle.putString("ruta_imagen2", tv_ruta_imagen);
        bundle.putString("descripcionAnuncio2", descripcionAnuncio);
        bundle.putString("categoria2", categoria);
        bundle.putString("key", key);

        AnadirAnuncioDialog3 ad3 = new AnadirAnuncioDialog3();
        ad3.show(getFragmentManager(), "ad3");
        ad3.setArguments(bundle);

        //DialogFragment ad2 = (DialogFragment) getFragmentManager().findFragmentByTag("ad2");
        //ad2.dismiss();

        //Log.v("dialog2", tipo1);
        //Log.v("dialog2", titulo);
        //Log.v("dialog2", tv_ruta_imagen);
        //Log.v("dialog2", descripcionAnuncio);
        Log.v("categoria2", categoria);

    }

    private void atrasDialog() {
        this.dismiss();

        //ad1.set.show(getFragmentManager(), "ad1");
    }

    //metodo para cerrar dialog2
    private void cerrarAnuncio() {
        DialogFragment ad1 = (DialogFragment) getFragmentManager().findFragmentByTag("ad1");
        this.dismiss();
        ad1.dismiss();
    }

    //metodo para obtener la imagen y el texto de la catgoria
    private void obtenerCategoria() {
        AnadirAnuncioCategoriaDialog2 adc2 = new AnadirAnuncioCategoriaDialog2();
        adc2.setTargetFragment(AnadirAnuncioDialog2.this, 0);
        Bundle datos = new Bundle();
        categoria = ed_anadir_anuncio2_categoria.getText().toString();
        datos.putString("categoria2", categoria);
        adc2.show(getFragmentManager(), "adc2");
        adc2.setArguments(datos);


    }


    @Override
    public void seleccionada(String categoria) {
        String cat = categoria;
        Log.v("clicado", "estoy aquiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
        AnadirAnuncioDialog2 a = (AnadirAnuncioDialog2) getActivity().getFragmentManager().findFragmentByTag("ad2");
        a.setearCategoria(categoria);
    }
}
