package com.skripsi.perpusta.extensions

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.skripsi.perpusta.R

class NotificationReceiver : BroadcastReceiver() {

    companion object {
        const val channelId = "task_channel"
        const val notificationId = 123
    }

    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("title") ?: "Reminder Pengembalian Buku"
        val content = intent.getStringExtra("content") ?: "Kamu sedang meminjam buku! Kembalikan atau Perpanjang sebelum"
        val returnDate = intent.getStringExtra("return_date") ?: ""

        val notificationContent = "$content - Tenggat Waktu: $returnDate"
        showNotification(context, title, notificationContent)

        Log.d("NotificationReceiver", "Notification received: $title - $content")
    }

    private fun showNotification(context: Context, title: String, content: String) {
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        with(NotificationManagerCompat.from(context)) {
            notify(notificationId, builder.build())
        }

    }
}