package com.example.e_learning.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_learning.data.Courses
import com.example.e_learning.util.Resource
import com.example.e_learning.util.coursesCollection
import com.example.e_learning.util.fireStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RCV1ViewModel: ViewModel() {

    private val _coursesList = MutableStateFlow<Resource<List<Courses>>>(Resource.Unspecified())
    val courseList = _coursesList.asStateFlow()

    private val _topCoursesList = MutableStateFlow<Resource<List<Courses>>>(Resource.Unspecified())
    val topCourseList = _topCoursesList.asStateFlow()

    init {
        fetchCourses()
        fetchTopCourses()
    }

    private fun fetchCourses() {
        viewModelScope.launch {
            _coursesList.emit(Resource.Loading())
        }
        fireStore.collection(coursesCollection).whereEqualTo("courseTag",0).get()
            .addOnSuccessListener {
                viewModelScope.launch {
                    _coursesList.emit(Resource.Success(it.toObjects(Courses::class.java)))
                }
            }
            .addOnFailureListener{
                viewModelScope.launch {
                    _coursesList.emit(Resource.Error(it.message))
                }
            }
    }

    private fun fetchTopCourses() {
        viewModelScope.launch {
            _topCoursesList.emit(Resource.Loading())
        }
        fireStore.collection(coursesCollection).whereEqualTo("courseTag",1).get()
            .addOnSuccessListener {
                viewModelScope.launch {
                    _topCoursesList.emit(Resource.Success(it.toObjects(Courses::class.java)))
                }
            }
            .addOnFailureListener{
                viewModelScope.launch {
                    _topCoursesList.emit(Resource.Error(it.message))
                }
            }
    }
}