package com.example.foodconnect

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class ContactUsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_contact_us, container, false)

        val tvPhone = view.findViewById<TextView>(R.id.tvPhone)

        tvPhone.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:+912222277777")
            startActivity(intent)
        }


        val tvEmail = view.findViewById<TextView>(R.id.tvEmail)

        tvEmail.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:foodconnectteam@gmail.com")
            intent.putExtra(Intent.EXTRA_SUBJECT, "Support Request")
            startActivity(intent)
        }


        return view
    }


}
