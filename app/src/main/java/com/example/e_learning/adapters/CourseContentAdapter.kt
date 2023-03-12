package com.example.e_learning.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.e_learning.data.CourseContent
import com.example.e_learning.databinding.CourseDropDownContentBinding

class CourseContentAdapter: RecyclerView.Adapter<CourseContentAdapter.CourseContentViewHolder>()  {

    inner class CourseContentViewHolder(private val binding: CourseDropDownContentBinding):
        RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(course: CourseContent){
            binding.apply {
                courseContentName.text = course.header
            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<CourseContent>(){
        override fun areItemsTheSame(oldItem: CourseContent, newItem: CourseContent): Boolean {
            return oldItem.header == newItem.header
        }

        override fun areContentsTheSame(oldItem: CourseContent, newItem: CourseContent): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseContentAdapter.CourseContentViewHolder {
        return CourseContentViewHolder(
            CourseDropDownContentBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: CourseContentViewHolder, position: Int) {
        val course = differ.currentList[position]
        holder.bind(course)
    }
}


