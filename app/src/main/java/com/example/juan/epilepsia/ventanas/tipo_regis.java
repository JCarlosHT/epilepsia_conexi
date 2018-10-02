package com.example.juan.epilepsia.ventanas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.juan.epilepsia.R;

public class tipo_regis extends AppCompatActivity {
     RadioButton familiar,usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_regis);
      familiar=(RadioButton)findViewById(R.id.radio1);
      usuario=(RadioButton)findViewById(R.id.radio2);
    }
    public void onClick(View view){
        if(view.getId()==R.id.Continuar)
        {
            Validar();
        }
    }
    public void Validar()
    {
        if(familiar.isChecked()==true)
        {
            Intent R_u = new Intent(this,Regis_us.class);
            R_u.putExtra("Parametro",1);
            startActivity(R_u);

        }else if (usuario.isChecked()==true)
        {
            Intent R_u = new Intent(this,Regis_us.class);
            R_u.putExtra("Parametro",2);
            startActivity(R_u);
        }else{
            Toast toast1 = Toast.makeText(getApplicationContext(), "Seleccione una opción", Toast.LENGTH_SHORT);
            toast1.show();
        }
    }
}
