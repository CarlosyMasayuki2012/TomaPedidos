package com.example.sistemas.tomapedidos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sistemas.tomapedidos.Entidades.Clientes;
import com.example.sistemas.tomapedidos.Entidades.Productos;
import com.example.sistemas.tomapedidos.Entidades.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BuscarProductoActivity extends AppCompatActivity {

    RadioGroup rggrupoproducto;
    RadioButton rbnombreproducto, rbcodigoproducto;
    Button btnbuscarProducto;
    ArrayList<Productos> listaProductos,listaproductoselegidos;
    Productos producto;
    ListView lvProducto;
    ArrayList<String> listaProducto;
    Clientes cliente;
    EditText etproducto;
    String url,Tipobusqueda,tipoPago;
    ProgressDialog progressDialog;
    Usuario usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_producto);

        cliente  = new Clientes();
        cliente = (Clientes)getIntent().getSerializableExtra("Cliente");
        usuario = (Usuario) getIntent().getSerializableExtra("Usuario");
        tipoPago = (String)getIntent().getStringExtra("TipoPago");

       // Toast.makeText(this,cliente.getIdCliente(), Toast.LENGTH_SHORT).show();

        listaProductos = new ArrayList<>();
        listaProducto = new ArrayList<>();
        listaproductoselegidos = new ArrayList<>();
        listaproductoselegidos = (ArrayList<Productos>) getIntent().getSerializableExtra("listaproductoselegidos");
        rggrupoproducto = findViewById(R.id.rgBuscarProducto);
        rbnombreproducto = findViewById(R.id.rbNombreProducto);
        rbcodigoproducto = findViewById(R.id.rbCodigoProducto);
        btnbuscarProducto = findViewById(R.id.btnBuscarProducto);
        lvProducto = findViewById(R.id.lvProducto);
        etproducto  = findViewById(R.id.etPrducto);

       // final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item,listaProducto);

        btnbuscarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //lvProducto.setAdapter(adapter);
                btnbuscarProducto.setVisibility(View.GONE);
                progressDialog = new ProgressDialog(BuscarProductoActivity.this);
                progressDialog.setMessage("Cargando...");
                progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
                progressDialog.show();

                buscarproducto(etproducto.getText().toString(),Tipobusqueda);

                // Se hace la busqueda desde el WS de Taiheng
/*
                grabarpedidoWS(etproducto.getText().toString(),tipoPago,usuario.getAlmacen().
                        toString(),cliente.getCodCliente().toString(),Cantidad);

*/
            }
        });

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
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable("listaproductoselegidos",listaproductoselegidos);
                intent.putExtras(bundle2);
                Bundle bundle3 = new Bundle();
                bundle3.putSerializable("Usuario",usuario);
                intent.putExtras(bundle3);
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
                        Tipobusqueda = "Nombre";
                        break;
                    case R.id.rbCodigoProducto:
                        etproducto.setInputType(2);   // envia el teclado de tipo numerico
                        Tipobusqueda = "Codigo";
                        break;
                }
            }
        });
    }

    private void buscarproducto(String numero, String tipoConsulta) {

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        url =  "http://mydsystems.com/pruebaDaniel/ConsultaProducto.php?producto='"+numero+"'&tipobusqueda=" + tipoConsulta;
        listaProducto = new ArrayList<>();
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        btnbuscarProducto.setVisibility(View.VISIBLE);
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("producto");
                            if (success){
                                for(int i=0;i<jsonArray.length();i++) {
                                    producto = new Productos();
                                    jsonObject = jsonArray.getJSONObject(i);
                                    producto.setIdProducto(jsonObject.getString("IdProducto"));
                                    producto.setCodigo(jsonObject.getString("CodigoProducto"));
                                    producto.setMarca(jsonObject.getString("Marca"));
                                    producto.setDescripcion(jsonObject.getString("DescripcionProducto"));
                                    producto.setPrecio(jsonObject.getString("Precio"));
                                    producto.setStock(jsonObject.getString("Stock"));
                                    producto.setUnidad(jsonObject.getString("Unidad"));
                                    producto.setFlete(jsonObject.getString("Flete"));
                                    producto.setEstado(jsonObject.getString("Estado"));
                                    listaProductos.add(producto);
                                    listaProducto.add(producto.getCodigo()+ " - " + producto.getDescripcion());

                                }
                                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext()
                                        , R.layout.support_simple_spinner_dropdown_item,listaProducto);
                                lvProducto.setAdapter(adapter);
                            }else {
                                listaProducto.clear();
                                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext()
                                        , R.layout.support_simple_spinner_dropdown_item,listaProducto);
                                lvProducto.setAdapter(adapter);
                                AlertDialog.Builder builder = new AlertDialog.Builder(BuscarProductoActivity.this);
                                builder.setMessage("No se llego a encontrar el registro")
                                        .setNegativeButton("Aceptar",null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) { e.printStackTrace(); }
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
