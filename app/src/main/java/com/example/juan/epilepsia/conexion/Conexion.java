package com.example.juan.epilepsia.conexion;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.juan.epilepsia.datos_contacto;
import com.example.juan.epilepsia.datos_prim;
import com.example.juan.epilepsia.sqlite.ConexionSQLite;
import org.json.JSONObject;
import java.util.ArrayList;

public class Conexion {

    ArrayList<String> lista1;
    RequestQueue request;
    public void inicio(Context context)
    {
        Context context1=context;
        request= Volley.newRequestQueue(context1);
        datos_contacto.sqLite= new ConexionSQLite(context1);
        lista1=this.viewsqlite();
        conexion();
    }

    private ArrayList viewsqlite(){
        String sql="select * from Contacto ";
        return datos_contacto.sqLite.llenarlista(sql);
    }

    public void conexion() {
        for (int x = 0; x < lista1.size(); x++) {
            String URL = datos_prim.direccion + "/android/push.php?registration_ids=" + lista1.get(x).toString();
            URL = URL.replace(" ", "%20");
            URL = URL.replace("[", "");
            URL = URL.replace("]", "");
            URL = URL.replace("\"", "");

            Log.e("valor", "" + URL);
            JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, URL, null,
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
