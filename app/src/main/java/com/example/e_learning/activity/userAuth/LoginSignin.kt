package com.example.e_learning.activity.userAuth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.e_learning.R
import com.example.e_learning.databinding.ActivityLoginSigninBinding
import com.example.e_learning.util.logReg_reg

class LoginSignin : AppCompatActivity() {

    private lateinit var binding: ActivityLoginSigninBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginSigninBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val toolBar: ConstraintLayout = findViewById(R.id.toolBar)
        toolBar.setBackgroundColor(resources.getColor(R.color.light_background))

        binding.loginBt.setOnClickListener{
            val intent = Intent(this,Login::class.java)
            startActivity(intent)
            overridePendingTransition(R.drawable.slide_in_right, R.drawable.slide_out_left)
        }

        binding.registerBt.setOnClickListener{
            val intent = Intent(this,Register::class.java)
            intent.putExtra("toReg", logReg_reg)
            startActivity(intent)
            overridePendingTransition(R.drawable.slide_in_right, R.drawable.slide_out_left)
        }

        val backArrow: ImageView = findViewById(R.id.toolBarBackIcon)
        backArrow.setOnClickListener{
            finish()
            overridePendingTransition(R.drawable.slide_in_left, R.drawable.slide_out_right)
        }
    }
}