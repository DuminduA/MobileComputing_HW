package com.example.composetutorial

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.example.composetutorial.notifications.SensorNotificationService

class MyComposeTutorialApp : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                SensorNotificationService.CHANNEL_ID,
                "sensor_alerts",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = "Channel used to trigger notifications"

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}