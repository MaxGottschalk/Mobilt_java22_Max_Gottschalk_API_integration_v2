package com.example.miniprojekt31

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.app.NotificationCompat
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val imageView: ImageView = findViewById(R.id.imageView)

        val maxWidth = 1100
        val maxHeight = 1000

        val layoutParams = LinearLayout.LayoutParams(maxWidth, maxHeight)
        imageView.layoutParams = layoutParams

        val rq: RequestQueue = Volley.newRequestQueue(this)
        var currentUrl = "https://api.waifu.pics/sfw/waifu"

        val btn1: Button = findViewById(R.id.button)
        btn1.setOnClickListener {
            //Swap for presentation
            currentUrl ="https://api.waifu.pics/sfw/happy"
            //currentUrl = "https://api.waifu.pics/nsfw/waifu"
            makeRequest(currentUrl, rq, imageView)
        }

        val btn2: Button = findViewById(R.id.button2)
        btn2.setOnClickListener {
            currentUrl = "https://api.waifu.pics/sfw/waifu"
            makeRequest(currentUrl, rq, imageView)
        }

        val btn3: Button = findViewById(R.id.button3)
        btn3.setOnClickListener {
            currentUrl = "https://api.waifu.pics/sfw/dance"
            makeRequest(currentUrl, rq, imageView, true)
        }

        val btn4 : Button = findViewById(R.id.backBtn)
        btn4.setOnClickListener {
           finish()
        }

        makeRequest(currentUrl, rq, imageView)
    }

    fun makeRequest(url: String, rq: RequestQueue, imageView: ImageView, isGif: Boolean = false) {
        val r: StringRequest = StringRequest(Request.Method.GET, url,
            { res -> Log.d("Waifu", res.toString()) },
            { err -> Log.d("Max", err.toString()) })

        rq.add(r)
        var j : JsonObjectRequest = JsonObjectRequest(url,
            { res ->
                val imageUrl = res.optString("url")
                Log.d("Max", imageUrl.toString())

                if (isGif) {
                    Glide.with(imageView)
                        .asGif()
                        .load(imageUrl)
                        .into(imageView)
                } else {
                    notification()
                    Picasso.get()
                        .load(imageUrl)
                        .into(imageView)
                }
                Log.d("Url", imageUrl.toString())
            },
            { err ->
                Log.d("Max", err.toString())
            }
        )
        rq.add(j)
    }

    private fun notification() {
        val channelId = "MINI_3"
        val notificationId = 1

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = "Max_app"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val notificationChannel = NotificationChannel(channelId, channelName, importance)

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.sus)
            .setContentTitle("Single moms ready to mingle")
            .setContentText("New Anime girl ready")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val intent = Intent(this, MainActivity2::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        notificationBuilder.setContentIntent(pendingIntent)

        val notificationManager = this.getSystemService(NotificationManager::class.java)
        notificationManager.notify(notificationId, notificationBuilder.build())
    }
}