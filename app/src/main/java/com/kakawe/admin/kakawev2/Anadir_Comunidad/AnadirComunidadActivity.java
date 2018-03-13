package com.kakawe.admin.kakawev2.Anadir_Comunidad;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.AnyRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kakawe.admin.kakawev2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AnadirComunidadActivity extends AppCompatActivity {
    private StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_comunidad);

        fotoPredeterminadaUsuario();
        Bundle datos = new Bundle();
        datos.putString("contenedor", "contenedor_anadirComunidad");
        Fragment fragmentoSeleccionado2 = new BuscarComunidadFragment();
        FragmentManager fm2 = getSupportFragmentManager();
        FragmentTransaction t2 = fm2.beginTransaction();
        t2.replace(R.id.contenedor_anadirComunidad, fragmentoSeleccionado2);
        t2.commit();
        fragmentoSeleccionado2.setArguments(datos);

    }


    public static final Uri getUriToDrawable(@NonNull Context context,
                                             @AnyRes int drawableId) {
        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + context.getResources().getResourcePackageName(drawableId)
                + '/' + context.getResources().getResourceTypeName(drawableId)
                + '/' + context.getResources().getResourceEntryName(drawableId));
        return imageUri;
    }

    //cargamos a firebase la foto predeterminada del usuario
    private void fotoPredeterminadaUsuario() {
        //subimos a firebase
        FirebaseAuth au = FirebaseAuth.getInstance();
        String correo = au.getCurrentUser().getEmail();
        Uri u = getUriToDrawable(AnadirComunidadActivity.this, R.drawable.stewie);
        //Uri file = Uri.fromFile(new File(R.drawable.stewie));
        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference rutaCarpetaImg = storageReference.child("ImagenesPerfilUsuario").child(correo);
        //subimos la imagen y verificamos mediante un toast que se subio la foto
        rutaCarpetaImg.putFile(u);
    }


}
