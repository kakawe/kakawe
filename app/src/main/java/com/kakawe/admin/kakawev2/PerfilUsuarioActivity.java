package com.kakawe.admin.kakawev2;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kakawe.admin.kakawev2.Dialogs.ConfirmarSalirAppDialog;
import com.kakawe.admin.kakawev2.Dialogs.PerfilUsuarioDialog;
import com.kakawe.admin.kakawev2.Tablon.TablonActivity;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class PerfilUsuarioActivity extends AppCompatActivity implements ConfirmarSalirAppDialog.AvisarSalirApp {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private StorageReference storageReference;
    Intent intent;
    private static final int GALERY_INTENT = 1;

    private String nombre = "";
    private String correo = "";
    private String correoViejo;
    private String comunidadActual;
    private String actuContra = user.getEmail();
    Uri uri;

    TextView bt_perfilUs_modContrasena;
    private ImageView iv_perfilUs_fotoUs1,iv_perfilUs_atras;
    private EditText et_perfilUs_nombreUsuario,et_perfilUs_correoUsuario;
    private Button bt_perfilUs_actualiarDatos, bt_perfilUs_cerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);
        storageReference = FirebaseStorage.getInstance().getReference();

        comunidadActual = getIntent().getStringExtra("comunidad");
        iv_perfilUs_fotoUs1 = (ImageView) findViewById(R.id.iv_perfilUs_fotoUs);
        iv_perfilUs_atras = (ImageView) findViewById(R.id.iv_perfilUs_atras);
        et_perfilUs_nombreUsuario = (EditText) findViewById(R.id.et_perfilUs_nombre);
        et_perfilUs_correoUsuario = (EditText) findViewById(R.id.et_perfilUs_email);
        bt_perfilUs_modContrasena = (TextView) findViewById(R.id.bt_perfilUs_modContrasena);
        bt_perfilUs_actualiarDatos = (Button) findViewById(R.id.bt_perfilUs_actualiarDatos);

        //metodo para acceder a la galetia
        iv_perfilUs_fotoUs1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALERY_INTENT);
            }
        });

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
        cargarFotoPerfil();

        iv_perfilUs_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PerfilUsuarioActivity.this, TablonActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("comunidad", comunidadActual);
                startActivity(intent);
            }
        });
        cargarPerfil();
    }

    private void cargarFotoPerfil() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        correo = user.getEmail();
        StorageReference refGuardar = storage.getReferenceFromUrl("gs://kakawe-22f82.appspot.com").child("ImagenesPerfilUsuario").child(correo);
        Log.v("refGuardar", refGuardar.toString());
        if (refGuardar.getName().isEmpty()) {
        } else {
            Log.v("Entrada", "2");
            Glide.with(this).using(new FirebaseImageLoader())
                    .load(refGuardar)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(iv_perfilUs_fotoUs1);
        }
    }
    //cargamos la imagen desde firebase
    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //verificamos si obtenemos la imagen de la galeria
        if (requestCode == GALERY_INTENT && resultCode == RESULT_OK) {
            //Aquí sólo se recoge la URI. No se grabará hasta que no se haya grabado el contacto
            uri = data.getData();
            subirFoto();
        }
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
                            //lanzamos el metodo para subir la imagen
                            Toast.makeText(PerfilUsuarioActivity.this, "Perfil actualizado con éxito", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(PerfilUsuarioActivity.this, TablonActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.putExtra("comunidad", comunidadActual);
                            startActivity(intent);

                        } else {
                            Toast.makeText(PerfilUsuarioActivity.this, "Fallo al actualizar perfil", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                });
    }

    private void subirFoto() {
        StorageReference rutaCarpetaImg = storageReference.child("ImagenesPerfilUsuario").child(user.getEmail());
        //subimos la imagen y verificamos mediante un toast que se subio la foto
        rutaCarpetaImg.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //descargar imagen de firebase
                Uri descargarFoto = taskSnapshot.getDownloadUrl();
                Glide.with(PerfilUsuarioActivity.this)
                        .load(descargarFoto)
                        .into(iv_perfilUs_fotoUs1);

                Toast.makeText(PerfilUsuarioActivity.this, "Foto actualizada", Toast.LENGTH_LONG).show();
            }
        });
    }


    private void cancelarActualizacionPerfil() {
        Intent intent = new Intent(PerfilUsuarioActivity.this, TablonActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("comunidad", comunidadActual);
        startActivity(intent);
    }

    @Override
    public void aceptarSalirApp() {
        FirebaseAuth.getInstance().signOut();
        System.exit(0);
    }
}
