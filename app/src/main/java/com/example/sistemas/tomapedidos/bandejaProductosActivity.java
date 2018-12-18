package com.example.sistemas.tomapedidos;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sistemas.tomapedidos.Entidades.Clientes;
import com.example.sistemas.tomapedidos.Entidades.Productos;

import java.util.ArrayList;

public class bandejaProductosActivity extends AppCompatActivity {

    TextView tvcodigoproducto,tvnombreproducto,tvtitulodinamico;
    Productos productos;
    Button btnbuscarproducto, btnterminar;
    ListView lvbandejaproductos;
    ArrayList<String> listabandejaproductos;
    Clientes cliente;
    String cantidad ,Item ,Precio,detalle;
    View mview;
    Integer cantidadProductos=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bandeja_productos);

        productos  = new Productos();
        productos = (Productos) getIntent().getSerializableExtra("Producto");
        listabandejaproductos = new ArrayList<>();
        cantidadProductos = listabandejaproductos.size();

        // valores para el sumarizado de la bandeja

        cantidad = "1";
        Item = "1.00";
        Precio = "4.30";

        cliente = new Clientes();
        cliente = (Clientes)getIntent().getSerializableExtra("Cliente");
        btnbuscarproducto =  findViewById(R.id.btnproducto);
        btnterminar = findViewById(R.id.btnterminar);
        tvtitulodinamico  = findViewById(R.id.tvtitulodinamico);

        String cadenaTituloAux = "Productos : "+ cantidad+"  |  Item : "+Item+"  |  Monto : S/. "+Precio+"";
        tvtitulodinamico.setText(cadenaTituloAux);
        mview = getLayoutInflater().inflate(R.layout.listview_dialog,null);
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

        detalle = productos.getCodigo() + "\n" + productos.getNombre() +"\n"+ "Cant : 1.00 " +"\n"+ "0.00" +"\n" +"Pre : S/. 3.50" +"\n"+"Subtotal : S/. 3.50";
        listabandejaproductos.add(detalle);

        lvbandejaproductos =  findViewById(R.id.lvbandejaProductos);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,listabandejaproductos);

        lvbandejaproductos.setAdapter(adapter);

        lvbandejaproductos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(bandejaProductosActivity.this);
                builder.setCancelable(true);
                ListView listView = (ListView)mview.findViewById(R.id.lvopciones);
                ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
                        bandejaProductosActivity.this,android.R.layout.simple_list_item_1,
                        getResources().getStringArray(R.array.opciones));
                adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String msg = "";
                        switch (position){

                            case 0:
                                msg = "Editar el prducto";
                            break;

                            case 1:
                                msg = "Borrar el producto";
                            break;

                            case 2:
                                Intent intent = new Intent(bandejaProductosActivity.this,bandejaProductosActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("Producto",productos);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                finish();
                            break; }

                        if(position == 0 || position ==1) {

                            final AlertDialog.Builder builder1 = new AlertDialog.Builder(
                                    bandejaProductosActivity.this);
                            builder1.setMessage("Esta seguro que desea " + msg + "?")
                                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                            Toast.makeText(bandejaProductosActivity.this, "Se ejecutó la opcion", Toast.LENGTH_SHORT).show();

                                        }
                                    })
                                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            Intent intent = new Intent(bandejaProductosActivity.this, bandejaProductosActivity.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putSerializable("Producto", productos);
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                            finish();
                                        }
                                    })
                                    .create()
                                    .show();
                        }
                    }
                });

                builder.setView(mview);
                AlertDialog dialog = builder.create();
                if(mview.getParent()!=null)
                    ((ViewGroup)mview.getParent()).removeView(mview); // <- fix
                dialog.show();
                return true;
            }
        });

        lvbandejaproductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(bandejaProductosActivity.this);
                builder.setMessage(
                        "Codigo     : "  + productos.getCodigo() + "\n" +
                        "Nombre    : " + productos.getNombre()+ "\n"+
                        "Cantidad  : "+ productos.getCantidad()+ "\n"+
                        "Stock       : "+ productos.getStock()+ "\n"+
                        "Precio      : "+ productos.getPrecio()+"\n"+
                        "Flete       : " + productos.getFlete())
                        .setNegativeButton("Aceptar",null)
                        .create()
                        .show();
            }
        });
    }
}
