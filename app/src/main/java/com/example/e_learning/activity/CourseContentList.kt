package com.example.e_learning.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_learning.R
import com.example.e_learning.adapters.CourseContentAdapter
import com.example.e_learning.adapters.TopCoursesAdapter
import com.example.e_learning.data.Courses
import com.example.e_learning.databinding.ActivityCourseContentListBinding
import com.example.e_learning.util.Resource
import com.example.e_learning.util.coursesCollection
import com.example.e_learning.util.fireStore
import com.example.e_learning.viewmodels.CourseContentViewModel
import com.example.e_learning.viewmodels.factory.CourseContentFactory
import com.example.e_shop.util.RegisterValidation
import kotlinx.coroutines.flow.collectLatest

class CourseContentList : AppCompatActivity() {

    private lateinit var binding: ActivityCourseContentListBinding

    private val courseContentAdapter: CourseContentAdapter by lazy { CourseContentAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseContentListBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

    private fun setUpCourseContentRV(courseId: String){

        binding.courseContentRV.apply {
            adapter = courseContentAdapter
            layoutManager = LinearLayoutManager(this@CourseContentList, LinearLayoutManager.VERTICAL, false)
        }

        val viewmodel by viewModels<CourseContentViewModel> {
            CourseContentFactory(courseId)
        }

        lifecycleScope.launchWhenStarted {
            viewmodel.courseContent.collectLatest {
                when(it){
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        courseContentAdapter.differ.submitList(it.data)
                    }
                    is Resource.Error -> {

                    }
                }
            }
        }
    }

}