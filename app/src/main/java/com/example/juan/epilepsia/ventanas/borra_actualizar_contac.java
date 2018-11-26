package com.example.juan.epilepsia.ventanas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.juan.epilepsia.R;
import com.example.juan.epilepsia.datos_contacto;
import com.example.juan.epilepsia.ventanas.alarma;

import static android.widget.Toast.LENGTH_LONG;

public class borra_actualizar_contac extends AppCompatActivity {
    int id;
    private EditText[]datos1=new EditText[3];
    String []datos=new String[3];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borra_actualizar_contac);
        Bundle b=getIntent().getExtras();
        if (b!=null){
            id=b.getInt("Id");
            datos[0]=b.getString("Nombre") ;
            datos[1]=b.getString("Apellido") ;
            datos[2]=b.getString("Relacion") ;
        }
        datos1[0]= (EditText) findViewById(R.id.Nombre_contac);
        datos1[1]= (EditText) findViewById(R.id.Apellido_con);
        datos1[2]= (EditText) findViewById(R.id.Relacion_cont);
        for (int i=0;i<3;i++){
            datos1[i].setText(datos[i]);
        }
    }

    public void Modificar(View view){
        for (int i=0;i<3;i++){
            datos[i]=datos1[i].getText().toString();
        }
        if ((datos[0].equals(""))||(datos[1].equals(""))||(datos[2].equals(""))){
            Toast.makeText(getApplicationContext(), "faltan datos", LENGTH_LONG).show();
        }else
        {
            if(updateusu()){
                Toast.makeText(getApplicationContext(), "Contacto Modificado", LENGTH_LONG).show();
                Intent menu = new Intent(getApplicationContext(),alarma.class);
                menu.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(menu);
            }else
            {
                Toast.makeText(getApplicationContext(), "Se produjo un error", LENGTH_LONG).show();
            }
        }
    }

    public void Borrar(View view){
        if (Borrar()){
            Toast.makeText(getApplicationContext(), "Contacto Eliminado", LENGTH_LONG).show();
            Intent menu = new Intent(getApplicationContext(),alarma.class);
            menu.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(menu);
        }else
        {
            Toast.makeText(getApplicationContext(), "Se produjo un error", LENGTH_LONG).show();
        }
    }

    private boolean Borrar(){
        String sql="delete from Contacto where id='"+id+"';";
        Log.e("valor","cadena: "+sql);
        return datos_contacto.sqLite.ejecutaSQL(sql);
    }

    private boolean updateusu(){
        String sql="Update Contacto set nombre='"+datos1[0].getText().toString()+"',apellido='"+datos1[1].getText().toString()+"',relacion='"+datos1[2].getText().toString()+
                "'where id='"+id+"';";
        Log.e("valor","cadena: "+sql);
        return datos_contacto.sqLite.ejecutaSQL(sql);
    }
}
