package com.example.admin.kakawev2.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.kakawev2.Entidades.Usuario;
import com.example.admin.kakawev2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import static android.app.Activity.RESULT_OK;

/**
 * Created by jose on 10/02/2018.
 */

public class PerfilUsuarioDialog extends DialogFragment implements View.OnClickListener{

    FirebaseUser user;

    View vista;

    EditText et_editarPerfil_contraseñaActual,et_editarPerfil_contraseñaNueva;

    OyenteDialog oyente;


    public interface OyenteDialog {
        public void editarperfil(Usuario usuario);

    }

    @Override
    public Dialog onCreateDialog(Bundle saveIntanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        vista = inflater.inflate(R.layout.dialog_editar_perfil, null);

        user = FirebaseAuth.getInstance().getCurrentUser();

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

    public void comprobardatos() {
        user = FirebaseAuth.getInstance().getCurrentUser();

        String contrasenaActual = ((EditText) vista.findViewById(R.id.et_editarPerfil_contraseñaActual)).getText().toString();
        String contrasenaNueva = ((EditText) vista.findViewById(R.id.et_editarPerfil_contraseñaNueva)).getText().toString();
    }



    private void actualizarNombre(final String nombre) {
        UserProfileChangeRequest actualizacionNombre = new UserProfileChangeRequest.Builder()
                .setDisplayName(nombre).build();
        user.updateProfile(actualizacionNombre)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.v("datos1",nombre+" cambiado");
                        }else{
                            Log.v("sincambio","nombre no cambiado");
                        }
                    }
                });
    }
    private void actualizarCorreo(final String correo) {
        user.updateEmail(correo)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.v("datos1",correo+" cambiado");
                        }else{
                            Log.v("sincambio","correo no cambiado");
                        }
                    }
                });
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













