package com.example.miniprojekt31

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
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
            val i = Intent(this@MainActivity5, MainActivity3::class.java)
            startActivity(i)
        }

        // Step 1: Get the total number of characters
        val charactersRequest = JsonObjectRequest(Request.Method.GET, charactersUrl, null,
            { charactersResponse ->
                try {
                    val info = charactersResponse.getJSONObject("info")
                    val count = info.getInt("count")

                    // Step 2: Generate a random character ID
                    val randomCharacterId = (1..count).random()

                    // Step 3: Use the random character ID to fetch character details
                    val characterDetailsUrl = "$charactersUrl/$randomCharacterId"
                    val characterDetailsRequest = JsonObjectRequest(Request.Method.GET, characterDetailsUrl, null,
                        { characterResponse ->
                            try {
                                // Step 4: Extract the image URL and display the image
                                val image = characterResponse.getString("image")

                                Log.d("RickAndMorty", image.toString())
                                // Load and display the image using Picasso, Glide, or any other image loading library
                                // For example, using Picasso:
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
}