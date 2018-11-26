package com.example.juan.epilepsia.Reporte;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.example.juan.epilepsia.R;
import com.example.juan.epilepsia.conexion_server.Conexion;
import com.example.juan.epilepsia.ventanas.mapa_ubicacion;
import com.example.juan.epilepsia.ventanas.menu_epi;
public class Service_notifi extends Service {
    private Context thiscontext=this;
    private final static String CHANNEL_ID = "NOTIFICACION";
    private final static int NOTIFICACION_ID = 0;
    @Override
    public void onCreate(){

    }

    @Override
    public int onStartCommand(Intent intent,int flag,int idProcess){
        createNotification();//se crea la notificacion para mandar informacion
        return START_STICKY;
    }

    private void createNotification(){
        NotificationCompat.Builder Builder = new NotificationCompat.Builder(this)
                .setContentTitle("Alerta de emergencia")
                .setContentText("Alerta de epilepsia app")
                .setSmallIcon(R.drawable.ic_add_alert_black_24dp)
                .setColor(Color.RED)
                //DEFAULT_NOTIFICATION_URI //DEFAULT_ALARM_ALERT_URI
                .setPriority(Notification.PRIORITY_HIGH);
        Intent i=new Intent(thiscontext,menu_epi.class);
        i.putExtra("bande",1);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, i, PendingIntent.FLAG_ONE_SHOT);
        Builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Conexion.bandera=2;
        notificationManager.notify(0, Builder.build());

        /*NotificationCompat.Builder builder = new NotificationCompat.Builder(thiscontext, CHANNEL_ID);
        Intent i = new Intent(getBaseContext(), menu_epi.class);
        i.putExtra("bande",1);
        Log.e("SErvicio", "Entro al servicio");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, 0);
        builder.setSmallIcon(R.drawable.ic_access_alarm_black_24dp);
        builder.setContentTitle("Alerta de emergencia");
        builder.setContentText("Alarma de epilepsia app");
        builder.setColor(Color.YELLOW);
        builder.setContentIntent(pendingIntent);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(thiscontext);
        notificationManagerCompat.notify(NOTIFICACION_ID, builder.build());*/
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
