package com.example.admin.kakawev2;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Handler;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.widget.ImageView;

import com.example.admin.kakawev2.Anadir_Comunidad.AnadirComunidadActivity;
import com.example.admin.kakawev2.Entidades.Anuncio;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private final int DURACION_SPLASH = 5000;
    private static DatabaseReference referencia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        ImageView imageView = (ImageView) findViewById(R.id.imageView);


        AnimatedVectorDrawableCompat splash = AnimatedVectorDrawableCompat.create(getApplicationContext(),R.drawable.animated_logo_kakawe);
        //AnimatedVectorDrawableCompat splash = AnimatedVectorDrawableCompat.create(getApplicationContext(), R.drawable.logo_kakawe);

        imageView.setImageDrawable(splash);

        final Animatable animatable = (Animatable) imageView.getDrawable();

        animatable.start();
        //insertar anuncio
        /*String titulo = "Taladro";
        String mensaje = "a ver si alguien puede prestarme un taladro por favor!!!";
        int foto = 1;
        String tipo = "demanda";


        FirebaseUser usuarioActual = FirebaseAuth.getInstance().getCurrentUser();
        String correoUsuario = usuarioActual.getEmail();
        Anuncio anuncio = new Anuncio(titulo,mensaje,foto,tipo);

        referencia = FirebaseDatabase.getInstance().getReference("comunidades");
        String key = referencia.push().getKey();
        referencia.child("NogalGuadalix").child("Anuncios").child(tipo).child(key).setValue(anuncio);

        //Recuperar anuncios
        /*String nombreCom="NogalGuadalix";
        referencia = FirebaseDatabase.getInstance().getReference("comunidades");
        referencia.child(nombreCom).child("Anuncios").child("oferta").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Anuncio> anuncio_oferta=new ArrayList<>();
                for (DataSnapshot dato : dataSnapshot.getChildren()){
                    Anuncio aO = dato.getValue(Anuncio.class);
                    anuncio_oferta.add(aO);

                    String titulo = aO.getTitulo();
                    String mensaje = aO.getMensaje();
                    int foto = aO.getFoto();
                    String tipo = aO.getTipo();
                    Log.v("datosBD",titulo);
                    Log.v("datosBD",mensaje);
                    Log.v("datosBD",Integer.toString(foto));
                    Log.v("datosBD",tipo);

                }
                Log.v("datosBD",Integer.toString(anuncio_oferta.size()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/


        /*new Handler().postDelayed(new Runnable(){
            public void run(){
                Intent intent = new Intent(MainActivity.this, AnadirComunidadActivity.class);
                startActivity(intent);
                finish();
            };
        }, DURACION_SPLASH);*/

        Intent intent = new Intent(this,TablonActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
