package com.skripsi.perpusta.extensions

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.skripsi.perpusta.R

class ReminderBroadcastReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("ReminderBroadcast", "Broadcast received")

        if (context != null && intent != null) {
            val title = intent.getStringExtra("title")
            val date = intent.getStringExtra("date")
            val hour = intent.getStringExtra("hour")
            Log.d("ReminderBroadcast", "Title: $title, Date: $date, Hour: $hour")

            showNotification(context, title ?: "Task Reminder", "Reminder at $hour on $date")
        }
}

    private fun showNotification(context: Context, title: String, content: String) {
        Log.d("ReminderBroadcast", "Show Notification: $title - $content")

        val channelId = "task_channel"
        val notificationId = 123 // Unique ID for the notification

        // Create a NotificationChannel (for Android 8.0 and higher)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Task Channel"
            val descriptionText = "Channel for task reminders"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }

            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        // Create the notification
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(R.drawable.ic_return) // Replace with your notification icon
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        // Show the notification
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId, notificationBuilder.build())
    }
}