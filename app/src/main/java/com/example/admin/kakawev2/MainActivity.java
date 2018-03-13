package com.example.admin.kakawev2;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Handler;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.admin.kakawev2.Dialogs.AnadirAnuncioCategoriaDialog2;
import com.example.admin.kakawev2.Dialogs.AnadirAnuncioDialog1;
import com.example.admin.kakawev2.Dialogs.AnadirAnuncioDialog2;
import com.google.firebase.database.DatabaseReference;

public class MainActivity extends AppCompatActivity {

    private final int DURACION_SPLASH = 5000;
    private static DatabaseReference referencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        AnimatedVectorDrawableCompat splash = AnimatedVectorDrawableCompat.create(getApplicationContext(), R.drawable.animated_logo_kakawe);
        //AnimatedVectorDrawableCompat splash = AnimatedVectorDrawableCompat.create(getApplicationContext(), R.drawable.vector_anim_kakawe);
        imageView.setImageDrawable(splash);
        final Animatable animatable = (Animatable) imageView.getDrawable();
        animatable.start();



        /*Button bt_tablon_anadirAnuncio = (Button)findViewById(R.id.bt_tablon_anadirAnuncio);
        bt_tablon_anadirAnuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //nombre comunidad se cogera del tablon
                String nomComunidad = "Cuatro";
                AnadirAnuncioDialog1 ad1 = new AnadirAnuncioDialog1();
                Bundle datos = new Bundle();
                datos.putString("nomComunidad",nomComunidad);
                ad1.show(getFragmentManager(),"ad1");
                ad1.setArguments(datos);

            }
        });*/


        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }

            ;
        }, DURACION_SPLASH);
/*
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
*/
    }

}