package com.example.admin.kakawev2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.admin.kakawev2.Entidades.Usuario;

import static android.app.Activity.RESULT_OK;

/**
 * Created by jose on 10/02/2018.
 */

public class PerfilUsuarioDialog extends DialogFragment implements View.OnClickListener{

    View vista;

    Button bt_editarPerfil_editarImagen;
    EditText et_editarPerfil_nombre,et_editarPerfil_correo,et_editarPerfil_contraseñaActual,et_editarPerfil_contraseñaNueva;

    OyenteDialog oyente;


    public interface OyenteDialog {
        public void editarperfil(Usuario usuario);

    }

    @Override
    public Dialog onCreateDialog(Bundle saveIntanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        vista = inflater.inflate(R.layout.dialog_editar_perfil, null);

        bt_editarPerfil_editarImagen = (Button) vista.findViewById(R.id.bt_editarPerfil_editarImagen);
        bt_editarPerfil_editarImagen.setOnClickListener(this);
        et_editarPerfil_nombre = (EditText) vista.findViewById(R.id.et_editarPerfil_nombre);
        et_editarPerfil_correo = (EditText) vista.findViewById(R.id.et_editarPerfil_correo);
        et_editarPerfil_contraseñaActual = (EditText) vista.findViewById(R.id.et_editarPerfil_contraseñaActual);
        et_editarPerfil_contraseñaNueva = (EditText) vista.findViewById(R.id.et_editarPerfil_contraseñaNueva);

        builder.setPositiveButton("Guardar datos", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

               comprobardatos();

            }
        });
        builder.setView(vista);
        return builder.create();

    }

    private void comprobardatos() {
        String nombre = ((EditText) vista.findViewById(R.id.et_editarPerfil_nombre)).getText().toString();
        String correo = ((EditText)vista.findViewById(R.id.et_editarPerfil_correo)).getText().toString();
        String contrasenaActual = ((EditText) vista.findViewById(R.id.et_editarPerfil_contraseñaActual)).getText().toString();
        String contrasenaNueva = ((EditText) vista.findViewById(R.id.et_editarPerfil_contraseñaNueva)).getText().toString();
        String fotoPerfil = ((EditText)vista.findViewById(R.id.bt_editarPerfil_editarImagen)).getText().toString();

        //la contraseña sera la nueva o la vieja segun la comprobación
        String contrasena = "";

        oyente = (OyenteDialog) getActivity();
        oyente.editarperfil(new Usuario(nombre,correo,contrasena,fotoPerfil));
        Log.v("datos",nombre );

    }

    @Override
    public void onClick(View v) {
        rutaImagenPerfil();

    }

    private void rutaImagenPerfil() {
        Intent i=new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        startActivityForResult(i, 1);
        Log.v("Añadir", "funciona");
    }

    //ESTE MÉTODO SERÁ LLAMADO CUANDO SE HAYA ELEGIDO UNA IMAGEN Y HAYA UN RESULTADO
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {//Para recuperar los datos de llamar a la galeria

        if (requestCode== 1 && resultCode==RESULT_OK )
        {
            //Aquí sólo se recoge la URI. No se grabará hasta que no se haya grabado el contacto
            Uri path=data.getData();
            String tv_ruta_imagen = ( path.toString());
            Log.v("ruta", tv_ruta_imagen.toString());

        }
    }
}













