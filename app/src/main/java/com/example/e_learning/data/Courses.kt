package com.example.e_learning.data

data class Courses(
    val id: String,
    val c_name: String,
    val imgs: List<String>,
    val features: String,
    val courseTag: Int
) {
    constructor(): this("","", imgs=emptyList(), "", 0)
}