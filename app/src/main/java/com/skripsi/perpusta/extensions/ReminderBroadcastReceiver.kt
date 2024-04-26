package com.skripsi.perpusta.extensions

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import com.skripsi.perpusta.R
import java.util.Calendar

class ReminderBroadcastReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("ReminderBroadcast", "Broadcast received")

        val dueDate = intent?.getStringExtra("dueDate")
        if (!dueDate.isNullOrEmpty()){
            context?.let { nonNullContext ->
                scheduleReturnBookNotification(nonNullContext)
            }
        } else {
            val title = intent?.getStringExtra("title")
            val date = intent?.getStringExtra("date")
            val hour = intent?.getStringExtra("hour")
            Log.d("ReminderBroadcast", "Title: $title, Date: $date, Hour: $hour")

            context?.let { nonNullContext ->
                showNotification(nonNullContext, title ?: "Task Reminder", "Reminder at $hour on $date")
            }
        }
}

    private fun scheduleReturnBookNotification(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent,PendingIntent.FLAG_IMMUTABLE)

        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 8)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)

            if (timeInMillis <= System.currentTimeMillis()) {
                add(Calendar.DAY_OF_MONTH, 1)
            }
        }

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
        Log.d("ReminderBroadcast", "Retrun book notification scheculed")
    }

    private fun showNotification(context: Context, title: String, content: String) {
        Log.d("ReminderBroadcast", "Show Notification: $title - $content")

        val notificationBuilder = NotificationCompat.Builder(context, NotificationReceiver.channelId)
            .setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(R.drawable.ic_return)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NotificationReceiver.notificationId, notificationBuilder.build())
    }
}