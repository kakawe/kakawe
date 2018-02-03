package com.example.admin.kakawev2.Anadir_Comunidad;


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
import com.example.admin.kakawev2.Entidades.Usuario;
import com.example.admin.kakawev2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class AnadirDomicilioFragment extends Fragment {
    private static DatabaseReference referencia;
    private static DatabaseReference referencia2;

    public interface OyenteInsercion{
        public void anadirComunidad(Comunidad comunidad, Usuario usuario);
    }

    TextView tv_anadirDomicilio_crear;
    EditText et_anadirDomicilio_piso,et_anadirDomicilio_puerta;
    Button bt_anadirDomicilio_continuar;

    private String nombreCom,localidad,direccion;

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
                Fragment crear = new CrearComunidadFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.contenedor_anadirComunidad,crear);
                ft.addToBackStack(null);
                ft.commit();
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
        String piso = et_anadirDomicilio_piso.getText().toString();
        String puerta = et_anadirDomicilio_puerta.getText().toString();

        if (piso.isEmpty()){
            Toast.makeText(getContext(),"Añada su piso", Toast.LENGTH_LONG).show();
            et_anadirDomicilio_piso.requestFocus();
            return;
        }if (puerta.isEmpty()) {
            Toast.makeText(getContext(), "Añada su puerta", Toast.LENGTH_LONG).show();
            et_anadirDomicilio_puerta.requestFocus();
            return;
        }
        FirebaseUser usuarioActual = FirebaseAuth.getInstance().getCurrentUser();
        String correoUsuario = usuarioActual.getEmail();
        Comunidad comunidad = new Comunidad(nombreCom,localidad,direccion);
        Usuario usuario = new Usuario(correoUsuario,piso,puerta);

        Log.v("datos1",correoUsuario);
        Log.v("datos1",nombreCom);
        Log.v("datos1",localidad);
        Log.v("datos1",direccion);
        Log.v("datos1",piso);
        Log.v("datos1",puerta);
        /*OyenteInsercion oyente=(OyenteInsercion) getTargetFragment();
        oyente.anadirComunidad(comunidad,usuario);
        //BBDD.anadirComunidad();*/
        referencia = FirebaseDatabase.getInstance().getReference("comunidades");
        String nomcom = comunidad.getNombre();
        String key = referencia.push().getKey();
        referencia.child(comunidad.getNombre()).setValue(comunidad);
        referencia.child(comunidad.getNombre()).child("usuarios").child(key).setValue(usuario);
        //referencia.child("comunidades").child(comunidad.getNombre()).child("usuarios").setValue(usuario);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        nombreCom = getArguments().getString("nombreCom");
        localidad = getArguments().getString("localidad");
        direccion = getArguments().getString("direccion");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_anadir_domicilio, container, false);
    }

}
