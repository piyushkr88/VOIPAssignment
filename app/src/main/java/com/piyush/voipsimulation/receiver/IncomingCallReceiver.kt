package com.piyush.voipsimulation.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import com.piyush.voipsimulation.service.IncomingCallService
import com.piyush.voipsimulation.ui.main.MainActivity
import com.piyush.voipsimulation.util.RingtonePlayer
class IncomingCallReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action == "com.piyush.voipsimulation.INCOMING_CALL") {
            // Log to confirm the receiver is triggered
            Log.d("IncomingCallReceiver", "Broadcast received - Incoming Call Triggered")

            // Trigger the MainActivity for incoming call
            val callIntent = Intent(context, MainActivity::class.java).apply {
                putExtra("IS_INCOMING_CALL", true)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            context.startActivity(callIntent)

            // Start the IncomingCallService (foreground service)
            val serviceIntent = Intent(context, IncomingCallService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(serviceIntent)  // For Android O and above
            } else {
                context.startService(serviceIntent)  // For below Android O
            }

            // Start ringtone and vibration
            RingtonePlayer.play(context)

            val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                vibrator.vibrate(1000)
            }
        }
    }
}

