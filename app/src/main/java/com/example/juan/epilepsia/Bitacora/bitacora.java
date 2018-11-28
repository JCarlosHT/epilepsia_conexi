package com.example.juan.epilepsia.Bitacora;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.juan.epilepsia.R;
import com.example.juan.epilepsia.Reporte.reporte;
import com.example.juan.epilepsia.datos_contacto;
import com.example.juan.epilepsia.recordatorio.recordatorio;
import com.example.juan.epilepsia.sqlite.ConexionSQLite;
import com.example.juan.epilepsia.ventanas.menu_epi;

import java.util.ArrayList;

public class bitacora extends AppCompatActivity {
    ListView listView;
    EditText text,text2;
    ArrayList<String>lista_recor;
    Spinner spi,pi;
    String spiner1,spiner2;
    String[] SPiner_ata_gra = new String[] {"Bajo", "Medio", "Alto"};
    String[] SPiner_tipo = new String[] {"generalizadas", "parciales"};
    String indice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitacora);
        spi=(Spinner)findViewById(R.id.spinner);
        pi=(Spinner)findViewById(R.id.tipo_ata);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, SPiner_ata_gra);
        spi.setAdapter(adapter);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, SPiner_tipo);
        pi.setAdapter(adapter);
        datos_contacto.sqLite= new ConexionSQLite(this);
        listView=(ListView) findViewById(R.id.listview_bitaco);
        carga();
        indice=  lista_recor.get(0).split(" ")[0];
        Log.e("lista",""+indice);
    }

    private void carga(){//en este metodo se optiene y se carga el sistema en pantalla
        lista_recor=this.viewsqlite();//se manda a llamar el metodo para agregar los datos a la lista
        ArrayAdapter<String> adapter=new ArrayAdapter <String>(this,android.R.layout.simple_list_item_1,lista_recor);
        listView.setAdapter(adapter);
    }

    private ArrayList<String> viewsqlite(){
        //se crea las intucciones sql y se mandar para ser ejecutadas
        //SELECT * FROM Recordatorio ORDER BY id DESC LIMIT 1;
        String sql="SELECT * FROM Alarma ORDER BY id DESC LIMIT 1";
         return datos_contacto.sqLite.llenarlista_alar(sql, 3);//se deside emplear la segunda para no invertir tanto codigo
    }

    public void inicio1(View view) {
        Intent i = new Intent(this,menu_epi.class);
        startActivity(i);
    }

    public void repo1(View view) {
        Intent r = new Intent(this,reporte.class);
        startActivity(r);
    }

    public void alar1(View view) {
        Intent alarma = new Intent(this,recordatorio.class);
        startActivity(alarma);
    }

    public void agregar_d(View view) {
        spi=(Spinner)findViewById(R.id.spinner);
        pi=(Spinner)findViewById(R.id.tipo_ata);
        text=(EditText)findViewById(R.id.Sintomas__IR);
        text2=(EditText)findViewById(R.id.Otro);
        if(TextUtils.isEmpty(spi.getSelectedItem().toString())&&TextUtils.isEmpty(pi.getSelectedItem().toString())&&TextUtils.isEmpty(text.getText())&&TextUtils.isEmpty(text.getText())){
            Toast.makeText(this,"faltan campos",Toast.LENGTH_LONG);
        }else {
            spiner1=spi.getSelectedItem().toString();
            spiner2=pi.getSelectedItem().toString();
            updateusu();
        }
    }

    private boolean updateusu(){
        String sql="Update Alarma set sin_previos='"+text.getText().toString()+"',ti_ataque='"+spiner2+"',grad_ata='"+spiner1+"',otros='"+text.getText().toString()+"'where id='"+indice+"';";
        Log.e("valor","cadena: "+sql);
        return datos_contacto.sqLite.ejecutaSQL(sql);
    }
}
