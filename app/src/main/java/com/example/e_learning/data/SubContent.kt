package com.example.e_learning.data

data class SubContent(
    val header: String,
    val id: String,
    val materialUrl: String
) {
    constructor(): this("","","")
}