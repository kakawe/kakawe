package com.kakawe.admin.kakawev2;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Handler;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;

public class MainActivity extends AppCompatActivity {

    private final int DURACION_SPLASH = 5000;
    private static DatabaseReference referencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        AnimatedVectorDrawableCompat splash = AnimatedVectorDrawableCompat.create(getApplicationContext(),R.drawable.animated_logo_kakawe);
        //AnimatedVectorDrawableCompat splash = AnimatedVectorDrawableCompat.create(getApplicationContext(), R.drawable.vector_anim_kakawe);
        imageView.setImageDrawable(splash);
        final Animatable animatable = (Animatable) imageView.getDrawable();
        animatable.start();




        new Handler().postDelayed(new Runnable(){
            public void run(){
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            };
        }, DURACION_SPLASH);*/

        Intent intent = new Intent(this, RegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    }