package com.example.admin.kakawev2.Tablon;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.kakawev2.Anadir_Comunidad.AnadirDomicilioFragment;
import com.example.admin.kakawev2.Anadir_Comunidad.BuscarComunidadFragment;
import com.example.admin.kakawev2.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuPrincipalFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener{

    TextView tv_menuPrincipal_nombre,tv_menuPrincipal_domicilio;
    View vista,vistaPrincipal,vistaPrincipal1;

    String comActual;
    CierraDrawer c;

    public interface CierraDrawer {
        public void cerrarDrawer();
    }

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

        comActual="UnoDos";

        NavigationView navegadorCom = (NavigationView)vista.findViewById(R.id.menu_principal);
        vistaPrincipal = navegadorCom.getHeaderView(0);
        vistaPrincipal1= navegadorCom.getHeaderView(1);
        navegadorCom.setNavigationItemSelectedListener(this);
        tv_menuPrincipal_nombre = (TextView) vistaPrincipal.findViewById(R.id.tv_menuPrincipal_nombre);
        tv_menuPrincipal_domicilio=(TextView) vistaPrincipal.findViewById(R.id.tv_menuPrincipal_domicilio);
        tv_menuPrincipal_nombre.setText("holaaaa".toString());
        tv_menuPrincipal_domicilio.setText("Primero, "+"derecha");




        final Bundle datos = new Bundle();

        tv_menuPrincipal_nombre.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                cerrarDrawer();
                final Fragment crear = new PerfilComunidadFragment();
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.contenedorTablon,crear).commit();
                datos.putString("nombreCom",comActual);
                crear.setArguments(datos);
            }
        });
        tv_menuPrincipal_domicilio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerrarDrawer();
                final Fragment crear = new PerfilComunidadFragment();
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.contenedorTablon,crear).commit();
                datos.putString("nombreCom",comActual);
                crear.setArguments(datos);
            }
        });

        // Retornamos la vista nueva creada
        return vista;
    }


    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Log.v("numeroqueviene",String.valueOf(id));
        FragmentTransaction fragmentManager = getFragmentManager().beginTransaction();
        Fragment crear = new ListaAnuncioFragment();
        Bundle datos= new Bundle();
        if (id == R.id.m_menuP_ofrecen) {
            cerrarDrawer();
            fragmentManager.replace(R.id.contenedorTablon,crear).commit();
            datos.putString("tipo","ofrecen");
            crear.setArguments(datos);

        }if (id == R.id.m_menuP_necesitan) {
            cerrarDrawer();
            fragmentManager.replace(R.id.contenedorTablon,crear).commit();
            datos.putString("tipo","necesitan");
            crear.setArguments(datos);
        }
        return true;
    }
    public void cerrarDrawer()
    {
        FragmentActivity fa= getActivity();
        c= (MenuPrincipalFragment.CierraDrawer)fa;
        c.cerrarDrawer();
    }
}
