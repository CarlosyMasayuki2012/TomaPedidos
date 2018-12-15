package com.example.sistemas.tomapedidos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class BusquedaClienteActivity extends AppCompatActivity {

    RadioGroup rggrupocliente;
    RadioButton rbnombre,rbcodigo;
    Button btnbuscar;
    ArrayList<String> listaClientes;

    ListView lvclientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda_cliente);

        rggrupocliente = findViewById(R.id.rgBuscarCliente);
        rbnombre = findViewById(R.id.rbNombre);
        rbcodigo = findViewById(R.id.rbCodigo);
        btnbuscar = findViewById(R.id.btnBuscar);
        lvclientes = findViewById(R.id.lvClientes);



        rggrupocliente.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (rggrupocliente.getCheckedRadioButtonId()){

                    case R.id.rbNombre:
                        Toast.makeText(getApplicationContext(), "Check nombre", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rbCodigo:
                        Toast.makeText(getApplicationContext(), "Check Codigo", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

    }
}
