package com.example.e_learning.fragments


import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore.Images.Media.getBitmap
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.e_learning.data.Courses
import com.example.e_learning.databinding.FragmentProfileBinding
import com.example.e_learning.util.coursesCollection
import com.example.e_learning.util.fireStore
import com.example.e_learning.util.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.util.*

class Profile : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private var selectedImages = mutableListOf<Uri>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.uploadCourse.setOnClickListener{
            val productValidation = validateInformation()
            if(!productValidation){
                Toast.makeText(requireContext(),"Please check your inputs", Toast.LENGTH_SHORT).show()
            }
            else{
                saveProduct()
            }
        }

        val selectIMagesActivityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if(result.resultCode == AppCompatActivity.RESULT_OK){
                val intent = result.data

                //Multiple images selected
                if(intent?.clipData != null){
                    val count = intent.clipData?.itemCount
                    (0 until count!!).forEach{
                        val imageUri = intent.clipData?.getItemAt(it)?.uri
                        imageUri?.let { imgUri ->
                            selectedImages.add(imgUri)
                        }
                    }
                }else{
                    val imageUri = intent?.data
                    imageUri?.let { selectedImages.add(it) }
                }
                updateImages()
            }
        }

        binding.courseImage.setOnClickListener{
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true)
            intent.type = "image/*"
            selectIMagesActivityResult.launch(intent)
        }
    }

    private fun saveProduct() {
        val courseName = binding.courseTitle.text.toString().trim()
        val features = binding.features.text.toString().trim()
        val imagesByteArray = getImagesByteArrays()
        val images = mutableListOf<String>()

        lifecycleScope.launch(Dispatchers.IO){
            withContext(Dispatchers.Main){
                showLoading()
            }
            try{
                withContext(Dispatchers.Default) {
                    imagesByteArray.forEach {ByteArr ->
                        val id = UUID.randomUUID().toString()
                        launch {
                            val imageStorage = storage.child("Courses/images/$id")
                            val result = imageStorage.putBytes(ByteArr).await()
                            val downloadUrl = result.storage.downloadUrl.await().toString()
                            images.add(downloadUrl)
                        }
                    }
                }
            }catch (e: Exception){
                e.printStackTrace()
                withContext(Dispatchers.Main){
                    hideLoading()
                }
            }
            val course = Courses(
                UUID.randomUUID().toString(),
                courseName, images, features)
            fireStore.collection(coursesCollection).document(course.id).set(course)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "Course added successfully", Toast.LENGTH_SHORT).show()
                    hideLoading()
                }
                .addOnFailureListener{
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    hideLoading()
                }
        }
    }

    private fun showLoading() {
        binding.progressbar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.progressbar.visibility = View.INVISIBLE
    }

    private fun updateImages() {
        binding.totalImg.text = selectedImages.size.toString()
    }

    private fun validateInformation(): Boolean {
        if(binding.courseTitle.text.toString().trim().isEmpty())
            return false
        if(binding.features.text.toString().trim().isEmpty())
            return false
        if(selectedImages.isEmpty())
            return false

        return true
    }

    private fun getImagesByteArrays(): List<ByteArray> {
        val imagesByteArray = mutableListOf<ByteArray>()
        selectedImages.forEach{
            val stream = ByteArrayOutputStream()
            val imgBmp = getBitmap(requireContext().contentResolver, it)
            if(imgBmp.compress(Bitmap.CompressFormat.JPEG,100,stream)){
                imagesByteArray.add(stream.toByteArray())
            }
        }
        return imagesByteArray
    }

}