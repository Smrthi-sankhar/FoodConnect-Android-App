package com.example.foodconnect

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.foodconnect.databinding.FragmentDonorPostingpageBinding
import com.google.firebase.firestore.FirebaseFirestore


class Donor_FoodPostingPage : Fragment() {

    private var _binding: FragmentDonorPostingpageBinding? = null
    private val binding get() = _binding!!
    private lateinit var firestore: FirebaseFirestore // Firestore instance

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDonorPostingpageBinding.inflate(inflater, container, false)
        firestore = FirebaseFirestore.getInstance() // Initialize Firestore

        // Set up button click listener
        binding.btnSubmit.setOnClickListener {
            saveFoodDetails()
        }

        return binding.root
    }


    private fun saveFoodDetails() {
            val hotelName = binding.etHotelName.text.toString().trim()
            val foodName = binding.etFoodName.text.toString().trim()
            val quantity = binding.etQuantity.text.toString().trim()
            val location = binding.etLocation.text.toString().trim()
            val preparationTime = binding.etPreparationTime.text.toString().trim()
            val expiryTime = binding.etExpiryTime.text.toString().trim()
            val contactNumber = binding.etContact.text.toString().trim()
            val specialNotes = binding.etNotes.text.toString().trim()

            // Validate inputs
            if (hotelName.isEmpty() || foodName.isEmpty() || quantity.isEmpty() ||
                location.isEmpty() || preparationTime.isEmpty() ||
                expiryTime.isEmpty() || contactNumber.isEmpty()
            ) {
                Toast.makeText(requireContext(), "Please fill all required fields", Toast.LENGTH_SHORT).show()
                return
            }

            // Create a data object
            val foodPost = hashMapOf(
                "hotelName" to hotelName,
                "foodName" to foodName,
                "quantity" to quantity,
                "location" to location,
                "preparationTime" to preparationTime,
                "expiryTime" to expiryTime,
                "contactNumber" to contactNumber,
                "specialNotes" to specialNotes,
                "timestamp" to System.currentTimeMillis()
            )

            // Store data in Firestore under "FoodPosts" collection
            firestore.collection("FoodPosts")
                .add(foodPost)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "Food details posted successfully!", Toast.LENGTH_SHORT).show()
                    clearFields()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(requireContext(), "Failed to post: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }


        private fun clearFields() {
            binding.etHotelName.text.clear()
            binding.etFoodName.text.clear()
            binding.etQuantity.text.clear()
            binding.etLocation.text.clear()
            binding.etPreparationTime.text.clear()
            binding.etExpiryTime.text.clear()
            binding.etContact.text.clear()
            binding.etNotes.text.clear()
        }

        override fun onStart() {
            super.onStart()
            // Hide the FAB when the fragment is visible
            (activity as DonorFrontpage).hideFab()
        }

        override fun onStop() {
            super.onStop()
            // Show the FAB again when navigating away from the fragment
            (activity as DonorFrontpage).showFab()
        }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null // Clean up the binding
        }


    }







