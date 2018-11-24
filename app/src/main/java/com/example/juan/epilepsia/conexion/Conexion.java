package com.example.juan.epilepsia.conexion;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.juan.epilepsia.datos_alerta_repo;
import com.example.juan.epilepsia.datos_contacto;
import com.example.juan.epilepsia.datos_prim;
import com.example.juan.epilepsia.sqlite.ConexionSQLite;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class Conexion  {
    public String latitu, longitud,direccion;
    public  ArrayList<String> lista1;
    RequestQueue request;
    public void inicio(Context context)
    {
        Context context1=context;
        request= Volley.newRequestQueue(context1);
        try {
            conexion();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    //en este metodo se manda la informacion que se va a guardar en la base de datos
    public void conexion() throws UnsupportedEncodingException {
        for (int x = 0; x < lista1.size(); x++) {
            //esta es la url que se va a enviar al servicio web de php
            String URL = datos_prim.direccion + "/android/push.php?latitud=" + latitu + "&longitud=" + longitud + "&direccion=" + URLEncoder.encode(direccion, "UTF-8") + "&registration_ids=" + lista1.get(x).toString();
            //lo siguiente hace un remplazo de los caractareres especificos
            URL = URL.replace(" ", "%20");
            URL = URL.replace("[", "");
            URL = URL.replace("]", "");
            URL = URL.replace("\"", "");
            Log.e("valor", "" + URLEncoder.encode(direccion, "UTF-8"));
            JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, URL, null,//se crea una respuesta paraenviar
                    new Response.Listener <JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (null != error.networkResponse) {
                                Log.e("error" + ": ", "Error Response code: " + error.networkResponse.statusCode);
                            }
                        }
                    });
            request.add(getRequest);
        }
    }
}
