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
    public static String latitu1;
    ArrayList<String> lista1;
    datos_contacto contacto_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerta_repo);

     }
}
