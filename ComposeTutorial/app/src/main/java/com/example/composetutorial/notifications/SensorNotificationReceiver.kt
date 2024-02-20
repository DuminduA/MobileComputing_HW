package com.example.composetutorial.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class SensorNotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        val service = SensorNotificationService(context = context)
        service.showNotification("Cleared the text")
    }
}