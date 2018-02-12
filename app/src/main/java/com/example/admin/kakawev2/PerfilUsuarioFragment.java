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

    ImageView iv_perfilUs_fotoUs;
    TextView tv_perfilUs_NomUsuario;
    TextView tv_perfilUs_correoUsuario,tv_perfilUs_contraUsuario;
    Button bt_perfilUs_editarUsuario;

    public PerfilUsuarioFragment() {
        // Required empty public constructor
    }
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        iv_perfilUs_fotoUs = (ImageView)getView().findViewById(R.id.iv_perfilUs_fotoUs);
        tv_perfilUs_NomUsuario = (TextView)getView().findViewById(R.id.tv_perfilUs_NomUsuario);
        tv_perfilUs_correoUsuario = (TextView)getView().findViewById(R.id.tv_perfilUs_correoUsuario);
        tv_perfilUs_contraUsuario = (TextView)getView().findViewById(R.id.tv_perfilUs_contraUsuario);
        bt_perfilUs_editarUsuario = (Button)getView().findViewById(R.id.bt_perfilUs_editarUsuario);

        bt_perfilUs_editarUsuario.setOnClickListener(new View.OnClickListener() {
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
