package com.example.e_learning.viewmodels

import android.app.Notification
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_learning.data.Courses
import com.example.e_learning.util.Resource
import com.example.e_learning.util.auth
import com.example.e_learning.util.coursesCollection
import com.example.e_learning.util.fireStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RCV1ViewModel: ViewModel() {

    private val _coursesList = MutableStateFlow<Resource<List<Courses>>>(Resource.Unspecified())
    val courseList = _coursesList.asStateFlow()

    init {
        fetchNotifications()
    }

    private fun fetchNotifications() {
        viewModelScope.launch {
            _coursesList.emit(Resource.Loading())
        }
        fireStore.collection(coursesCollection).get()
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
}