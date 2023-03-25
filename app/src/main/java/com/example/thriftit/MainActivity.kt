package com.example.thriftit

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import android.app.Notification
//import android.app.NotificationChannel
//import android.app.NotificationManager
//import android.app.PendingIntent

class MainActivity : AppCompatActivity() {
    lateinit var app: MyApplication
    lateinit var notificationChannel: NotificationChannel
    lateinit var notificationManager: NotificationManager
    lateinit var builder: Notification.Builder
    private var channelId = 10000
    private val description = "Sale Notification"
    /*val resultContract = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            app.thriftShops.add( ThriftShop(
                data?.getStringExtra("name")!!,
                data.getStringExtra("street")!!,
                data.getIntExtra("streetNumber",1),
                data.getBooleanExtra("sale",false)
            )
            )
            app.saveData()
           // Log.i("MainActivityAdd",app.data.guests.last().toString())

        }

    }*/

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = application as MyApplication
        for(ts in app.thriftShops) {
            if(ts.sale) {
                notificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationChannel = NotificationChannel(
                    channelId.toString(),
                    description,
                    NotificationManager.IMPORTANCE_HIGH
                )
                notificationChannel.lightColor = Color.BLUE
                notificationChannel.enableVibration(true)
                notificationManager.createNotificationChannel(notificationChannel)
                val intent = Intent(this, MainActivity::class.java)
                val pendingIntent =
                    PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
                builder = Notification.Builder(this, channelId.toString())
                    .setContentTitle("SALE NOTIFICATION ")
                    .setContentText("There is a sale at ${ts.name}! Hurry up check them out!")
                    .setSmallIcon(R.drawable.ic_position).setLargeIcon(
                    BitmapFactory.decodeResource(
                        this.resources, R.drawable
                            .ic_launcher_background
                    )
                ).setContentIntent(pendingIntent)
                notificationManager.notify(channelId, builder.build())
                channelId++
            }
        }
        setContentView(R.layout.activity_main)

    }

    fun about(view: android.view.View) {
        val intent = Intent(this,AboutActivity::class.java)
        startActivity(intent)
    }
    /*fun AddThriftShop(view: android.view.View) {
        val intent = Intent(this,AddThriftShopActivity::class.java)
        resultContract.launch(intent)
    }*/
    fun showThriftShops(view: android.view.View) {
        val intent = Intent(this,ShowThriftShopsActivity::class.java)
        startActivity(intent)
    }

    fun exit(view: android.view.View) {}
    fun openMap(view: android.view.View) {
        val intent = Intent(this,MapsActivity::class.java)
        startActivity(intent)
    }
}