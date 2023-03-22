package com.example.e_learning.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_learning.R
import com.example.e_learning.data.CourseContent
import com.example.e_learning.databinding.CourseDropDownContentBinding
import com.example.e_learning.util.Resource
import com.example.e_learning.viewmodels.CourseContentViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest

class CourseContentAdapter(val context: Context, val viewmodel: CourseContentViewModel, val lifecycleOwner: LifecycleOwner):
    RecyclerView.Adapter<CourseContentAdapter.CourseContentViewHolder>()  {

    var visibility = false

    inner class CourseContentViewHolder(private val binding: CourseDropDownContentBinding):
        RecyclerView.ViewHolder(binding.root){
        @OptIn(DelicateCoroutinesApi::class)
        @SuppressLint("SetTextI18n")
        fun bind(course: CourseContent){
            binding.apply {
                courseContentName.text = course.header

                val subContentAdapter: SubContentAdapter by lazy { SubContentAdapter(context) }
                subContentRV.apply {
                    adapter = subContentAdapter
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                }

                lifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                    withContext(Dispatchers.Main) {
                        viewmodel.courseSubContent.collectLatest {
                            when(it){
                                is Resource.Loading -> {

                                }
                                is Resource.Success -> {
                                    subContentAdapter.differ.submitList(it.data)
                                }
                                is Resource.Error -> {

                                }
                            }
                        }
                    }
                }

                showSubContent.setOnClickListener{
                    if(visibility){
                        subContentLL.visibility = View.GONE
                        showSubContent.setImageResource(R.drawable.arrow_drop_down)
                        visibility = false
                    }
                    else{
                        subContentLL.visibility = View.VISIBLE
                        showSubContent.setImageResource(R.drawable.arrow_drop_up)
                        visibility = true
                    }
                }
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseContentViewHolder {
        return CourseContentViewHolder(
            CourseDropDownContentBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: CourseContentAdapter.CourseContentViewHolder, position: Int) {
        val course = differ.currentList[position]
        holder.bind(course)
    }
}


