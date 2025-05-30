package com.example.petcare_app;

import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.petcare_app.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class PetcareFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FCM";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d("FCM", "Mensagem recebida de: ${remoteMessage.from}");

        // Recebe a notificação (se existir)
        RemoteMessage.Notification notification = remoteMessage.getNotification();
        if (notification != null) {
            showNotification(notification.getTitle(), notification.getBody());
        }
    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.d(TAG, "Novo token: " + token);
        // Enviar token para backend, se necessário
    }

    private void showNotification(String title, String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default_channel")
                .setSmallIcon(R.drawable.ic_bell)
                .setContentTitle(title != null ? title : "Nova mensagem")
                .setContentText(message != null ? message : "")
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }

}
