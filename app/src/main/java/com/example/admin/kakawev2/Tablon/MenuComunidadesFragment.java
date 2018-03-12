package com.example.admin.kakawev2.Tablon;


import android.content.Context;
import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.admin.kakawev2.Adaptadores.LVAdapter;
import com.example.admin.kakawev2.Anadir_Comunidad.AnadirDomicilioFragment;
import com.example.admin.kakawev2.Anadir_Comunidad.BuscarComunidadFragment;
import com.example.admin.kakawev2.Anadir_Comunidad.CrearComunidadFragment;
import com.example.admin.kakawev2.Entidades.Comunidad;
import com.example.admin.kakawev2.Entidades.Vecino;
import com.example.admin.kakawev2.LoginActivity;
import com.example.admin.kakawev2.PerfilUsuarioActivity;
import com.example.admin.kakawev2.R;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuComunidadesFragment extends Fragment{

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    Button btn_menu_comunidades_anadir;
    TextView tv_menuComunidades_nombre;
    ImageView iv_menuComunidades_foto;
    View vista,vistaComunidad;
    CierraDrawer c;
    ArrayList<String> comus_usuario=new ArrayList<>();
    String comunidadActual;
    LVAdapter adaptador;
    ListView lv_listaCom;

    private static DatabaseReference referencia;

    public interface CierraDrawer
    {
        public void cerrarDrawer();
    }
    public MenuComunidadesFragment() {

        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        vista = inflater.inflate(R.layout.fragment_menu_comunidades,null);
        btn_menu_comunidades_anadir = (Button)vista.findViewById(R.id.btn_menu_comunidades_anadir);
        comunidadActual=getArguments().getString("nombreCom");
        lv_listaCom =(ListView)vista.findViewById(R.id.lv_listaCom);
        lv_listaCom.setClickable(true);

        tv_menuComunidades_nombre = (TextView) vista.findViewById(R.id.tv_menuComunidades_nombre);
        iv_menuComunidades_foto = (ImageView)vista.findViewById(R.id.iv_menuComunidades_foto);
        tv_menuComunidades_nombre.setText(user.getDisplayName());

        tv_menuComunidades_nombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerrarDrawer();
                Intent intent = new Intent(getContext(), PerfilUsuarioActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("comunidad",comunidadActual);
                startActivity(intent);
            }
        });
        iv_menuComunidades_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerrarDrawer();
                Intent intent = new Intent(getContext(), PerfilUsuarioActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("comunidad",comunidadActual);
                startActivity(intent);
            }
        });
        btn_menu_comunidades_anadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentManager = getFragmentManager().beginTransaction();
                Fragment crear = new BuscarComunidadFragment();
                Bundle datos= new Bundle();
                cerrarDrawer();
                fragmentManager.replace(R.id.contenedorTablon,crear).commit();
                datos.putString("contenedor","contenedorTablon");
                crear.setArguments(datos);

            }
        });
        lv_listaCom.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView comunidad = view.findViewById(R.id.tv_vistaCom_nomCom);
                comunidadActual = comunidad.getText().toString();
                recargarTablon();
                Log.v("clic",comunidadActual);
            }
        });
        cargaComunidadesMenuLateral();
        // Retornamos la vista nueva creada
        return vista;
    }

    private void recargarTablon() {
        Intent intent = new Intent(getContext(), TablonActivity.class);
        intent.putExtra("comunidad",comunidadActual);
        intent.putExtra("tipo","ofrecen");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void cargaComunidadesMenuLateral() {
        final String correo= user.getEmail();
        referencia = FirebaseDatabase.getInstance().getReference("comunidades");
        referencia.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dato : dataSnapshot.getChildren()){
                    Comunidad com= dato.getValue(Comunidad.class);
                    final String nombreComunidad= com.getNombre();
                    DatabaseReference referencia1 = FirebaseDatabase.getInstance().getReference("comunidades").child(nombreComunidad).child("usuarios");
                    referencia1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot1) {
                            for (DataSnapshot dato2 : dataSnapshot1.getChildren()) {{
                                Vecino vD = dato2.getValue(Vecino.class);
                                String corre=vD.getMail();
                                String corr=corre;
                                if (corr.equals(correo)){
                                    comus_usuario.add(nombreComunidad);
                                    Log.v("nombreApuntado4",String.valueOf(comus_usuario.size()));
                                    //enviar cada nombre de comunidad al pager primero
                                }
                                adaptador = new LVAdapter(comus_usuario,getContext());
                                lv_listaCom.setAdapter(adaptador);
                            }}
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                Log.v("nombreApuntado",String.valueOf(comus_usuario.size()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void cerrarDrawer()
    {
      FragmentActivity fa= getActivity();
       c= (CierraDrawer)fa;
       c.cerrarDrawer();
    }
}
