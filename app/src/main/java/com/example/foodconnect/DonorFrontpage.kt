package com.example.foodconnect

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.foodconnect.databinding.ActivityDonorFrontpageBinding
import com.google.android.material.navigation.NavigationView
import android.view.View
import androidx.core.view.GravityCompat



class DonorFrontpage : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var fragmentManager: FragmentManager
    private lateinit var binding: ActivityDonorFrontpageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDonorFrontpageBinding.inflate(layoutInflater)
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
                R.id.bottom_home -> openFragment(HomeFragment())
                R.id.bottom_profile -> openFragment(ProfileFragment())
            }
            true
        }
        fragmentManager = supportFragmentManager
        openFragment(HomeFragment())

        // FAB Click Listener: Open SampleDonor & Hide FAB
        binding.fab.setOnClickListener {
            openFragment(Donor_FoodPostingPage())
        }
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

    // ✅ Ensure this function is inside the class body
    private fun openFragment(fragment: Fragment) {
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()

        // Hide FAB only when opening SampleDonor
        if (fragment is Donor_FoodPostingPage) {
            hideFab()
        } else {
            showFab()
        }
    }

    // Public methods to show/hide FAB
    fun hideFab() {
        binding.fab.visibility = View.GONE
    }

    fun showFab() {
        binding.fab.visibility = View.VISIBLE
    }
}



