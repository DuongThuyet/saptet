package com.sugardev.saptet.service

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log
import android.widget.RemoteViews

import androidx.core.app.NotificationCompat
import com.sugardev.saptet.MainActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*


class NotifyCountDownService : Service() {

    private val CHANNEL_ID = "ForegroundServiceChannel"
    private lateinit var cd: CountDownTimer

    override fun onCreate() {
        Log.d("TAG_FOREGROUND_SERVICE", "My foreground service onCreate().")
        super.onCreate()
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val input = intent?.getStringExtra("inputExtra")
        createNotificationChannel()
        val notificationIntent = Intent(this, MainActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, 0
        )
        val notificationLayout = RemoteViews(this.packageName, com.sugardev.saptet.R.layout.notification_small)
        val now = Calendar.getInstance().time.time
        val tet = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(initTet()).time
        val period = tet - now
        cd = object : CountDownTimer(period, 1000) {
            override fun onFinish() {
            }

            override fun onTick(p0: Long) {
                notificationLayout.setTextViewText(
                    com.sugardev.saptet.R.id.tvDate,
                    String.format("%02d", p0 / (24 * 60 * 60 * 1000))
                )
                notificationLayout.setTextViewText(
                    com.sugardev.saptet.R.id.tvHour,
                    String.format("%02d", p0 / (60 * 60 * 1000) % 24)
                )
                notificationLayout.setTextViewText(
                    com.sugardev.saptet.R.id.tvMinute,
                    String.format("%02d", p0 / (60 * 1000) % 60)
                )
                notificationLayout.setTextViewText(
                    com.sugardev.saptet.R.id.tvMinute,
                    String.format("%02d", p0 / 1000 % 60)
                )
            }
        }
        cd.start()
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_delete)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setContent(notificationLayout)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(1, notification)

        return START_NOT_STICKY

    }


    private fun initTet(): String {
        val currentTime = Calendar.getInstance().time
        return  when (SimpleDateFormat("yyyy").format(currentTime)) {
            MainActivity.Tet.tet20 -> {
                MainActivity.Tet.tet20
            }
            MainActivity.Tet.tet21 -> {
                MainActivity.Tet.tet21
            }
            else -> {
                MainActivity.Tet.tet20
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager!!.createNotificationChannel(serviceChannel)
        }

    }


    override fun onBind(p0: Intent?): IBinder? {
        throw  UnsupportedOperationException("Not yet implemented")
    }
}