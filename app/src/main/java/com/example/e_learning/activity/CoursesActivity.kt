package com.example.e_learning.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.e_learning.R
import com.example.e_learning.databinding.ActivityCoursesBinding

class CoursesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoursesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoursesBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}