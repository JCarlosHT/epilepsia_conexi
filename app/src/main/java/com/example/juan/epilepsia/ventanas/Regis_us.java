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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.juan.epilepsia.datos_prim;
import com.example.juan.epilepsia.sqlite.ConexionSQLite;
import com.example.juan.epilepsia.R;

import org.json.JSONArray;

import static android.widget.Toast.LENGTH_LONG;

public class Regis_us extends AppCompatActivity {
    private EditText nombre,apelli_p,apelli_mat,correo,contra,nombre_usu;
    public datos_prim datosPrim=new datos_prim();
    RequestQueue request;
    int re;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis_us);
        request= Volley.newRequestQueue(this);
        ini();
    }

    private void ini(){
        nombre=(EditText) findViewById(R.id.editText);
        apelli_p=(EditText) findViewById(R.id.editText2);
        apelli_mat=(EditText) findViewById(R.id.Apell_mate);
        contra=(EditText) findViewById(R.id.editText4);
        nombre_usu=(EditText) findViewById(R.id.editText5);
        correo=(EditText) findViewById(R.id.editText3);
    }

    public void Per_me(View view){
        datosPrim.setNombre(nombre.getText().toString());
        datosPrim.setApelli_pater(apelli_p.getText().toString());
        datosPrim.setApelli_mater(apelli_mat.getText().toString());
        datosPrim.setCorreo(correo.getText().toString());
        datosPrim.setContra(contra.getText().toString());
        datos_prim.Us_nom=nombre_usu.getText().toString();
        Bundle datos = this.getIntent().getExtras();
        re=datos.getInt("Parametro");
        vacios(re);
    }

    private void vacios(int re){
        if (TextUtils.isEmpty(datosPrim.getNombre())|| TextUtils.isEmpty(datosPrim.getApelli_pater())||TextUtils.isEmpty(datosPrim.getApelli_mater())||TextUtils.isEmpty(datosPrim.getCorreo())||TextUtils.isEmpty(datos_prim.Us_nom)||TextUtils.isEmpty(datosPrim.getContra())){
            Toast.makeText(this,"Complete todos los datos",Toast.LENGTH_SHORT).show();
        }else
            if (re==2){
            Intent Per =new Intent(this,Perfil_medico.class);
            Bundle bundle=new Bundle();
            bundle.putSerializable("datos_pri",datosPrim);
            Per.putExtras(bundle);
            startActivity(Per);

        }else{
            conexion();

        }

    }

    public void conexion(){
        datosPrim.sqLite=new ConexionSQLite(this);                                                                                                                                            //
        String URL=datos_prim.direccion+"/android/insertar.php?opc=contacto&Nombre="+datosPrim.getNombre()+"&Apell_pater="+datosPrim.getApelli_pater()+"&Apell_mater="+datosPrim.getApelli_mater()+"&Correo="+datosPrim.getCorreo()+"&Contra="+datosPrim.getContra()+"&nombre_usu="+datos_prim.Us_nom+"&Token="+datos_prim.token+"";
        URL=URL.replace(" ","%20");
        // prepare the Request
        JsonArrayRequest getRequest  = new JsonArrayRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response) {
                        boolean o=addusuario();
                        if (o){
                            Toast.makeText(getApplicationContext(), "registro completo", LENGTH_LONG).show();
                            Intent menu = new Intent(getApplicationContext(),menu_epi.class);
                            menu.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(menu);
                        }else
                        {
                            Log.e("error",String.valueOf(o));
                        }
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
        request.add(getRequest);
    }

    private boolean addusuario(){
        String sql="insert into usuario values('1','"+datosPrim.getNombre()+"','"+datosPrim.getApelli_pater()+"','"+datosPrim.getApelli_mater()+"','"+datosPrim.getCorreo()+"','"+datos_prim.Us_nom+"','"+datosPrim.getContra()+"','"+datosPrim.token+"');";
        return datosPrim.sqLite.ejecutaSQL(sql);
    }





}
