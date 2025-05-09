package com.piyush.voipsimulation.util

import android.content.Context
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.util.Log

object RingtonePlayer {
    private var ringtone: Ringtone? = null

    fun play(context: Context) {
        try {
            val ringtoneUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
            ringtone = RingtoneManager.getRingtone(context, ringtoneUri)
            ringtone?.play()
        } catch (e: Exception) {
            Log.e("RingtonePlayer", "Error playing ringtone: ${e.message}")
        }
    }

    fun stop() {
        ringtone?.stop()
    }
}

