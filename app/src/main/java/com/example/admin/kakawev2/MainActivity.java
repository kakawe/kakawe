package com.example.admin.kakawev2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.admin.kakawev2.Entidades.Anuncio;
import com.example.admin.kakawev2.Entidades.Comunidad;
import com.example.admin.kakawev2.Entidades.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static DatabaseReference referencia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //insertar anuncio
        /*String titulo = "Comparto internet";
        String mensaje = "Comparto la linea de internet";
        int foto = 3;
        String tipo = "oferta";


        FirebaseUser usuarioActual = FirebaseAuth.getInstance().getCurrentUser();
        String correoUsuario = usuarioActual.getEmail();
        Anuncio anuncio = new Anuncio(titulo,mensaje,foto,tipo);

        referencia = FirebaseDatabase.getInstance().getReference("comunidades");
        String key = referencia.push().getKey();
        referencia.child("NogalGuadalix").child("Anuncios").child(tipo).child(key).setValue(anuncio);*/

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



        Intent intent = new Intent(this,RegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}