package com.example.bgh.aplicacion_ml;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;


public class DetalleItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_item);

        visualizar_detalle();
    }

    protected void visualizar_detalle(){
        Intent intent = getIntent();
        String item = intent.getStringExtra("item");
        JsonObject objeto = new Gson().fromJson(item, JsonObject.class);
        TextView datos = findViewById(R.id.datos);

        /*Se genera el string con datos del producto. Luego se coloca en el componente Text*/
        String acepta_mercado = objeto.get("accepts_mercadopago").getAsBoolean() ? "SI" : "NO";
        String cantidad_disponible = objeto.get("available_quantity").getAsInt() == 0 ? "NO HAY DISPONIBILIDAD" : "Cantidad Disponible: "+objeto.get("available_quantity").getAsInt();
        String string_datos = objeto.get("title").getAsString()+"\n";
        string_datos+= "Precio: "+objeto.get("currency_id").getAsString()+" "+objeto.get("price").getAsString()+"\n";
        string_datos+= "Acepta Mercado Pago: "+acepta_mercado+"\n";
        string_datos+= cantidad_disponible+"\n";
        string_datos+= "Cantidad vendida: "+objeto.get("sold_quantity").getAsString();
        datos.setText(string_datos);

        /*Se obtiene la url de la imagen del producto. Se obtiene el componente Image y con la libreria Picasso se carga la imagen desde la url*/
        String imageUrl = objeto.get("thumbnail").getAsString();
        ImageView im = findViewById(R.id.imagen);
        Picasso.get().load(imageUrl).placeholder(R.drawable.ic_launcher_background).into(im);
    }
}
