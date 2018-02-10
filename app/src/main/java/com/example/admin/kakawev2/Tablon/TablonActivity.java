package com.example.admin.kakawev2.Tablon;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.admin.kakawev2.R;
import com.example.admin.kakawev2.Tablon.ListaAnuncioFragment;

public class TablonActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablon);

        Fragment fragmentoSeleccionado2 = new ListaAnuncioFragment();
        FragmentManager fm2 = getSupportFragmentManager();
        FragmentTransaction t2 = fm2.beginTransaction();
        t2.replace(R.id.contenedorTablon, fragmentoSeleccionado2);
        t2.commit();
    }
}
