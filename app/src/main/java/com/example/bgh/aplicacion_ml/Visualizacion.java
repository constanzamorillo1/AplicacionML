package com.example.bgh.aplicacion_ml;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Visualizacion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizacion);

        Intent intent = getIntent();
        String item = intent.getStringExtra("palabra");
        cargarItems(item);
    }

    /**
     *
     * @param item, artículo abuscar
     * Se utiliza la libreria volley para realizar el requerimiento a la api ,
     * y así obtener los artículos relacionados con la búsqueda
     *  Para cada artículo se crea un botón con el nombre del mismo.
     *  Cuando se clickea, se dirige a la visualización detallada.
     */
    protected void cargarItems(String item){
        final LinearLayout botones = findViewById(R.id.botones);
        final Context context = this;
        String URL = "https://api.mercadolibre.com/sites/MLU/search?q="+item;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray obj = response.getJSONArray("results");
                            if (obj.length()>0){
                                for(int i = 0; i<obj.length(); i++) {
                                    final JSONObject it = obj.getJSONObject(i);
                                    Button boton = new Button(context);
                                    boton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                                    boton.setText(it.getString("title"));
                                    boton.setOnClickListener(new View.OnClickListener() {
                                        public void onClick(View v) {
                                            Intent intent = new Intent(context,DetalleItem.class);
                                            intent.putExtra("item",it.toString());
                                            startActivity(intent);
                                        }
                                    });
                                    botones.addView(boton);
                                }
                            }else {
                                Intent intent = new Intent(context,MainActivity.class);
                                startActivity(intent);
                                Toast.makeText(context,"No se han encontrado datos relacionados a la búsqueda. Vuelva a intentar",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("error",error.toString());
                }
            }
        );

        requestQueue.add(objectRequest);
    }
}
