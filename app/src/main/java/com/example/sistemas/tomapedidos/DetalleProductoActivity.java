package com.example.sistemas.tomapedidos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sistemas.tomapedidos.Entidades.Clientes;
import com.example.sistemas.tomapedidos.Entidades.Productos;

public class DetalleProductoActivity extends AppCompatActivity {


    TextView tvcodigoproducto,tvnombreproducto;
    Productos productos;
    Button btnguardaryrevisar, btnguardaryagregar;
    Clientes cliente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_producto);


        productos  = new Productos();
        productos = (Productos) getIntent().getSerializableExtra("Producto");

        cliente = new Clientes();
        cliente = (Clientes)getIntent().getSerializableExtra("Cliente");


        tvcodigoproducto =  findViewById(R.id.tvcodigoproducto);
        tvnombreproducto = findViewById(R.id.tvnombreProducto);
        btnguardaryrevisar = findViewById(R.id.btnGuardarrevisar);
        btnguardaryagregar = findViewById(R.id.btnGuardaryagregar);

        btnguardaryagregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        btnguardaryrevisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DetalleProductoActivity.this,bandejaProductosActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Producto",productos);
                intent.putExtras(bundle);
                Bundle bundle1 = new Bundle();
                bundle.putSerializable("Cliente",cliente);
                intent.putExtras(bundle1);
                startActivity(intent);
                finish();

            }
        });

         tvcodigoproducto.setText(productos.getCodigo());
         tvnombreproducto.setText(productos.getNombre());

    }
}
