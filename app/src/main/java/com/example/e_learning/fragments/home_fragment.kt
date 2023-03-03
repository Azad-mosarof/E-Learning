package com.example.e_learning.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.e_learning.R
import com.example.e_learning.adapters.SlideAdapter
import com.example.e_learning.databinding.FragmentHomeFragmentBinding
import java.util.*

class home_fragment : Fragment() {

    private lateinit var binding: FragmentHomeFragmentBinding
    val slides = listOf(
        R.drawable.slide_1,
        R.drawable.slide_2,
        R.drawable.slide_3
    )

    private val handler = Handler(Looper.getMainLooper())
    private var currentPosition = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHomeViewPager()
    }

    private fun setHomeViewPager(){
        binding.homeViewPager.adapter = SlideAdapter(getImages())

        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                handler.post {
                    if (currentPosition == getImages().size) {
                        currentPosition = 0
                    }
                    binding.homeViewPager.setCurrentItem(currentPosition++, true)
                }
            }
        }, 3000, 7000)
    }

    private fun getImages(): List<Int> {
        return listOf(R.drawable.slide_1, R.drawable.slide_2, R.drawable.slide_3)
    }

}