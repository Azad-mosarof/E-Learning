package com.example.e_learning.fragments

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.e_learning.databinding.FragmentSearchBinding


class Search : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }


}


//Code for Automatic scroll view
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val handler = Handler()
//        val delay: Long = 100 // Scroll interval in milliseconds
//        val runnable = object : Runnable {
//            override fun run() {
//                val scrollY = binding.scrollView.scrollY
//                binding.scrollView.smoothScrollTo(0, scrollY + 3) // Scroll by 1 pixel
//                handler.postDelayed(this, delay)
//            }
//        }
//        handler.postDelayed(runnable, delay)
//    }