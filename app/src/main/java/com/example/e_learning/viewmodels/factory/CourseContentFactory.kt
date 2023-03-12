package com.example.e_learning.viewmodels.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.e_learning.viewmodels.CourseContentViewModel

class CourseContentFactory(
    private val courseId: String,
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CourseContentViewModel(courseId) as T
    }
}