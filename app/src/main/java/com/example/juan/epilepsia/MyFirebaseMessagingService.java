package com.example.juan.epilepsia;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;
import com.example.juan.epilepsia.ventanas.mapa_ubicacion;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService{
    private static final String LOGTAG = "android-fcm";
    String latitud,longitud,Direccion;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
            String titulo = "Emergencia";
            String texto = "El usuario ";
            Direccion=remoteMessage.getData().get("mensaje");
            latitud=remoteMessage.getData().get("latitud");
            longitud=remoteMessage.getData().get("longitud");
            Log.e(LOGTAG, "NOTIFICACION RECIBIDA");
            Log.e(LOGTAG, "Título: " + titulo);
            Log.e(LOGTAG, "Texto: " + texto);
            //Opcional: mostramos la notificación en la barra de estado
            showNotification(titulo, texto);

    }

    private void showNotification(String title, String text) {

        Builder Builder = new Builder(this)
                        .setAutoCancel(true)
                        .setContentTitle(title)
                        .setContentText(text)
                        .setSmallIcon(android.R.drawable.ic_dialog_alert)
                        //DEFAULT_NOTIFICATION_URI //DEFAULT_ALARM_ALERT_URI
                        .setPriority(Notification.PRIORITY_HIGH)
                        .setSound(Uri.withAppendedPath(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, "7"))
                        .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);

        Intent i=new Intent(this,mapa_ubicacion.class);
        i.putExtra("latitud",latitud);
        i.putExtra("longitud",longitud);
        i.putExtra("direc",Direccion);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, i, PendingIntent.FLAG_ONE_SHOT);
        Builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, Builder.build());

    }

}
