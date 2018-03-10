package com.example.admin.kakawev2.Tablon;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.admin.kakawev2.Adaptadores.RVAdapter;
import com.example.admin.kakawev2.DetallesAnuncio;
import com.example.admin.kakawev2.Dialogs.AnadirAnuncioCategoriaDialog2;
import com.example.admin.kakawev2.Dialogs.AnadirAnuncioDialog1;
import com.example.admin.kakawev2.Dialogs.AnadirAnuncioDialog2;
import com.example.admin.kakawev2.Entidades.Anuncio2;
import com.example.admin.kakawev2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListaAnuncioFragment extends Fragment{

    private static DatabaseReference referencia;

    private RecyclerView rv_tablon_listatablon;
    private FloatingActionButton fb_listar_anadirAnuncio;
    private Button bt_tablon_izquierdo, bt_tablon_derecho;
    private RVAdapter rvAdapter;
    LinearLayout contComu;
    private String nombreCom;
    private String pestana, tipo;
    ArrayList<String> comus_usuario = new ArrayList<>();

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    public ListaAnuncioFragment() {
        // Required empty public constructor
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista =  inflater.inflate(R.layout.fragment_lista_anuncio, container, false);
        nombreCom = getArguments().getString("nombreCom");
        tipo = getArguments().getString("tipo");

        fb_listar_anadirAnuncio = (FloatingActionButton)vista.findViewById(R.id.fb_listar_anadirAnuncio);
        rv_tablon_listatablon = (RecyclerView) vista.findViewById(R.id.rv_tablon_listatablon);
        rv_tablon_listatablon.setLayoutManager(new LinearLayoutManager(getContext()));

        bt_tablon_izquierdo = (Button) vista.findViewById(R.id.bt_tablon_izquierdo);
        bt_tablon_derecho = (Button) vista.findViewById(R.id.bt_tablon_derecho);



        fb_listar_anadirAnuncio = (FloatingActionButton) vista.findViewById(R.id.fb_listar_anadirAnuncio);


        if (tipo.equals("Ofrezco")) {
            Log.v("situacion","salto en ofrecen");
            obtenerOfertas();
            pestana="Ofrezco";
        } else {
            obtenerDemandas();
            pestana="Necesito";
        }

        bt_tablon_izquierdo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerOfertas();
                pestana = "Ofrezco";
            }
        });
        bt_tablon_derecho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerDemandas();
                pestana = "Necesito";
            }
        });


        //poder seleccionar las card dentro del recyclerView
        final GestureDetector mGestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        rv_tablon_listatablon.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                try {
                    View child = rv.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && mGestureDetector.onTouchEvent(e)) {
                        int posicion = rv.getChildAdapterPosition(child);
                        selectorAnuncio(pestana, posicion);
                        //mensaje numero card selecciona
                        Toast.makeText(getContext(), "Clicado " + posicion, Toast.LENGTH_LONG).show();
                        return true;
                    }

                } catch (Exception e1) {
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
        fb_listar_anadirAnuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnadirAnuncioDialog1 ad1 = new AnadirAnuncioDialog1();
                Bundle datos = new Bundle();
                datos.putString("nomComunidad",nombreCom);
                ad1.show(getActivity().getFragmentManager(), "ad1");
                ad1.setArguments(datos);

            }
        });

        return vista;
    }


    private void selectorAnuncio(String pestana, final int position) {
        if (pestana.equals("oferta")) {
            referencia = FirebaseDatabase.getInstance().getReference("comunidades");
            referencia.child(nombreCom).child("Anuncios").child("Ofrezco").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ArrayList<Anuncio2> anuncio_oferta = new ArrayList<>();
                    for (DataSnapshot dato : dataSnapshot.getChildren()) {
                        Anuncio2 aO = dato.getValue(Anuncio2.class);
                        anuncio_oferta.add(aO);
                    }

                    String titulo = anuncio_oferta.get(position).getTitulo();
                    String mensaje = anuncio_oferta.get(position).getDescripcion();
                    //String foto = anuncio_oferta.get(position).getFoto();
                    String tipo = anuncio_oferta.get(position).getTipo();

                    DetallesAnuncio detallesAnuncio = new DetallesAnuncio();
                    Bundle b = new Bundle();
                    //b.putString("ImagenAnuncio","imangen_anuncio");
                    b.putString("TipoAnuncio", tipo);
                    b.putString("TituloAnuncio", titulo);
                    b.putString("MensajeAnuncio", mensaje);
                    //b.putString("foto",foto);
                    detallesAnuncio.setArguments(b);

                    detallesAnuncio.show(getActivity().getFragmentManager(), "Dialog");

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else {
            referencia = FirebaseDatabase.getInstance().getReference("comunidades");
            referencia.child(nombreCom).child("Anuncios").child("Necesito").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ArrayList<Anuncio2> anuncio_demanda = new ArrayList<>();
                    for (DataSnapshot dato : dataSnapshot.getChildren()) {
                        Anuncio2 aO = dato.getValue(Anuncio2.class);
                        anuncio_demanda.add(aO);
                    }
                    String titulo = anuncio_demanda.get(position).getTitulo();
                    String mensaje = anuncio_demanda.get(position).getDescripcion();
                    //String foto = anuncio_demanda.get(position).getFoto();
                    String tipo = anuncio_demanda.get(position).getTipo();

                    DetallesAnuncio detallesAnuncio = new DetallesAnuncio();
                    Bundle b = new Bundle();
                    //b.putString("ImagenAnuncio","imangen_anuncio");
                    b.putString("TipoAnuncio", tipo);
                    b.putString("TituloAnuncio", titulo);
                    b.putString("MensajeAnuncio", mensaje);
                    //b.putString("foto",foto);
                    detallesAnuncio.setArguments(b);
                    detallesAnuncio.show(getActivity().getFragmentManager(), "Dialog");
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
    }


    public void obtenerOfertas() {
        referencia = FirebaseDatabase.getInstance().getReference("comunidades");
        referencia.child(nombreCom).child("Anuncios").child("Ofrezco").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Anuncio2> anuncio_oferta = new ArrayList<>();
                for (DataSnapshot dato : dataSnapshot.getChildren()) {
                    Anuncio2 aO = dato.getValue(Anuncio2.class);
                    Log.v("baseDatos",String.valueOf(anuncio_oferta.size()));
                    anuncio_oferta.add(aO);
                }
                rvAdapter = new RVAdapter(anuncio_oferta);
                rv_tablon_listatablon.setAdapter(rvAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void obtenerDemandas() {
        referencia = FirebaseDatabase.getInstance().getReference("comunidades");
            referencia.child(nombreCom).child("Anuncios").child("Necesito").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Anuncio2> anuncio_demanda = new ArrayList<>();
                for (DataSnapshot dato : dataSnapshot.getChildren()) {
                    Anuncio2 aO = dato.getValue(Anuncio2.class);
                    anuncio_demanda.add(aO);
                }
                rvAdapter = new RVAdapter(anuncio_demanda);
                rv_tablon_listatablon.setAdapter(rvAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
