package com.example.e_learning.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.e_learning.activity.temp.PDFViewer
import com.example.e_learning.data.SubContent
import com.example.e_learning.databinding.SubContentLayoutBinding

class SubContentAdapter(val context: Context): RecyclerView.Adapter<SubContentAdapter.SubContentViewHolder>()  {

    inner class SubContentViewHolder(private val binding: SubContentLayoutBinding):
        RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(course: SubContent){
            binding.apply {
                subContentName.text = course.header
                ll1.setOnClickListener{
                    val intent = Intent(context, PDFViewer::class.java)
                    intent.putExtra("materialUrl", course.materialUrl)
                    intent.putExtra("title", course.header)
                    context.startActivity(intent)
                }
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