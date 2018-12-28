package com.example.sistemas.tomapedidos;

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
import com.example.sistemas.tomapedidos.Entidades.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BusquedaClienteActivity extends AppCompatActivity {

    RadioGroup rggrupocliente;
    RadioButton rbnombre,rbcodigo;
    Button btnbuscar;
    ArrayList<Clientes> listaClientes;
    Clientes cliente;
    ListView lvclientes;
    ArrayList<String> listaCliente;
    EditText etcliente;
    String url, tipoConsulta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda_cliente);

        listaClientes = new ArrayList<>();
        listaCliente  =new ArrayList<>();

        /*
        for (int i=0; i<5;i++){

            cliente = new Clientes();
            cliente.setIdCliente(String.valueOf(i));
            cliente.setNombre("Cliente " + i);
            cliente.setDireccion("Direccion " + i);
            cliente.setDeuda("Deuda " +i);
            cliente.setGiro("Giro " + i);
            cliente.setEstado("Etado " + i);
            cliente.setTipoCliente("Tipo Cliente " + i);
            cliente.setUsuarioultpedido("Usuario Ult Pedido" + i);
            cliente.setFechaultpedido("fecha ult Pedido " + i);
            listaClientes.add(cliente);
            listaCliente.add(cliente.getNombre()+ '\r' + cliente.getDireccion());
        }
        */

        rggrupocliente = findViewById(R.id.rgBuscar);
        rbnombre = findViewById(R.id.rbNombre);
        rbcodigo = findViewById(R.id.rbCodigo);
        btnbuscar = findViewById(R.id.btnBuscar);
        lvclientes = findViewById(R.id.lvCliente);
        etcliente = findViewById(R.id.etCliente);

        if (etcliente.equals(""))
        {
            Toast.makeText(this, "Se ha ingresado  un valor nulo", Toast.LENGTH_SHORT).show();

        }


        btnbuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etcliente.getText().toString().equals(""))
                {

                    Toast.makeText(BusquedaClienteActivity.this, "Por favor ingrese un valor valido ", Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(BusquedaClienteActivity.this);

                }else {
                    buscarCliente(etcliente.getText().toString(),tipoConsulta);
                }
            }
        });

        lvclientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            cliente = new Clientes();
            Intent intent =  new Intent(BusquedaClienteActivity.this,MostrarClienteActivity.class);
            Bundle bundle = new Bundle();
            cliente =  listaClientes.get(position);
            bundle.putSerializable("Cliente",cliente);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
            }
        });

        rggrupocliente.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (rggrupocliente.getCheckedRadioButtonId()){

                    case R.id.rbNombre:
                        etcliente.setInputType(1);
                        tipoConsulta = "Nombre";
                        break;
                    case R.id.rbCodigo:
                        etcliente.setInputType(2);
                        tipoConsulta = "Codigo";
                        break;
                }
            }
        });
    }

    private void buscarCliente(String numero, String tipoConsulta) {

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());

        url =  "http://mydsystems.com/pruebaDaniel/ConsultaCliente.php?ruc='"+numero+"'&tipobusqueda=" + tipoConsulta;

        listaCliente = new ArrayList<>();
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Toast.makeText(BusquedaClienteActivity.this, response,
                                    Toast.LENGTH_SHORT).show();
                            JSONObject jsonObject=new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("cliente");

                            if (success){
                                for(int i=0;i<jsonArray.length();i++) {
                                    cliente = new Clientes();
                                    jsonObject = jsonArray.getJSONObject(i);
                                    cliente.setCodCliente(jsonObject.getString("CodCliente"));
                                    cliente.setNombre(jsonObject.getString("Nombre"));
                                    cliente.setDireccion(jsonObject.getString("Direccion"));
                                    cliente.setGiro(jsonObject.getString("Giro"));
                                    cliente.setTipoCliente(jsonObject.getString("TipoCliente"));
                                    cliente.setEstado(jsonObject.getString("Estado"));
                                    cliente.setFechaultpedido(jsonObject.getString("Fechaultpedido"));
                                    cliente.setUsuarioultpedido(jsonObject.getString("Usuarioultpedido"));
                                    listaClientes.add(cliente);
                                    listaCliente.add(cliente.getNombre()+ '\n' + cliente.getDireccion());

                                }
                                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext()
                                        , R.layout.support_simple_spinner_dropdown_item,listaCliente);
                                lvclientes.setAdapter(adapter);

                            }else {
                                listaCliente.clear();
                                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext()
                                        , R.layout.support_simple_spinner_dropdown_item,listaCliente);
                                lvclientes.setAdapter(adapter);

                                AlertDialog.Builder builder = new AlertDialog.Builder(BusquedaClienteActivity.this);
                                builder.setMessage("No se llego a encontrar el registro")
                                        .setNegativeButton("Aceptar",null)
                                        .create()
                                        .show();
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
