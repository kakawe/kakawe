package com.example.admin.kakawev2.Tablon;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.kakawev2.Anadir_Comunidad.AnadirDomicilioFragment;
import com.example.admin.kakawev2.Anadir_Comunidad.BuscarComunidadFragment;
import com.example.admin.kakawev2.Anadir_Comunidad.CrearComunidadFragment;
import com.example.admin.kakawev2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuComunidadesFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    TextView tv_menuComunidades_nombre;
    View vista,vistaComunidad;
    LinearLayout contComu;
    CierraDrawer c;
    ArrayList<String> nombreComunidades;

    public interface CierraDrawer
    {
        public void cerrarDrawer();
    }

    private String nombre="benitocamela";

    public MenuComunidadesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.fragment_menu_comunidades,null);


        NavigationView navegadorCom = (NavigationView)vista.findViewById(R.id.menu_comunidades);
        NavigationView navegadorCom2 = (NavigationView)vista.findViewById(R.id.menu_comunidades2);
        vistaComunidad=navegadorCom.getHeaderView(0);
        tv_menuComunidades_nombre = (TextView) vistaComunidad.findViewById(R.id.tv_menuComunidades_nombre);
        tv_menuComunidades_nombre.setText(nombre);
        navegadorCom2.setNavigationItemSelectedListener(this);
        contComu = (LinearLayout) vista.findViewById(R.id.contComu);
        tv_menuComunidades_nombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerrarDrawer();
                Fragment crear = new PerfilUsuarioFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.contenedorTablon,crear);
                ft.addToBackStack(null);
                ft.commit();

            }
        });
        //Log.v("orden","MenuComunidadesFragment");
        //nombreComunidades=getArguments().getStringArrayList("comus");
        //Log.v("comunidadesMenu",String.valueOf(nombreComunidades.size()));
        // Retornamos la vista nueva creada
        return vista;
    }

    private void agregarComLateral() {
        Log.v("llega","llega");
        LayoutInflater layoutInflater =
                (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View addView = layoutInflater.inflate(R.layout.campo_menucom_comunidadagregada, null);
        //Añado TextViews a la vista
        TextView nomcom_agregada=(TextView)addView.findViewById(R.id.nomcom_agregada);
        nomcom_agregada.setText("hola hola".toString());
        contComu.addView(addView);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Log.v("numeroqueviene",String.valueOf(id));
        FragmentTransaction fragmentManager = getFragmentManager().beginTransaction();
        Fragment crear = new BuscarComunidadFragment();
        Bundle datos= new Bundle();
        if (id == R.id.m_menuCom_anadir) {
            agregarComLateral();
            //cerrarDrawer();
            fragmentManager.replace(R.id.contenedorTablon,crear).commit();
            datos.putString("contenedor","contenedorTablon");
            crear.setArguments(datos);

        }
        return true;
    }
    public void cerrarDrawer()
    {
      FragmentActivity fa= getActivity();
       c= (CierraDrawer)fa;
       c.cerrarDrawer();
    }
}
