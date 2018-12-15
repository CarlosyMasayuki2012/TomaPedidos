package com.example.sistemas.tomapedidos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sistemas.tomapedidos.Entidades.Usuario;

public class LoginActivity extends AppCompatActivity {

    EditText etusuario,etclave;
    Button btnlogeo;
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usuario = new Usuario();
        etusuario = findViewById(R.id.etUsuario);
        etclave = findViewById(R.id.etClave);
        btnlogeo = findViewById(R.id.btnLogin);


        btnlogeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                usuario.setNombre(etusuario.getText().toString().replace(" ",""));
                usuario.setClave(etclave.getText().toString().replace(" ",""));
                // Se recibe los parametros

                usuario.setNombre("Carlos");
                usuario.setApellido("Miyashiro");
                usuario.setCodigo("001");


                Toast.makeText(getApplicationContext(), etusuario.getText().toString(), Toast.LENGTH_SHORT).show();
                if(usuario.getNombre().equals("Carlos")&& usuario.getClave().equals("12345")){
                    Toast.makeText(LoginActivity.this, "Entro", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Usuario",usuario);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }else {

                    Toast.makeText(LoginActivity.this, "Nombre o oclave incorrecta", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
