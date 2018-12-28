package com.example.sistemas.tomapedidos;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sistemas.tomapedidos.Entidades.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    EditText etusuario,etclave;
    Button btnlogeo;
    Usuario usuario;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usuario = new Usuario();
        etusuario = findViewById(R.id.etUsuario);
        etclave = findViewById(R.id.etClave);
        btnlogeo = findViewById(R.id.btnLogin);
        btnlogeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario.setNombre(etusuario.getText().toString().replace(" ",""));
                usuario.setClave(etclave.getText().toString().replace(" ",""));
                // Se recibe los parametros
                usuario.setNombre("Carlos");
                usuario.setApellido("Miyashiro");
                usuario.setCodigo("001");
                // metodo para hacer la verificacion mediante in llamado a la webservice

                /*

                if (usuario.getUsuario().equals("") && usuario.getClave().equals("")){

                }else{
                    verificarUsuario();
                }

                // Se hace la verificacion en duro

                */

                if(usuario.getNombre().equals("Carlos")&& usuario.getClave().equals("12345")){
                    Toast.makeText(LoginActivity.this, "Entro", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Usuario",usuario);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this, "Nombre o oclave incorrecta", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void verificarUsuario(){

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());

        // se debe de colocar la url para la validacion del usuario

        url =  "http://www.taiheng.com.pe:8494/oracle/ejecutaFuncionCursorTestMovil.php?funcion=pkg_movil_funciones.fn_obtener_motivos_hruta&variables='9'";

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response1) {

                        try {
                            JSONObject jsonObject=new JSONObject(response1);
                            boolean success = jsonObject.getBoolean("success");
                            if (success){
                                JSONArray jsonArray = jsonObject.getJSONArray("hojaruta");

                                for(int i=0;i<jsonArray.length();i++) {
                                    jsonObject = jsonArray.getJSONObject(i);

                                }

                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("Usuario",usuario);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                finish();

                            }else{
                                AlertDialog.Builder build1 = new AlertDialog.Builder(LoginActivity.this);
                                build1.setTitle("No se encontro el usuario")
                                        .setNegativeButton("Regresar",null)
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
