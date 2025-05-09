package com.piyush.voipsimulation.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.piyush.voipsimulation.R
import com.piyush.voipsimulation.ui.main.MainActivity

@RequiresApi(Build.VERSION_CODES.O)
fun buildFullScreenNotification(context: Context): Notification {
    val fullScreenIntent = Intent(context, MainActivity::class.java).
    putExtra("IS_INCOMING_CALL", true)
    val fullScreenPendingIntent = PendingIntent.getActivity(
        context, 0, fullScreenIntent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val channelId = "incoming_call_channel"
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            channelId,
            "Incoming Calls",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Incoming call notifications"
            lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        }
        notificationManager.createNotificationChannel(channel)
    }

    return NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.ic_call)
        .setContentTitle("Incoming Call")
        .setContentText("Piyush is calling...")
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setCategory(NotificationCompat.CATEGORY_CALL)
        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
        .setFullScreenIntent(fullScreenPendingIntent, true)
        .setAutoCancel(true)
        .build()
}
