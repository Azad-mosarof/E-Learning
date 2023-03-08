package com.example.e_learning.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_learning.R
import com.example.e_learning.adapters.RCV1Adapter
import com.example.e_learning.adapters.RecentlyViewedRVAdapter
import com.example.e_learning.adapters.SlideAdapter
import com.example.e_learning.adapters.TopCoursesAdapter
import com.example.e_learning.databinding.FragmentHomeFragmentBinding
import com.example.e_learning.viewmodels.RCV1ViewModel
import kotlinx.coroutines.flow.collectLatest
import java.util.*

class home_fragment : Fragment() {

    private lateinit var binding: FragmentHomeFragmentBinding
    val slides = listOf(
        R.drawable.slide_1,
        R.drawable.slide_2,
        R.drawable.slide_3
    )

    var l1 = false
    var l2 = false

    lateinit var rcv1Viewmodel: RCV1ViewModel

    private val handler = Handler(Looper.getMainLooper())
    private var currentPosition = 0

    private val rcv1Adapter: RCV1Adapter by lazy { RCV1Adapter() }
    private val topCoursesAdapter: TopCoursesAdapter by lazy { TopCoursesAdapter() }
    private val recentlyViewedAdapter: RecentlyViewedRVAdapter by lazy { RecentlyViewedRVAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeFragmentBinding.inflate(layoutInflater)
        rcv1Viewmodel = ViewModelProvider(this)[RCV1ViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHomeViewPager()
        setUpAdapter()
        setUpContinueReading()
        setUpTopCourses()
        setUpRecentlyViewed()
        setUpSoftwareDev()
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

    private fun setUpAdapter(){
        binding.rcv1.apply {
            adapter = rcv1Adapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        lifecycleScope.launchWhenStarted {
            rcv1Viewmodel.courseList.collectLatest {
                when(it){
                    is com.example.e_learning.util.Resource.Loading -> {
                        loading()
                    }
                    is com.example.e_learning.util.Resource.Success -> {
                        rcv1Adapter.differ.submitList(it.data)
                        l1 = true
                        loading()
                    }
                    is com.example.e_learning.util.Resource.Error -> {
                        loading()
                    }
                }
            }
        }
    }

    private fun setUpTopCourses(){
        binding.topCoursesRV.apply {
            adapter = topCoursesAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        lifecycleScope.launchWhenStarted {
            rcv1Viewmodel.topCourseList.collectLatest {
                when(it){
                    is com.example.e_learning.util.Resource.Loading -> {
                        loading()
                    }
                    is com.example.e_learning.util.Resource.Success -> {
                        topCoursesAdapter.differ.submitList(it.data)
                        l2 = true
                        loading()
                    }
                    is com.example.e_learning.util.Resource.Error -> {
                        loading()
                    }
                }
            }
        }
    }

    private fun setUpContinueReading(){
        binding.continueReadingRV.apply {
            adapter = topCoursesAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        lifecycleScope.launchWhenStarted {
            rcv1Viewmodel.topCourseList.collectLatest {
                when(it){
                    is com.example.e_learning.util.Resource.Loading -> {
                        loading()
                    }
                    is com.example.e_learning.util.Resource.Success -> {
                        topCoursesAdapter.differ.submitList(it.data)
                        l2 = true
                        loading()
                    }
                    is com.example.e_learning.util.Resource.Error -> {
                        loading()
                    }
                }
            }
        }
    }

    private fun setUpRecentlyViewed(){
        binding.recentlyViewedRV.apply {
            adapter = recentlyViewedAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        lifecycleScope.launchWhenStarted {
            rcv1Viewmodel.topCourseList.collectLatest {
                when(it){
                    is com.example.e_learning.util.Resource.Loading -> {
                        loading()
                    }
                    is com.example.e_learning.util.Resource.Success -> {
                        recentlyViewedAdapter.differ.submitList(it.data)
                        l2 = true
                        loading()
                    }
                    is com.example.e_learning.util.Resource.Error -> {
                        loading()
                    }
                }
            }
        }
    }

    private fun setUpSoftwareDev(){
        binding.softwareDevRV.apply {
            adapter = recentlyViewedAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        lifecycleScope.launchWhenStarted {
            rcv1Viewmodel.topCourseList.collectLatest {
                when(it){
                    is com.example.e_learning.util.Resource.Loading -> {
                        loading()
                    }
                    is com.example.e_learning.util.Resource.Success -> {
                        recentlyViewedAdapter.differ.submitList(it.data)
                        l2 = true
                        loading()
                    }
                    is com.example.e_learning.util.Resource.Error -> {
                        loading()
                    }
                }
            }
        }
    }

    private fun showLoading() {
        binding.home.visibility = View.GONE
        binding.progressbar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.home.visibility = View.VISIBLE
        binding.progressbar.visibility = View.INVISIBLE
    }

    private fun loading(){
        if(l1 && l2)
            hideLoading()
        else
            showLoading()
    }
}