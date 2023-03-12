package com.example.e_learning.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_learning.data.CourseContent
import com.example.e_learning.data.SubContent
import com.example.e_learning.util.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CourseContentViewModel(
    private val courseId: String
):ViewModel() {

    private val _courseContent = MutableStateFlow<Resource<List<CourseContent>>>(Resource.Unspecified())
    val courseContent = _courseContent.asStateFlow()

    private val _courseSubContent = MutableStateFlow<Resource<List<SubContent>>>(Resource.Unspecified())
    val courseSubContent = _courseSubContent.asStateFlow()

    init {
        fetchCourseContent()
    }

    private fun fetchCourseContent() {
        viewModelScope.launch {
            _courseContent.emit(Resource.Loading())
        }
        fireStore.collection(coursesCollection).document(courseId).collection(contentCollection).get()
            .addOnSuccessListener {
                viewModelScope.launch {
                    val courseContents = it.toObjects(CourseContent::class.java)
                    _courseContent.emit(Resource.Success(courseContents))
                    viewModelScope.launch {
                        _courseContent.emit(Resource.Loading())
                    }
                    for(content in courseContents){
                        fireStore.collection(coursesCollection).document(courseId)
                            .collection(contentCollection).document(content.id).collection(subContentCollection).get()
                            .addOnSuccessListener { subContent ->
                                viewModelScope.launch {
                                    _courseSubContent.emit(Resource.Success(subContent.toObjects(SubContent::class.java)))
                                }
                            }
                            .addOnFailureListener{ exp ->
                                viewModelScope.launch {
                                    _courseSubContent.emit(Resource.Error(exp.message))
                                }
                            }
                    }
                }
            }
            .addOnFailureListener{
                viewModelScope.launch {
                    _courseContent.emit(Resource.Error(it.message))
                }
            }
    }
}
