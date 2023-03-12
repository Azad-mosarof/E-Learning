package com.example.e_learning.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_learning.data.Courses
import com.example.e_learning.util.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class myCoursesViewModel: ViewModel() {
    private val _myCourses = MutableStateFlow<Resource<List<Courses>>>(Resource.Unspecified())
    val myCourses = _myCourses

    init {
        fetchMyCourses()
    }

    private fun fetchMyCourses() {
        viewModelScope.launch {
            _myCourses.emit(Resource.Loading())
        }
        fireStore.collection(UserCollection).document(auth.currentUser!!.uid).collection(MyCourses).get()
            .addOnSuccessListener {
                viewModelScope.launch {
                    _myCourses.emit(Resource.Success(it.toObjects(Courses::class.java)))
                }
            }
            .addOnFailureListener{
                viewModelScope.launch {
                    _myCourses.emit(Resource.Error(it.message))
                }
            }
    }

}