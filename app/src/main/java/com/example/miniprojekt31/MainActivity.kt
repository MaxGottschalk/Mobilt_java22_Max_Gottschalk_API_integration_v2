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
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val btnLogin: Button = findViewById(R.id.loginBtn)
        val btnCreateUser: Button = findViewById(R.id.createBtn)
        val db = Firebase.firestore
        Log.d("Max", db.toString())

        val enterEmail: EditText = findViewById(R.id.emailBar)
        val enterPassword: EditText = findViewById(R.id.passwordBar)

        btnCreateUser.setOnClickListener {
            val i = Intent(this@MainActivity, MainActivity4::class.java)
            startActivity(i)
        }
        btnLogin.setOnClickListener {

            val email = enterEmail.text.toString()
            val password = enterPassword.text.toString()

            Log.d("Max", email);
            Log.d("Max", password);

            // Perform a Firestore query to check if the email and password exist
            db.collection("users")
                .whereEqualTo("email", email)
                .whereEqualTo("password", password)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    if (!querySnapshot.isEmpty) {
                        // User with the provided email and password exists in Firestore
                        val i = Intent(this@MainActivity, MainActivity3::class.java)
                        i.putExtra("email", email)
                        i.putExtra("password", password)
                        startActivity(i)
                    } else {
                        // User does not exist or the password is incorrect
                        Log.d("Authentication", "User not found or incorrect password")
                        // You can display an error message to the user
                        Toast.makeText(this@MainActivity,"L USER", Toast.LENGTH_SHORT).show();
                    }
                }
        }
    }
}