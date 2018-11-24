package com.example.juan.epilepsia.ventanas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.juan.epilepsia.Alergias;
import com.example.juan.epilepsia.datos_prim;
import com.example.juan.epilepsia.sqlite.ConexionSQLite;
import com.example.juan.epilepsia.R;

import org.json.JSONArray;

import static android.widget.Toast.LENGTH_LONG;

public class Perfil_medico extends AppCompatActivity{
    CheckBox alergia,enfer;
    EditText edad,sangre,hospital;
    RadioButton m,h;
    public datos_prim datosPrim;
    RequestQueue request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_medico);
        Bundle datos_prim_envi=getIntent().getExtras();
        if(datos_prim_envi!=null)
        {
            datosPrim=(datos_prim) datos_prim_envi.getSerializable("datos_pri");
            request=Volley.newRequestQueue(getBaseContext());
            ini();
        }

    }

    private boolean addusuario(){
        String sql="insert into usuario values('1','"+datosPrim.getNombre()+"','"+datosPrim.getApelli_pater()+"','"+datosPrim.getApelli_mater()+"','"+datosPrim.getCorreo()+"','"+datosPrim.getContra()+"','"+datos_prim.Us_nom+"','"+datosPrim.token+"');";
        datosPrim.sqLite.ejecutaSQL(sql);
        sql="insert into Datos_medi(Edad,sexo,tipo_san,hospital_pre) values('"+datosPrim.getEdad()+"','"+datosPrim.getSexo()+"','"+datosPrim.getSangre()+"','"+datosPrim.getHospital()+"');";
        return datosPrim.sqLite.ejecutaSQL(sql);
    }

    public void conexion(){
        Log.e("token ", datos_prim.token);
        String URL=datos_prim.direccion+"/android/insertar.php?opc=use_epilep&Nombre="+datosPrim.getNombre()+"&Apell_pater="+datosPrim.getApelli_pater()+"&Apell_mater="+datosPrim.getApelli_mater()+"&Correo="+datosPrim.getCorreo()+"&Contra="+datos_prim.Us_nom+"&nombre_usu="+datosPrim.getContra()+"&Token="+datos_prim.token+"&Edad="+datosPrim.getEdad()+"&sexo="+datosPrim.getSexo()+"&Ti_sangre="+datosPrim.getSangre()+"&Hospital_pref="+datosPrim.getHospital()+" ";
        URL=URL.replace(" ","%20");
        Log.e("datos",""+URL);
        // prepare the Request
        JsonArrayRequest getRequest  = new JsonArrayRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response) {
                        boolean o=addusuario();//metodo implemtado para agregar a la base de datos sqlite
                        if (o){
                            Toast.makeText(getApplicationContext(), "registro completo", LENGTH_LONG).show();
                            startActivity(new Intent(getBaseContext(),menu_epi.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                        }else
                        {
                            Log.e("falla",String.valueOf(o));
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
    }//se implementa la libreria de volley donde se manda al servidor

    public void Contin(View view){
            if (view.getId()==R.id.Continuar){
                String s=sangre.getText().toString();
                String a=alergia.getText().toString();
                String e=enfer.getText().toString();
                int ed=Integer.parseInt(edad.getText().toString());
                String h=hospital.getText().toString();
                vacio(s,ed,h);
                consultar();
            }
    }

    //se verifica si se marcaron las opciones de alergias para agregar al sistema
    private void consultar(){
        if (alergia.isChecked()==true && enfer.isChecked()==true){
            Intent intent = new Intent(this,Alergias.class);
            intent.putExtra("Parametro",1);
            startActivity(intent);

        }else if (alergia.isChecked()==true&& enfer.isChecked()==false){
            Intent intent = new Intent(this,Alergias.class);
            intent.putExtra("Parametro",2);
            startActivity(intent);
        }else if (alergia.isChecked()==false&& enfer.isChecked()==true){
            Intent intent = new Intent(this,Alergias.class);
            intent.putExtra("Parametro",3);
            startActivity(intent);
        }
    }
    //se rellena los datos de la interface
    private void ini(){//se almasena la informacion de la interface
        alergia=(CheckBox) findViewById(R.id.checkBox3);
        enfer=(CheckBox) findViewById(R.id.checkBox5);
        m=(RadioButton) findViewById(R.id.radioButton3);
        h=(RadioButton) findViewById(R.id.radioButton2);
        edad=(EditText) findViewById(R.id.editText7);
        sangre=(EditText) findViewById(R.id.editText6);
        hospital=(EditText)findViewById(R.id.editText12);
    }
    //verifican que no esten vacios los datos
    private void vacio(String sangre, int edad, String hospital){
            if (TextUtils.isEmpty(sangre)&&edad!=0&&TextUtils.isEmpty(hospital)&&(m.isChecked()!=false&&h.isChecked()!=false)){
                Toast.makeText(this,"Faltan datos necesarios",Toast.LENGTH_SHORT).show();
            }else {
                String s;
                s=(h.isChecked()==true)?"M":"F";
                datosPrim.setEdad(edad);
                datosPrim.setSangre(sangre);
                datosPrim.setHospital(hospital);
                datosPrim.setSexo(s);
                conexion();
            }
    }



}
