package com.example.juan.epilepsia.Reporte;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.juan.epilepsia.R;
import com.example.juan.epilepsia.recordatorio.recordatorio;
import com.example.juan.epilepsia.ventanas.bitacora;
import com.example.juan.epilepsia.ventanas.menu_epi;

public class reporte extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte);
    }
     public void inicio3(View view) {
        Intent i = new Intent(this,menu_epi.class);
        startActivity(i);
    }

    public void rec3(View view) {
        Intent r = new Intent(this,recordatorio.class);
        startActivity(r);
    }

    public void bita3(View view) {
        Intent b = new Intent(this,bitacora.class);
        startActivity(b);
    }
}
