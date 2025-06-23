package com.example.foodconnect

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodconnect.databinding.EachItemBinding

class CarouselAdapter(private val imageList : MutableList<Int>) :
    RecyclerView.Adapter<CarouselAdapter.CarouseViewHolder>(){

    inner class CarouseViewHolder(private val binding: EachItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(image:Int){
            binding.imageView.setImageResource(image)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouseViewHolder {
        return CarouseViewHolder(
            EachItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false))


    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: CarouseViewHolder, position: Int) {
        holder.bind(imageList[position])
    }
}