package com.example.juan.epilepsia.ventanas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.example.juan.epilepsia.datos_alerta_repo;

import com.example.juan.epilepsia.R;
import com.example.juan.epilepsia.datos_contacto;
import com.example.juan.epilepsia.sqlite.ConexionSQLite;

import java.util.ArrayList;

public class Alerta_repo extends AppCompatActivity {
    private TextView datos1;
    ArrayList<String> lista1;
    datos_contacto contacto_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        datos1=findViewById(R.id.ubica);
        datos_contacto.sqLite= new ConexionSQLite(this);
        lista1=this.viewsqlite();//se rellena la lista
        if (lista1!=null){
            Log.e("jfjfjf",""+lista1.get(0).toString());//con la lista retornada se puede pintar los datos
        }
     }

    private ArrayList viewsqlite(){
        String sql="select * from Datos_medi";//se crea la instruccion y se retorna una lista
        return datos_contacto.sqLite.llenarlista(sql,4);
    }

}
