package com.weather.weatherapp.pushnotification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.weather.weatherapp.MainActivity
import com.weather.weatherapp.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class FirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        if (remoteMessage.getNotification() != null) {
            remoteMessage.notification?.let {
                showNotification(
                    remoteMessage
                )
            }
        }
    }

    override fun onNewToken(p0: String) {
        Log.e("DT", "token : $p0")
        super.onNewToken(p0)
    }

    private fun showNotification(remoteMessage: RemoteMessage) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    setupChannels()
                val intent = Intent(this@FirebaseMessagingService, MainActivity::class.java)
                val resultPendingIntent =
                    PendingIntent.getActivity(this@FirebaseMessagingService, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
                val notificationBuilder =
                    NotificationCompat.Builder(
                        this@FirebaseMessagingService,
                        getString(R.string.channel_id)
                    )
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setColor(ContextCompat.getColor(this@FirebaseMessagingService, R.color.black))
                        .setContentTitle(remoteMessage.notification?.title)
                        .setContentText(remoteMessage.notification?.body)
                        .setAutoCancel(true)
                        .setContentIntent(resultPendingIntent)

                val mNotificationManager = NotificationManagerCompat.from(this@FirebaseMessagingService)
                mNotificationManager.notify(createID(), notificationBuilder.build())

            } catch (e: NumberFormatException) {
                e.message?.let { it1 -> Log.e(javaClass.name, it1) }
            }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun setupChannels() {
        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val name = getString(R.string.channel_name)
        val description = getString(R.string.channel_description)
        val importance = NotificationManager.IMPORTANCE_HIGH
        val mChannel = NotificationChannel(getString(R.string.channel_id), name, importance)
        mChannel.description = description
        mChannel.enableLights(true)
        mChannel.lightColor = ContextCompat.getColor(this, R.color.black)
        mChannel.enableVibration(true)
        mChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)

        mNotificationManager.createNotificationChannel(mChannel)
    }

    private fun createID(): Int = Integer.parseInt(
        SimpleDateFormat("ddHHmmss", Locale.US).format(
            Date()
        ))
}