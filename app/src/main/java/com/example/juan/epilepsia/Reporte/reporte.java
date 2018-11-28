package com.example.juan.epilepsia.Reporte;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.juan.epilepsia.R;
import com.example.juan.epilepsia.Bitacora.bitacora;
import com.example.juan.epilepsia.datos_contacto;
import com.example.juan.epilepsia.recordatorio.recordatorio;
import com.example.juan.epilepsia.sqlite.ConexionSQLite;
import com.example.juan.epilepsia.ventanas.menu_epi;

import java.util.ArrayList;

public class reporte extends AppCompatActivity {
    ListView listView;
    ArrayList<String> lista_recor;
    int clave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte);
        datos_contacto.sqLite= new ConexionSQLite(this);
        listView=(ListView) findViewById(R.id.listview_repo);
        carga();
        clave= Integer.parseInt((lista_recor.get(0).split(" ")[0]));

        if(lista_recor!=null&&clave!=0){
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView <?> parent, View view, int position, long l) {//este metodo se usa para cuando se pica un objeto de la lista
                    clave= Integer.parseInt((lista_recor.get(position).split(" ")[0]));
                    Log.e("indice","valor del indice"+clave);
                    lista_recor=viewsqlite(clave);
                    Intent Mo=new Intent(getBaseContext(),Repote_info.class);
                    Mo.putExtra("datos_alertas", lista_recor);
                    Mo.putExtra("indice",String.valueOf(clave));
                    startActivity(Mo);
                }
            });

        }
    }

    private void carga(){//en este metodo se optiene y se carga el sistema en pantalla
        lista_recor=this.viewsqlite();//se manda a llamar el metodo para agregar los datos a la lista
        ArrayAdapter<String> adapter=new ArrayAdapter <String>(this,android.R.layout.simple_list_item_1,lista_recor);
        listView.setAdapter(adapter);
    }

    private ArrayList<String> viewsqlite(int indice){
        //se crea las intucciones sql y se mandar para ser ejecutadas
        //SELECT * FROM Recordatorio ORDER BY id DESC LIMIT 1;
        String sql="SELECT * FROM Alarma where id="+indice;
        return datos_contacto.sqLite.llenarlista_alar(sql, 4);//se deside emplear la segunda para no invertir tanto codigo
    }

    private ArrayList<String> viewsqlite(){
        //se crea las intucciones sql y se mandar para ser ejecutadas
        //SELECT * FROM Recordatorio ORDER BY id DESC LIMIT 1;
        String sql="SELECT * FROM Alarma";
        return datos_contacto.sqLite.llenarlista_alar(sql, 3);//se deside emplear la segunda para no invertir tanto codigo
    }

    public void inicio3(View view) {
        Intent i = new Intent(this,menu_epi.class);
        startActivity(i);
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
        finish();
    }

    public void rec3(View view) {
        Intent r = new Intent(this,recordatorio.class);
        startActivity(r);
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
        finish();
    }

    public void bita3(View view) {
        Intent b = new Intent(this,bitacora.class);
        startActivity(b);
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
        finish();
    }

    public void estadisti(View view) {
    }
}
