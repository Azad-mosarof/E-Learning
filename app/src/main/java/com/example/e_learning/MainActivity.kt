package com.example.e_learning

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.viewpager.widget.ViewPager
import com.example.e_learning.adapters.SlideAdapter
import com.example.e_learning.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val slides = listOf(
        R.drawable.slide_1,
        R.drawable.slide_2,
        R.drawable.slide_3
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        setHomeViewPager()
    }

    private fun setHomeViewPager(){
        val adapter = SlideAdapter(this, slides)
        binding.homeViewPager.adapter = adapter

        val timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    val currentItem = binding.homeViewPager.currentItem
                    val nextItem = if (currentItem == slides.size - 1) 0 else currentItem + 1
                    binding.homeViewPager.setCurrentItem(nextItem, true)
                }
            }
        }, 0, 10000)
    }

}