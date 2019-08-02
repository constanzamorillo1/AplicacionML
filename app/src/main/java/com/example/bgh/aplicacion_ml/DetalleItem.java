package com.example.bgh.aplicacion_ml;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONObject;

public class DetalleItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_item);
        TextView texto = (TextView)findViewById(R.id.textView);
        Intent intent = getIntent();
        String item = intent.getStringExtra("item");
        texto.setText(item);
    }
}
