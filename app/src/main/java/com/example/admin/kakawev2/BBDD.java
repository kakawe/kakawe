package com.example.admin.kakawev2;

import com.example.admin.kakawev2.Entidades.Comunidad;
import com.example.admin.kakawev2.Entidades.Vecino;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by admin on 02/02/2018.
 */

public class BBDD {

    private static DatabaseReference referencia;
    private static DatabaseReference referencia2;


    public static void anadirComunidad(Comunidad comunidad,Vecino vecino){
        referencia = FirebaseDatabase.getInstance().getReference("comunidades");
        String nomcom = comunidad.getNombre();
        referencia.child(nomcom).setValue(comunidad);
        referencia2 = FirebaseDatabase.getInstance().getReference(nomcom);
        referencia2.child("Usuarios").setValue(vecino);

        //referencia.child(c.getDatosComunidad().getNombreComunidad()).child(key).setValue(c);
    }
}
