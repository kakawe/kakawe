package com.kakawe.admin.kakawev2.Anadir_Comunidad;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.kakawe.admin.kakawev2.Entidades.Comunidad;
import com.kakawe.admin.kakawev2.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class CrearComunidadFragment extends Fragment {

    private static DatabaseReference referencia;
    private ProgressDialog progreso;

    ToggleButton bt_anadir_buscar,bt_anadir_crear;
    Button bt_anadirCrear_continuar;
    EditText et_anadirCrear_nomcom,et_anadirCrear_localidad,et_anadirCrear_direccion;

    String contenedor;

    public CrearComunidadFragment() {
        // Required empty public constructor
    }
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        progreso = new ProgressDialog(getContext());

        et_anadirCrear_nomcom = (EditText) getView().findViewById(R.id.et_anadirCrear_nomcom);
        et_anadirCrear_localidad = (EditText) getView().findViewById(R.id.et_anadirCrear_localidad);
        et_anadirCrear_direccion = (EditText) getView().findViewById(R.id.et_anadirCrear_direccion);
        bt_anadir_buscar = (ToggleButton) getView().findViewById(R.id.bt_anadir_buscar);
        bt_anadir_crear = (ToggleButton) getView().findViewById(R.id.bt_anadir_crear);
        bt_anadirCrear_continuar = (Button) getView().findViewById(R.id.bt_anadirCrear_continuar);

        bt_anadir_crear.setChecked(true);
        bt_anadir_crear.setEnabled(false);
        bt_anadir_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contenedor.equals("contenedorTablon")){
                    Fragment crear = new BuscarComunidadFragment();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.contenedorTablon,crear);
                    ft.addToBackStack(null);
                    ft.commit();
                    Bundle datos=new Bundle();
                    datos.putString("contenedor","contenedorTablon");
                    crear.setArguments(datos);
                }else{
                    Fragment crear = new BuscarComunidadFragment();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.contenedor_anadirComunidad,crear);
                    ft.addToBackStack(null);
                    ft.commit();
                    Bundle datos=new Bundle();
                    datos.putString("contenedor","contenedor_anadirComunidad");
                    crear.setArguments(datos);
                }

            }
        });
        bt_anadirCrear_continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearContinuarDomicilio();
            }
        });
    }

    private void crearContinuarDomicilio() {
        String nombreCom = et_anadirCrear_nomcom.getText().toString().trim();
        String localidad = et_anadirCrear_localidad.getText().toString().trim();
        String direccion = et_anadirCrear_direccion.getText().toString().trim();
        String direc = direccion.trim();
        String local = localidad.trim();
        if (localidad.isEmpty()){
            String a= getResources().getString(R.string.localidadRequerida);
            Toast.makeText(getContext(),a, Toast.LENGTH_LONG).show();
            et_anadirCrear_localidad.requestFocus();
            return;
        }if (direccion.isEmpty()) {
            String a= getResources().getString(R.string.direccionRequerida);
            Toast.makeText(getContext(), a, Toast.LENGTH_LONG).show();
            et_anadirCrear_direccion.requestFocus();
            return;
        }if (nombreCom.isEmpty()){
            nombreCom = direc+local;
            comprobarSiExiste(nombreCom,localidad,direccion);
        }
        comprobarSiExiste(nombreCom,localidad,direccion);



    }
    //comprobamos si la comunidad existe o no
    private void comprobarSiExiste(final String nombreCom, final String localidad, final String direccion) {
        progreso.show();
        String a= getResources().getString(R.string.comprobandoDisponibilidad);
        progreso.setMessage(a);
        referencia = FirebaseDatabase.getInstance().getReference("comunidades");
        referencia.child(nombreCom).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Comunidad c=dataSnapshot.getValue(Comunidad.class);
                if (c!=null){
                    String a= getResources().getString(R.string.laComunidadYaExiste);
                    Toast.makeText(getContext(), a, Toast.LENGTH_LONG).show();
                    progreso.cancel();
                    return;
                }else{
                    progreso.cancel();
                    anadirCrearMiDomicilio(nombreCom,localidad,direccion);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //Si la comunidad no existe, pasamos a añadir el domicilio
    private void anadirCrearMiDomicilio(String n,String l,String d){
        if (contenedor.equals("contenedorTablon")){
            Fragment crear = new AnadirDomicilioFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.contenedorTablon,crear);
            ft.addToBackStack(null);
            ft.commit();
            Bundle datos = new Bundle();
            datos.putString("nombreCom",n);
            datos.putString("localidad",l);
            datos.putString("direccion",d);
            datos.putString("ventana","crear");
            datos.putString("contenedor","contenedorTablon");
            crear.setArguments(datos);
        }else{
            Fragment crear = new AnadirDomicilioFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.contenedor_anadirComunidad,crear);
            ft.addToBackStack(null);
            ft.commit();
            Bundle datos = new Bundle();
            datos.putString("nombreCom",n);
            datos.putString("localidad",l);
            datos.putString("direccion",d);
            datos.putString("ventana","crear");
            datos.putString("contenedor","contenedor_anadirComunidad");
            crear.setArguments(datos);
        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contenedor = getArguments().getString("contenedor");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_crear_comunidad, container, false);
    }

}
