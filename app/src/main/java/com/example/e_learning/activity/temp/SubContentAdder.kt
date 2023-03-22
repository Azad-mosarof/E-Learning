package com.example.e_learning.activity.temp

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.e_learning.R
import com.example.e_learning.data.SubContent
import com.example.e_learning.databinding.ActivitySubContentAdderBinding
import com.example.e_learning.util.*
import java.util.*

class SubContentAdder : AppCompatActivity() {

    private lateinit var binding: ActivitySubContentAdderBinding
    var filePath: Uri? = null
    var subContentCounter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubContentAdderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val contentId = intent.getStringExtra("contentId")
        val courseId = intent.getStringExtra("courseId")
        val id = UUID.randomUUID().toString()

        binding.browes.setOnClickListener{
            val intent = Intent()
            intent.type = "application/pdf"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select pdf files"),101)
        }

        binding.back.setOnClickListener{
            finish()
            overridePendingTransition(R.drawable.slide_in_left, R.drawable.slide_out_right)
        }

        binding.addSubContent.setOnClickListener{
            uploadData(filePath, courseId!!, contentId!!, id)
        }

    }

    private fun uploadData(filePath: Uri?, courseId:String, contentId: String, subContentId:String) {
        val pd = ProgressDialog(this)
        pd.setTitle("File Uploading")
        pd.show()

        val ref = storage.child("uploads/"+System.currentTimeMillis()+".pdf")

        ref.putFile(filePath!!).addOnSuccessListener {
                ref.downloadUrl.addOnSuccessListener { uri ->
                    val subContent = SubContent(binding.subContentName.text.toString(), subContentId, uri.toString())
                    fireStore.collection(coursesCollection).document(courseId).collection(contentCollection)
                        .document(contentId).collection(subContentCollection).document(subContentId).set(subContent)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Sub Content successfully added", Toast.LENGTH_SHORT).show()
                            subContentCounter++
                            binding.subContentCounter.text = subContentCounter.toString()
                            binding.subContentName.text.clear()
                            pd.dismiss()
                        }
                        .addOnFailureListener{
                            Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
                            pd.dismiss()
                        }
                }
            }
            .addOnFailureListener{
                Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT)
                pd.dismiss()
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 101 && resultCode == RESULT_OK){
            filePath = data!!.data
        }
    }

}