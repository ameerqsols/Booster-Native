package com.usawdistributing.ibberp.booster
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.RemoteViews
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val txt:TextView = findViewById(R.id.txt)
        val btn:Button = findViewById(R.id.btn)
        val cnclbtn:Button = findViewById(R.id.cnclbtn)

        btn.setOnClickListener {
            showNotifcation()
        }

        cnclbtn.setOnClickListener {
            cancelNotification();
        }


    }

    private  fun cancelNotification(){
        var notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancelAll();
    }
    private fun showNotifcation(){
        val channelID = "3434"
        var notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val contentView = RemoteViews(packageName,R.layout.custom_notification_layout)
        contentView.setTextViewText(R.id.subTitle,"Device is Heat Up!")
        contentView.setTextViewText(R.id.titleNot,"Warning")
        contentView.setImageViewResource(R.id.imageViewNotification,R.drawable.temp)
        val builder = NotificationCompat.Builder(applicationContext,channelID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(true)
//                .setContentTitle("This is First Notification")
//                .setContentText("This is content text")
            .setCustomContentView(contentView)
        builder.setContent(contentView)
        val contentIntent = PendingIntent.getActivity(
            this, 0,
            Intent(this, MainActivity::class.java), PendingIntent.FLAG_UPDATE_CURRENT
        )


        builder.setContentIntent(contentIntent)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(channelID,"Custom Notification",NotificationManager.IMPORTANCE_HIGH)
            channel.enableVibration(true)
            notificationManager.createNotificationChannel(channel)
            builder.setChannelId(channelID)
            val notification = builder.build()

            notificationManager.notify(1000,notification)
        }
    }
}
