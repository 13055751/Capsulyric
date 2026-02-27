package com.example.islandlyrics

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import android.widget.RemoteViews

class LyricNotificationManager(private val context: Context) {

    companion object {
        private const val CHANNEL_ID = "focus_lyric_notification"
        private const val CHANNEL_NAME = "歌词焦点通知"
        private const val NOTIFY_ID = 10001
    }

    init {
        createNotificationChannel()
    }

    fun showLyricNotification(lyric: String) {
        val remoteViews = RemoteViews(context.packageName, R.layout.focuslyric_layout)
        remoteViews.setTextViewText(R.id.focuslyric, lyric)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground) // 替换为实际图标
            .setContent(remoteViews)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setOngoing(true)

        NotificationManagerCompat.from(context).notify(NOTIFY_ID, builder.build())
    }

    fun cancelLyricNotification() {
        NotificationManagerCompat.from(context).cancel(NOTIFY_ID)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            channel.description = "实时焦点歌词显示"
            val manager = context.getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel)
        }
    }
}