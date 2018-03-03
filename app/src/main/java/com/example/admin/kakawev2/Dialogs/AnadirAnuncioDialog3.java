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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.admin.kakawev2.Entidades.Anuncio2;
import com.example.admin.kakawev2.R;

import java.util.Calendar;

/**
 * Created by jose on 19/02/2018.
 */

public class AnadirAnuncioDialog3 extends DialogFragment implements View.OnClickListener {
    View vista;
    private String tipo, titulo, ruta_imagen, descripcion, categoria;
    TextView tv_anadir_anuncio3_etiquetaFC, tv_anadir_anuncio3_etiquetaFP, tv_anadir_anuncio3_etiquetaHC, tv_anadir_anuncio3_etiquetaHP,tv_anadir_anuncio3_cerrar;
    Button bt_anadir_anuncio3_publicar;
    Switch sw_anadir_anuncio3_desabilitar;
    EditText et_anadir_anuncio3_fecha_caducidad, et_anadir_anuncio3_fecha_publicacion, et_anadir_anuncio3_hora_caducidad, et_anadir_anuncio3_hora_publicacion;
    DatePickerDialog.OnDateSetListener mDatelistenerCaducidad, mDatelistenerPublicacion;
    TimePickerDialog.OnTimeSetListener onTimeSetListenerCaducidad, onTimeSetListenerPublicacion;

