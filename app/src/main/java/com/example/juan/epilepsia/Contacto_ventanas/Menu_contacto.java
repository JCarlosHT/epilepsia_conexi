package com.example.juan.epilepsia.Contacto_ventanas;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.juan.epilepsia.R;
import com.example.juan.epilepsia.datos_contacto;
import com.example.juan.epilepsia.sqlite.ConexionSQLite;
import com.example.juan.epilepsia.ventanas.mapa_ubicacion;

import java.util.ArrayList;

public class Menu_contacto extends AppCompatActivity {
    private TextView[]datos1=new TextView[3];
    datos_contacto contacto_user;
    ArrayList<String> lista1;
    String []datos_aler=new String[6];
    int bandera;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_contacto);
        contacto_user=new datos_contacto();
        datos_contacto.sqLite= new ConexionSQLite(this);
        Bundle datos = this.getIntent().getExtras();
        if(datos != null) {
            bandera=datos.getInt("bande");//se resibe una bandera de otro intent
        }
        datos1[0]=  findViewById(R.id.Nombre_ini_contac);///en el siguiente se incian las variables del sistema
        datos1[1]=  findViewById(R.id.Nombre_usu_contac);//para pintar los text file quiere decir que entra a pintar
        datos1[2]=  findViewById(R.id.Correo_contac);
        lista1=this.viewsqlite();
        pintar();
        if (bandera==1){
             datos_aler=datos.getStringArray("dato_alerta");
             dialog=new Dialog(this);
             disparo();
        }
    }

    private void disparo() {
        TextView close ;
        TextView [] alerta=new TextView[4];
        Button mapa;
        int o=0;
        dialog.setContentView(R.layout.activity_alerta_repo);
        alerta[0]=(TextView) dialog.findViewById(R.id.ubicacion_aler);
        alerta[1]=(TextView) dialog.findViewById(R.id.usuario_aler);
        alerta[2]=(TextView) dialog.findViewById(R.id.tipo_s_aler);
        alerta[3]=(TextView) dialog.findViewById(R.id.hospi_aler);
        close=(TextView) dialog.findViewById(R.id.cerrar);
        mapa=(Button) dialog.findViewById(R.id.Generar_mapa);
        for (int i=2;i<6;i++){
            alerta[o].setText(datos_aler[i]);
            o++;
        }
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("entro","aqui");
                Intent i=new Intent(getBaseContext(),mapa_ubicacion.class);
                i.putExtra("latitud",datos_aler[0]);
                i.putExtra("longitud",datos_aler[1]);
                i.putExtra("direc",datos_aler[2]);
                startActivity(i);
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private ArrayList viewsqlite(){
        String sql="select * from Contacto_da";//se crea la instruccion sql para poder consultar todos los registros de usuario
        return datos_contacto.sqLite.llenarlista(sql,6);
    }

    private void pintar(){
        for (int i=0;i<3;i++){
            datos1[i].setText(lista1.get(i).toString());//con la lista retornada se puede pintar los datos
        }
    }
}
