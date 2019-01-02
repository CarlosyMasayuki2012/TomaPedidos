package com.example.sistemas.tomapedidos;

import android.app.ProgressDialog;
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

public class bandejaProductosActivity extends AppCompatActivity {

    TextView tvtitulodinamico;
    Productos productos;
    Button btnbuscarproducto, btnterminar;
    ListView lvbandejaproductos;
    ArrayList<String> listabandejaproductos,listabandejaproductoselegidos;
    Clientes cliente;
    String cantidad ,Item ,Precio,url;
    View mview;
    Integer cantidadProductos=0;
    ArrayList<Productos> listaproductoselegidos,listaProductosresultante;
    Double precio = 0.0,item=0.0;
    Usuario  usuario;
    ProgressDialog progressDialog ;
    Productos producto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bandeja_productos);

        // Se captura los parametros de los otros Intent
        listaproductoselegidos = (ArrayList<Productos>) getIntent().getSerializableExtra("listaProductoselegidos");
        cliente = (Clientes)getIntent().getSerializableExtra("Cliente");
        listabandejaproductos = new ArrayList<>();
        cantidadProductos = listabandejaproductos.size();
        listabandejaproductoselegidos = new ArrayList<>();

        // valores para el sumarizado de la bandeja

        for (int i=0;i<listaproductoselegidos.size();i++){
           // calcula numero de productos
            precio = precio + Double.valueOf(listaproductoselegidos.get(i).getPrecioAcumulado());
            item = item + Double.valueOf(listaproductoselegidos.get(i).getCantidad());
            listabandejaproductoselegidos.add(listaproductoselegidos.get(i).getCodigo().toString());
        }

        cantidad = String.valueOf(listaproductoselegidos.size());
        Item = String.valueOf(item);
        Precio = String.valueOf(precio);
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
                AlertDialog.Builder builder =  new AlertDialog.Builder(bandejaProductosActivity.this);
                    builder.setMessage("Está seguro que desea grabar el pedido")
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Toast.makeText(bandejaProductosActivity.this,
                                    "Se cancelo la gravacion del Pedido", Toast.LENGTH_SHORT).show();
                            }
                        });

                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            progressDialog = new ProgressDialog(bandejaProductosActivity.this);
                            progressDialog.setMessage("..actualizando");
                            progressDialog.show();

                            // Metodo para hacer la actualizacion de los nuevos pedidos

                            // Se hace el recorrido de la lista de los productos elegidos
                            for (int i=0; i<listaproductoselegidos.size();i++){
                                String codProducto = listaproductoselegidos.get(i).getCodigo();
                                String almacen = listaproductoselegidos.get(i).getAlmacen();
                                String distrito = usuario.getAlmacen();
                                String codCliente = cliente.getCodCliente();
                                String cantidad = listaproductoselegidos.get(i).getCantidad();
                                // se hace la invocacion del metodo para la gravacion de los pedidos
                                //grabarpedidoWS(codProducto,almacen,distrito,codCliente,cantidad);
                            }
                            Intent intent = new Intent(bandejaProductosActivity.this,MainActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("Usuario",usuario);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                        }
                    });
            }
        });

        lvbandejaproductos =  findViewById(R.id.lvbandejaProductos);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.
                support_simple_spinner_dropdown_item,listabandejaproductoselegidos);
        lvbandejaproductos.setAdapter(adapter);
        lvbandejaproductos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

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
                    public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                        String msg = "";
                        switch (i){
                            case 0:
                                Alertsdialog("Editar el prducto");
                                Editarproductoselecionado(position);
                            break;
                            case 1:
                                listaproductoselegidos.remove(position);
                                Alertsdialog("Borrar el producto");
                            break;
                            case 2:
                                Alertsdialog("Cancelar");
                            break; }
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
    private void Alertsdialog(String msg){

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
                    }
                })
                .create()
                .show();
    }

    private void grabarpedidoWS(String numero, String almacen, String distrito, String codCliente, String cantidad) {

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());

        url =  "http://www.taiheng.com.pe:8494/oracle/ejecutaFuncionCursorTestMovil.php?funcion=" +
                "PKG_WEB_HERRAMIENTAS.SP_WS_CONSULTAR_PRODUCTO&variables='"+almacen+"|"+distrito+"|"+numero+"||"+codCliente+"|||"+cantidad+"'";


        StringRequest stringRequest=new StringRequest(Request.Method.GET, url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
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
                                    listaProductosresultante.add(producto);
                                }
                            }else {

                                AlertDialog.Builder builder = new AlertDialog.Builder(bandejaProductosActivity.this);
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

    private void Editarproductoselecionado(Integer position) {

        producto = listaproductoselegidos.get(position);
        Intent intent =  new Intent(bandejaProductosActivity.this, ActualizarRegistroPedidosActivity.class);
        intent.putExtra("position",position.toString());
        Bundle bundle = new Bundle();
        bundle.putSerializable("Usuario",usuario);
        intent.putExtras(bundle);
        Bundle bundle1 = new Bundle();
        bundle1.putSerializable("listaproductoselegidos",listaproductoselegidos);
        intent.putExtras(bundle1);
        Bundle bundle2 = new Bundle();
        bundle2.putSerializable("Cliente",cliente);
        intent.putExtras(bundle2);
        startActivity(intent);
        finish();
    }
}
