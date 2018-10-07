package com.example.juan.epilepsia.ventanas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.juan.epilepsia.R;
import com.example.juan.epilepsia.conexion.Conexion;
import com.example.juan.epilepsia.datos_contacto;
import com.example.juan.epilepsia.datos_prim;
import com.example.juan.epilepsia.sqlite.ConexionSQLite;

import org.json.JSONArray;
import org.json.JSONException;


public class alarma extends AppCompatActivity {
    private EditText use,cor;
    private String use_con,correo;
    RequestQueue request;
    datos_contacto contacto_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarma);
        request= Volley.newRequestQueue(getApplicationContext());
        contacto_user=new datos_contacto();
    }

    public void insert(View view){
        use=findViewById(R.id.contacto_nom);
        cor=findViewById(R.id.correo_contac);
        use_con=use.getText().toString();
        correo=cor.getText().toString();
        vacio(use_con,correo);
    }

    private void vacio(String use_co,String corre){
        if (TextUtils.isEmpty(use_co)&&TextUtils.isEmpty(corre)){
            Toast.makeText(this,"Faltan datos necesarios",Toast.LENGTH_SHORT).show();
        }else {
            conexion();
        }
    }

    private boolean addusuario(){
        String sql="insert into Contacto (nombre_cont,correo,token)values('"+use_con+"','"+correo+"','"+datos_contacto.token+"');";
        Log.e("valor","completado ");
        return datos_contacto.sqLite.ejecutaSQL(sql);
    }

    public void conexion(){
        datos_contacto.sqLite=new ConexionSQLite(this);
        Log.e("valor",""+use_con);
        Log.e("valor",""+correo);
        String URL= datos_prim.direccion+"/android/consulta.php?nombre="+use_con+"&correo="+correo;
        URL=URL.replace(" ","%20");
        // prepare the Request
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.length()>0&&!(response.equals(null))){
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.e("valor",""+ja.toString());
                        datos_contacto.token=ja.toString();
                        boolean o=addusuario();
                        Log.e("valor",""+o);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("valor","error");
                    }
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);
    }

    public void notifi(View view){
        Intent r = new Intent(this,cordenadas.class);
        startActivity(r);
    }
}
