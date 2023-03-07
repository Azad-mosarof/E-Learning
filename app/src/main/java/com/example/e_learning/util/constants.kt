package com.example.e_learning.util

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

var fireStore: FirebaseFirestore = FirebaseFirestore.getInstance()
var auth: FirebaseAuth = FirebaseAuth.getInstance()
val storageRef = FirebaseStorage.getInstance().reference
val storage = Firebase.storage.reference
val coursesCollection = "Courses"