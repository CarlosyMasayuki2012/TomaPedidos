package com.example.sistemas.tomapedidos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MostrarClienteActivity extends AppCompatActivity {

    Button btnpedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_cliente);

        btnpedido = findViewById(R.id.btnPedido);
        btnpedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MostrarClienteActivity.this,ListadoAlmacenActivity.class);
                startActivity(intent);
                finish();
            }
        });



    }
}
