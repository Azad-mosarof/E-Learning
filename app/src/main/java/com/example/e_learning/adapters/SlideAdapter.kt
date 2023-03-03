package com.example.e_learning.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.example.e_learning.R

class SlideAdapter(private val images: List<Int>) :
    RecyclerView.Adapter<SlideAdapter.ImageSliderViewHolder>() {

    inner class ImageSliderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageSliderViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.auto_image_slider, parent, false)
        return ImageSliderViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageSliderViewHolder, position: Int) {
        val imageView = holder.itemView.findViewById<ImageView>(R.id.imageView)
        imageView.setImageResource(images[position])
    }

    override fun getItemCount(): Int {
        return images.size
    }
}

