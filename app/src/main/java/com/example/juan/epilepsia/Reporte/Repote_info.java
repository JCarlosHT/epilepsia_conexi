package com.example.juan.epilepsia.Reporte;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juan.epilepsia.Bitacora.bitacora;
import com.example.juan.epilepsia.R;
import com.example.juan.epilepsia.datos_contacto;
import com.example.juan.epilepsia.ventanas.alarma;
import com.example.juan.epilepsia.ventanas.menu_epi;

import java.util.ArrayList;

public class Repote_info extends AppCompatActivity {
    ArrayList<String> lista_recor;
    TextView direccion, hora, fecha;
    EditText sinto,otro;
    Spinner tipo_ata,grado_ata;
    String tipo_ata1,grado_ata2;
    public String indice;
    String[] SPiner_ata_gra = new String[] {"Bajo", "Medio", "Alto"};
    String[] SPiner_tipo = new String[] {"generalizadas", "parciales"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repote_info);
        lista_recor=getIntent().getStringArrayListExtra("datos_alertas");
        indice=getIntent().getExtras().getString("indice");
        grado_ata=(Spinner)findViewById(R.id.spinner);
        tipo_ata=(Spinner)findViewById(R.id.tipo_ata);
        direccion=(TextView) findViewById(R.id.Direccion_IR);
        hora=(TextView) findViewById(R.id.Hora_IR);
        fecha=(TextView) findViewById(R.id.Fecha_IR);
        sinto=(EditText) findViewById(R.id.Sintomas__IR);
        otro=(EditText) findViewById(R.id.otros_IR);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, SPiner_ata_gra);
        grado_ata.setAdapter(adapter);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, SPiner_tipo);
        tipo_ata.setAdapter(adapter);
        pintar();
     }

    private void pintar() {
        direccion.setText(lista_recor.get(0).toString());
        sinto.setText(lista_recor.get(1).toString());
        fecha.setText(lista_recor.get(4).toString());
        hora.setText(lista_recor.get(5).toString());
        otro.setText(lista_recor.get(6).toString());
    }

    public void Editar_reporte(View view) {
        if(TextUtils.isEmpty(tipo_ata.getSelectedItem().toString())&&TextUtils.isEmpty(grado_ata.getSelectedItem().toString())&&TextUtils.isEmpty(sinto.getText())&&TextUtils.isEmpty(otro.getText())){
            Toast.makeText(this,"faltan campos",Toast.LENGTH_LONG);
        }else {
            tipo_ata1=tipo_ata.getSelectedItem().toString();
            grado_ata2=grado_ata.getSelectedItem().toString();
            if (updateusu()){
                Intent r = new Intent(getApplicationContext(),reporte.class);
                startActivity(r);
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
                finish();
            }
        }
    }

    private boolean updateusu(){
        String sql="Update Alarma set sin_previos='"+sinto.getText().toString()+"',ti_ataque='"+tipo_ata1+"',grad_ata='"+grado_ata2+"',otros='"+otro.getText().toString()+"'where id='"+indice+"';";
        Log.e("valor","cadena: "+sql);
        return datos_contacto.sqLite.ejecutaSQL(sql);
    }

    public void repo_EB(View view) {
        Intent rep = new Intent(this,reporte.class);
        startActivity(rep);
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
        finish();
    }

    public void Bita_EB(View view) {
        Intent bita = new Intent(this,bitacora.class);
        startActivity(bita);
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
        finish();
    }

    public void inicio2(View view) {
        Intent ini = new Intent(this,menu_epi.class);
        startActivity(ini);
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
        finish();
    }

    public void Alarma_IR(View view) {
        Intent ini = new Intent(this,alarma.class);
        startActivity(ini);
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
        finish();
    }
}