    @Override
    public Dialog onCreateDialog(Bundle saveIntanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        vista = inflater.inflate(R.layout.dialog_anadir_anuncio3, null);

        //datos pasados por argumentes desde dialog2
        tipo = getArguments().getString("tipo2");
        titulo = getArguments().getString("titulo2");
        ruta_imagen = getArguments().getString("ruta_imagen2");
        descripcion = getArguments().getString("descripcionAnuncio2");
        categoria = getArguments().getString("categoria2");

        //declaramos los componentes de la vista

        //imagen X para cerrar
        tv_anadir_anuncio3_cerrar = (TextView) vista.findViewById(R.id.tv_anadir_anuncio3_cerrar);

        //etiqueta fecha
        tv_anadir_anuncio3_etiquetaFC = (TextView) vista.findViewById(R.id.tv_anadir_anuncio3_etiquetaFC);
        tv_anadir_anuncio3_etiquetaFP = (TextView) vista.findViewById(R.id.tv_anadir_anuncio3_etiquetaFP);

        //etiqueta hora
        tv_anadir_anuncio3_etiquetaHC = (TextView) vista.findViewById(R.id.tv_anadir_anuncio3_etiquetaHC);
        tv_anadir_anuncio3_etiquetaHP = (TextView) vista.findViewById(R.id.tv_anadir_anuncio3_etiquetaHP);

        //Edittext fecha
        et_anadir_anuncio3_fecha_caducidad = (EditText) vista.findViewById(R.id.et_anadir_anuncio3_fecha_caducidad);
        et_anadir_anuncio3_fecha_publicacion = (EditText) vista.findViewById(R.id.et_anadir_anuncio3_fecha_publicacion);

        //Edittext hora
        et_anadir_anuncio3_hora_caducidad = (EditText) vista.findViewById(R.id.et_anadir_anuncio3_hora_caducidad);
        et_anadir_anuncio3_hora_publicacion = (EditText) vista.findViewById(R.id.et_anadir_anuncio3_hora_publicacion);

        //metodo para cerrar el Dialog3
        tv_anadir_anuncio3_cerrar.setOnClickListener(this);

        //boton desabilitar
        bt_anadir_anuncio3_publicar = (Button) vista.findViewById(R.id.bt_anadir_anuncio3_publicar);
        sw_anadir_anuncio3_desabilitar = (Switch) vista.findViewById(R.id.sw_anadir_anuncio3_desabilitar);

        //metodo para cargar anuncio a labase de tados
        bt_anadir_anuncio3_publicar.setOnClickListener(this);

        //metodo para desabilitar componetes
        sw_anadir_anuncio3_desabilitar.setChecked(false);
        desabilitar(sw_anadir_anuncio3_desabilitar);

        //eventos onclick hora
        et_anadir_anuncio3_hora_caducidad.setOnClickListener(this);
        et_anadir_anuncio3_hora_publicacion.setOnClickListener(this);

        //eventos onclick fecha
        et_anadir_anuncio3_fecha_caducidad.setOnClickListener(this);
        ;
        et_anadir_anuncio3_fecha_publicacion.setOnClickListener(this);

        //metodos para obtener las fechas
        mDatelistenerCaducidad = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int y = year;
                int m = month + 1;
                int d = dayOfMonth;
                Log.v("year2", String.valueOf(year));
                et_anadir_anuncio3_fecha_caducidad.setText(d + "/" + m + "/" + y);

            }
        };

        mDatelistenerPublicacion = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int y = year;
                int m = month + 1;
                int d = dayOfMonth;
                Log.v("year", String.valueOf(year));
                et_anadir_anuncio3_fecha_publicacion.setText(d + "/" + m + "/" + y);
            }
        };


        builder.setView(vista);
        return builder.create();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.et_anadir_anuncio3_fecha_caducidad) {
            obtenerFechaCaducidad();
        } else if (v.getId() == R.id.et_anadir_anuncio3_fecha_publicacion) {
            obtenerFechaPublicacion();
        } else if (v.getId() == R.id.et_anadir_anuncio3_hora_caducidad) {
            obtenerHoraCaducidad();
        } else if (v.getId() == R.id.et_anadir_anuncio3_hora_publicacion) {
            obtenerHoraPublicacion();
        } else if (v.getId() == R.id.bt_anadir_anuncio3_publicar) {
            publicarAnuncio();
        } else if (v.getId() == R.id.tv_anadir_anuncio3_cerrar) {
            cerrarAnuncio();
        }

    }

    //eventos añadir anuncio 3

    //evento que recoge los datos de los 3 dialog para la creación del anuncio
    private void publicarAnuncio() {

        String fechaCaducidad = et_anadir_anuncio3_fecha_caducidad.getText().toString();
        String fechaPublicacion = et_anadir_anuncio3_fecha_publicacion.getText().toString();
        String horaCaducidad = et_anadir_anuncio3_hora_caducidad.getText().toString();
        String horaPublicacion = et_anadir_anuncio3_hora_publicacion.getText().toString();

        String tipoA = tipo;
        String tituloA = titulo;
        String ruta_imagenA = ruta_imagen;
        String descripcionA = descripcion;
        String categoriaA = categoria;
        String correo = "josele@mail.com";

        Anuncio2 anuncio2 = new Anuncio2(correo, tituloA, tipoA, categoriaA, ruta_imagenA, descripcionA, fechaPublicacion, fechaCaducidad);

        creacionAnuncioFirebase(anuncio2);
        //Log.v("dialog3", fechaCaducidad);
        //Log.v("dialog3", fechaPublicacion);
        //Log.v("dialog3", horaCaducidad);
        //Log.v("dialog3", horaPublicacion);
        //Log.v("dialog3", tipo);
        //Log.v("dialog3", titulo);
        //Log.v("dialog3", ruta_imagen);
        //Log.v("dialog3", categoria);
        //Log.v("dialog3", descripcion);

        Log.v("anuncio2", anuncio2.toString());
    }

    //metodo para subir los datos a firebase para posterior creacón de anuncio
    private void creacionAnuncioFirebase(Anuncio2 anuncio2) {
    }


    private void obtenerFechaCaducidad() {
        Log.v("funciona", "funciona");
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDatelistenerCaducidad, year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }

    private void obtenerFechaPublicacion() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDatelistenerPublicacion, year, month, day);
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

    private void obtenerHoraPublicacion() {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR);
        int minute = cal.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                int h = hourOfDay;
                int m = minute;

                et_anadir_anuncio3_hora_publicacion.setText(String.valueOf(h) + ":" + String.valueOf(m));
            }
        }, hour, minute, true);
        timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        timePickerDialog.show();
    }


    private void desabilitar(Switch sw_anadir_anuncio3_desabilitar) {
        sw_anadir_anuncio3_desabilitar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    tv_anadir_anuncio3_etiquetaFC.setEnabled(true);
                    tv_anadir_anuncio3_etiquetaFP.setEnabled(true);
                    tv_anadir_anuncio3_etiquetaHC.setEnabled(true);
                    tv_anadir_anuncio3_etiquetaHP.setEnabled(true);
                    et_anadir_anuncio3_fecha_caducidad.setEnabled(true);
                    et_anadir_anuncio3_fecha_publicacion.setEnabled(true);
                    et_anadir_anuncio3_hora_caducidad.setEnabled(true);
                    et_anadir_anuncio3_hora_publicacion.setEnabled(true);

                } else {
                    tv_anadir_anuncio3_etiquetaFC.setEnabled(false);
                    tv_anadir_anuncio3_etiquetaFP.setEnabled(false);
                    tv_anadir_anuncio3_etiquetaHC.setEnabled(false);
                    tv_anadir_anuncio3_etiquetaHP.setEnabled(false);
                    et_anadir_anuncio3_fecha_caducidad.setEnabled(false);
                    et_anadir_anuncio3_fecha_publicacion.setEnabled(false);
                    et_anadir_anuncio3_hora_caducidad.setEnabled(false);
                    et_anadir_anuncio3_hora_publicacion.setEnabled(false);
                }
            }
        });
    }

    //metodo para cerrar el dialog3
    private void cerrarAnuncio() {
        DialogFragment ad1=(DialogFragment)getFragmentManager().findFragmentByTag("ad1");
        DialogFragment ad2=(DialogFragment)getFragmentManager().findFragmentByTag("ad2");
        this.dismiss();
        ad1.dismiss();
        ad2.dismiss();

    }

}
