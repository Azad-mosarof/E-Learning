package com.example.e_learning.activity.temp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.e_learning.R
import com.example.e_learning.data.CourseContent
import com.example.e_learning.data.SubContent
import com.example.e_learning.databinding.ActivitySubContentAdderBinding
import com.example.e_learning.util.contentCollection
import com.example.e_learning.util.coursesCollection
import com.example.e_learning.util.fireStore
import com.example.e_learning.util.subContentCollection
import java.util.*

class SubContentAdder : AppCompatActivity() {

    private lateinit var binding: ActivitySubContentAdderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubContentAdderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val contentId = intent.getStringExtra("contentId")
        val courseId = intent.getStringExtra("courseId")

        var subContentCounter = 0

        binding.back.setOnClickListener{
            finish()
            overridePendingTransition(R.drawable.slide_in_left, R.drawable.slide_out_right)
        }

        binding.addSubContent.setOnClickListener{
            val id = UUID.randomUUID().toString()
            val subContent = SubContent(binding.subContentName.text.toString(), id)

            fireStore.collection(coursesCollection).document(courseId!!).collection(contentCollection)
                .document(contentId!!).collection(subContentCollection).document(id).set(subContent)
                .addOnSuccessListener {
                    Toast.makeText(this, "Sub Content successfully added", Toast.LENGTH_SHORT).show()
                    subContentCounter++
                    binding.subContentCounter.text = subContentCounter.toString()
                    binding.subContentName.text.clear()
                }
                .addOnFailureListener{
                    Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
                }
        }
    }

}