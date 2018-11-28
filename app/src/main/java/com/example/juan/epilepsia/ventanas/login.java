package com.example.juan.epilepsia.ventanas;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.juan.epilepsia.Contacto_ventanas.Menu_contacto;
import com.example.juan.epilepsia.Manifest;
import com.example.juan.epilepsia.datos_prim;
import com.example.juan.epilepsia.paginaweb;
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
        datos_prim.cursor=this.viewsqlite(1);
        int layout= R.layout.activity_login;//se indica con que actividad debe iniciar
       /* if(datos_prim.cursor.getCount()>0){//se verifica que existan datos en la base de datos sqlite
            //y se finaliza la anterior
        }
        datos_prim.cursor=this.viewsqlite(2);
        if(datos_prim.cursor.getCount()>0){//se verifica que existan datos en la base de datos sqlite
            Intent menu = new Intent(getApplicationContext(), Menu_contacto.class);
            startActivity(menu);
            finish();//y se finaliza la anterior
        }*/
        setContentView(layout);
        inicio();
    }

    private Cursor viewsqlite(int bandera){//se indica que seleccione el primer registro existente
        String sql="";
        if (bandera==1){
            sql="select * from usuario limit 1";
        }else{
            sql="select * from Contacto_da limit 1";
            Log.e("aqui",""+sql);
        }
        return datos_prim.sqLite.consultaSQL(sql);
    }

    //metodos incompletos
    public void ini_se(View view){
        String Contra=Contraseña.getText().toString();
        if(login(Contra)){
            Intent menu = new Intent(getApplicationContext(), menu_epi.class);
            startActivity(menu);
            finish();
        }
    }

    public void Regis(View view){
        reg();
    }

    private void inicio (){
        NombreUsu= (EditText)  findViewById(R.id.NombreUsu);
        Contraseña= (EditText) findViewById(R.id.Contraseña);
        btnLogin= (Button) findViewById(R.id.btnLogin);
        Registro=(Button) findViewById(R.id.button3);
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


    public void masinfo(View view) {
        Intent r = new Intent(getApplicationContext(),paginaweb.class);
        startActivity(r);
    }
}

