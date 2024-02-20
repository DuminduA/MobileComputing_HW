package com.example.composetutorial.notifications

import android.Manifest
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.composetutorial.MainActivity
import com.example.composetutorial.R

class SensorNotificationService(
    private val context: Context
) {
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showNotification(content: String) {

        val activityIntent = Intent(context, MainActivity::class.java)

        val activityPendingIntent = PendingIntent.getActivity(
            context,
            1,
            activityIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val actionIntent = PendingIntent.getBroadcast(
            context,
            2,
            Intent(context, SensorNotificationReceiver::class.java),
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_bakery_dining_24)
            .setContentTitle("Sensor Event Notification")
            .setContentText(content)
            .setContentIntent(activityPendingIntent)
            .addAction(
                R.drawable.baseline_bakery_dining_24,
                "Clear Data",
                actionIntent
            ).build()

        notificationManager.notify(2, notification)


    }

    companion object {
        const val CHANNEL_ID = "sensor_alerts"
    }
}