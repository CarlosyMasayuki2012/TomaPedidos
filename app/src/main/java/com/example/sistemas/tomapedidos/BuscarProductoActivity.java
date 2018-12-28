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
import com.example.sistemas.tomapedidos.Entidades.Productos;

import java.util.ArrayList;

public class BuscarProductoActivity extends AppCompatActivity {

    RadioGroup rggrupoproducto;
    RadioButton rbnombreproducto, rbcodigoproducto;
    Button btnbuscarProducto;
    ArrayList<Productos> listaProductos;
    Productos producto;
    ListView lvProducto;
    ArrayList<String> listaProducto;
    Clientes cliente;
    EditText etproducto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_producto);

        cliente  = new Clientes();
        cliente = (Clientes)getIntent().getSerializableExtra("Cliente");

       // Toast.makeText(this,cliente.getIdCliente(), Toast.LENGTH_SHORT).show();

        listaProductos = new ArrayList<>();
        listaProducto = new ArrayList<>();

        for (int i=0; i<9;i++){

            producto = new Productos();
            producto.setCodigo("Codigo Producto" + i);
            producto.setNombre("Nombre Producto " + i);
            producto.setStock(""+ i);
            listaProductos.add(producto);
           // Toast.makeText(this, producto.getNombre(), Toast.LENGTH_SHORT).show();
            listaProducto.add(producto.getCodigo());
        }

        rggrupoproducto = findViewById(R.id.rgBuscarProducto);
        rbnombreproducto = findViewById(R.id.rbNombreProducto);
        rbcodigoproducto = findViewById(R.id.rbCodigoProducto);
        btnbuscarProducto = findViewById(R.id.btnBuscarProducto);
        lvProducto = findViewById(R.id.lvProducto);
        etproducto  = findViewById(R.id.etPrducto);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item,listaProducto);

        btnbuscarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lvProducto.setAdapter(adapter);

            }
        });

        lvProducto.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                producto = new Productos();

                Intent intent =  new Intent(BuscarProductoActivity.this,DetalleProductoActivity.class);
                Bundle bundle = new Bundle();
                producto =  listaProductos.get(position);
               // Toast.makeText(BuscarProductoActivity.this, producto.getNombre(), Toast.LENGTH_SHORT).show();
                bundle.putSerializable("Producto",producto);
                intent.putExtras(bundle);
                Bundle bundle1 = new Bundle();
                cliente = new Clientes();
                cliente = (Clientes)getIntent().getSerializableExtra("Cliente");
                intent.putExtras(bundle1);
                startActivity(intent);
                finish();

            }
        });

        rggrupoproducto.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (rggrupoproducto.getCheckedRadioButtonId()){

                    case R.id.rbNombreProducto:
                        etproducto.setInputType(1);  // envia el tipo de teclado de tipo alfanumerico

                        break;
                    case R.id.rbCodigoProducto:
                        etproducto.setInputType(2);   // envia el teclado de tipo numerico

                        break;
                }

            }
        });
    }
}
