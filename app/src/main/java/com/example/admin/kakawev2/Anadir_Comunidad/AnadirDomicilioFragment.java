package com.example.admin.kakawev2.Anadir_Comunidad;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.kakawev2.Entidades.Comunidad;
import com.example.admin.kakawev2.Entidades.Vecino;
import com.example.admin.kakawev2.R;
import com.example.admin.kakawev2.Tablon.TablonActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class AnadirDomicilioFragment extends Fragment {
    private static DatabaseReference referencia;

    public interface OyenteInsercion{
        public void anadirComunidad(Comunidad comunidad, Vecino vecino);
    }

    TextView tv_anadirDomicilio_crear;
    EditText et_anadirDomicilio_piso,et_anadirDomicilio_puerta;
    Button bt_anadirDomicilio_continuar;

    private String nombreCom,localidad,direccion,ventana;

    public AnadirDomicilioFragment() {
        // Required empty public constructor
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        et_anadirDomicilio_piso = (EditText) getView().findViewById(R.id.et_anadirDomicilio_piso);
        et_anadirDomicilio_puerta = (EditText) getView().findViewById(R.id.et_anadirDomicilio_puerta);
        tv_anadirDomicilio_crear = (TextView) getView().findViewById(R.id.tv_anadirDomicilio_crear);
        bt_anadirDomicilio_continuar = (Button) getView().findViewById(R.id.bt_anadirDomicilio_continuar);

        tv_anadirDomicilio_crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ventana.equals("buscar")){
                    Fragment crear = new BuscarComunidadFragment();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.contenedor_anadirComunidad,crear);
                    ft.addToBackStack(null);
                    ft.commit();
                }else{
                    Fragment crear = new CrearComunidadFragment();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.contenedor_anadirComunidad,crear);
                    ft.addToBackStack(null);
                    ft.commit();
                }
            }
        });
        bt_anadirDomicilio_continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comprobar();
            }
        });
    }

    private void comprobar() {
        String piso = et_anadirDomicilio_piso.getText().toString().trim();
        String puerta = et_anadirDomicilio_puerta.getText().toString().trim();

        if (piso.isEmpty()){
            Toast.makeText(getContext(),"Añada su piso", Toast.LENGTH_LONG).show();
            et_anadirDomicilio_piso.requestFocus();
            return;
        }if (puerta.isEmpty()) {
            Toast.makeText(getContext(), "Añada su puerta", Toast.LENGTH_LONG).show();
            et_anadirDomicilio_puerta.requestFocus();
            return;
        }

        //si viene de crear, hay que añadir la comunidad,por lo que es un metodo diferente
        if (ventana.equals("crear")){
            FirebaseUser usuarioActual = FirebaseAuth.getInstance().getCurrentUser();
            String correoUsuario = usuarioActual.getEmail();
            Comunidad comunidad = new Comunidad(nombreCom,localidad,direccion);
            Vecino vecino = new Vecino(correoUsuario,piso,puerta);
        /*OyenteInsercion oyente=(OyenteInsercion) getTargetFragment();
        oyente.anadirComunidad(comunidad,vecino);
        //BBDD.anadirComunidad();*/

            referencia = FirebaseDatabase.getInstance().getReference("comunidades");
            String key = referencia.push().getKey();
            referencia.child(comunidad.getNombre()).setValue(comunidad);
            referencia.child(comunidad.getNombre()).child("usuarios").child(key).setValue(vecino);
            //mandamos/volvemos a tablon

            Intent intent = new Intent(getContext(), TablonActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

            //si viene de buscar, lo que hay que hacer es agregar el vecino a esa comunidad.
        }else{
            FirebaseUser usuarioActual = FirebaseAuth.getInstance().getCurrentUser();
            String correoUsuario = usuarioActual.getEmail();
            Vecino vecino = new Vecino(correoUsuario,piso,puerta);
            referencia = FirebaseDatabase.getInstance().getReference("comunidades");
            String key = referencia.push().getKey();
            referencia.child(nombreCom).child("usuarios").child(key).setValue(vecino);
            //mandamos/volvemos a tablon
            Intent intent = new Intent(getContext(), TablonActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        nombreCom = getArguments().getString("nombreCom");
        localidad = getArguments().getString("localidad");
        direccion = getArguments().getString("direccion");
        ventana = getArguments().getString("ventana");
        Log.v("datosA",ventana);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_anadir_domicilio, container, false);
    }

}
