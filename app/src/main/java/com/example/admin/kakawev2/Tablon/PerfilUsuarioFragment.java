package com.example.admin.kakawev2.Tablon;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.kakawev2.Dialogs.PerfilUsuarioDialog;
import com.example.admin.kakawev2.LoginActivity;
import com.example.admin.kakawev2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;


/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilUsuarioFragment extends Fragment{

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private String nombre="";
    private String correo="";
    private String correoViejo;

    private String actuContra = user.getEmail();

    private ImageView iv_perfilUs_fotoUs;
    private TextView et_perfilUs_nombreUsuario;
    private TextView et_perfilUs_correoUsuario;
    private Button bt_perfilUs_modContrasena,bt_perfilUs_actualiarDatos,bt_perfilUs_cerrarSesion;

    public PerfilUsuarioFragment() {
        // Required empty public constructor
    }
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        iv_perfilUs_fotoUs = (ImageView)getView().findViewById(R.id.iv_perfilUs_fotoUs);
        et_perfilUs_nombreUsuario = (TextView)getView().findViewById(R.id.et_perfilUs_nombreUsuario);
        et_perfilUs_correoUsuario = (TextView)getView().findViewById(R.id.et_perfilUs_correoUsuario);
        bt_perfilUs_modContrasena = (Button)getView().findViewById(R.id.bt_perfilUs_modContrasena);
        bt_perfilUs_actualiarDatos = (Button)getView().findViewById(R.id.bt_perfilUs_actualiarDatos);


        bt_perfilUs_modContrasena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerfilUsuarioDialog pu = new PerfilUsuarioDialog();
                pu.show(getActivity().getFragmentManager(), "Dialog boton Añadir");
                Bundle datos = new Bundle();
                datos.putString("correo",actuContra);
                pu.setArguments(datos);

            }
        });
        bt_perfilUs_actualiarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarDatos();
            }
        });

        bt_perfilUs_cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerrarSesion();
            }
        });
        cargarPerfil();
}



    //Carga los datos del usuario actual y los setea en sitio, luego habrá que hacer que el dialog, compare los datos nuevos con los viejos para
    //ver que se quiere cambiar, al igual que la contraseña.
    private void cargarPerfil() {
        if (user != null) {
            nombre = user.getDisplayName();
            correo = user.getEmail();
            et_perfilUs_nombreUsuario.setText(nombre);
            et_perfilUs_correoUsuario.setText(correo);
            boolean emailVerified = user.isEmailVerified();
            String uid = user.getUid();
        }
    }

    private void guardarDatos() {
        nombre = et_perfilUs_nombreUsuario.getText().toString();
        correoViejo = user.getEmail();
        correo = user.getEmail();
        correo = et_perfilUs_correoUsuario.getText().toString().trim();

        if (nombre.isEmpty()) {
            nombre = user.getDisplayName();

        }
        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
            Toast.makeText(getContext(),"Correo no válido", Toast.LENGTH_LONG).show();
            return;
        }
        //Si se actualiza el correo, hay que ir mirando en cada comunidad en la que se encuentre, para cambiar el correo con el que está inscrito,
        //pq si no,no cargará ninguna comunidad
        user.updateEmail(correo)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            actualizaNombreYFoto();
                        }else{
                            Toast.makeText(getContext(),"Correo en uso", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void actualizaNombreYFoto() {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(nombre)
                //.setPhotoUri(Uri.parse(foto))
                .build();
        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.v("nombre2",nombre);
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(),"Perfil actualizado con éxito", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getContext(),"Fallo al actualizar perfil", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                });
    }

    private void cerrarSesion() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getContext(),LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_perfil_usuario, container, false);
    }


}
