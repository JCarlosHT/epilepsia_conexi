package com.example.juan.epilepsia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Alergias extends AppCompatActivity {
     EditText alergia, enferme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alergias);
        Bundle datos = this.getIntent().getExtras();
        int re=datos.getInt("Parametro");
        if (re==2){
              enferme = (EditText) findViewById(R.id.editText9);
              enferme.setVisibility(View.INVISIBLE);
        } else if(re==3){
            alergia =(EditText) findViewById(R.id.editText8);
            alergia.setVisibility(View.INVISIBLE);

        }

    }
    public void valores(int i){

    }
}
