package com.example.juan.epilepsia.conexion_server;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.juan.epilepsia.datos_contacto;
import com.example.juan.epilepsia.datos_prim;
import com.example.juan.epilepsia.recordatorio.recordatorio;
import com.example.juan.epilepsia.sqlite.ConexionSQLite;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;

public class Conexion  {
    ////crear una tabla para poder guardar las alarmas SELECT * FROM tablename ORDER BY columna DESC LIMIT 1;
    //crear un nuevo registro de contactos bueno un tabla para poder comparar y saber a que inten inicar
    public String latitu, longitud,direccion;
    public  ArrayList<String> lista1;
    public int[]datos_fecha_hor=new int [5];
    public String fecha, hora;
    RequestQueue request;
    Context context1;
    public static int bandera=0;
    public void inicio(Context context){
        context1=context;
        request= Volley.newRequestQueue(context1);
        try {
            Notificacion_de_alarma();
            if(bandera==2){
                registro_reporte_ba();
                datos_contacto.sqLite= new ConexionSQLite(context);
                if(addrecor()){
                    Log.e("reco","Alarma agregado");
                }

            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    //en este metodo se manda la informacion que se va a guardar en la base de datos
    public void Notificacion_de_alarma() throws UnsupportedEncodingException {
        for (int x = 0; x < lista1.size(); x++) {
            //esta es la url que se va a enviar al servicio web de php
            String URL = datos_prim.direccion + "/android/push.php?latitud=" + latitu + "&longitud=" + longitud + "&direccion=" + URLEncoder.encode(direccion, "UTF-8") + "&registration_ids=" + lista1.get(x).toString()+"&Nombre="+datos_prim.Us_nom+"&Ti_sangre=o positivo&Hospital_pref=imms";
            //lo siguiente hace un remplazo de los caractareres especificos
            URL = URL.replace(" ", "%20");
            URL = URL.replace("[", "");
            URL = URL.replace("]", "");
            URL = URL.replace("\"", "");
            Log.e("valor", "" + URL);
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

    private void registro_reporte_ba() throws UnsupportedEncodingException {
        extracion_ho_fech();
        String URL = datos_prim.direccion + "/android/Reporte.php?nombre_usu="+ datos_prim.Us_nom +"&&ubicacion="+URLEncoder.encode(direccion, "UTF-8")+"&&fecha="+fecha+"&&hora="+hora;
        //lo siguiente hace un remplazo de los caractareres especificos
        URL = URL.replace(" ", "%20");

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

    private void extracion_ho_fech() {
        final Calendar calen=Calendar.getInstance();
        datos_fecha_hor[0]=calen.get(Calendar.DAY_OF_MONTH);
        datos_fecha_hor[1]=calen.get(Calendar.MONTH)+1;
        datos_fecha_hor[2]=calen.get(Calendar.YEAR);
        fecha=datos_fecha_hor[2]+"/"+datos_fecha_hor[1]+"/"+datos_fecha_hor[0];
        datos_fecha_hor[3]=calen.get(Calendar.HOUR_OF_DAY);
        datos_fecha_hor[4]=calen.get(Calendar.MINUTE);
        hora=datos_fecha_hor[3]+":"+datos_fecha_hor[4]+":00";
    }

    private boolean addrecor(){
        String sql="insert into Alarma(direccion,sin_previos,ti_ataque,grad_ata,fecha,hora,otros)values" +
                                "('"+direccion+"','sinromas previos','tipo de ataque','grado de ataque','"+fecha+"','"+hora+"','otros datos');";
        Log.e("valor","cadena: "+sql);
        return datos_contacto.sqLite.ejecutaSQL(sql);
    }

}
