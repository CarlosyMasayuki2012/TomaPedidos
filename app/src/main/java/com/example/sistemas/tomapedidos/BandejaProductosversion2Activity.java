package com.example.sistemas.tomapedidos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.example.sistemas.tomapedidos.Entidades.Productos;

import java.util.ArrayList;

public class BandejaProductosversion2Activity extends AppCompatActivity {

    RecyclerView rvbandejaproductos;
    ArrayList<Productos> listabandejaproductos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bandeja_productosversion2);

        rvbandejaproductos = findViewById(R.id.rvlistaPedidos);

        bandejaProductosActivity adapter = new bandejaProductosActivity();


    }
}
