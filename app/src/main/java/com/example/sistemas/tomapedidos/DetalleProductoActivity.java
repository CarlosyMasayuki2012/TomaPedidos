package com.example.sistemas.tomapedidos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sistemas.tomapedidos.Entidades.Clientes;
import com.example.sistemas.tomapedidos.Entidades.Productos;

import java.util.ArrayList;

public class DetalleProductoActivity extends AppCompatActivity {


    TextView tvcodigoproducto,tvnombreproducto;
    Productos productos;
    Button btnguardaryrevisar, btnguardaryagregar;
    Clientes cliente;
    ArrayList<Productos> listaproductoselegidos;
    EditText etcantidadelegida;
    Double preciounitario,cantidad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_producto);

        etcantidadelegida =  findViewById(R.id.etCantidadElegida);
        listaproductoselegidos = new ArrayList<Productos>();
        productos  = new Productos();
        productos = (Productos) getIntent().getSerializableExtra("Producto");

        cliente = new Clientes();
        cliente = (Clientes)getIntent().getSerializableExtra("Cliente");
        listaproductoselegidos = (ArrayList<Productos>)(ArrayList<Productos>) getIntent()
                .getSerializableExtra("listaproductoselegidos");





        tvcodigoproducto =  findViewById(R.id.tvcodigoCliente);
        tvnombreproducto = findViewById(R.id.tvGiroCliente);
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

                productos.setCantidad(etcantidadelegida.getText().toString());
                preciounitario = Double.valueOf(productos.getPrecio());
                cantidad = Double.valueOf(productos.getCantidad());
                productos.setPrecioAcumulado(String.valueOf(Math.ceil((cantidad*preciounitario*100.00))/100.00));
                Toast.makeText(DetalleProductoActivity.this, productos.getPrecioAcumulado().toString(), Toast.LENGTH_SHORT).show();
                listaproductoselegidos.add(productos);
                Intent intent = new Intent(DetalleProductoActivity.this,bandejaProductosActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("listaProductoselegidos", listaproductoselegidos);
                intent.putExtras(bundle);
                Bundle bundle1 = new Bundle();
                bundle.putSerializable("Cliente",cliente);
                intent.putExtras(bundle1);
                startActivity(intent);
                finish();

            }
        });

         tvcodigoproducto.setText(productos.getCodigo());
         tvnombreproducto.setText(productos.getDescripcion());

    }
}
