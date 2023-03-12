package com.example.e_learning.util

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

var fireStore: FirebaseFirestore = FirebaseFirestore.getInstance()
var auth: FirebaseAuth = FirebaseAuth.getInstance()
val storageRef = FirebaseStorage.getInstance().reference
val storage = Firebase.storage.reference
var db = Firebase.firestore
const val UserCollection = "Users"
const val coursesCollection = "Courses"
const val contentCollection = "Content_Collection"
const val subContentCollection = "Sub_Content_Collection"
const val MyCourses = "MyCourses"
val TAG = "eLearning"
const val login_reg: Int = 100
const val logReg_reg: Int = 101