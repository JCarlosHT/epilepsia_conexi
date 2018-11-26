package com.example.juan.epilepsia.ventanas;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.juan.epilepsia.R;
import com.example.juan.epilepsia.Reporte.Service_notifi;
import com.example.juan.epilepsia.datos_contacto;
import com.example.juan.epilepsia.datos_prim;
import com.example.juan.epilepsia.recordatorio.recordatorio;
import com.example.juan.epilepsia.sqlite.ConexionSQLite;


import java.util.ArrayList;

public class menu_epi extends AppCompatActivity {
    private TextView[]datos1=new TextView[3];//un arreglo que se ocupa para los textos de la interface
    ArrayList<String> lista1;
    datos_contacto contacto_user;
    int bande=0;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_epi);
        Bundle datos = this.getIntent().getExtras();
        if(datos != null) {
            bande=datos.getInt("bande");//se resibe una bandera de otro intent
        }
        startService(new Intent(getBaseContext(), Service_notifi.class));
        datos1[0]=  findViewById(R.id.Nombre_ini);///en el siguiente se incian las variables del sistema
        datos1[1]=  findViewById(R.id.Nombre_usu_ini);//para pintar los text file quiere decir que entra a pintar
        datos1[2]=  findViewById(R.id.Correo_ini);
        contacto_user=new datos_contacto();
        datos_contacto.sqLite= new ConexionSQLite(this);
        lista1=this.viewsqlite();
        pintar();
         if(bande==1){
            Intent r = new Intent(this,cordenadas.class);
            r.putExtra("bande",1);
            startActivity(r);
            /*dialog=new Dialog(this);
            r = new Intent(this,Alerta_repo.class);
            startActivity(r);
            disparo();*/
         }
    }

  /*  private void disparo() {
        TextView close;

        dialog.setContentView(R.layout.activity_alerta_repo);
        close=(TextView) dialog.findViewById(R.id.cerrar);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }*/

    private ArrayList viewsqlite(){
        String sql="select * from usuario";//se crea la instruccion sql para poder consultar todos los registros de usuario
        return datos_contacto.sqLite.llenarlista(sql,3);
    }

    private void pintar(){
        for (int i=0;i<3;i++){
            datos1[i].setText(lista1.get(i).toString());//con la lista retornada se puede pintar los datos
        }
        datos_prim.Us_nom=lista1.get(1).toString();
    }

    public void continuar(View view){
        Intent r = new Intent(getApplicationContext(),alarma.class);
        startActivity(r);
    }

    public void bita(View view) {
        Intent bitacora = new Intent(this,bitacora.class);
        startActivity(bitacora);
    }

    public void repo(View view) {
        Intent reporte = new Intent(this, com.example.juan.epilepsia.Reporte.reporte.class);
        startActivity(reporte);
    }

    public void alar(View view) {
        Intent a = new Intent(this,recordatorio.class);
        startActivity(a);
    }

}
