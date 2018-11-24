package com.example.juan.epilepsia.ventanas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.juan.epilepsia.R;
import com.example.juan.epilepsia.datos_contacto;
import com.example.juan.epilepsia.sqlite.ConexionSQLite;

import java.util.ArrayList;


public class alarma extends AppCompatActivity {
    RequestQueue request;
    datos_contacto contacto_user;
    int datovital=1;//este verifica si hay datos en la lista
    ListView listView;
    ArrayList<String> contactos_us;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarma);
        request= Volley.newRequestQueue(getApplicationContext());
        contacto_user=new datos_contacto();
        datos_contacto.sqLite= new ConexionSQLite(this);
        listView=(ListView) findViewById(R.id.listview_contac);
        carga();
        if(datovital==1){
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView <?> parent, View view, int position, long l) {//este metodo se usa para cuando se pica un objeto de la lista
                    int clave= Integer.parseInt((contactos_us.get(position).split(" ")[0]));//se optiene el indice
                    String nombre=contactos_us.get(position).split(" ")[1];
                    String apelli=contactos_us.get(position).split(" ")[2];
                    String relacion=contactos_us.get(position).split(" ")[3];
                    Intent Mo=new Intent(alarma.this,borra_actualizar_contac.class);
                    Mo.putExtra("Id",clave);
                    Mo.putExtra("Nombre",nombre);
                    Mo.putExtra("Apellido",apelli);
                    Mo.putExtra("Relacion",relacion);
                    startActivity(Mo);
                }
            });
        }
    }

    public void insert(View view){//se abre la ventana para insertar
        Intent r = new Intent(this,cordenadas.class);
        r.putExtra("bande",0);
        r.putExtra("validar",datovital);
        startActivity(r);
    }

    private void carga(){//en este metodo se optiene y se carga el sistema en pantalla
        contactos_us=this.viewsqlite();//se manda a llamar el metodo para agregar los datos a la lista
        ArrayAdapter<String> adapter=new ArrayAdapter <String>(this,android.R.layout.simple_list_item_1,contactos_us);
        listView.setAdapter(adapter);
        if (contactos_us.get(0).toString()=="0"){
            datovital=0;
        }


    }

    private ArrayList<String> viewsqlite(){//se crea las intucciones sql y se mandar para ser ejecutadas
        String sql="select * from Contacto";
        return datos_contacto.sqLite.llenarlista(sql,2);
    }


    public void notifi(View view){
        Intent r = new Intent(this,cordenadas.class);
        r.putExtra("bande",1);
        startActivity(r);
    }
}
