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

    TextView tvtitulodinamico;
    Productos productos;
    Button btnbuscarproducto, btnterminar;
    ListView lvbandejaproductos;
    ArrayList<String> listabandejaproductos,listabandejaproductoselegidos;
    Clientes cliente;
    String cantidad ,Item ,Precio;
    View mview;
    Integer cantidadProductos=0;
    ArrayList<Productos> listaproductoselegidos;
    Double precio = 0.0,item=0.0,precioinicial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bandeja_productos);

        listaproductoselegidos = new ArrayList<Productos>();
        listaproductoselegidos = (ArrayList<Productos>) getIntent().getSerializableExtra("listaProductoselegidos");
        listabandejaproductos = new ArrayList<>();
        cantidadProductos = listabandejaproductos.size();
        listabandejaproductoselegidos = new ArrayList<>();

        // valores para el sumarizado de la bandeja

        precioinicial = Double.valueOf(listaproductoselegidos.get(0).getPrecio());
        for (int i=0;i<listaproductoselegidos.size();i++){
           // calcula numero de productos
            precio = precio + Double.valueOf(listaproductoselegidos.get(i).getPrecioAcumulado());
            item = item + Double.valueOf(listaproductoselegidos.get(i).getCantidad());
            listabandejaproductoselegidos.add(listaproductoselegidos.get(i).getCodigo().toString());

        }

        cantidad = String.valueOf(listaproductoselegidos.size());
        Item = String.valueOf(item);
        Precio = String.valueOf(precio);

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
                Bundle bundle = new Bundle();
                bundle.putSerializable("Cliente",cliente);
                intent.putExtras(bundle);
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("listaproductoselegidos",listaproductoselegidos);
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

      //  listabandejaproductos.add(detalle);

        lvbandejaproductos =  findViewById(R.id.lvbandejaProductos);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,listabandejaproductoselegidos);

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

                            break; }

                        if(position == 0 || position ==1) {

                            final AlertDialog.Builder builder1 = new AlertDialog.Builder(
                                    bandejaProductosActivity.this);
                            builder1.setMessage("Esta seguro que desea " + msg + "?")
                                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                            dialogInterface.cancel();
                                            salirlistview();


                                        }
                                    })
                                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                            salirlistview();


                                            /*
                                            Intent intent = new Intent(bandejaProductosActivity.this, bandejaProductosActivity.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putSerializable("Producto", productos);
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                            finish();
                                            */
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
                        "Codigo     : "  + listaproductoselegidos.get(position).getCodigo() + "\n" +
                        "Nombre    : " + listaproductoselegidos.get(position).getDescripcion()+ "\n"+
                        "Cantidad  : "+ listaproductoselegidos.get(position).getCantidad()+ "\n"+
                        "Stock       : "+ listaproductoselegidos.get(position).getStock()+ "\n"+
                        "Precio      : "+ listaproductoselegidos.get(position).getPrecio()+"\n"+
                        "Flete       : " + listaproductoselegidos.get(position).getFlete())
                        .setNegativeButton("Aceptar",null)
                        .create()
                        .show();


                Toast.makeText(bandejaProductosActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void salirlistview (){

        Intent intent = new Intent(bandejaProductosActivity.this, bandejaProductosActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Producto", productos);
        intent.putExtras(bundle);
        Bundle bundle1 = new Bundle();
        bundle1.putSerializable("listaProductoselegidos", listaproductoselegidos);
        intent.putExtras(bundle1);
        startActivity(intent);
        finish();

    }
    private void Alertsdialog(){





    }
}
