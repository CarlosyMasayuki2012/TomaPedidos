package com.example.sistemas.tomapedidos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.sistemas.tomapedidos.Entidades.Clientes;
import com.example.sistemas.tomapedidos.Entidades.Usuario;

import java.util.ArrayList;

public class BusquedaClienteActivity extends AppCompatActivity {

    RadioGroup rggrupocliente;
    RadioButton rbnombre,rbcodigo;
    Button btnbuscar;
    ArrayList<Clientes> listaClientes;
    Clientes cliente;
    ListView lvclientes;
    ArrayList<String> listaCliente;
    EditText etcliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda_cliente);

        listaClientes = new ArrayList<>();
        listaCliente  =new ArrayList<>();
        for (int i=0; i<5;i++){

            cliente = new Clientes();
            cliente.setIdCliente(String.valueOf(i));
            cliente.setNombre("Cliente " + i);
            cliente.setDireccion("Direccion " + i);
            cliente.setDeuda("Deuda " +i);
            cliente.setGiro("Giro " + i);
            cliente.setEstado("Etado " + i);
            cliente.setTipoCliente("Tipo Cliente " + i);
            cliente.setUsuarioultpedido("Usuario Ult Pedido" + i);
            cliente.setFechaultpedido("fecha ult Pedido " + i);
            listaClientes.add(cliente);
            listaCliente.add(cliente.getNombre()+ '\r' + cliente.getDireccion());
        }

        rggrupocliente = findViewById(R.id.rgBuscarCliente);
        rbnombre = findViewById(R.id.rbNombre);
        rbcodigo = findViewById(R.id.rbCodigo);
        btnbuscar = findViewById(R.id.btnBuscar);
        lvclientes = findViewById(R.id.lvClientes);
        etcliente = findViewById(R.id.etcliente);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item,listaCliente);

        btnbuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lvclientes.setAdapter(adapter);

            }
        });

        lvclientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cliente = new Clientes();

                Intent intent =  new Intent(BusquedaClienteActivity.this,MostrarClienteActivity.class);
                Bundle bundle = new Bundle();
                cliente =  listaClientes.get(position);
                bundle.putSerializable("Cliente",cliente);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();

            }
        });

        rggrupocliente.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (rggrupocliente.getCheckedRadioButtonId()){

                    case R.id.rbNombre:

                        etcliente.setInputType(1);

                        break;
                    case R.id.rbCodigo:

                        etcliente.setInputType(2);

                        break;
                }
            }
        });

    }
}
