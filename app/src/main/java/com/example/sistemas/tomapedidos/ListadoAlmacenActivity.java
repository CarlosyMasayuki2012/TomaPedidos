package com.example.sistemas.tomapedidos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListadoAlmacenActivity extends AppCompatActivity {

    ListView lvAlmacenes;
    ArrayList<String> listaalmacen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_almacen);

        listaalmacen =  new ArrayList<>();

        for (int i =0 ; i<10;i++){

            listaalmacen.add("almacen " + i);
        }

        lvAlmacenes = findViewById(R.id.lvAlmacenes);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,listaalmacen);

        lvAlmacenes.setAdapter(adapter);
    }
}
