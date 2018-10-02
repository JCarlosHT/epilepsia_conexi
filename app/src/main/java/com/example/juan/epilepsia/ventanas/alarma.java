package com.example.juan.epilepsia.ventanas;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.juan.epilepsia.R;
import com.example.juan.epilepsia.datos_contacto;
import com.example.juan.epilepsia.datos_prim;
import com.example.juan.epilepsia.sqlite.ConexionSQLite;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_LONG;

public class alarma extends AppCompatActivity {
    ListView lv;
    ArrayList<String> lista1;
    ArrayAdapter adaptador;
    RequestQueue request;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarma);
        lv=(ListView)findViewById(R.id.lista);
        request=Volley.newRequestQueue(getBaseContext());
        datos_contacto.sqLite= new ConexionSQLite(this);
        lista1=this.viewsqlite();
        adaptador=new ArrayAdapter(this, android.R.layout.simple_list_item_1,lista1);
        lv.setAdapter(adaptador);
        conexion();
    }
    private ArrayList viewsqlite(){
        String sql="select * from Contacto ";
        return datos_contacto.sqLite.llenarlista(sql);
    }

    public void conexion(){
        for(int x=0;x<lista1.size();x++) {
            Log.e("valor",""+lista1.get(x).toString());
            String URL=datos_prim.direccion+"/android/push.php?registration_ids="+lista1.get(x).toString();
            URL=URL.replace(" ","%20");
            URL=URL.replace("[","");
            URL=URL.replace("]","");
            URL=URL.replace("\"","");

            Log.e("valor",""+URL);
            JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, URL, null,
                    new Response.Listener<JSONObject>()
                    {
                        @Override
                        public void onResponse(JSONObject response)
                        {
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            if (null != error.networkResponse)
                            {
                                Log.e("error" + ": ", "Error Response code: " + error.networkResponse.statusCode);
                            }
                        }
                    });
            request.add(getRequest);
        }
        // prepare the Request
    /*JsonArrayRequest getRequest  = new JsonArrayRequest(Request.Method.GET, URL, null,
            new Response.Listener<JSONArray>()
            {
                @Override
                public void onResponse(JSONArray response) {
                    startActivity(new Intent(getBaseContext(),menu_epi.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));

                }
            },
            new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("error al registrar", error.toString());

                }
            }
    );
    request.add(getRequest);*/
    }
}
