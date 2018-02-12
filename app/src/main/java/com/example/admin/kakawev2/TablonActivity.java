package com.example.admin.kakawev2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

        obtenerOfertas();
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
        //poder seleccionar las card dentro del recyclerView
        final GestureDetector mGestureDetector = new GestureDetector(TablonActivity.this, new GestureDetector.SimpleOnGestureListener() {
            @Override public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
        rv_tablon_listatablon.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                try{
                    View child = rv.findChildViewUnder(e.getX(),e.getY());
                    if (child != null && mGestureDetector.onTouchEvent(e)){
                        int posicion = rv.getChildAdapterPosition(child);

                        DetallesAnuncio detallesAnuncio = new DetallesAnuncio();
                        Bundle b = new Bundle();
                        //b.putString("ImagenAnuncio","imangen_anuncio");
                        b.putString("TipoAnuncio","tipo_anuncio");
                        b.putString("TituloAnuncio","titulo_anuncio");
                        b.putString("MensajeAnuncio","mensaje_anuncio");
                        detallesAnuncio.setArguments(b);
                        detallesAnuncio.show(getFragmentManager(),"Dialog");
                        //mensaje numero card selecciona
                        Toast.makeText(TablonActivity.this, "Clicado " + posicion, Toast.LENGTH_LONG).show();
                        return true;
                    }

                }catch (Exception e1){
                    e1.printStackTrace();
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

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
