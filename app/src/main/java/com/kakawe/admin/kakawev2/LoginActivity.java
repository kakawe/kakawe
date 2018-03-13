package com.kakawe.admin.kakawev2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kakawe.admin.kakawev2.Entidades.Comunidad;
import com.kakawe.admin.kakawev2.Entidades.Vecino;
import com.kakawe.admin.kakawev2.Tablon.TablonActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth instancia;
    private ProgressDialog progreso;
    private static DatabaseReference referencia;
    FirebaseUser user;
    String correo;

    private EditText et_login_correo,et_login_contrasena;
    private Button bt_login_entrar;
    private TextView tv_login_recordarcontra;
    private ImageView iv_login_registro;

    ArrayList<String> comus_usuario=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progreso = new ProgressDialog(this);
        instancia= FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        et_login_correo=(EditText)findViewById(R.id.et_login_correo);
        et_login_contrasena=(EditText)findViewById(R.id.et_login_contrasena);
        bt_login_entrar=(Button) findViewById(R.id.bt_login_entrar);
        tv_login_recordarcontra=(TextView) findViewById(R.id.tv_login_recordarcontra);
        iv_login_registro=(ImageView) findViewById(R.id.iv_login_registro);

        iv_login_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        tv_login_recordarcontra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordarContraseña();
            }
        });
        bt_login_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loguearUsuario();
            }
        });
    }
    private void recordarContraseña() {
        String correo = et_login_correo.getText().toString().trim();
        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
            String a= getResources().getString(R.string.correoreg);
            Toast.makeText(this, a, Toast.LENGTH_LONG).show();
            et_login_correo.requestFocus();
            return;
        }
        progreso.show();
        String a= getResources().getString(R.string.correoNuev);
        progreso.setMessage(a);

        instancia.sendPasswordResetEmail(correo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    progreso.cancel();
                    String a= getResources().getString(R.string.correoEnv);
                    Toast.makeText(LoginActivity.this,a, Toast.LENGTH_LONG).show();

                }else{
                    progreso.cancel();
                    String a= getResources().getString(R.string.correoEnvFallo);
                    Toast.makeText(LoginActivity.this,a, Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    private void loguearUsuario() {

        String correo = et_login_correo.getText().toString().trim();
        String contrasena = et_login_contrasena.getText().toString();
        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
            String a= getResources().getString(R.string.correoreg);
            Toast.makeText(this, a, Toast.LENGTH_LONG).show();
            et_login_correo.requestFocus();
            return;
        }
        if (contrasena.isEmpty()) {
            String a= getResources().getString(R.string.contraseñaRequerida);
            Toast.makeText(this, a, Toast.LENGTH_LONG).show();
            et_login_contrasena.requestFocus();
            return;
        }if (contrasena.length()<6) {

            String a= getResources().getString(R.string.contraseñaCorta);
            Toast.makeText(this, a, Toast.LENGTH_LONG).show();
            et_login_contrasena.requestFocus();
            return;
        }
        instancia.signInWithEmailAndPassword(correo,contrasena).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                String a= getResources().getString(R.string.iniciandoSesion);
                progreso.setMessage(a+"...");
                progreso.show();
                if (task.isSuccessful()){
                    cargaComunidades();
                }else{
                    String b= getResources().getString(R.string.credencialesInvalidas);
                    if (task.getException() instanceof FirebaseAuthInvalidUserException){
                        progreso.cancel();

                        Toast.makeText(LoginActivity.this, b, Toast.LENGTH_SHORT).show();
                    }
                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                        progreso.cancel();
                        Toast.makeText(LoginActivity.this, b, Toast.LENGTH_SHORT).show();
                    }else{
                        progreso.cancel();
                        Toast.makeText(LoginActivity.this,b, Toast.LENGTH_LONG).cancel();
                    }
                }
            }
        });
    }
    private void cargaComunidades() {
        //
        referencia = FirebaseDatabase.getInstance().getReference("comunidades");
        referencia.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dato : dataSnapshot.getChildren()){
                    Comunidad com= dato.getValue(Comunidad.class);
                    final String nombreComunidad= com.getNombre();
                    final DatabaseReference referencia1 = FirebaseDatabase.getInstance().getReference("comunidades").child(nombreComunidad).child("usuarios");
                    referencia1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot1) {
                            for (DataSnapshot dato2 : dataSnapshot1.getChildren()) {{
                                Vecino vD = dato2.getValue(Vecino.class);
                                String corre=vD.getMail();
                                String corr=corre;
                                if (corr.equals(user.getEmail())){
                                    comus_usuario.add(nombreComunidad);
                                    String nombreCom=comus_usuario.get(0);
                                    loginLanzaTablon(nombreCom);
                                }
                            }}
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void loginLanzaTablon(String nombrecomunidad) {
        Intent intent = new Intent(LoginActivity.this, TablonActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("comunidad",nombrecomunidad);
        startActivity(intent);
    }

}
