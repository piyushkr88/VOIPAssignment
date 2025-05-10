package com.piyush.voipsimulation.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.*
import androidx.core.app.NotificationCompat
import com.piyush.voipsimulation.R
import com.piyush.voipsimulation.ui.main.MainActivity

class OngoingCallService : Service() {

    private var callStartTime = 0L
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var updateRunnable: Runnable

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        callStartTime = System.currentTimeMillis()

        createNotificationChannel()

        startForeground(2, buildNotification(0))

        updateRunnable = object : Runnable {
            override fun run() {
                val duration = (System.currentTimeMillis() - callStartTime) / 1000
                startForeground(2, buildNotification(duration))
                handler.postDelayed(this, 1000)
            }
        }
        handler.post(updateRunnable)

        return START_STICKY
    }

    override fun onDestroy() {
        handler.removeCallbacks(updateRunnable)
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun buildNotification(durationSec: Long): Notification {
        val openAppIntent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }

        val pendingIntent = PendingIntent.getActivity(
            this, 0, openAppIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Call in Progress")
            .setContentText("Duration: ${durationSec}s")
            .setSmallIcon(R.drawable.ic_call)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val chan = NotificationChannel(
                CHANNEL_ID,
                "Ongoing Call",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(chan)
        }
    }

    companion object {
        const val CHANNEL_ID = "ongoing_call_channel"
    }
}
