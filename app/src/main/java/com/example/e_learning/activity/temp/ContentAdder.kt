package com.example.e_learning.activity.temp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.e_learning.data.CourseContent
import com.example.e_learning.databinding.ActivityContentAdderBinding
import com.example.e_learning.util.contentCollection
import com.example.e_learning.util.coursesCollection
import com.example.e_learning.util.fireStore
import java.util.*

class ContentAdder : AppCompatActivity() {

    private lateinit var binding: ActivityContentAdderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContentAdderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val courseId = intent.getStringExtra("courseId")
        var contentCounter = 0

        binding.add.setOnClickListener{
            val id = UUID.randomUUID().toString()
            val courseContent = CourseContent(binding.contentName.text.toString(), id)
            fireStore.collection(coursesCollection).document(courseId!!).collection(contentCollection).add(courseContent)
                .addOnSuccessListener {
                    Toast.makeText(this, "Content successfully added", Toast.LENGTH_SHORT).show()
                    contentCounter++
                    binding.contentCounter.text = contentCounter.toString()
                    binding.contentName.text.clear()
                }
                .addOnFailureListener{
                    Toast.makeText(this, it.message.toString(),Toast.LENGTH_SHORT).show()
                }
        }
    }
}