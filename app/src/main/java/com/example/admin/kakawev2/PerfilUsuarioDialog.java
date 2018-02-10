package com.example.admin.kakawev2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.admin.kakawev2.Entidades.Usuario;

/**
 * Created by jose on 10/02/2018.
 */

public class PerfilUsuarioDialog extends DialogFragment{

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

    }
}













