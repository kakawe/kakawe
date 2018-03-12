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
import com.example.admin.kakawev2.Entidades.Vecino;
import com.example.admin.kakawev2.FragmentMisAnuncios;
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
public class MenuPrincipalFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener{

    private static DatabaseReference referencia;

    TextView tv_menuPrincipal_comunidad,tv_menuPrincipal_domicilio;
    View vista,vistaPrincipal,vistaPrincipal1;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

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

        comActual=getArguments().getString("nombreCom");
        String nombreUser = user.getDisplayName();

        NavigationView navegadorCom = (NavigationView)vista.findViewById(R.id.menu_principal);
        vistaPrincipal = navegadorCom.getHeaderView(0);
        vistaPrincipal1= navegadorCom.getHeaderView(1);
        navegadorCom.setNavigationItemSelectedListener(this);
        tv_menuPrincipal_comunidad = (TextView) vistaPrincipal.findViewById(R.id.tv_menuPrincipal_comunidad);
        tv_menuPrincipal_domicilio=(TextView) vistaPrincipal.findViewById(R.id.tv_menuPrincipal_domicilio);
        tv_menuPrincipal_comunidad.setText(comActual);

        //tv_menuPrincipal_domicilio.setText("Primero, "+"derecha");




        final Bundle datos = new Bundle();

        tv_menuPrincipal_comunidad.setOnClickListener(new View.OnClickListener() {

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
        cargaComunidad();
        // Retornamos la vista nueva creada
        return vista;
    }

    private void cargaComunidad() {
        final String correo = user.getEmail();
        referencia = FirebaseDatabase.getInstance().getReference("comunidades");
        referencia.child(comActual).child("usuarios").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Vecino> listado_Vecinos = new ArrayList<>();
                for (DataSnapshot dato : dataSnapshot.getChildren()) {
                    Vecino vc = dato.getValue(Vecino.class);
                    listado_Vecinos.add(vc);
                    String corre = vc.getMail();
                    String key = dato.getKey();
                    if (corre.equals(correo)) {
                        String piso = vc.getPiso();
                        String puerta = vc.getPuerta();
                        String pisoyPuerta = piso + " " + puerta;
                        tv_menuPrincipal_domicilio.setText(pisoyPuerta);

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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
            datos.putString("tipo","Ofrezco");
            datos.putString("nombreCom",comActual);
            crear.setArguments(datos);

        }if (id == R.id.m_menuP_necesitan) {
            cerrarDrawer();
            fragmentManager.replace(R.id.contenedorTablon,crear).commit();
            datos.putString("tipo","Necesito");
            datos.putString("nombreCom",comActual);
            crear.setArguments(datos);
        }if(id== R.id.m_menuP_historial){

            FragmentTransaction fm = getFragmentManager().beginTransaction();
            Fragment listado = new FragmentMisAnuncios();
            cerrarDrawer();
            fm.replace(R.id.contenedorTablon,listado).commit();
            Bundle dato= new Bundle();
            dato.putString("nombreCom",comActual);
            listado.setArguments(dato);

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
