package com.kakawe.admin.kakawev2.Anadir_Comunidad;


import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.AnyRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.kakawe.admin.kakawev2.Entidades.Comunidad;
import com.kakawe.admin.kakawev2.Entidades.Vecino;
import com.kakawe.admin.kakawev2.R;
import com.kakawe.admin.kakawev2.Tablon.ListaAnuncioFragment;
import com.kakawe.admin.kakawev2.Tablon.TablonActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


/**
 * A simple {@link Fragment} subclass.
 */
public class AnadirDomicilioFragment extends Fragment {
    private static DatabaseReference referencia;
    private Intent intent;
    private StorageReference storageReference;
    private ImageView fotoComPredeterminada;


    public interface OyenteInsercion {
        public void anadirComunidad(Comunidad comunidad, Vecino vecino);
    }

    EditText et_anadirDomicilio_piso, et_anadirDomicilio_puerta;
    Button bt_anadirDomicilio_continuar;
    ImageView tv_anadirDomicilio_crear;

    private String nombreCom, localidad, direccion, ventana, contenedor;

    public AnadirDomicilioFragment() {
        // Required empty public constructor
    }


    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        storageReference = FirebaseStorage.getInstance().getReference();
        et_anadirDomicilio_piso = (EditText) getView().findViewById(R.id.et_anadirDomicilio_piso);
        et_anadirDomicilio_puerta = (EditText) getView().findViewById(R.id.et_anadirDomicilio_puerta);
        tv_anadirDomicilio_crear = (ImageView) getView().findViewById(R.id.tv_anadirDomicilio_crear);
        bt_anadirDomicilio_continuar = (Button) getView().findViewById(R.id.bt_anadirDomicilio_continuar);

        tv_anadirDomicilio_crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contenedor.equals("contenedorTablon")) {
                    if (ventana.equals("buscar")) {
                        Fragment crear = new BuscarComunidadFragment();
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
                        ft.replace(R.id.contenedorTablon, crear);
                        ft.addToBackStack(null);
                        ft.commit();
                        Bundle datos = new Bundle();
                        datos.putString("contenedor", "contenedorTablon");
                        crear.setArguments(datos);
                    }
                } else {
                    if (ventana.equals("buscar")) {
                        Fragment crear = new BuscarComunidadFragment();
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.contenedor_anadirComunidad, crear);
                        ft.addToBackStack(null);
                        ft.commit();
                        Bundle datos = new Bundle();
                        datos.putString("contenedor", "contenedor_anadirComunidad");
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

        if (piso.isEmpty()) {
            Toast.makeText(getContext(), "Añada su piso", Toast.LENGTH_LONG).show();
            et_anadirDomicilio_piso.requestFocus();
            return;
        }
        if (puerta.isEmpty()) {
            Toast.makeText(getContext(), "Añada su puerta", Toast.LENGTH_LONG).show();
            et_anadirDomicilio_puerta.requestFocus();
            return;
        }

        //si viene de crear, hay que añadir la comunidad,por lo que es un metodo diferente
        if (ventana.equals("crear")) {
            FirebaseUser usuarioActual = FirebaseAuth.getInstance().getCurrentUser();
            String correoUsuario = usuarioActual.getEmail();
            Comunidad comunidad = new Comunidad(nombreCom, localidad, direccion);
            String correo = correoUsuario;
            String mail = correo;
            Vecino vecino = new Vecino(correo, mail, piso, puerta);
            referencia = FirebaseDatabase.getInstance().getReference("comunidades");
            String key = referencia.push().getKey();
            referencia.child(comunidad.getNombre()).setValue(comunidad);
            referencia.child(comunidad.getNombre()).child("usuarios").child(key).setValue(vecino);
            fotoPredeterminadaComunidad();

            //si viene de buscar, lo que hay que hacer es agregar el vecino a esa comunidad.
        } else {
            FirebaseUser usuarioActual = FirebaseAuth.getInstance().getCurrentUser();
            String correoUsuario = usuarioActual.getEmail();
            String correo = correoUsuario;
            String mail = correo;
            Vecino vecino = new Vecino(correo, mail, piso, puerta);
            referencia = FirebaseDatabase.getInstance().getReference("comunidades");
            String key = referencia.push().getKey();
            referencia.child(nombreCom).child("usuarios").child(key).setValue(vecino);

        }
        if (contenedor.equals("contenedorTablon")) {
            Bundle datos = new Bundle();
            Fragment crear = new ListaAnuncioFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            datos.putString("nombreCom", nombreCom);
            datos.putString("tipo", "ofrecen");
            ft.replace(R.id.contenedorTablon, crear);
            ft.addToBackStack(null);
            ft.commit();
            crear.setArguments(datos);
        } else {

            intent = new Intent(getContext(), TablonActivity.class);
            intent.putExtra("comunidad", nombreCom);
            intent.putExtra("tipo", "ofrecen");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }


    }

    //cargamos a firebase la foto predeterminada de la comunidad
    public static final Uri getUriToDrawable(@NonNull Context context,
                                             @AnyRes int drawableId) {
        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + context.getResources().getResourcePackageName(drawableId)
                + '/' + context.getResources().getResourceTypeName(drawableId)
                + '/' + context.getResources().getResourceEntryName(drawableId));
        return imageUri;
    }

    private void fotoPredeterminadaComunidad() {

        //subimos a firebase
        FirebaseAuth au = FirebaseAuth.getInstance();
        Uri u = getUriToDrawable(getContext(), R.drawable.stewie);
        //Uri file = Uri.fromFile(new File(R.drawable.stewie));
        StorageReference rutaCarpetaImg = storageReference.child("ImagenesComunidad").child(nombreCom);
        //subimos la imagen y verificamos mediante un toast que se subio la foto
        rutaCarpetaImg.putFile(u);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        nombreCom = getArguments().getString("nombreCom");
        localidad = getArguments().getString("localidad");
        direccion = getArguments().getString("direccion");
        ventana = getArguments().getString("ventana");
        contenedor = getArguments().getString("contenedor");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_anadir_domicilio, container, false);
    }

}
