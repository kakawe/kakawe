package com.example.admin.kakawev2.Tablon;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.admin.kakawev2.Adaptadores.RVAdapter;
import com.example.admin.kakawev2.Anadir_Comunidad.BuscarComunidadFragment;
import com.example.admin.kakawev2.Entidades.Anuncio;
import com.example.admin.kakawev2.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListaAnuncioFragment extends Fragment {

    private static DatabaseReference referencia;

    private RecyclerView rv_tablon_listatablon;
    private Button bt__tablon_izquierdo,bt_tablon_derecho;
    private RVAdapter rvAdapter;

    public ListaAnuncioFragment() {
        // Required empty public constructor
    }
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        rv_tablon_listatablon = (RecyclerView)getView().findViewById(R.id.rv_tablon_listatablon);
        rv_tablon_listatablon.setLayoutManager(new LinearLayoutManager(getContext()));

        bt__tablon_izquierdo = (Button)getView().findViewById(R.id.bt__tablon_izquierdo);
        bt_tablon_derecho = (Button)getView().findViewById(R.id.bt_tablon_derecho);

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lista_anuncio, container, false);
    }

}
