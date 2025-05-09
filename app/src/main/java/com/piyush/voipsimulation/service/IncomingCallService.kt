package com.piyush.voipsimulation.service

import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import com.piyush.voipsimulation.util.RingtonePlayer
import com.piyush.voipsimulation.util.buildFullScreenNotification

class IncomingCallService : Service() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(1, buildFullScreenNotification(this))
        RingtonePlayer.play(this)
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
