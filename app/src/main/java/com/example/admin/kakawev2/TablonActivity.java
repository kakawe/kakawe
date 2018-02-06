package com.example.admin.kakawev2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.admin.kakawev2.Adaptadores.RVAdapter;
import com.example.admin.kakawev2.Entidades.Anuncio;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TablonActivity extends AppCompatActivity {

    private static DatabaseReference referencia;

    private RecyclerView rv_tablon_listatablon;
    private Button bt__tablon_izquierdo,bt_tablon_derecho;
    private RVAdapter rvAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablon);

        rv_tablon_listatablon = (RecyclerView)findViewById(R.id.rv_tablon_listatablon);
        rv_tablon_listatablon.setLayoutManager(new LinearLayoutManager(this));

        bt__tablon_izquierdo = (Button)findViewById(R.id.bt__tablon_izquierdo);
        bt_tablon_derecho = (Button)findViewById(R.id.bt_tablon_derecho);

        rvAdapter = new RVAdapter(pruebaVision());
        rv_tablon_listatablon.setAdapter(rvAdapter);

        bt__tablon_izquierdo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerOfertas();
            }
        });
        bt_tablon_derecho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerDemandas();
            }
        });
    }

    public List<Anuncio> pruebaVision() {
        List<Anuncio> anuncios = new ArrayList<>();
        anuncios.add(new Anuncio("Necesito taladro", "Necesito urgentemente un taladro para colocar varios cuadros", 1, "hola"));
        anuncios.add(new Anuncio("Cocino", "asdfibc jfbasfb asfdboasnodf asf a", 1, "hola"));
        anuncios.add(new Anuncio("paseo ancianos", "dsfgjbsadasd asdasfasfasf asdf asd", 1, "hola"));
        anuncios.add(new Anuncio("compro pan", "aobdgjdsbjfbsadbj ksadblfnasfljasbf", 1, "hola"));
        anuncios.add(new Anuncio("Paseo perro", "aikjdfgoa sgngipgpasdg asdgas dga gdas d", 1, "hola"));
        return anuncios;
    }
    public void obtenerOfertas(){
        String nombreCom="NogalGuadalix";
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
                    Log.v("datosta",titulo);
                    Log.v("datosta",mensaje);
                    Log.v("datosta",Integer.toString(foto));
                    Log.v("datosta",tipo);

                }
                Log.v("datosBD",Integer.toString(anuncio_oferta.size()));
                String uno = anuncio_oferta.get(0).getTitulo();
                String dos = anuncio_oferta.get(1).getTitulo();
                Log.v("datosta",uno);
                Log.v("datosta",dos);
                rvAdapter = new RVAdapter(anuncio_oferta);
                rv_tablon_listatablon.setAdapter(rvAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void obtenerDemandas(){
        String nombreCom="NogalGuadalix";
        referencia = FirebaseDatabase.getInstance().getReference("comunidades");
        referencia.child(nombreCom).child("Anuncios").child("demanda").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Anuncio> anuncio_demanda=new ArrayList<>();
                for (DataSnapshot dato : dataSnapshot.getChildren()){
                    Anuncio aO = dato.getValue(Anuncio.class);
                    anuncio_demanda.add(aO);

                    String titulo = aO.getTitulo();
                    String mensaje = aO.getMensaje();
                    int foto = aO.getFoto();
                    String tipo = aO.getTipo();
                    Log.v("datosta",titulo);
                    Log.v("datosta",mensaje);
                    Log.v("datosta",Integer.toString(foto));
                    Log.v("datosta",tipo);

                }
                Log.v("datosBD",Integer.toString(anuncio_demanda.size()));
                String uno = anuncio_demanda.get(0).getTitulo();
                String dos = anuncio_demanda.get(1).getTitulo();
                Log.v("datosta",uno);
                Log.v("datosta",dos);
                rvAdapter = new RVAdapter(anuncio_demanda);
                rv_tablon_listatablon.setAdapter(rvAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
