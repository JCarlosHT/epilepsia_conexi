package com.example.juan.epilepsia.ventanas;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.juan.epilepsia.datos_prim;
import com.example.juan.epilepsia.sqlite.ConexionSQLite;
import com.example.juan.epilepsia.R;

public class login extends AppCompatActivity {
    private EditText NombreUsu;
    private EditText Contraseña;
    private Switch Recordar;
    private Button btnLogin;
    private Button Registro;
    NotificationCompat.Builder nota;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        datos_prim.sqLite= new ConexionSQLite(this);
        datos_prim.cursor=this.viewsqlite();
        int layout= R.layout.activity_login;
        if(datos_prim.cursor.getCount()>0){
            startActivity(new Intent(getBaseContext(),menu_epi.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
        }
        setContentView(layout);
        inicio();
    }

    private Cursor viewsqlite(){
        String sql="select * from usuario limit 1";
        return datos_prim.sqLite.consultaSQL(sql);
    }

    public void ini_se(View view){
        String Contra=Contraseña.getText().toString();
        login(Contra);
    }

    public void Regis(View view){
        reg();
    }

    private void inicio (){
        NombreUsu= (EditText)  findViewById(R.id.NombreUsu);
        Contraseña= (EditText) findViewById(R.id.Contraseña);
        btnLogin= (Button) findViewById(R.id.btnLogin);
        Registro=(Button) findViewById(R.id.button3);
        Recordar= (Switch) findViewById(R.id.switch2);
    }

    private boolean contra(String Contraseña){
        return !TextUtils.isEmpty(Contraseña);
    }

    private void reg (){
        Intent r = new Intent(this,tipo_regis.class);
        startActivity(r);
    }

    private boolean login(String Contraseña){
        if (!contra(Contraseña)){
            Toast.makeText(this,"Contraseña vacia",Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }

}
