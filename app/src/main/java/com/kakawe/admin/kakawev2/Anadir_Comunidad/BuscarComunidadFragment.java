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
import com.kakawe.admin.kakawev2.Entidades.Vecino;
import com.kakawe.admin.kakawev2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class BuscarComunidadFragment extends Fragment {

    private static DatabaseReference referencia;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private ProgressDialog progreso;

    ToggleButton bt_anadir_crear,bt_anadir_buscar;
    Button  bt_anadirBuscar_continuar;
    EditText et_anadirBuscar_nomcom, et_anadirBuscar_localidad, et_anadirBuscar_direccion;
    String contenedor;

    public BuscarComunidadFragment() {
        // Required empty public constructor
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        progreso = new ProgressDialog(getContext());

        et_anadirBuscar_nomcom = (EditText) getView().findViewById(R.id.et_anadirBuscar_nomcom);
        et_anadirBuscar_localidad = (EditText) getView().findViewById(R.id.et_anadirBuscar_localidad);
        et_anadirBuscar_direccion = (EditText) getView().findViewById(R.id.et_anadirBuscar_direccion);
        bt_anadir_crear = (ToggleButton) getView().findViewById(R.id.bt_anadir_crear);
        bt_anadir_buscar = (ToggleButton) getView().findViewById(R.id.bt_anadir_buscar);
        bt_anadirBuscar_continuar = (Button) getView().findViewById(R.id.bt_anadirBuscar_continuar);

        bt_anadir_buscar.setChecked(true);
        bt_anadir_buscar.setEnabled(false);
        bt_anadir_crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contenedor.equals("contenedorTablon")) {
                    Fragment crear = new CrearComunidadFragment();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.contenedorTablon, crear);
                    ft.addToBackStack(null);
                    ft.commit();
                    Bundle datos = new Bundle();
                    datos.putString("contenedor", "contenedorTablon");
                    crear.setArguments(datos);
                } else {
                    Fragment crear = new CrearComunidadFragment();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.contenedor_anadirComunidad, crear);
                    ft.addToBackStack(null);
                    ft.commit();
                    Bundle datos = new Bundle();
                    datos.putString("contenedor", "contenedor_anadirComunidad");
                    crear.setArguments(datos);
                }

            }
        });
        bt_anadirBuscar_continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarContinuarDomicilio();
            }
        });
    }

    private void buscarContinuarDomicilio() {
        String nombreCom = et_anadirBuscar_nomcom.getText().toString().trim();
        String localidad = et_anadirBuscar_localidad.getText().toString().trim();
        String direccion = et_anadirBuscar_direccion.getText().toString().trim();
        String direc = direccion.trim();
        String local = localidad.trim();
        if (localidad.isEmpty() && direccion.isEmpty() && nombreCom.isEmpty()) {
            Toast.makeText(getContext(), "Introduce el nombre de una comunidad a buscar", Toast.LENGTH_LONG).show();
            et_anadirBuscar_nomcom.requestFocus();
            return;
        }
        if (direccion.isEmpty() && nombreCom.isEmpty()) {
            Toast.makeText(getContext(), "Direccion requerida", Toast.LENGTH_LONG).show();
            et_anadirBuscar_direccion.requestFocus();
            return;
        }
        if (localidad.isEmpty() && nombreCom.isEmpty()) {
            Toast.makeText(getContext(), "Localidad requerida", Toast.LENGTH_LONG).show();
            et_anadirBuscar_localidad.requestFocus();
            return;
        }
        if (nombreCom.isEmpty()) {
            nombreCom = direc + local;
            comprobarSiExiste(nombreCom, localidad, direccion);
        }
        comprobarSiExiste(nombreCom, localidad, direccion);


    }

    private void comprobarSiExiste(final String nombreCom, final String localidad, final String direccion) {
        progreso.show();
        progreso.setMessage("Buscando . . .");
        referencia = FirebaseDatabase.getInstance().getReference("comunidades");
        referencia.child(nombreCom).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Comunidad c = dataSnapshot.getValue(Comunidad.class);
                if (c != null) {
                    progreso.cancel();
                    mirarSiPertenece(nombreCom, localidad, direccion);
                    progreso.cancel();
                } else {
                    Toast.makeText(getContext(), "No existe una comunidad con el nombre " + nombreCom, Toast.LENGTH_LONG).show();
                    progreso.cancel();
                    return;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void mirarSiPertenece(final String nombreCom, final String localidad, final String direccion) {
        final String correo = user.getEmail();
        FirebaseAuth au = FirebaseAuth.getInstance();
        referencia = FirebaseDatabase.getInstance().getReference("comunidades");
        referencia.child(nombreCom).child("usuarios").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dato : dataSnapshot.getChildren()) {
                    Vecino aV = dato.getValue(Vecino.class);
                    String corre = aV.getMail();
                    //te deja entrar si munidad hay mucha gente, ya que tarda en comparar los correos
                    //crear un metodo boolean para ver esto
                    if (corre.equals(correo)) {
                        Toast.makeText(getContext(), "Ya estás en la comunidad " + nombreCom, Toast.LENGTH_LONG).show();
                        progreso.cancel();
                        return;

                    } else {

                        anadirBuscarMiDomicilio(nombreCom, localidad, direccion);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    //Cuando se compruebe si esa comunidad realmente existe, pasamos a añadir el domicilio
    private void anadirBuscarMiDomicilio(String n, String l, String d) {
        if (contenedor.equals("contenedorTablon")) {
            Fragment crear = new AnadirDomicilioFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.contenedorTablon, crear);
            ft.addToBackStack(null);
            ft.commit();
            Bundle datos = new Bundle();
            datos.putString("nombreCom", n);
            datos.putString("localidad", l);
            datos.putString("direccion", d);
            datos.putString("ventana", "buscar");
            datos.putString("contenedor", "contenedorTablon");
            crear.setArguments(datos);
        } else {
            Fragment crear = new AnadirDomicilioFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.contenedor_anadirComunidad, crear);
            ft.addToBackStack(null);
            ft.commit();
            Bundle datos = new Bundle();
            datos.putString("nombreCom", n);
            datos.putString("localidad", l);
            datos.putString("direccion", d);
            datos.putString("ventana", "buscar");
            datos.putString("contenedor", "contenedor_anadirComunidad");
            crear.setArguments(datos);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contenedor = getArguments().getString("contenedor");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buscar_comunidad, container, false);
    }

}
