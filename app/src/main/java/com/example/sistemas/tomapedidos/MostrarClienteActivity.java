package com.example.sistemas.tomapedidos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sistemas.tomapedidos.Entidades.Clientes;
import com.example.sistemas.tomapedidos.Entidades.Usuario;

public class MostrarClienteActivity extends AppCompatActivity {

    Button btnpedido;
    Clientes cliente;
    TextView tvcodigo,tvNombre,tvDireccion,tvGiro,tvTipoCiente,tvDeuda,tvestado,tvFechaUltPedido,
            tvUsuarioUltPedido;
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_cliente);

        cliente  = new Clientes();
        cliente = (Clientes)getIntent().getSerializableExtra("Cliente");
        usuario = (Usuario) getIntent().getSerializableExtra("Usuario");

        Toast.makeText(this,cliente.getIdCliente(), Toast.LENGTH_SHORT).show();
        tvcodigo = findViewById(R.id.tvCofigoProducto);
        tvNombre = findViewById(R.id.tvNombreCliente);
        tvDireccion = findViewById(R.id.tvDireccion);
        tvGiro = findViewById(R.id.tvNombreProducto);
        tvTipoCiente = findViewById(R.id.tvAlmacenProducto);
        tvDeuda = findViewById(R.id.tvStock);
        tvestado = findViewById(R.id.tvPrecio);
        tvFechaUltPedido = findViewById(R.id.tvSubtotal);
        tvUsuarioUltPedido = findViewById(R.id.tvTotal);

        tvcodigo.setText(cliente.getCodCliente());
        tvNombre.setText(cliente.getNombre());
        tvDireccion.setText(cliente.getDireccion());
        tvGiro.setText(cliente.getGiro());
        tvTipoCiente.setText(cliente.getTipoCliente());
        tvDeuda.setText(cliente.getDeuda());
        tvestado.setText(cliente.getEstado());
        tvFechaUltPedido.setText(cliente.getFechaultpedido());
        tvUsuarioUltPedido.setText(cliente.getUsuarioultpedido());


        btnpedido = findViewById(R.id.btnPedido);
        btnpedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MostrarClienteActivity.this,ListadoAlmacenActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Cliente",cliente);
                intent.putExtras(bundle);
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("Usuario",usuario);
                intent.putExtras(bundle1);
                startActivity(intent);
                finish();
            }
        });



    }
}
