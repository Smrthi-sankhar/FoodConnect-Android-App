package com.example.foodconnect

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.foodconnect.databinding.ActivityVolunteerFrontpageBinding
import com.google.android.material.navigation.NavigationView

class VolunteerFrontpage : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityVolunteerFrontpageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVolunteerFrontpageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, binding.toolbar, R.string.nav_open, R.string.nav_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        binding.navigationDrawer.setNavigationItemSelectedListener(this)

        binding.bottomNavigation.background = null
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_home -> openFragment(Volunteer_HomeFragment())
                R.id.bottom_profile -> openFragment(ProfileFragment())
                R.id.notification -> openFragment(VolunteerNotificationFragment()) // 🔹 Loads Firestore data
            }
            true
        }

        handleIntent(intent)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_privacy_policy -> openFragment(PrivacyFragment())
            R.id.nav_contact_us -> openFragment(ContactUsFragment())
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        val openFragment = intent.getStringExtra("openFragment")

        if (openFragment == "VolunteerNotificationFragment") {
            val foodId = intent.getStringExtra("foodId")
            val location = intent.getStringExtra("location")
            val quantity = intent.getStringExtra("quantity")
            val foodName = intent.getStringExtra("foodName")
            val hotelName = intent.getStringExtra("hotelName")
            val preparationTime = intent.getStringExtra("preparationTime")
            val expiryTime = intent.getStringExtra("expiryTime")
            val contactNumber = intent.getStringExtra("contactNumber")
            val specialNotes = intent.getStringExtra("specialNotes")

            Log.d("FCM", "Opening Notification Fragment with Food ID: $foodId")

            val fragment = VolunteerNotificationFragment()
            val bundle = Bundle().apply {
                putString("foodId", foodId)
                putString("location", location)
                putString("quantity", quantity)
                putString("foodName", foodName)
                putString("hotelName", hotelName)
                putString("preparationTime", preparationTime)
                putString("expiryTime", expiryTime)
                putString("contactNumber", contactNumber)
                putString("specialNotes", specialNotes)
            }
            fragment.arguments = bundle

            openFragment(fragment)
        } else {
            openFragment(Volunteer_HomeFragment())
        }
    }

}









