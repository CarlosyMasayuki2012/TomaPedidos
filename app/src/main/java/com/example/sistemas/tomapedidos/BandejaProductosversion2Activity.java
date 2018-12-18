package com.example.sistemas.tomapedidos;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.sistemas.tomapedidos.Entidades.Productos;

import java.util.ArrayList;

public class BandejaProductosversion2Activity extends AppCompatActivity {

    RecyclerView rvbandejaproductos;
    ArrayList<Productos> listabandejaproductos,listaproductos;
    ArrayList<String> lista;
    Productos producto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bandeja_productosversion2);

        rvbandejaproductos = findViewById(R.id.rvlistaPedidos);



        for (int i = 0; i<listabandejaproductos.size() ; i++){
            producto = new Productos();



        }

        //bandejaProductosActivity adapter = new bandejaProductosActivity(listabandejaproductos);



        rvbandejaproductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });


    }
}
