package com.example.juan.epilepsia.ventanas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.juan.epilepsia.R;

public class recordatorio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordatorio);
    }

    public void repo2(View view) {
        Intent rep = new Intent(this,reporte.class);
        startActivity(rep);
    }

    public void bita2(View view) {
        Intent bita = new Intent(this,bitacora.class);
        startActivity(bita);
    }

    public void inicio2(View view) {
        Intent ini = new Intent(this,menu_epi.class);
        startActivity(ini);
    }
}
