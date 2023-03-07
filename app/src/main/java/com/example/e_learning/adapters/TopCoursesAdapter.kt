package com.example.e_learning.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_learning.data.Courses
import com.example.e_learning.databinding.TopCourseLayoutBinding

class TopCoursesAdapter: RecyclerView.Adapter<TopCoursesAdapter.TopCoursesViewHolder>() {

    inner class TopCoursesViewHolder(private val binding: TopCourseLayoutBinding):
        RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(course: Courses){
            binding.apply {
                courseName.text = course.c_name
                Glide.with(itemView).load(course.imgs[0]).into(courseImg)
                features.text = course.features
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopCoursesAdapter.TopCoursesViewHolder {
        return TopCoursesViewHolder(
            TopCourseLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: TopCoursesAdapter.TopCoursesViewHolder, position: Int) {
        val course = differ.currentList[position]
        holder.bind(course)
    }
}