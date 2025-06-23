package com.example.foodconnect

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.foodconnect.databinding.ActivityMainBinding
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager =CarouselLayoutManager()
        CarouselSnapHelper().attachToRecyclerView(binding.recyclerView)

        val imageList =mutableListOf<Int>()
        imageList.add(R.drawable.one)
        imageList.add(R.drawable.two)
        imageList.add(R.drawable.three)

        val adapter =CarouselAdapter(imageList)
        binding.recyclerView.adapter =adapter

        binding.nextButton.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }
    }
}