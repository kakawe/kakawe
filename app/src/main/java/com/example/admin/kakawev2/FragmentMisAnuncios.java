package com.example.admin.kakawev2;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.kakawev2.Adaptadores.RVAdapter;
import com.example.admin.kakawev2.Entidades.Anuncio2;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class FragmentMisAnuncios extends Fragment {

    private static DatabaseReference referencia;
    RecyclerView rv_tablon_misAnuncios;
    private String nombreCom;
    private RVAdapter rvAdapter;
    public FragmentMisAnuncios() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_fragment_mis_anuncios, container, false);
        rv_tablon_misAnuncios = (RecyclerView) vista.findViewById(R.id.rv_tablon_misAnuncios);
        rv_tablon_misAnuncios.setLayoutManager(new LinearLayoutManager(getContext()));
        nombreCom = getArguments().getString("nombreCom");

        cargarListado();
        return vista;
    }

    private void cargarListado() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String alias = user.getDisplayName();
        referencia = FirebaseDatabase.getInstance().getReference("AnunciosUsuarios");
        referencia.child(alias).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Anuncio2> anuncios = new ArrayList<>();
                for (DataSnapshot dato : dataSnapshot.getChildren()) {
                    Anuncio2 aO = dato.getValue(Anuncio2.class);
                    anuncios.add(aO);
                    Log.v("datosUsuarios",aO.toString());
                }
                rvAdapter = new RVAdapter(anuncios, getActivity());
                rv_tablon_misAnuncios.setAdapter(rvAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
