package com.example.sistemas.tomapedidos;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sistemas.tomapedidos.Adaptadores.bandejaproductosadapter;
import com.example.sistemas.tomapedidos.Entidades.Productos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BandejaProductosversion2Activity extends AppCompatActivity {
/*
    RecyclerView rvsimple;
    ArrayList<Usuarios> listadatos, listausuariosseleccionados;
    Usuarios usuarios, usuarioelegido;
    String url;
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bandeja_productosversion2);
/*
        rvsimple = findViewById(R.id.rvprimeramuestra);
        rvsimple.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.
                VERTICAL,false));
        listausuariosseleccionados = (ArrayList<Usuarios>) getIntent().getSerializableExtra("listausuariosseleccionados");
        listadatos = new ArrayList<>();
        consultarusuario();

        */
    }
/*
    public void consultarusuario (){

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        url =  "http://mydsystems.com/pruebaDaniel/Consultausuarios.php";
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response1) {
                        try {

                            JSONObject jsonObject = new JSONObject(response1);
                            Boolean success = jsonObject.getBoolean("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("usuarios");

                            if (success) {
                                for(int i=0;i<jsonArray.length();i++) {
                                    usuarios = new Usuarios();
                                    jsonObject = jsonArray.getJSONObject(i);
                                    usuarios.setId_Usuario(jsonObject.getString("Id_Usuario"));
                                    usuarios.setUsuario(jsonObject.getString("Usuario"));
                                    usuarios.setNombre(jsonObject.getString("Nombre"));
                                    usuarios.setApellido(jsonObject.getString("Apellido"));
                                    usuarios.setPerfil(jsonObject.getString("Perfil"));
                                    listadatos.add(usuarios);
                                }
                                RecyclerViewAdapter adapter = new RecyclerViewAdapter(listadatos);
                                adapter.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        usuarioelegido = listadatos.get(rvsimple.getChildAdapterPosition(v));
                                        if (listausuariosseleccionados ==null){
                                            listausuariosseleccionados = new ArrayList<>();
                                        }else{

                                            Toast.makeText(MainActivity.this, "ingreso a la zona buena", Toast.LENGTH_SHORT).show();
                                        }
                                        listausuariosseleccionados.add(usuarioelegido);
                                        Intent intent =  new Intent(MainActivity.this,DetalleUsuarioActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable("listausuariosseleccionados", listausuariosseleccionados);
                                        intent.putExtras(bundle);
                                        Bundle bundle1 = new Bundle();
                                        bundle1.putSerializable("usuarios",usuarioelegido);
                                        intent.putExtras(bundle1);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                                rvsimple.setAdapter(adapter);
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
    */
}
