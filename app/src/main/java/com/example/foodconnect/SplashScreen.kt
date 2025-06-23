package com.example.foodconnect

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        Handler(Looper.getMainLooper()).postDelayed({
            val user = auth.currentUser
            if (user != null) {
                // Check user type from Firestore
                db.collection("users").document(user.uid).get()
                    .addOnSuccessListener { document ->
                        if (document.exists()) {
                            val userType = document.getString("userType") // Assuming "userType" is stored
                            when (userType) {
                                "volunteer" -> startActivity(Intent(this, VolunteerFrontpage::class.java))
                                "donor" -> startActivity(Intent(this, DonorFrontpage::class.java))
                                else -> startActivity(Intent(this, MainActivity::class.java)) // Fallback if no role found
                            }
                        } else {
                            startActivity(Intent(this, SignUp::class.java))
                        }
                        finish()
                    }
                    .addOnFailureListener {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
            } else {
                startActivity(Intent(this, MainActivity::class.java)) // If user not logged in
                finish()
            }
        }, 1000) // 1-second delay
    }
}
