package com.example.admin.kakawev2;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.AnyRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.kakawev2.Anadir_Comunidad.AnadirComunidadActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.StorageReference;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth instancia;
    private ProgressDialog progreso;


    private EditText et_registro_nombre, et_registro_correo, et_registro_contrasena;
    private Button bt_registro_registrar;
    private TextView tv_registro_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        progreso = new ProgressDialog(this);
        instancia = FirebaseAuth.getInstance();

        et_registro_nombre = (EditText) findViewById(R.id.et_registro_nombre);
        et_registro_correo = (EditText) findViewById(R.id.et_registro_correo);
        et_registro_contrasena = (EditText) findViewById(R.id.et_registro_contrasena);
        bt_registro_registrar = (Button) findViewById(R.id.bt_registro_registrar);
        tv_registro_login = (TextView) findViewById(R.id.tv_registro_login);


        tv_registro_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        bt_registro_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario();
            }
        });
    }

    private void registrarUsuario() {
        final String nombre = et_registro_nombre.getText().toString();
        String correo = et_registro_correo.getText().toString().trim();
        String contrasena = et_registro_contrasena.getText().toString();

        if (nombre.isEmpty()) {

            Toast.makeText(this, "Nombre requerido", Toast.LENGTH_LONG).show();
            et_registro_nombre.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            Toast.makeText(this, "Correo no valido", Toast.LENGTH_LONG).show();
            et_registro_correo.requestFocus();
            return;
        }
        if (contrasena.isEmpty()) {
            Toast.makeText(this, "Contraseña requerida", Toast.LENGTH_LONG).show();
            et_registro_contrasena.requestFocus();
            return;
        }
        if (contrasena.length() < 6) {
            Toast.makeText(this, "Contraseña corta, mñinimo 6 caracteres", Toast.LENGTH_LONG).show();
            et_registro_contrasena.requestFocus();
            return;
        }
        instancia.createUserWithEmailAndPassword(correo, contrasena).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progreso.show();
                progreso.setMessage("Registrando usuario...");

                if (task.isSuccessful()) {
                    UserProfileChangeRequest cambio_usuario = new UserProfileChangeRequest.Builder()
                            .setDisplayName(nombre).build();
                    FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
                    usuario.updateProfile(cambio_usuario).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            registroAnadirComunidad();
                        }
                    });

                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        progreso.cancel();
                        Toast.makeText(RegisterActivity.this, "Correo ya registrado", Toast.LENGTH_LONG).show();
                        et_registro_correo.requestFocus();
                    } else {
                        progreso.cancel();
                        Toast.makeText(RegisterActivity.this, task.getException().getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }


    private void registroAnadirComunidad() {
        Intent intent = new Intent(RegisterActivity.this, AnadirComunidadActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }
}
