package com.example.admin.kakawev2.Anadir_Comunidad;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.admin.kakawev2.R;

public class AnadirComunidadActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_comunidad);

        Bundle datos = new Bundle();
        datos.putString("contenedor","contenedor_anadirComunidad");
        Fragment fragmentoSeleccionado2 = new BuscarComunidadFragment();
        FragmentManager fm2 = getSupportFragmentManager();
        FragmentTransaction t2 = fm2.beginTransaction();
        t2.replace(R.id.contenedor_anadirComunidad, fragmentoSeleccionado2);
        t2.commit();
        fragmentoSeleccionado2.setArguments(datos);

    }
}
