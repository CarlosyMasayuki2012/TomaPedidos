package com.example.sistemas.tomapedidos;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

public class ListadoFormaPagoActivity extends AppCompatActivity {

    ListView lvtipopago;
    ArrayList<String> listatipopago,listaAux;
    Clientes cliente;
    String url;
    ArrayList<Productos> listaproductoselegidos;
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_forma_pago);

        listaproductoselegidos = new ArrayList<>();
        cliente  = new Clientes();
        cliente = (Clientes)getIntent().getSerializableExtra("Cliente");
        usuario = (Usuario) getIntent().getSerializableExtra("Usuario");

        Toast.makeText(this,cliente.getIdCliente(), Toast.LENGTH_SHORT).show();

        listatipopago =  new ArrayList<>();

        //Consultartipopago();

        for (int i =0 ; i<10;i++){

            listatipopago.add("Tipo de Pago " + i);
        }

        lvtipopago = findViewById(R.id.lvtipopago);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.
                support_simple_spinner_dropdown_item,listatipopago);

        lvtipopago.setAdapter(adapter);
        lvtipopago.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent =  new Intent(ListadoFormaPagoActivity.this,BuscarProductoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Cliente",cliente);
                intent.putExtras(bundle);
                intent.putExtra("TipoPago",listatipopago.get(position));
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("listaproductoselegidos",listaproductoselegidos);
                intent.putExtras(bundle1);
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable("Usuario",usuario);
                intent.putExtras(bundle2);
                startActivity(intent);
                finish();
            }
        });
    }

    private void Consultartipopago() {

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        url =  "http://www.taiheng.com.pe:8494/oracle/ejecutaFuncionCursorTestMovil.php?funcion=PKG_WEB_HERRAMIENTAS.SP_WS_FPAGOS";
        listatipopago = new ArrayList<>();
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Toast.makeText(ListadoFormaPagoActivity.this, response, Toast.LENGTH_SHORT).show();
                            JSONObject jsonObject=new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("hojaruta");

                            if (success){
                                for(int i=0;i<jsonArray.length();i++) {
                                    jsonObject = jsonArray.getJSONObject(i);
                                    String listado = jsonObject.getString("COD_FPAGO")+ " - "
                                            + jsonObject.getString("DESCRIPCION");
                                    listatipopago.add(listado);
                                    listaAux.add(jsonObject.getString("COD_FPAGO"));

                                }

                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.
                                        support_simple_spinner_dropdown_item,listatipopago);

                                lvtipopago.setAdapter(adapter);
                                lvtipopago.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                        Intent intent =  new Intent(ListadoFormaPagoActivity.this,BuscarProductoActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable("Cliente",cliente);
                                        intent.putExtras(bundle);
                                        intent.putExtra("TipoPago",listaAux.get(position));
                                        Bundle bundle1 = new Bundle();
                                        bundle1.putSerializable("listaproductoselegidos",listaproductoselegidos);
                                        intent.putExtras(bundle1);
                                        Bundle bundle2 = new Bundle();
                                        bundle1.putSerializable("Usuario",usuario);
                                        intent.putExtras(bundle2);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                            }else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ListadoFormaPagoActivity.this);
                                builder.setMessage("No se pudo encontrar los tipos de pago correspondientes");
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
