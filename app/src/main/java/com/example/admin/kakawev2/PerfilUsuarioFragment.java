package com.example.admin.kakawev2;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilUsuarioFragment extends Fragment {

    ImageView ep_img_perfil;
    TextView ep_tv_nombre;
    EditText ep_ed_correo,ep_ed_contrasena;
    Button ep_btn_editar;

    public PerfilUsuarioFragment() {
        // Required empty public constructor
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ep_img_perfil = (ImageView)getView().findViewById(R.id.ep_img_perfil);
        ep_tv_nombre = (TextView)getView().findViewById(R.id.ep_tv_nombre);
        ep_ed_correo = (EditText)getView().findViewById(R.id.ep_ed_correo);
        ep_ed_contrasena = (EditText)getView().findViewById(R.id.ep_ed_contrasena);
        ep_btn_editar = (Button)getView().findViewById(R.id.ep_btn_editar);

        ep_btn_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerfilUsuarioDialog pu = new PerfilUsuarioDialog();
                pu.show(getActivity().getFragmentManager(), "Dialog boton Añadir");
            }
        });


}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_perfil_usuario, container, false);
    }


}
