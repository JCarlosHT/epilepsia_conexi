package com.example.juan.epilepsia.ventanas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.juan.epilepsia.R;

public class bitacora extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitacora);
    }
    public void inicio1(View view) {
        Intent i = new Intent(this,menu_epi.class);
        startActivity(i);
    }

    public void repo1(View view) {
       /* Intent r = new Intent(this,reporte.class);
        startActivity(r);*/
    }

    public void alar1(View view) {
        Intent alarma = new Intent(this,recordatorio.class);
        startActivity(alarma);
    }
}
