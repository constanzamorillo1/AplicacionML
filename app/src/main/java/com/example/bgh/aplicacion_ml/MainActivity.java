package com.example.bgh.aplicacion_ml;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buscar(View view){
        Intent intent = new Intent(this,Visualizacion.class);
        EditText edit_buscar = (EditText)findViewById(R.id.edit_buscar);
        String texto = edit_buscar.getText().toString();
        intent.putExtra("palabra",texto);
        startActivity(intent);
    }
}
