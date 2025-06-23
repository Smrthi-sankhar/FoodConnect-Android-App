package com.example.foodconnect

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.foodconnect.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private val firestore = FirebaseFirestore.getInstance()
    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userType = intent.getStringExtra("userType") ?: "volunteer"
        loadUserProfile(userType)  // Fetch and display the profile
    }

    private fun loadUserProfile(userType: String) {
        if (userId == null) return

        // Use Firestore real-time listener to fetch the user profile data
        firestore.collection("users").document(userId)
            .addSnapshotListener { document, error ->
                if (error != null) {
                    Toast.makeText(this, "Error fetching profile: ${error.message}", Toast.LENGTH_SHORT).show()
                    return@addSnapshotListener
                }

                if (document != null && document.exists()) {
                    val name = document.getString("name") ?: ""
                    val email = document.getString("email") ?: ""
                    val phone = document.getString("phone") ?: ""

                    val bundle = Bundle().apply {
                        putString("name", name)
                        putString("email", email)
                        putString("phone", phone)
                    }

                    val fragment: Fragment = if (userType == "donor") {
                        DonorProfileFragment()
                    } else {
                        VolunteerProfileFragment()
                    }
                    fragment.arguments = bundle

                    // Replace the existing fragment with the user profile fragment
                    supportFragmentManager.beginTransaction()
                        .replace(binding.fragmentContainer.id, fragment)
                        .commit()
                }
            }
    }
}
