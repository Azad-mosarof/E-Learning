package com.example.e_learning.activity.temp

import android.app.ProgressDialog
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.e_learning.R
import com.example.e_learning.databinding.ActivityPdfviewerBinding
import com.example.e_learning.util.storage
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class PDFViewer : AppCompatActivity() {

    private lateinit var binding: ActivityPdfviewerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPdfviewerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()

        /*============= Toolbar properties==============*/

        val toolBar: ConstraintLayout = findViewById(R.id.toolBar)
        toolBar.setBackgroundColor(resources.getColor(R.color.purple_700))
        val toolBarTitle: TextView = findViewById(R.id.tootlBarTitle)
        toolBarTitle.text = intent.getStringExtra("title")
        val backIcon: ImageView = findViewById(R.id.toolBarBackIcon)
        backIcon.setOnClickListener{
            finish()
            overridePendingTransition(R.drawable.slide_in_left, R.drawable.slide_out_right)
        }

        /*============================================*/

        /*================Loading pdf file from Firebase==============*/

        val materialUrl = intent.getStringExtra("materialUrl")
        val storage = FirebaseStorage.getInstance()
        val pdfRef = storage.getReferenceFromUrl(materialUrl!!)

        // Download the PDF file to a local directory
        val localFile = File.createTempFile("pdf", "file")
        val pd = ProgressDialog(this)
        pd.setTitle("File Loading")
        pd.show()
        pdfRef.getFile(localFile).addOnSuccessListener {
            binding.pdfView.fromFile(localFile).load()
            pd.dismiss()
        }.addOnFailureListener {
            Log.e("PDFView", "Failed to download PDF file from Firebase Storage: ${it.message}")
            pd.dismiss()
        }

        /*======================================================*/
    }
}