package com.example.admin.kakawev2.Dialogs;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.admin.kakawev2.Entidades.Anuncio2;
import com.example.admin.kakawev2.Entidades.Vecino;
import com.example.admin.kakawev2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by jose on 19/02/2018.
 */

public class AnadirAnuncioDialog3 extends DialogFragment implements View.OnClickListener {
    DatabaseReference reference;
    FirebaseStorage storage;
    FirebaseAuth user;
    View vista;
    String fechaCaducidad,horaCaducidad,nomComunidad,piso,puerta,key;
    private String tipo, titulo, ruta_imagen, descripcion, categoria;
    TextView tv_anadir_anuncio3_etiquetaFC, tv_anadir_anuncio3_etiquetaHC;
    ImageView iv_anuncio3_cerrar;
    Button bt_anadir_anuncio3_publicar;
    Switch sw_anadir_anuncio3_desabilitar;
    EditText et_anadir_anuncio3_fecha_caducidad, et_anadir_anuncio3_hora_caducidad;
    DatePickerDialog.OnDateSetListener mDatelistenerCaducidad, mDatelistenerPublicacion;
    TimePickerDialog.OnTimeSetListener onTimeSetListenerCaducidad, onTimeSetListenerPublicacion;

    @Override
    public Dialog onCreateDialog(Bundle saveIntanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        vista = inflater.inflate(R.layout.dialog_anadir_anuncio3, null);
        user = FirebaseAuth.getInstance();
        //datos pasados por argumentes desde dialog2
        tipo = getArguments().getString("tipo2");
        titulo = getArguments().getString("titulo2");
        ruta_imagen = getArguments().getString("ruta_imagen2");
        descripcion = getArguments().getString("descripcionAnuncio2");
        categoria = getArguments().getString("categoria2");
        nomComunidad = getArguments().getString("nomComunidad");
        key = getArguments().getString("key");


        //imagen X para cerrar
        iv_anuncio3_cerrar = (ImageView) vista.findViewById(R.id.iv_anuncio3_cerrar);

        //etiqueta fecha
        tv_anadir_anuncio3_etiquetaFC = (TextView) vista.findViewById(R.id.id_expiracion_dato);

        //etiqueta hora
        tv_anadir_anuncio3_etiquetaHC = (TextView) vista.findViewById(R.id.id_expiracion_hora);

        //Edittext fecha
        et_anadir_anuncio3_fecha_caducidad = (EditText) vista.findViewById(R.id.et_anadir_anuncio3_fecha_caducidad);

        //Edittext hora
        et_anadir_anuncio3_hora_caducidad = (EditText) vista.findViewById(R.id.et_anadir_anuncio3_hora_caducidad);

        //metodo para cerrar el Dialog3
        iv_anuncio3_cerrar.setOnClickListener(this);

        //boton desabilitar
        bt_anadir_anuncio3_publicar = (Button) vista.findViewById(R.id.bt_anadir_anuncio3_publicar);
        sw_anadir_anuncio3_desabilitar = (Switch) vista.findViewById(R.id.sw_anadir_anuncio3_desabilitar);

        //metodo para cargar anuncio a labase de tados
        bt_anadir_anuncio3_publicar.setOnClickListener(this);

        //metodo para desabilitar componetes
        tv_anadir_anuncio3_etiquetaFC.setEnabled(false);
        tv_anadir_anuncio3_etiquetaHC.setEnabled(false);
        et_anadir_anuncio3_fecha_caducidad.setEnabled(false);
        et_anadir_anuncio3_hora_caducidad.setEnabled(false);
        sw_anadir_anuncio3_desabilitar.setChecked(false);
        desabilitar(sw_anadir_anuncio3_desabilitar);


        //eventos onclick hora
        et_anadir_anuncio3_hora_caducidad.setOnClickListener(this);

        //eventos onclick fecha
        et_anadir_anuncio3_fecha_caducidad.setOnClickListener(this);

        //metodos para obtener las fechas
        mDatelistenerCaducidad = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int y = year;
                int m = month + 1;
                int d = dayOfMonth;
                et_anadir_anuncio3_fecha_caducidad.setText(d + "/" + m + "/" + y);

            }
        };
        cogerDatosComunidad();
        builder.setView(vista);
        return builder.create();
    }

    private void cogerDatosComunidad() {
        reference = FirebaseDatabase.getInstance().getReference("comunidades");
        reference.child(nomComunidad).child("usuarios").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Vecino> listado_Vecinos = new ArrayList<>();
                for (DataSnapshot dato : dataSnapshot.getChildren()) {
                    Vecino vc = dato.getValue(Vecino.class);
                    listado_Vecinos.add(vc);
                    String corre = vc.getMail();
                    String key = dato.getKey();
                    if (corre.equals(user.getCurrentUser().getEmail())) {
                        piso = vc.getPiso();
                        puerta = vc.getPuerta();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.et_anadir_anuncio3_fecha_caducidad) {
            obtenerFechaCaducidad();
        } else if (v.getId() == R.id.et_anadir_anuncio3_hora_caducidad) {
            obtenerHoraCaducidad();
        } else if (v.getId() == R.id.bt_anadir_anuncio3_publicar) {
            publicarAnuncio();
        } else if (v.getId() == R.id.iv_anuncio3_cerrar) {
            cerrarAnuncio();
        }

    }

    //eventos añadir anuncio 3

    //evento que recoge los datos de los 3 dialog para la creación del anuncio
    private void publicarAnuncio() {

        fechaCaducidad = et_anadir_anuncio3_fecha_caducidad.getText().toString();
        horaCaducidad = et_anadir_anuncio3_hora_caducidad.getText().toString();
        if (fechaCaducidad.isEmpty()){
            fechaCaducidad="Permanente";
        }if (horaCaducidad.isEmpty()){
            horaCaducidad="Permanente";
        }
        creacionAnuncioFirebase();

    }

    //metodo para subir los datos a firebase para posterior creacón de anuncio
    private void creacionAnuncioFirebase() {
        String correo = user.getCurrentUser().getEmail().toString();
        String alias = user.getCurrentUser().getDisplayName();

        reference = FirebaseDatabase.getInstance().getReference().child("comunidades");
        Anuncio2 a2 = new Anuncio2(key,correo,alias,titulo,tipo,categoria,descripcion,fechaCaducidad,horaCaducidad,piso,puerta);
        reference.child(nomComunidad).child("Anuncios").child(tipo).child(key).setValue(a2);

        //creamos otro carpeta desde la raiz para poder referenciar a cada user con su anuncio
        //para poder ver su historial
        reference = FirebaseDatabase.getInstance().getReference().child("AnunciosUsuarios");
        reference.child(alias).child(key).setValue(a2);

        //cerramos los dialog
        DialogFragment ad3 = (DialogFragment) getFragmentManager().findFragmentByTag("ad3");
        ad3.dismiss();
        DialogFragment ad2 = (DialogFragment) getFragmentManager().findFragmentByTag("ad2");
        ad2.dismiss();
        DialogFragment ad1 = (DialogFragment) getFragmentManager().findFragmentByTag("ad1");
        ad1.dismiss();



    }


    private void obtenerFechaCaducidad() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDatelistenerCaducidad, year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }

    private void obtenerHoraCaducidad() {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR);
        int minute = cal.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog2 = new TimePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String h = String.valueOf(hourOfDay);
                String m = String.valueOf(minute);
                et_anadir_anuncio3_hora_caducidad.setText(String.valueOf(h) + ":" + String.valueOf(m));
            }
        }, hour, minute, true);
        timePickerDialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        timePickerDialog2.show();

    }

    private void desabilitar(Switch sw_anadir_anuncio3_desabilitar) {
        sw_anadir_anuncio3_desabilitar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    tv_anadir_anuncio3_etiquetaFC.setEnabled(true);
                    tv_anadir_anuncio3_etiquetaHC.setEnabled(true);
                    et_anadir_anuncio3_fecha_caducidad.setEnabled(true);
                    et_anadir_anuncio3_hora_caducidad.setEnabled(true);

                } else {
                    tv_anadir_anuncio3_etiquetaFC.setEnabled(false);
                    tv_anadir_anuncio3_etiquetaHC.setEnabled(false);
                    et_anadir_anuncio3_fecha_caducidad.setEnabled(false);
                    et_anadir_anuncio3_hora_caducidad.setEnabled(false);
                }
            }
        });
    }

    //metodo para cerrar el dialog3
    private void cerrarAnuncio() {
        DialogFragment ad1 = (DialogFragment) getFragmentManager().findFragmentByTag("ad1");
        DialogFragment ad2 = (DialogFragment) getFragmentManager().findFragmentByTag("ad2");
        this.dismiss();
        ad1.dismiss();
        ad2.dismiss();

    }

}
