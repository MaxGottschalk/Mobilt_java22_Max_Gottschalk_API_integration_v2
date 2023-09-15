package com.example.miniprojekt31

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView


class MainActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        val waifuBtn = findViewById<TextView>(R.id.btnWaifu)

        waifuBtn.setOnClickListener {
            val i = Intent(this@MainActivity3, MainActivity2::class.java)
            startActivity(i)
        }

        val rNMBtn = findViewById<TextView>(R.id.btnRnM)

        rNMBtn.setOnClickListener {
            val i = Intent(this@MainActivity3, MainActivity5::class.java)
            startActivity(i)
        }

        //Delete backstack when logged out
        findViewById<Button>(R.id.button4).setOnClickListener{ v ->

            Log.d("Max", v.toString())
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            this.startActivity(intent)
        }
    }
}