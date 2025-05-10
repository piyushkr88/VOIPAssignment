package com.piyush.voipsimulation.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class CallBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("CallBroadcastReceiver", "Alarm triggered")
        val incomingIntent = Intent(context, IncomingCallReceiver::class.java).apply {
            action = "com.piyush.voipsimulation.INCOMING_CALL"
        }
        context.sendBroadcast(incomingIntent)
    }
}

