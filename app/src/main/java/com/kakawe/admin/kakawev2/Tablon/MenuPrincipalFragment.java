package com.kakawe.admin.kakawev2.Tablon;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kakawe.admin.kakawev2.Entidades.Vecino;
import com.kakawe.admin.kakawev2.FragmentMisAnuncios;
import com.kakawe.admin.kakawev2.PerfilComunidadActivity;
import com.kakawe.admin.kakawev2.R;
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
public class MenuPrincipalFragment extends Fragment{

    private static DatabaseReference referencia;

    TextView tv_menuPrincipal_comunidad,tv_menuPrincipal_domicilio;
    View vista,vistaPrincipal,vistaPrincipal1;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    Button btn_menu_tablon_ofrecen,btn_menu_tablon_necesitan,btn_menu_misAnuncios_historial;
    String comActual;
    CierraDrawer c;
    ImageView iv_menuPrincipal_fotoCom;

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
        tv_menuPrincipal_comunidad = (TextView) vista.findViewById(R.id.tv_menuPrincipal_comunidad_nombre);
        tv_menuPrincipal_domicilio=(TextView) vista.findViewById(R.id.tv_menuPrincipal_comunidad_domicilio);
        iv_menuPrincipal_fotoCom=(ImageView) vista.findViewById(R.id.iv_menuPrincipal_fotoCom);

        btn_menu_tablon_ofrecen=(Button) vista.findViewById(R.id.btn_menu_tablon_ofrecen);
        btn_menu_tablon_necesitan=(Button) vista.findViewById(R.id.btn_menu_tablon_necesitan);
        btn_menu_misAnuncios_historial=(Button) vista.findViewById(R.id.btn_menu_misAnuncios_historial);

        tv_menuPrincipal_comunidad.setText(comActual);

        final Bundle datos = new Bundle();
        tv_menuPrincipal_comunidad.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                cerrarDrawer();
                Intent intent = new Intent(getContext(), PerfilComunidadActivity.class);
                intent.putExtra("nombreCom",comActual);
                startActivity(intent);
            }
        });
        tv_menuPrincipal_domicilio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerrarDrawer();
                final Fragment crear = new PerfilComunidadFragment();
                Intent intent = new Intent(getContext(), PerfilComunidadActivity.class);
                intent.putExtra("nombreCom",comActual);
                startActivity(intent);
            }
        });
        btn_menu_tablon_ofrecen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentManager = getFragmentManager().beginTransaction();
                Fragment crear = new ListaAnuncioFragment();
                Bundle datos= new Bundle();
                cerrarDrawer();
                fragmentManager.replace(R.id.contenedorTablon,crear).commit();
                datos.putString("tipo","Ofrezco");
                datos.putString("nombreCom",comActual);
                crear.setArguments(datos);
            }
        });
        btn_menu_tablon_necesitan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentManager = getFragmentManager().beginTransaction();
                Fragment crear = new ListaAnuncioFragment();
                Bundle datos= new Bundle();
                cerrarDrawer();
                fragmentManager.replace(R.id.contenedorTablon,crear).commit();
                datos.putString("tipo","Necesito");
                datos.putString("nombreCom",comActual);
                crear.setArguments(datos);
            }
        });
        btn_menu_misAnuncios_historial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fm = getFragmentManager().beginTransaction();
                Fragment listado = new FragmentMisAnuncios();
                cerrarDrawer();
                fm.replace(R.id.contenedorTablon,listado).commit();
                Bundle dato= new Bundle();
                dato.putString("nombreCom",comActual);
                listado.setArguments(dato);
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
    public void cerrarDrawer()
    {
        FragmentActivity fa= getActivity();
        c= (MenuPrincipalFragment.CierraDrawer)fa;
        c.cerrarDrawer();
    }
}
