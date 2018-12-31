package com.example.sistemas.tomapedidos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sistemas.tomapedidos.Entidades.Clientes;
import com.example.sistemas.tomapedidos.Entidades.Productos;
import com.example.sistemas.tomapedidos.Entidades.Usuario;

import java.util.ArrayList;

public class ActualizarRegistroPedidosActivity extends AppCompatActivity {

    TextView tvcodigoelegido,tvnombreproductoelegdo,tvalmacenelegido,tvstockproductoelegido,
            tvprecioproductoelegido,tvsubtotalproductoelegido,tvtotalproductoelegido,
            tvosbervacionproductoelegido;
    EditText etcantidadelegido;
    Button btnactualizar;
    ArrayList<Productos> listaproductoselegidos;
    Double precio,cantidad,total,subtotal;
    Usuario usuario;
    Clientes cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_registro_pedidos);

        tvcodigoelegido = findViewById(R.id.tvCodProdEegido);
        tvnombreproductoelegdo = findViewById(R.id.tvNomProductoelegido);
        tvalmacenelegido = findViewById(R.id.tvAlmacenProductoElegido);
        tvstockproductoelegido = findViewById(R.id.tvStockElegido);
        tvprecioproductoelegido = findViewById(R.id.tvPrecioElegido);
        tvsubtotalproductoelegido = findViewById(R.id.tvSubtotalElegido);
        tvtotalproductoelegido = findViewById(R.id.tvTotalElegido);
        tvosbervacionproductoelegido =  findViewById(R.id.tvObsProductoElegido);
        etcantidadelegido = findViewById(R.id.etCantidadActualizada);
        btnactualizar = findViewById(R.id.btnActualizarElegido);
        // Se reciben los parametro de productos, usuario y clientes
        listaproductoselegidos = (ArrayList<Productos>)getIntent().getSerializableExtra("listaproductoselegidos");
        usuario = (Usuario)getIntent().getSerializableExtra("Usuario");
        cliente = (Clientes)getIntent().getSerializableExtra("cliente");

        // Se hace el llenado de los campos
        tvcodigoelegido.setText(listaproductoselegidos.get(0).getCodigo());
        tvnombreproductoelegdo.setText(listaproductoselegidos.get(0).getNombre());
        tvalmacenelegido.setText(listaproductoselegidos.get(0).getAlmacen());
        tvstockproductoelegido.setText(listaproductoselegidos.get(0).getStock());
        tvprecioproductoelegido.setText(listaproductoselegidos.get(0).getPrecio());
        // Se hace el calculo de los totales y subtotales
        precio = Double.valueOf(listaproductoselegidos.get(0).getPrecio());
        cantidad = Double.valueOf(listaproductoselegidos.get(0).getCantidad());
        total = precio + cantidad;
        subtotal = total/1.18;

        tvsubtotalproductoelegido.setText(String.valueOf(subtotal));
        tvtotalproductoelegido.setText(String.valueOf(total));
        tvosbervacionproductoelegido.setText(listaproductoselegidos.get(0).getObservacion());
        etcantidadelegido.setText(listaproductoselegidos.get(0).getCantidad());
        btnactualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActualizarRegistroPedidosActivity.this,bandejaProductosActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("listaproductoselegidos",listaproductoselegidos);
                intent.putExtras(bundle);
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("Usuario",usuario);
                intent.putExtras(bundle1);
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable("Cliente",cliente);
                intent.putExtras(bundle2);
                startActivity(intent);
                finish();
            }
        });
    }
}
