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


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuPrincipalFragment extends Fragment {

    TextView tv_menuPrincipal_nombre,tv_menuPrincipal_domicilio;
    View vista,vistaPrincipal,vistaPrincipal1;

    public MenuPrincipalFragment() {
        // Required empty public constructor
    }
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);





    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.fragment_menu_principal,null);

        NavigationView navegadorCom = (NavigationView)vista.findViewById(R.id.menu_principal);
        vistaPrincipal = navegadorCom.getHeaderView(0);
        vistaPrincipal1= navegadorCom.getHeaderView(1);
        tv_menuPrincipal_nombre = (TextView) vistaPrincipal.findViewById(R.id.tv_menuPrincipal_nombre);
        tv_menuPrincipal_domicilio=(TextView) vistaPrincipal.findViewById(R.id.tv_menuPrincipal_domicilio);
        tv_menuPrincipal_nombre.setText("holaaaa".toString());
        tv_menuPrincipal_domicilio.setText("Primero, "+"derecha");

        // Retornamos la vista nueva creada
        return vista;
    }

}
