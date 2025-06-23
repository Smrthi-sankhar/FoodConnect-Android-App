package com.example.foodconnect

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.foodconnect.databinding.FragmentDonorProfileBinding

class DonorProfileFragment : Fragment() {
    private var _binding: FragmentDonorProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDonorProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve the arguments passed from ProfileActivity
        val name = arguments?.getString("name")
        val email = arguments?.getString("email")
        val phone = arguments?.getString("phone")

        // Set the retrieved data into the views
        binding.textViewName.text = name
        binding.textViewEmail.text = email
        binding.textViewPhone.text = phone
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
