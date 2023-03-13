package com.example.e_learning.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
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
    var visibility: Boolean = true

    private val courseContentAdapter: CourseContentAdapter by lazy { CourseContentAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseContentListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()

        val toolBar: ConstraintLayout = findViewById(R.id.toolBar)
        toolBar.setBackgroundColor(resources.getColor(R.color.purple_700))
        val tootlBarTitle: TextView = findViewById(R.id.tootlBarTitle)
        tootlBarTitle.setText("Course Content")
        val backIcon: ImageView = findViewById(R.id.toolBarBackIcon)
        backIcon.setOnClickListener{
            finish()
            overridePendingTransition(R.drawable.slide_in_left, R.drawable.slide_out_right)
        }

        binding.dropdownMenu.setOnClickListener{
            if(visibility){
                binding.courseContentRV.visibility = View.GONE
                visibility = false
                binding.dropdownMenu.setImageResource(R.drawable.arrow_drop_down)
            }
            else{
                binding.courseContentRV.visibility = View.VISIBLE
                visibility = true
                binding.dropdownMenu.setImageResource(R.drawable.arrow_drop_up)
            }
        }

        val courseId = intent.getStringExtra("courseId")
        setUpCourseContentRV(courseId!!)
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