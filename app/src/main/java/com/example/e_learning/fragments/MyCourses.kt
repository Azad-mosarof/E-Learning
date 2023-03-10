package com.example.e_learning.fragments

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.registerReceiver
import com.example.e_learning.R
import com.example.e_learning.databinding.FragmentMyCoursesBinding
import com.example.e_learning.databinding.FragmentSearchBinding

class MyCourses : Fragment() {


    private lateinit var binding: FragmentMyCoursesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyCoursesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.download.setOnClickListener{
            downloadVideo(binding.link.text.toString())
            Log.i("url", binding.link.text.toString())
        }
    }

    private fun downloadVideo(url:String){

// Create a DownloadManager.Request object
        val request = DownloadManager.Request(Uri.parse(url))
            .setTitle("My Video") // Set the title of the download
            .setDescription("Downloading...") // Set the description of the download
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE) // Show a notification while downloading
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "myvideo.mp4") // Set the destination directory and filename

// Get a reference to the DownloadManager system service
        val downloadManager = requireContext().getSystemService(DOWNLOAD_SERVICE) as DownloadManager

// Enqueue the download request and get the download ID
        val downloadId = downloadManager.enqueue(request)

// You can listen for download status updates using a BroadcastReceiver
        val broadcastReceiver = object : BroadcastReceiver() {
            @SuppressLint("Range")
            override fun onReceive(context: Context?, intent: Intent?) {
                val action = intent?.action
                if (DownloadManager.ACTION_DOWNLOAD_COMPLETE == action) {
                    // Get the download ID from the intent
                    val downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0)
                    // Check if the download is successful
                    val query = DownloadManager.Query().apply {
                        setFilterById(downloadId)
                    }
                    val cursor = downloadManager.query(query)
                    if (cursor.moveToFirst()) {
                        val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                        if (status == DownloadManager.STATUS_SUCCESSFUL) {
                            // The download is successful, do something with the downloaded file
                        }
                    }
                }
            }
        }

        requireContext().registerReceiver(broadcastReceiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    }

}