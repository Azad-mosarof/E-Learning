package com.example.e_learning.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.e_learning.data.SubContent
import com.example.e_learning.databinding.SubContentLayoutBinding

class SubContentAdapter: RecyclerView.Adapter<SubContentAdapter.SubContentViewHolder>()  {

    inner class SubContentViewHolder(private val binding: SubContentLayoutBinding):
        RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(course: SubContent){
            binding.apply {
                subContentName.text = course.header
            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<SubContent>(){
        override fun areItemsTheSame(oldItem: SubContent, newItem: SubContent): Boolean {
            return oldItem.header == newItem.header
        }

        override fun areContentsTheSame(oldItem: SubContent, newItem: SubContent): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubContentViewHolder {
        return SubContentViewHolder(
            SubContentLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: SubContentViewHolder, position: Int) {
        val course = differ.currentList[position]
        holder.bind(course)
    }
}