package com.example.foodconnect

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.foodconnect.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignIn : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        binding.textView7.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }

        binding.loginButton.setOnClickListener {
            val email = binding.editTextTextEmailAddress.text.toString()
            val pass = binding.editTextTextPassword.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = firebaseAuth.currentUser

                            if (user != null) {
                                // Check if email is verified
                                if (!user.isEmailVerified) {
                                    Toast.makeText(
                                        this,
                                        "Please verify your email before logging in.",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    return@addOnCompleteListener
                                }

                                val userId = user.uid

                                firestore.collection("users").document(userId)
                                    .get()
                                    .addOnSuccessListener { document ->
                                        if (document.exists()) {
                                            val userType = document.getString("userType")
                                            val intent = if (userType == "donor") {
                                                Intent(this, DonorFrontpage::class.java)
                                            } else {
                                                Intent(this, VolunteerFrontpage::class.java)
                                            }
                                            Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()
                                            startActivity(intent)
                                            finish() // Prevent going back to the sign-in page
                                        }
                                    }
                                    .addOnFailureListener { e ->
                                        Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                                    }
                            }
                        } else {
                            Toast.makeText(this, "Sign-In Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Empty Fields Are Not Allowed!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}










