package com.example.sistemas.tomapedidos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sistemas.tomapedidos.Adaptadores.BandejaProductosAdapter1;
import com.example.sistemas.tomapedidos.Entidades.Clientes;
import com.example.sistemas.tomapedidos.Entidades.Productos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BuscarProductoActivity extends AppCompatActivity {

    RadioGroup rggrupoproducto;
    RadioButton rbnombreproducto, rbcodigoproducto;
    Button btnbuscarProducto;
    ArrayList<Productos> listaProductos,listausuariosseleccionados;
    Productos producto,productoelegido;
    ListView lvProducto;
    ArrayList<String> listaProducto;
    Clientes cliente;
    EditText etproducto;
    RecyclerView rvlistadoProductos;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_producto);

        cliente  = new Clientes();
        cliente = (Clientes)getIntent().getSerializableExtra("Cliente");
        listausuariosseleccionados = (ArrayList<Productos>) getIntent().getSerializableExtra("listausuariosseleccionados");

       // Toast.makeText(this,cliente.getIdCliente(), Toast.LENGTH_SHORT).show();

        listaProductos = new ArrayList<>();
        listaProducto = new ArrayList<>();

        /* Se ingresa el producto en datos duros

        for (int i=0; i<9;i++){

            producto = new Productos();
            producto.setCodigo("Codigo Producto" + i);
            producto.setNombre("Nombre Producto " + i);
            producto.setStock(""+ i);
            listaProductos.add(producto);
           // Toast.makeText(this, producto.getNombre(), Toast.LENGTH_SHORT).show();
            listaProducto.add(producto.getCodigo());
        }


*/

        rggrupoproducto = findViewById(R.id.rgBuscarProducto);
        rbnombreproducto = findViewById(R.id.rbNombreProducto);
        rbcodigoproducto = findViewById(R.id.rbCodigoProducto);
        btnbuscarProducto = findViewById(R.id.btnBuscarProducto);
        rvlistadoProductos = findViewById(R.id.rvListadoProducto);
        etproducto  = findViewById(R.id.etPrducto);


        rvlistadoProductos.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.
            VERTICAL,false));
        listausuariosseleccionados = (ArrayList<Productos>) getIntent().getSerializableExtra("listausuariosseleccionados");
        listaProducto = new ArrayList<>();
        consultarproducto();
            rvlistadoProductos.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.
                VERTICAL,false));
        // listausuariosseleccionados = (ArrayList<Usuarios>) getIntent().getSerializableExtra("listausuariosseleccionados");

        consultarproducto();




        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item,listaProducto);

        btnbuscarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                lvProducto.setAdapter(adapter);
*/
            }
        });

        /*

        lvProducto.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                producto = new Productos();

                Intent intent =  new Intent(BuscarProductoActivity.this,DetalleProductoActivity.class);
                Bundle bundle = new Bundle();
                producto =  listaProductos.get(position);
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
        */


        rggrupoproducto.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (rggrupoproducto.getCheckedRadioButtonId()){

                    case R.id.rbNombreProducto:

                        etproducto.setInputType(1);

                        break;
                    case R.id.rbCodigoProducto:

                        etproducto.setInputType(2);

                        break;
                }
            }
        });
    }

    private void consultarproducto() {

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        url =  "http://mydsystems.com/pruebaDaniel/ConsultaProducto.php";
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response1) {
                        try {

                            JSONObject jsonObject = new JSONObject(response1);
                            Boolean success = jsonObject.getBoolean("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("productos");

                            if (success) {
                                for(int i=0;i<jsonArray.length();i++) {
                                    producto  = new Productos();
                                    jsonObject = jsonArray.getJSONObject(i);
                                    producto.setIdProducto(jsonObject.getString("IdProducto"));
                                    producto.setCodigo(jsonObject.getString("CodigoProducto"));
                                    producto.setDescripcion(jsonObject.getString("DescripcionProducto"));
                                    producto.setMarca(jsonObject.getString("Marca"));
                                    producto.setUnidad(jsonObject.getString("Unidad"));
                                    producto.setFlete(jsonObject.getString("Flete"));
                                    producto.setCantidad(jsonObject.getString("Cantidad"));
                                    producto.setEstado(jsonObject.getString("Estado"));
                                    listaProductos.add(producto);
                                }
                                BandejaProductosAdapter1 adapter = new BandejaProductosAdapter1(listaProductos);
                                adapter.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        productoelegido = listaProductos.get(rvlistadoProductos.getChildAdapterPosition(v));
                                        if (listausuariosseleccionados ==null){
                                            listausuariosseleccionados = new ArrayList<>();
                                        }else{

                                            Toast.makeText(BuscarProductoActivity.this, "ingreso a la zona buena", Toast.LENGTH_SHORT).show();
                                        }
                                        listausuariosseleccionados.add(productoelegido);
                                        Intent intent =  new Intent(BuscarProductoActivity.this,BuscarProductoActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable("listaproductosseleccionados", listausuariosseleccionados);
                                        intent.putExtras(bundle);
                                        Bundle bundle1 = new Bundle();
                                        bundle1.putSerializable("producto",productoelegido);
                                        intent.putExtras(bundle1);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                                rvlistadoProductos.setAdapter(adapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);





    }
}
