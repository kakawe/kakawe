package com.example.admin.kakawev2.Anadir_Comunidad;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
public class BuscarComunidadFragment extends Fragment {

    Button bt_anadir_crear,bt_anadirBuscar_continuar;
    EditText et_anadirBuscar_nomcom,et_anadirBuscar_localidad,et_anadirBuscar_direccion;


    public BuscarComunidadFragment() {
        // Required empty public constructor
    }
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        et_anadirBuscar_nomcom = (EditText)getView().findViewById(R.id.et_anadirBuscar_nomcom);
        et_anadirBuscar_localidad = (EditText)getView().findViewById(R.id.et_anadirBuscar_localidad);
        et_anadirBuscar_direccion = (EditText)getView().findViewById(R.id.et_anadirBuscar_direccion);
        bt_anadir_crear = (Button) getView().findViewById(R.id.bt_anadir_crear);
        bt_anadirBuscar_continuar = (Button) getView().findViewById(R.id.bt_anadirBuscar_continuar);

        bt_anadir_crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment crear = new CrearComunidadFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.contenedor_anadirComunidad,crear);
                ft.addToBackStack(null);
                ft.commit();
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
        String nombreCom = et_anadirBuscar_nomcom.getText().toString();
        String localidad = et_anadirBuscar_localidad.getText().toString().trim();
        String direccion = et_anadirBuscar_direccion.getText().toString();
        if (localidad.isEmpty()){
            Toast.makeText(getContext(),"Localidad requerida", Toast.LENGTH_LONG).show();
            et_anadirBuscar_localidad.requestFocus();
            return;
        }if (direccion.isEmpty()) {
        Toast.makeText(getContext(), "Direccion requerida", Toast.LENGTH_LONG).show();
            et_anadirBuscar_direccion.requestFocus();
        return;}

    }
    //Cuando se compruebe si esa comunidad realmente existe, pasamos a añadir el domicilio
    private void anadirBuscarMiDomicilio(){
        Fragment crear = new BuscarComunidadFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.contenedor_anadirComunidad,crear);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buscar_comunidad, container, false);
    }

}
