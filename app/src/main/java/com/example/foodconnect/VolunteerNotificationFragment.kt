package com.example.foodconnect

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore

class VolunteerNotificationFragment : Fragment() {

    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_volunteer_notification, container, false)

        db = FirebaseFirestore.getInstance() // Initialize Firestore

        val foodId = arguments?.getString("foodId")
        Log.d("FCM", "Food ID in Fragment: $foodId") // ✅ Debugging log

        if (!foodId.isNullOrEmpty()) {
            fetchFoodDetails(foodId, view)  // ✅ Ensure this function loads food details
        } else {
            context?.let {
                Toast.makeText(it, "No food details found!", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun fetchFoodDetails(foodId: String, view: View) {
        db.collection("foodPosts").document(foodId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    view.findViewById<TextView>(R.id.foodNameTextView).text = "Food: ${document.getString("foodName")}"
                    view.findViewById<TextView>(R.id.locationTextView).text = "Location: ${document.getString("location")}"
                    view.findViewById<TextView>(R.id.quantityTextView).text = "Quantity: ${document.getString("quantity")}"
                    view.findViewById<TextView>(R.id.hotelNameTextView).text = "Hotel: ${document.getString("hotelName")}"
                    view.findViewById<TextView>(R.id.preparationTimeTextView).text = "Prepared at: ${document.getString("preparationTime")}"
                    view.findViewById<TextView>(R.id.expiryTimeTextView).text = "Expires at: ${document.getString("expiryTime")}"
                    view.findViewById<TextView>(R.id.contactTextView).text = "Contact: ${document.getString("contactNumber")}"
                    view.findViewById<TextView>(R.id.specialNotesTextView).text = "Notes: ${document.getString("specialNotes")}"
                } else {
                    context?.let {
                        Toast.makeText(it, "Food details not found!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreError", "Error fetching food details", e)
                context?.let {
                    Toast.makeText(it, "Failed to load food details!", Toast.LENGTH_SHORT).show()
                }
            }
    }
}

