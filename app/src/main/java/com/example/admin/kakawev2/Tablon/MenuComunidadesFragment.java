package com.example.admin.kakawev2.Tablon;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.kakawev2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuComunidadesFragment extends Fragment {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    TextView tv_menuComunidades_nombre;
    View vista,vistaComunidad;


    private String nombre="";

    public MenuComunidadesFragment() {
        // Required empty public constructor
    }
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View prueba = inflater.inflate(R.layout.header_menu_comunidades,null);
        TextView no= (TextView)prueba.findViewById(R.id.tv_menuComunidades_nombre);

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("datos5","Clicandoooooo");
            }
        });
        no.setText("gola");

        //cargadatos();
    }

    private void cargadatos() {
        if (user != null) {
            nombre = user.getDisplayName();
            Log.v("datos3",nombre);
            String n = nombre;
            //tv_menuComunidades_nombre.setText(nombre);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.fragment_menu_comunidades,null);

        NavigationView navegadorCom = (NavigationView)vista.findViewById(R.id.menu_comunidades);
        vistaComunidad=navegadorCom.getHeaderView(0);
        tv_menuComunidades_nombre = (TextView) vistaComunidad.findViewById(R.id.tv_menuComunidades_nombre);
        tv_menuComunidades_nombre.setText("sdfoisndfoansdfa");

        // Retornamos la vista nueva creada
        return vista;
    }


}
