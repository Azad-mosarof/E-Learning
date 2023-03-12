package com.example.e_learning.activity.userAuth

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.e_learning.R
import com.example.e_learning.activity.Home
import com.example.e_learning.databinding.ActivityIntroductionBinding

class Introduction : AppCompatActivity() {

    private lateinit var binding: ActivityIntroductionBinding
    private val SHARED_PREFS = "eLearningSharedPrefs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroductionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkBox()

        binding.startButton.setOnClickListener{
            val intent = Intent(this@Introduction,LoginSignin::class.java)
            startActivity(intent)
            overridePendingTransition(R.drawable.slide_in_right, R.drawable.slide_out_left)
        }
    }

    private fun checkBox() {
        val sharedPreferences: SharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)
        val check: String? = sharedPreferences.getString("name", "")
        if(check.equals("true")){
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
            finish()
        }
    }
}