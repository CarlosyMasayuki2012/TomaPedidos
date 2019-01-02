package com.example.sistemas.tomapedidos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    Double precio,cantidad,total,subtotal,Aux;
    Usuario usuario;
    Clientes cliente;
    Productos producto;
    String position;
    Integer i;

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
        position = getIntent().getStringExtra("position");

        Toast.makeText(this, position, Toast.LENGTH_LONG).show();

        i = Integer.valueOf(position);
        // Se hace el llenado de los campos
        tvcodigoelegido.setText(listaproductoselegidos.get(i).getCodigo());
        tvnombreproductoelegdo.setText(listaproductoselegidos.get(i).getDescripcion());
        tvalmacenelegido.setText(listaproductoselegidos.get(i).getAlmacen());
        tvstockproductoelegido.setText(listaproductoselegidos.get(i).getStock());
        tvprecioproductoelegido.setText(listaproductoselegidos.get(i).getPrecio());

        // Se hace el calculo de los totales y subtotales
        precio = Double.valueOf(listaproductoselegidos.get(i).getPrecio());
        cantidad = Double.valueOf(listaproductoselegidos.get(i).getCantidad());
        total = precio + cantidad;
        subtotal = total/1.18;

        Toast.makeText(this, total.toString(), Toast.LENGTH_SHORT).show();

        tvsubtotalproductoelegido.setText(String.valueOf(subtotal));
        tvtotalproductoelegido.setText(String.valueOf(total));
        tvosbervacionproductoelegido.setText(listaproductoselegidos.get(i).getObservacion());
        etcantidadelegido.setText(listaproductoselegidos.get(i).getCantidad());

        final TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (etcantidadelegido.getText().toString().equals("")){
                    Aux = 0d;
                    tvtotalproductoelegido.setText("");
                    tvsubtotalproductoelegido.setText("");
                }else{

                    Double Aux = Double.valueOf(etcantidadelegido.getText().toString());
                    Aux = precio * Aux;
                    subtotal = Aux/1.18;

                    Toast.makeText(ActualizarRegistroPedidosActivity.this, Aux.toString(), Toast.LENGTH_SHORT)
                            .show();
                    tvtotalproductoelegido.setText(String.format("%.2f", (double)Aux));
                    tvsubtotalproductoelegido.setText(String.format("%.2f",(double)subtotal));
                }
            }
        };

        etcantidadelegido.addTextChangedListener(textWatcher);

        btnactualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listaproductoselegidos.get(i).setCantidad(etcantidadelegido.getText().toString());
                Intent intent = new Intent(ActualizarRegistroPedidosActivity.this,bandejaProductosActivity.class);
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
    }
}
