package com.example.admin.kakawev2.Tablon;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.admin.kakawev2.R;

public class TablonActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablon);

        String nombrecom = getIntent().getStringExtra("comunidad");
        Fragment fragmentoSeleccionado2 = new PerfilUsuarioFragment();
        FragmentManager fm2 = getSupportFragmentManager();
        FragmentTransaction t2 = fm2.beginTransaction();
        t2.replace(R.id.contenedorTablon, fragmentoSeleccionado2);
        t2.commit();
        Bundle datos = new Bundle();
        datos.putString("nombreCom",nombrecom);
        fragmentoSeleccionado2.setArguments(datos);


    }
}
