package com.example.juan.epilepsia.recordatorio;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.juan.epilepsia.R;
import com.example.juan.epilepsia.datos_contacto;
import com.example.juan.epilepsia.datos_prim;
import com.example.juan.epilepsia.sqlite.ConexionSQLite;

import java.util.ArrayList;
import java.util.Calendar;

public class servicio_recor extends BroadcastReceiver {
    private NotificationManager notificationManager;
    Context context_se;
    static int indice;
    Calendar calendario = Calendar.getInstance();
    int hora, min,dia,mes,year;
    String fecha_sistema,hora_sistema;
    ArrayList<String> recorda;
    @Override
    public void onReceive(Context context, Intent intent) {
        context_se=context;
        dia = calendario.get(Calendar.DAY_OF_MONTH);
        mes = calendario.get(Calendar.MONTH)+1;
        year = calendario.get(Calendar.YEAR);
        hora = calendario.get(Calendar.HOUR_OF_DAY);
        min = calendario.get(Calendar.MINUTE);
        fecha_sistema=year+"/"+mes+"/"+dia;
        hora_sistema=hora+":"+min;
        datos_prim.sqLite= new ConexionSQLite(context);
        datos_prim.cursor=this.viewsqlite();
        //Borrar(22);
        if(datos_prim.cursor.moveToFirst()){//se verifica que existan datos en la base de datos sqlite
            recorda=this.viewsqlite2();
            Lanzar_noti();
        }
    }

    private void Lanzar_noti() {
        Intent notificationIntent = new Intent(context_se, Borra_actua_recor.class);
        notificationIntent.putExtra("listarecorda", recorda);
        notificationIntent.putExtra("indice",indice);
        ///atributos a enviar
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(context_se, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        long[] pattern = new long[]{2000, 1000, 2000};
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context_se);
        builder.setContentIntent(contentIntent)
                .setTicker("")
                .setContentTitle("Recordatorio medico")
                .setContentText("medicina")
                .setContentInfo(recorda.get(0).toString())
                .setLargeIcon(BitmapFactory.decodeResource(context_se.getResources(), R.drawable.ic_add_alert_black_24dp))
                .setSmallIcon(R.drawable.ic_add_alert_black_24dp)
                .setAutoCancel(true) //Cuando se pulsa la notificación ésta desaparece
                .setSound(defaultSound)
                .setVibrate(pattern)
                .setColor(Color.BLUE);
        Notification notificacion = new NotificationCompat.BigTextStyle(builder)
                .bigText(recorda.get(0).toString())
                .setBigContentTitle("Recordatorio medico")
                .build();
        notificationManager = (NotificationManager) context_se.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notificacion);
    }

    public ArrayList<String> viewsqlite2(){//se crea las intucciones sql y se mandar para ser ejecutadas
        String sql="select * from Recordatorio where id='"+indice+"';";
         return datos_contacto.sqLite.llenarlista_alar(sql,1);
    }

    private Cursor viewsqlite(){//se indica que seleccione el primer registro existente
        String sql="SELECT * FROM Recordatorio WHERE fecha='"+fecha_sistema+"' AND hora= '"+hora_sistema+"'"+" AND id= '"+indice+"'";
        Log.e("valor","cadena: "+sql);
        return datos_prim.sqLite.consultaSQL(sql);
    }



}
