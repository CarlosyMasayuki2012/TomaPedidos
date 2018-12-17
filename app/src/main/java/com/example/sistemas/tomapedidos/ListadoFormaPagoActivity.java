package com.example.sistemas.tomapedidos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sistemas.tomapedidos.Entidades.Clientes;

import java.util.ArrayList;

public class ListadoFormaPagoActivity extends AppCompatActivity {

    ListView lvtipopago;
    ArrayList<String> listatipopago;
    Clientes cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_forma_pago);

        cliente  = new Clientes();
        cliente = (Clientes)getIntent().getSerializableExtra("Cliente");

        Toast.makeText(this,cliente.getIdCliente(), Toast.LENGTH_SHORT).show();

        listatipopago =  new ArrayList<>();

        for (int i =0 ; i<10;i++){

            listatipopago.add("Tipo de Pago " + i);
        }


        lvtipopago = findViewById(R.id.lvtipopago);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,listatipopago);

        lvtipopago.setAdapter(adapter);

        lvtipopago.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent =  new Intent(ListadoFormaPagoActivity.this,BuscarProductoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Cliente",cliente);
                intent.putExtras(bundle);
                intent.putExtra("TipoPago",listatipopago.get(position));
                startActivity(intent);
                finish();


               // Toast.makeText(ListadoFormaPagoActivity.this, listatipopago.get(position), Toast.LENGTH_SHORT).show();
            }
        });





    }
}
