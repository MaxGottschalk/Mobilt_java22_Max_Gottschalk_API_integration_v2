package com.example.miniprojekt31

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity4 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)


        val db = Firebase.firestore

        val btnCreateUser: Button = findViewById(R.id.createUserBtn)

        val enterEmail: EditText = findViewById(R.id.createEmail)

        val enterPassword: EditText = findViewById(R.id.creatPassword)

        btnCreateUser.setOnClickListener {

            val email = enterEmail.text.toString()
            val password = enterPassword.text.toString()

            // Create a new user document in Firestore
            val user = hashMapOf(
                "email" to email,
                "password" to password
            )
            db.collection("users")
                .add(user)
                .addOnSuccessListener { documentReference ->
                    Toast.makeText(this@MainActivity4, "User created successfully with ID: ${documentReference.id}", Toast.LENGTH_SHORT).show()

                    val i = Intent(this@MainActivity4, MainActivity::class.java)
                    startActivity(i)
                }
                .addOnFailureListener { e ->
                    Toast.makeText(
                        this@MainActivity4, "Error creating user: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}