package com.example.sistemas.tomapedidos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.sistemas.tomapedidos.Entidades.Clientes;
import com.example.sistemas.tomapedidos.Entidades.Usuario;

import java.util.ArrayList;

public class ListadoAlmacenActivity extends AppCompatActivity {

    ListView lvAlmacenes;
    ArrayList<String> listaalmacen;
    Clientes cliente;
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_almacen);

        cliente  = new Clientes();
        cliente = (Clientes)getIntent().getSerializableExtra("Cliente");
        usuario = (Usuario) getIntent().getSerializableExtra("Usuario");

        listaalmacen =  new ArrayList<>();
        listaalmacen.add("T02");
        listaalmacen.add("T04");
        listaalmacen.add("T10");
        listaalmacen.add("T11");
        listaalmacen.add("T12");
        listaalmacen.add("T14");
        listaalmacen.add("CD1");
        listaalmacen.add("CD2");
        listaalmacen.add("CD3");
        listaalmacen.add("CD4");

        lvAlmacenes = findViewById(R.id.lvAlmacenes);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,listaalmacen);
        lvAlmacenes.setAdapter(adapter);
        lvAlmacenes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent =  new Intent(ListadoAlmacenActivity.this,ListadoFormaPagoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Cliente",cliente);
                intent.putExtra("Almacen",listaalmacen.get(position));
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("Usuario",usuario);
                intent.putExtras(bundle1);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
    }
}
