package com.example.sistemas.tomapedidos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class DetalleProductoActivity extends AppCompatActivity {

    TextView tvcodigoproducto,tvnombreproducto,tvalmacenproducto,tvstock,tvprecio,tvsubtotal,
            tvtotal,tvobservacionproducto;
    Productos productos;
    Button btnguardaryrevisar, btnguardaryagregar;
    Clientes cliente;
    ArrayList<Productos> listaproductoselegidos;
    EditText etcantidadelegida;
    Double preciounitario,cantidad;
    Usuario usuario;
    String url;
    ProgressDialog progressDialog;
    Productos producto;
    ArrayList<Productos> listaProductos;
    ArrayList<String> listaProducto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_producto);

        etcantidadelegida =  findViewById(R.id.etCantidadElegida);
        listaproductoselegidos = new ArrayList<Productos>();
        productos  = new Productos();
        productos = (Productos) getIntent().getSerializableExtra("Producto");
        usuario = (Usuario)getIntent().getSerializableExtra("Usuario");
        cliente = new Clientes();
        cliente = (Clientes)getIntent().getSerializableExtra("Cliente");
        usuario = (Usuario)getIntent().getSerializableExtra("Usuario");
        listaproductoselegidos = (ArrayList<Productos>)(ArrayList<Productos>) getIntent()
                .getSerializableExtra("listaproductoselegidos");

        // Se referencia a todas las partes del XML asociado al Activity
        tvcodigoproducto =  findViewById(R.id.tvCofigoProducto);
        tvnombreproducto = findViewById(R.id.tvNombreProducto);
        tvalmacenproducto = findViewById(R.id.tvAlmacenProducto);
        tvstock  =findViewById(R.id.tvStock);
        tvprecio = findViewById(R.id.tvPrecio);
        tvsubtotal = findViewById(R.id.tvSubtotal);
        tvtotal = findViewById(R.id.tvTotal);
        tvobservacionproducto = findViewById(R.id.tvObservacionProducto);
        btnguardaryrevisar = findViewById(R.id.btnGuardarrevisar);
        btnguardaryagregar = findViewById(R.id.btnGuardaryagregar);

        // Se hace una nueva

        progressDialog =  new ProgressDialog(DetalleProductoActivity.this);
        progressDialog.setMessage("... Por favor esperar");
        progressDialog.show();
        btnguardaryagregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(DetalleProductoActivity.this, "Se ha registrado correctamente", Toast.LENGTH_SHORT).show();
                usuario =  new Usuario();
                //RegistroPedido(); // Esta clase va a hacer el registro de los pedidos que se lleguen a consolidar
            }
        });
        btnguardaryrevisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                productos.setCantidad(etcantidadelegida.getText().toString());
                preciounitario = Double.valueOf(productos.getPrecio());
                cantidad = Double.valueOf(productos.getCantidad());
                productos.setPrecioAcumulado(String.valueOf(Math.ceil((cantidad*preciounitario*100.00))/100.00)); // Se hace la definicion del precio que se va ha acumular
                Toast.makeText(DetalleProductoActivity.this, productos.getPrecioAcumulado().toString(), Toast.LENGTH_SHORT).show();
                productos.setEstado(String.valueOf(cantidad)); // Se define la cantidad que e debe de tener
                listaproductoselegidos.add(productos);
                Intent intent = new Intent(DetalleProductoActivity.this,bandejaProductosActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("listaProductoselegidos", listaproductoselegidos);
                intent.putExtras(bundle);
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("Cliente",cliente);
                intent.putExtras(bundle1);
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable("Usuario",usuario);
                intent.putExtras(bundle2);
                startActivity(intent);
                finish();

            }
        });

         tvcodigoproducto.setText(productos.getCodigo());
         tvnombreproducto.setText(productos.getDescripcion());
         tvalmacenproducto.setText(productos.getAlmacen());
         tvstock.setText(productos.getStock());
         tvprecio.setText(productos.getPrecio());
         Double total = Double.valueOf(productos.getPrecio());
         total = total*cantidad;
         Double subtotal = total/1.18;
         tvsubtotal.setText(String.valueOf("subtotal"));
         tvtotal.setText(String.valueOf(total));

         tvobservacionproducto.setText(producto.getObservacion());
         tvsubtotal.setText(String.valueOf(subtotal));


    }

    private void RegistroPedido() {

        listaproductoselegidos.add(productos);
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        url =  "http://www.taiheng.com.pe:8494/oracle/ejecutaFuncionCursorTestMovil.php?funcion = " +
                "PKG_WEB_HERRAMIENTAS.SP_WS_GENERA_PEDIDO&variables=[TAI HEN]G Jueves 06.09.2018"; // Se debe de encontrar el metodo
        //listaProducto = new ArrayList<>();
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        //btnbuscarProducto.setVisibility(View.VISIBLE); // No se puede haer la presentacion de
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("hojaruta");
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
                                Intent intent =  new Intent(DetalleProductoActivity.this,MainActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("listaProductoselegidos", listaproductoselegidos);
                                intent.putExtras(bundle);
                                Bundle bundle1 = new Bundle();
                                bundle1.putSerializable("Cliente",cliente);
                                intent.putExtras(bundle1);
                                Bundle bundle2 = new Bundle();
                                bundle2.putSerializable("Usuario",usuario);
                                intent.putExtras(bundle2);
                                startActivity(intent);
                                finish();

                                startActivity(intent);
                                finish();
                            }else {
                                listaProducto.clear();
                                AlertDialog.Builder builder = new AlertDialog.Builder(DetalleProductoActivity.this);
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
