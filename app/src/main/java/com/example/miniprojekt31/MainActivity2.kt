package com.example.miniprojekt31

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
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
            //currentUrl ="https://api.waifu.pics/sfw/happy"
            currentUrl = "https://api.waifu.pics/nsfw/waifu"
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
            val i = Intent(this@MainActivity2, MainActivity3::class.java)
            startActivity(i)
        }

        // Initial request
        makeRequest(currentUrl, rq, imageView)
    }

    fun makeRequest(url: String, rq: RequestQueue, imageView: ImageView, isGif: Boolean = false) {
        val r: StringRequest = StringRequest(Request.Method.GET, url,
            { res -> Log.d("Waifu", res.toString()) },
            { err -> Log.d("Max", err.toString()) })

        rq.add(r)
        var j : JsonObjectRequest = JsonObjectRequest(url,
            { res ->
                // Inside the success callback
                val imageUrl = res.optString("url") // Assign the URL from the JSON response
                Log.d("Max", imageUrl.toString())

                // Now, you can use 'imageUrl' for other operations or display it.
                if (isGif) {
                    Glide.with(imageView)
                        .asGif()
                        .load(imageUrl)
                        .into(imageView)
                } else {
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
}