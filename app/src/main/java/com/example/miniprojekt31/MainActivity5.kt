package com.example.miniprojekt31

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.NotificationCompat
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import org.json.JSONException

class MainActivity5 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main5)

        val imageView: ImageView = findViewById(R.id.imageView2)
        val rq: RequestQueue = Volley.newRequestQueue(this)
        val charactersUrl = "https://rickandmortyapi.com/api/character"

        val goBack = findViewById<TextView>(R.id.goBackBtn)

        goBack.setOnClickListener {
            finish()
        }

        val charactersRequest = JsonObjectRequest(Request.Method.GET, charactersUrl, null,
            { charactersResponse ->
                try {
                    val info = charactersResponse.getJSONObject("info")
                    val count = info.getInt("count")

                    val randomCharacterId = (1..count).random()

                    val characterDetailsUrl = "$charactersUrl/$randomCharacterId"
                    val characterDetailsRequest = JsonObjectRequest(Request.Method.GET, characterDetailsUrl, null,
                        { characterResponse ->
                            try {
                                val image = characterResponse.getString("image")

                                Log.d("RickAndMorty", image.toString())
                                notification()
                                Picasso.get().load(image).into(imageView)
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }
                        },
                        { characterError ->
                            Log.e("RickAndMorty", "Character Details Request Error: ${characterError.message}")
                        }
                    )
                    rq.add(characterDetailsRequest)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { charactersError ->
                Log.e("RickAndMorty", "Characters Request Error: ${charactersError.message}")
            }
        )
        rq.add(charactersRequest)
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
            .setContentTitle("Crazy Notification")
            .setContentText("New Rick and Morty Character!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val intent = Intent(this, MainActivity5::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        notificationBuilder.setContentIntent(pendingIntent)

        val notificationManager = this.getSystemService(NotificationManager::class.java)
        notificationManager.notify(notificationId, notificationBuilder.build())
    }
}