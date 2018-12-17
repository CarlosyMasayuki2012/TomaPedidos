package com.example.sistemas.tomapedidos;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sistemas.tomapedidos.Entidades.Clientes;
import com.example.sistemas.tomapedidos.Entidades.Productos;

import java.util.ArrayList;

public class bandejaProductosActivity extends AppCompatActivity {

    TextView tvcodigoproducto,tvnombreproducto;
    Productos productos;
    Button btnbuscarproducto, btnterminar;
    ListView lvbandejaproductos;
    ArrayList<String> listabandejaproductos;
    Clientes cliente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bandeja_productos);

        productos  = new Productos();
        productos = (Productos) getIntent().getSerializableExtra("Producto");
        listabandejaproductos = new ArrayList<>();

        cliente = new Clientes();
        cliente = (Clientes)getIntent().getSerializableExtra("Cliente");
        //Toast.makeText(this,cliente.getIdCliente(), Toast.LENGTH_SHORT).show();
        btnbuscarproducto =  findViewById(R.id.btnproducto);
        btnterminar = findViewById(R.id.btnterminar);
        btnbuscarproducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(bandejaProductosActivity.this,BuscarProductoActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("Cliente",cliente);
                intent.putExtras(bundle1);
                startActivity(intent);
                finish();

            }
        });
        btnterminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(bandejaProductosActivity.this,MainActivity.class);
                startActivity(intent);
                finish();

            }
        });

        String detalle = productos.getCodigo() + " \n" + productos.getNombre() +"\n\r "+ "Cant : 1.00 " +"\r\n"+ "0.00" +"\r\n" +"Pre : S/. 3.50" +"\r\n"+"Subtotal : S/. 3.50";
        listabandejaproductos.add(detalle);

        lvbandejaproductos =  findViewById(R.id.lvbandejaProductos);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,listabandejaproductos);

        lvbandejaproductos.setAdapter(adapter);

        lvbandejaproductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(bandejaProductosActivity.this);

                builder.setMessage("Codigo : "  + productos.getCodigo())
                        .setNegativeButton("Retry",null)
                        .create()
                        .show();

            }
        });

    }
}
