// com.piyush.voipsimulation.util.NotificationHelper.kt
package com.piyush.voipsimulation.util

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.piyush.voipsimulation.R
import com.piyush.voipsimulation.ui.main.MainActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.core.net.toUri

object NotificationHelper {
    private const val CHANNEL_ID = "missed_call_channel"
    private const val CHANNEL_NAME = "Missed Call Notifications"
    private const val CHANNEL_DESC = "Shows when a call is missed"

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun showMissedCallNotification(context: Context, callerName: String, callTime: Long) {
        createNotificationChannel(context)

        val timeStr = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date(callTime))

        val mainIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("OPEN_CALL_LOG", true)
        }
        val mainPendingIntent = PendingIntent.getActivity(
            context, 0, mainIntent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val callbackIntent = Intent(Intent.ACTION_DIAL).apply {
            data = "tel:6205661099".toUri()
        }
        val callbackPendingIntent = PendingIntent.getActivity(
            context, 1, callbackIntent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_missed)
            .setContentTitle("Missed call from $callerName")
            .setContentText("Time: $timeStr")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(mainPendingIntent)
            .setAutoCancel(true)
            .addAction(R.drawable.ic_call, "Call Back", callbackPendingIntent)

        NotificationManagerCompat.from(context).notify(System.currentTimeMillis().toInt(), builder.build())
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = CHANNEL_DESC
            }
            val manager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }
}
