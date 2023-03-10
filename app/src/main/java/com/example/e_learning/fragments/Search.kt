package com.example.e_learning.fragments

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SearchView
import com.example.e_learning.adapters.SearchListAdapter
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val courses = listOf<String>("C", "C++", "CRS", "Computer Vision", "Java", "Python", "Kotlin", "Java Script",
                "HTML", "CSS", "Node JS", "Software Development", "Android Development",
                "Web Development")

//        val courseAdapter: ArrayAdapter<String> = ArrayAdapter(
//            requireContext(), android.R.layout.simple_list_item_1,
//            courses
//        )

        val courseAdapter = SearchListAdapter(requireContext(), courses)
        binding.courseList.adapter = courseAdapter

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.search.clearFocus()
                if(courses.contains(query)){
//                    binding.courseList.visibility = View.VISIBLE
                    courseAdapter.filter.filter(query)
                }
//                if(query =="")
//                    binding.courseList.visibility = View.GONE
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                binding.courseList.visibility = View.VISIBLE
                courseAdapter.filter.filter(newText)
//                if(newText =="")
//                    binding.courseList.visibility = View.GONE
                return false
            }

        })
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