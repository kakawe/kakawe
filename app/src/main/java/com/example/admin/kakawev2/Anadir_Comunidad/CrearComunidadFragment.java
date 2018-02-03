package com.example.admin.kakawev2.Anadir_Comunidad;


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

import com.example.admin.kakawev2.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class CrearComunidadFragment extends Fragment {

    Button bt_anadir_buscar,bt_anadirCrear_continuar;
    EditText et_anadirCrear_nomcom,et_anadirCrear_localidad,et_anadirCrear_direccion;

    public CrearComunidadFragment() {
        // Required empty public constructor
    }
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        et_anadirCrear_nomcom = (EditText) getView().findViewById(R.id.et_anadirCrear_nomcom);
        et_anadirCrear_localidad = (EditText) getView().findViewById(R.id.et_anadirCrear_localidad);
        et_anadirCrear_direccion = (EditText) getView().findViewById(R.id.et_anadirCrear_direccion);
        bt_anadir_buscar = (Button) getView().findViewById(R.id.bt_anadir_buscar);
        bt_anadirCrear_continuar = (Button) getView().findViewById(R.id.bt_anadirCrear_continuar);

        bt_anadir_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment crear = new BuscarComunidadFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.contenedor_anadirComunidad,crear);
                ft.addToBackStack(null);
                ft.commit();
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
        String nombreCom = et_anadirCrear_nomcom.getText().toString();
        String localidad = et_anadirCrear_localidad.getText().toString();
        String direccion = et_anadirCrear_direccion.getText().toString();
        if (localidad.isEmpty()){
            Toast.makeText(getContext(),"Localidad requerida", Toast.LENGTH_LONG).show();
            et_anadirCrear_localidad.requestFocus();
            return;
        }if (direccion.isEmpty()) {
            Toast.makeText(getContext(), "Direccion requerida", Toast.LENGTH_LONG).show();
            et_anadirCrear_direccion.requestFocus();
            return;
        }if (nombreCom.isEmpty()){
            Toast.makeText(getContext(),"Nombre de comunidad requerida", Toast.LENGTH_LONG).show();
            et_anadirCrear_nomcom.requestFocus();
            return;
        }
        anadirCrearMiDomicilio();

    }
    //Si la comunidad no existe, pasamos a añadir el domicilio
    private void anadirCrearMiDomicilio(){
        String nombreCom = et_anadirCrear_nomcom.getText().toString();
        String localidad = et_anadirCrear_localidad.getText().toString().trim();
        String direccion = et_anadirCrear_direccion.getText().toString();

        Fragment crear = new AnadirDomicilioFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.contenedor_anadirComunidad,crear);
        ft.addToBackStack(null);
        ft.commit();
        Bundle datos = new Bundle();
        datos.putString("nombreCom",nombreCom);
        datos.putString("localidad",localidad);
        datos.putString("direccion",direccion);
        crear.setArguments(datos);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_crear_comunidad, container, false);
    }

}
