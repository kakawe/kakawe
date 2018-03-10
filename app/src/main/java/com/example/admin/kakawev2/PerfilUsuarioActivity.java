package com.example.admin.kakawev2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.kakawev2.Dialogs.PerfilUsuarioDialog;
import com.example.admin.kakawev2.Tablon.TablonActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class PerfilUsuarioActivity extends AppCompatActivity implements ConfirmarSalirAppDialog.AvisarSalirApp{


    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private String nombre = "";
    private String correo = "";
    private String correoViejo;
    private String comunidadActual;
    private String actuContra = user.getEmail();

    private ImageView iv_perfilUs_fotoUs;
    private TextView et_perfilUs_nombreUsuario;
    private TextView et_perfilUs_correoUsuario;
    private Button bt_perfilUs_modContrasena, bt_perfilUs_actualiarDatos, bt_perfilUs_cerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);

        comunidadActual=getIntent().getStringExtra("comunidad");
        iv_perfilUs_fotoUs = (ImageView)findViewById(R.id.iv_perfilUs_fotoUs1);
        et_perfilUs_nombreUsuario = (TextView)findViewById(R.id.et_perfilUs_nombreUsuario1);
        et_perfilUs_correoUsuario = (TextView)findViewById(R.id.et_perfilUs_correoUsuario1);
        bt_perfilUs_modContrasena = (Button)findViewById(R.id.bt_perfilUs_modContrasena1);
        bt_perfilUs_actualiarDatos = (Button)findViewById(R.id.bt_perfilUs_actualiarDatos1);
        bt_perfilUs_cerrarSesion = (Button)findViewById(R.id.bt_perfilUs_cerrarSesion);

        bt_perfilUs_modContrasena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PerfilUsuarioDialog pu = new PerfilUsuarioDialog();
                pu.show(getFragmentManager(), "Dialog boton Añadir");
                Bundle datos = new Bundle();
                datos.putString("correo", actuContra);
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
                ConfirmarSalirAppDialog df=new ConfirmarSalirAppDialog();
                df.show(getFragmentManager(), "Confirmar salir de la App");
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
        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            Toast.makeText(PerfilUsuarioActivity.this, "Correo no válido", Toast.LENGTH_LONG).show();
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
                        } else {
                            Toast.makeText(PerfilUsuarioActivity.this, "Correo en uso", Toast.LENGTH_LONG).show();
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
                        Log.v("nombre2", nombre);
                        if (task.isSuccessful()) {
                            Toast.makeText(PerfilUsuarioActivity.this, "Perfil actualizado con éxito", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(PerfilUsuarioActivity.this, TablonActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.putExtra("comunidad",comunidadActual);
                            startActivity(intent);

                        } else {
                            Toast.makeText(PerfilUsuarioActivity.this, "Fallo al actualizar perfil", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                });
    }
    private void cancelarActualizacionPerfil(){
        Intent intent = new Intent(PerfilUsuarioActivity.this, TablonActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("comunidad",comunidadActual);
        startActivity(intent);
    }
    @Override
    public void aceptarSalirApp() {
        FirebaseAuth.getInstance().signOut();
        System.exit(0);
    }
}
