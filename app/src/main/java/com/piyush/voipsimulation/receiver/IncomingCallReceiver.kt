package com.piyush.voipsimulation.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.piyush.voipsimulation.service.IncomingCallService
import com.piyush.voipsimulation.ui.main.MainActivity
import com.piyush.voipsimulation.util.RingtonePlayer

class IncomingCallReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action == "com.piyush.voipsimulation.INCOMING_CALL") {
            Log.d("IncomingCallReceiver", "Broadcast received - Incoming Call Triggered")
            val callIntent = Intent(context, MainActivity::class.java).apply {
                putExtra("IS_INCOMING_CALL", true)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            context.startActivity(callIntent)

            val serviceIntent = Intent(context, IncomingCallService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(serviceIntent)
            } else {
                context.startService(serviceIntent)
            }
            RingtonePlayer.play(context)
        }
    }
}

