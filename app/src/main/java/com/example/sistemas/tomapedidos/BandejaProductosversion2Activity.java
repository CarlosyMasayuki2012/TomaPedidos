package com.example.sistemas.tomapedidos;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.sistemas.tomapedidos.Adaptadores.bandejaproductosadapter;
import com.example.sistemas.tomapedidos.Entidades.Productos;

import java.util.ArrayList;

public class BandejaProductosversion2Activity extends AppCompatActivity {

    RecyclerView rvbandejaproductos;
    ArrayList<Productos> listabandejaproductos,listaproductos;
    ArrayList<String> lista;
    Productos producto;
    bandejaproductosadapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bandeja_productosversion2);

        listabandejaproductos = new ArrayList<>();
        rvbandejaproductos = findViewById(R.id.rvlistaPedidos);
        rvbandejaproductos.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        rvbandejaproductos.setHasFixedSize(true);


        adapter =  new bandejaproductosadapter();

        rvbandejaproductos.setAdapter(adapter);






    }
}
