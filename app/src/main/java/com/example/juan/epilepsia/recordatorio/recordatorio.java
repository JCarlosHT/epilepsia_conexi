package com.example.juan.epilepsia.recordatorio;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.juan.epilepsia.R;
import com.example.juan.epilepsia.datos_contacto;
import com.example.juan.epilepsia.sqlite.ConexionSQLite;
import com.example.juan.epilepsia.ventanas.bitacora;
import com.example.juan.epilepsia.ventanas.menu_epi;
import com.example.juan.epilepsia.Reporte.reporte;

import java.util.ArrayList;

public class recordatorio extends AppCompatActivity {
    ListView listView;
    ArrayList<String> lista_recor;
    int bandera=0;
    String indice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordatorio);
        datos_contacto.sqLite= new ConexionSQLite(this);
        listView=(ListView) findViewById(R.id.lista_recor);
        carga();
        if(lista_recor!=null){
             indice=lista_recor.get(0).split(" ")[0];
            if(indice.equals("0")){
            }else
            {
                servicio_recor.indice=Integer.parseInt(indice);//se crea el servicio con un penting para ejecutarse mas tarde con un
                //interbalo de 1000 milisegundos
                Intent recorda = new Intent(this, servicio_recor.class);
                AlarmManager alarm = (AlarmManager)getSystemService(Activity.ALARM_SERVICE);
                PendingIntent servisepi = PendingIntent.getBroadcast(this,0,recorda,PendingIntent.FLAG_CANCEL_CURRENT);
                alarm.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),1000,servisepi);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView <?> parent, View view, int position, long l) {//este metodo se usa para cuando se pica un objeto de la lista
                        lista_recor=viewsqlite2();
                        Log.e("valor",""+indice);
                        Intent Mo=new Intent(getBaseContext(),Borra_actua_recor.class);
                        Mo.putExtra("listarecorda", lista_recor);
                        Mo.putExtra("indice",Integer.parseInt(indice));
                        startActivity(Mo);
                    }
                });
            }
        }
    }

    private void carga(){//en este metodo se optiene y se carga el sistema en pantalla
        lista_recor=this.viewsqlite();//se manda a llamar el metodo para agregar los datos a la lista
        ArrayAdapter<String> adapter=new ArrayAdapter <String>(this,android.R.layout.simple_list_item_1,lista_recor);
        listView.setAdapter(adapter);
        if (lista_recor.get(0).toString()=="0"){
            bandera=0;
        }
    }

    public ArrayList<String> viewsqlite2(){//se crea las intucciones sql y se mandar para ser ejecutadas
        String sql="select * from Recordatorio where id='"+indice+"';";
        return datos_contacto.sqLite.llenarlista_alar(sql,1);
    }

    private ArrayList<String> viewsqlite(){//se crea las intucciones sql y se mandar para ser ejecutadas
        String sql="select id,medicina,fecha,hora from Recordatorio";
        return datos_contacto.sqLite.llenarlista(sql, 5);
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

    public void nuevo_reco(View view) {
        Intent reco = new Intent(this,agregar_recorda.class);
        startActivity(reco);
    }
}
