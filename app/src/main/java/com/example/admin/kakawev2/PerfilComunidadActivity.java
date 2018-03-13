package com.example.admin.kakawev2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.admin.kakawev2.Entidades.Vecino;
import com.example.admin.kakawev2.Tablon.TablonActivity;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class PerfilComunidadActivity extends AppCompatActivity implements View.OnClickListener{
    private static DatabaseReference referencia;
    private ProgressDialog progreso;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    Intent intent;
    Uri uri;

    //Subir imagen a FireBase
    private ImageView iv_perfilCom_fotoCom,iv_perfilCom_cerrar;
    private StorageReference storageReference;
    private static final int GALERY_INTENT = 1;

    private String correo;
    //Habrá que traer de la ventana anterior la comunidad actual en la que se encuentra, para poder cargar los datos en esa comunidad
    private String comActual;
    private String piso, puerta, ruta;

    private Button bt_perfilCom_actualiarDatos;


    private EditText et_perfilCom_piso, et_perfilCom_puerta;
    private TextView tv_perfilCom_nombreCom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_comunidad);
        progreso = new ProgressDialog(this);
        comActual = getIntent().getStringExtra("nombreCom");

        //creamos la referencia a firebaseStorage para poder interactuar con el storage
        storageReference = FirebaseStorage.getInstance().getReference();
        //instanciar imagen para proceder a obtenerla y lanzarla a firebase
        iv_perfilCom_fotoCom = (ImageView)findViewById(R.id.iv_menuPrincipal_fotoCom);
        iv_perfilCom_cerrar = (ImageView)findViewById(R.id.iv_perfilCom_cerrar);
        //metodo para clicar en la imagen
        iv_perfilCom_fotoCom.setOnClickListener(this);

        iv_perfilCom_cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PerfilComunidadActivity.this, TablonActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("comunidad", comActual);
                startActivity(intent);
            }
        });
        correo = user.getEmail();
        et_perfilCom_piso = (EditText)findViewById(R.id.et_perfilCom_piso);
        et_perfilCom_puerta = (EditText)findViewById(R.id.et_perfilCom_puerta);
        tv_perfilCom_nombreCom = (TextView)findViewById(R.id.tv_perfilCom_nombreCom);
        bt_perfilCom_actualiarDatos = (Button)findViewById(R.id.bt_perfilCom_actualiarDatos);
        bt_perfilCom_actualiarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarDatos();

            }
        });

        cargaComunidad();
        cargarFotoComunidd();

    }

    private void cargaComunidad() {
        //Buscamos en la comunidad el usuario con el mismo correo que el logueado, para coger sus datos de sesion
        referencia = FirebaseDatabase.getInstance().getReference("comunidades");
        referencia.child(comActual).child("usuarios").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Vecino> listado_Vecinos = new ArrayList<>();
                for (DataSnapshot dato : dataSnapshot.getChildren()) {
                    Vecino vc = dato.getValue(Vecino.class);
                    listado_Vecinos.add(vc);
                    String corre = vc.getMail();
                    String key = dato.getKey();
                    if (corre.equals(correo)) {
                        piso = vc.getPiso();
                        puerta = vc.getPuerta();
                        ruta = key;
                        et_perfilCom_piso.setText(piso);
                        et_perfilCom_puerta.setText(puerta);
                    }
                }
                tv_perfilCom_nombreCom.setText(comActual);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    //Actualizamos los datos del usuario si le da al boton, si algun campo está vacio,avisa de que no puede estar vacio
    private void actualizarDatos() {
        String pisoAct = et_perfilCom_piso.getText().toString();
        String puertaAct = et_perfilCom_puerta.getText().toString();
        if (pisoAct.isEmpty()) {
            Toast.makeText(this, "Introduce un piso", Toast.LENGTH_LONG).show();
            return;
        }
        if (puertaAct.isEmpty()) {
            Toast.makeText(this, "Introduce una puerta", Toast.LENGTH_LONG).show();
            return;
        }
        FirebaseUser usuarioActual = FirebaseAuth.getInstance().getCurrentUser();
        String correoUsuario = usuarioActual.getEmail();
        String mail = correoUsuario;
        Vecino vecino = new Vecino(correo, mail, pisoAct, puertaAct);
        referencia = FirebaseDatabase.getInstance().getReference("comunidades");
        referencia.child(comActual).child("usuarios").child(ruta).setValue(vecino);
        Toast.makeText(this, "Datos comunidad actualizados", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(PerfilComunidadActivity.this, TablonActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("comunidad", comActual);
        startActivity(intent);

    }

    //cuando se actualizan los datos lanza la foto a firebase
    private void subirFoto() {
        StorageReference rutaCarpetaImg = storageReference.child("ImagenesComunidad").child(tv_perfilCom_nombreCom.getText().toString());
        //subimos la imagen y verificamos mediante un toast que se subio la foto
        rutaCarpetaImg.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //descargar imagen de firebase

                Uri descargarFoto = taskSnapshot.getDownloadUrl();
                Glide.with(PerfilComunidadActivity.this)
                        .load(descargarFoto)
                        .into(iv_perfilCom_fotoCom);

                Toast.makeText(PerfilComunidadActivity.this, "Se subio la foto", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void cargarFotoComunidd() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference refGuardar = storage.getReferenceFromUrl("gs://kakawe-22f82.appspot.com").child("ImagenesComunidad").child(comActual);
        Log.v("comActual", comActual);
        Log.v("refGuardar", refGuardar.toString());
        if (refGuardar.getName().isEmpty()) {
        } else {
            Log.v("Entrada", "2");
            Glide.with(PerfilComunidadActivity.this).using(new FirebaseImageLoader())
                    .load(refGuardar)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(iv_perfilCom_fotoCom);
        }


    }


    //metodo que se activa al tocar la imagen
    @Override
    public void onClick(View v) {
        //creamos el acceso a la galeria
        intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, GALERY_INTENT);

    }

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
}
