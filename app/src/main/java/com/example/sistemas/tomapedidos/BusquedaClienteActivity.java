package com.example.sistemas.tomapedidos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class BusquedaClienteActivity extends AppCompatActivity {

    RadioGroup rggrupocliente;
    RadioButton rbnombre,rbcodigo;
    Button btnbuscar;

    ListView lvclientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rggrupocliente = findViewById(R.id.rgBuscarCliente);
        rbnombre = findViewById(R.id.rbNombre);
        rbcodigo = findViewById(R.id.rbCodigo);
        btnbuscar = findViewById(R.id.btnBuscar);
        lvclientes = findViewById(R.id.lvClientes);

    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rbNombre:
                if (checked)
                    // Pirates are the best
                    break;
            case R.id.rbCodigo:
                if (checked)
                    // Ninjas rule
                    break;
        }
    }
}
