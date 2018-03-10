package com.example.admin.kakawev2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.admin.kakawev2.Dialogs.AnadirAnuncioCategoriaDialog2;
import com.example.admin.kakawev2.Dialogs.AnadirAnuncioDialog1;
import com.example.admin.kakawev2.Dialogs.AnadirAnuncioDialog2;
import com.google.firebase.database.DatabaseReference;

public class MainActivity extends AppCompatActivity implements AnadirAnuncioCategoriaDialog2.CategoriaSeleccionada {

    private final int DURACION_SPLASH = 5000;
    private static DatabaseReference referencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        AnimatedVectorDrawableCompat splash = AnimatedVectorDrawableCompat.create(getApplicationContext(),R.drawable.animated_logo_kakawe);
        //AnimatedVectorDrawableCompat splash = AnimatedVectorDrawableCompat.create(getApplicationContext(), R.drawable.vector_anim_kakawe);
        imageView.setImageDrawable(splash);
        final Animatable animatable = (Animatable) imageView.getDrawable();
        animatable.start();

        //insertar anuncio
        String titulo = "Taladro";
        String mensaje = "a ver si alguien puede prestarme un taladro por favor!!!";
        int foto = 1;
        String tipo = "demanda";


        FirebaseUser usuarioActual = FirebaseAuth.getInstance().getCurrentUser();
        String correoUsuario = usuarioActual.getEmail();
        Anuncio anuncio = new Anuncio(titulo,mensaje,foto,tipo);

        referencia = FirebaseDatabase.getInstance().getReference("comunidades");
        String key = referencia.push().getKey();
        referencia.child("NogalGuadalix").child("Anuncios").child(tipo).child(key).setValue(anuncio);

        //Recuperar anuncios
        /*String nombreCom="NogalGuadalix";
        referencia = FirebaseDatabase.getInstance().getReference("comunidades");
        referencia.child(nombreCom).child("Anuncios").child("oferta").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Anuncio> anuncio_oferta=new ArrayList<>();
                for (DataSnapshot dato : dataSnapshot.getChildren()){
                    Anuncio aO = dato.getValue(Anuncio.class);
                    anuncio_oferta.add(aO);

                    String titulo = aO.getTitulo();
                    String mensaje = aO.getMensaje();
                    int foto = aO.getFoto();
                    String tipo = aO.getTipo();
                    Log.v("datosBD",titulo);
                    Log.v("datosBD",mensaje);
                    Log.v("datosBD",Integer.toString(foto));
                    Log.v("datosBD",tipo);

                }
                Log.v("datosBD",Integer.toString(anuncio_oferta.size()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        Button bt_tablon_anadirAnuncio = (Button)findViewById(R.id.bt_tablon_anadirAnuncio);
        bt_tablon_anadirAnuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //nombre comunidad se cogera del tablon
                String nomComunidad = "Cuatro";
                AnadirAnuncioDialog1 ad1 = new AnadirAnuncioDialog1();
                Bundle datos = new Bundle();
                datos.putString("nomComunidad",nomComunidad);
                ad1.show(getFragmentManager(),"ad1");
                ad1.setArguments(datos);

            }
        });



        /*new Handler().postDelayed(new Runnable(){
            public void run(){
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            };
        }, DURACION_SPLASH);*/
        /*
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        */
    }

    //metodo para poder introdicir la categoria dentro de AñadirAnuncioDialog2
    @Override
    public void seleccionada(String categoria) {
        String cat = categoria;
        AnadirAnuncioDialog2 a = (AnadirAnuncioDialog2) getFragmentManager().findFragmentByTag("ad2");
        a.setearCategoria(categoria);
    }
}
