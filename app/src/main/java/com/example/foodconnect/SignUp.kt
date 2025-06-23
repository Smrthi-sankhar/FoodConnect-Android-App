package com.example.foodconnect

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.foodconnect.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging

class SignUp : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        binding.AlreadyHaveAccount.setOnClickListener {
            startActivity(Intent(this, SignIn::class.java))
        }

        binding.signupbutton.setOnClickListener {
            val email = binding.editTextTextEmailAddress2.text.toString()
            val pass = binding.editTextTextPassword2.text.toString()
            val name = binding.editTextText.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty() && name.isNotEmpty()) {
                firebaseAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = firebaseAuth.currentUser
                            user?.sendEmailVerification()
                                ?.addOnSuccessListener {
                                    Toast.makeText(
                                        this,
                                        "Verification email sent! Please check your inbox.",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                                ?.addOnFailureListener {
                                    Toast.makeText(
                                        this,
                                        "Failed to send verification email",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                            val userId = user?.uid
                            val userType =
                                if (binding.radioButton.isChecked) "donor" else "volunteer"

                            FirebaseMessaging.getInstance().token.addOnCompleteListener { taskToken ->
                                if (!taskToken.isSuccessful) {
                                    Log.e("FCM", "Fetching FCM token failed", taskToken.exception)
                                    return@addOnCompleteListener
                                }

                                val fcmToken = taskToken.result ?: ""
                                val userMap = hashMapOf(
                                    "name" to name,
                                    "email" to email,
                                    "userType" to userType,
                                    "fcmToken" to fcmToken,
                                    "emailVerified" to false
                                )

                                if (userId != null) {
                                    firestore.collection("users").document(userId)
                                        .set(userMap)
                                        .addOnSuccessListener {
                                            Toast.makeText(
                                                this,
                                                "User Registered. Verify your email before logging in.",
                                                Toast.LENGTH_LONG
                                            ).show()

                                            // Redirect to SignIn page after registration
                                            startActivity(Intent(this, SignIn::class.java))
                                            finish()
                                        }
                                        .addOnFailureListener { e ->
                                            Toast.makeText(
                                                this,
                                                "Firestore Error: ${e.message}",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                }
                            }
                        } else {
                            Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
            } else {
                Toast.makeText(this, "Empty Fields Are Not Allowed!", Toast.LENGTH_SHORT).show()
            }
        }

    }
}




