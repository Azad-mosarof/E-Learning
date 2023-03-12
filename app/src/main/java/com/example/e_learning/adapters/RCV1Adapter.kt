package com.example.e_learning.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_learning.databinding.ActivityCoursesBinding
import com.example.e_learning.data.Courses

class RCV1Adapter: RecyclerView.Adapter<RCV1Adapter.RCV1ViewHolder>() {

    inner class RCV1ViewHolder(private val binding: ActivityCoursesBinding):
        RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(course: Courses){
            binding.apply {
                courseName.text = course.c_name
                Glide.with(itemView).load(course.imgs[0]).into(courseImg)
            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Courses>(){
        override fun areItemsTheSame(oldItem: Courses, newItem: Courses): Boolean {
            return oldItem.c_name == newItem.c_name
        }

        override fun areContentsTheSame(oldItem: Courses, newItem: Courses): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RCV1ViewHolder {
        return RCV1ViewHolder(
            ActivityCoursesBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: RCV1ViewHolder, position: Int) {
        val course = differ.currentList[position]
        holder.bind(course)
    }
}