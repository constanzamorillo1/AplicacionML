package com.example.bgh.aplicacion_ml;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Constraints;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

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
