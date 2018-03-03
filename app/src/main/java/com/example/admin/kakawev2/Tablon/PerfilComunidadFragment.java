package com.example.admin.kakawev2.Tablon;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.kakawev2.DetallesAnuncio;
import com.example.admin.kakawev2.Entidades.Anuncio;
import com.example.admin.kakawev2.Entidades.Vecino;
import com.example.admin.kakawev2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PerfilComunidadFragment extends Fragment {

    private static DatabaseReference referencia;
    private ProgressDialog progreso;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    private String correo;
    //Habrá que traer de la ventana anterior la comunidad actual en la que se encuentra, para poder cargar los datos en esa comunidad
    private String comActual;
    private String piso,puerta,ruta;

    private Button bt_perfilCom_actualiarDatos;
    private EditText et_perfilCom_piso,et_perfilCom_puerta;
    private TextView tv_perfilCom_nombreCom;

    public PerfilComunidadFragment() {
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        progreso = new ProgressDialog(getContext());
        comActual = getArguments().getString("nombreCom");

        correo = user.getEmail();
        et_perfilCom_piso = (EditText) getView().findViewById(R.id.et_perfilCom_piso);
        et_perfilCom_puerta = (EditText) getView().findViewById(R.id.et_perfilCom_puerta);
        tv_perfilCom_nombreCom = (TextView) getView().findViewById(R.id.tv_perfilCom_nombreCom);
        bt_perfilCom_actualiarDatos = (Button)getView().findViewById(R.id.bt_perfilCom_actualiarDatos);
        bt_perfilCom_actualiarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarDatos();
            }
        });

        cargaComunidad();
    }

    private void cargaComunidad() {
        correo = "eloy1@mail.com";


        //Buscamos en la comunidad el usuario con el mismo correo que el logueado, para coger sus datos de sesion
        referencia = FirebaseDatabase.getInstance().getReference("comunidades");
        referencia.child(comActual).child("usuarios").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Vecino> listado_Vecinos = new ArrayList<>();
                for (DataSnapshot dato : dataSnapshot.getChildren()) {
                    Vecino vc = dato.getValue(Vecino.class);
                    listado_Vecinos.add(vc);
                    String corre= vc.getMail();
                    String key=dato.getKey();
                    if (corre.equals(correo)){
                        piso = vc.getPiso();
                        puerta = vc.getPuerta();
                        ruta = key;
                        et_perfilCom_piso.setText(piso);
                        et_perfilCom_puerta.setText(puerta);
                    }
                }
                tv_perfilCom_nombreCom.setText(comActual);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    //Actualizamos los datos del usuario si le da al boton, si algun campo está vacio,avisa de que no puede estar vacio
    private void actualizarDatos() {
        String pisoAct = et_perfilCom_piso.getText().toString();
        String puertaAct = et_perfilCom_puerta.getText().toString();
        if (pisoAct.isEmpty()) {
            Toast.makeText(getContext(),"Introduce un piso", Toast.LENGTH_LONG).show();
            return;
        }if (puertaAct.isEmpty()) {
            Toast.makeText(getContext(),"Introduce una puerta", Toast.LENGTH_LONG).show();
            return;
        }
        FirebaseUser usuarioActual = FirebaseAuth.getInstance().getCurrentUser();
        String correoUsuario = usuarioActual.getEmail();
        String mail=correoUsuario;
        Vecino vecino = new Vecino(correo,mail,pisoAct,puertaAct);
        referencia = FirebaseDatabase.getInstance().getReference("comunidades");
        referencia.child(comActual).child("usuarios").child(ruta).setValue(vecino);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_perfil_comunidad, container, false);
    }



}
