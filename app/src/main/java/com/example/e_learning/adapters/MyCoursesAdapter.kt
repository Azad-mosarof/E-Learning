package com.example.e_learning.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_learning.activity.CourseContentList
import com.example.e_learning.data.Courses
import com.example.e_learning.databinding.MyCoursesAdapterLayoutBinding
import com.mikhaellopez.circularprogressbar.CircularProgressBar

class MyCoursesAdapter(val context: Context): RecyclerView.Adapter<MyCoursesAdapter.MyCoursesViewHolder>() {

    inner class MyCoursesViewHolder(private val binding: MyCoursesAdapterLayoutBinding):
        RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(course: Courses){
            binding.apply {
                courseName.text = course.c_name
                Glide.with(itemView).load(course.imgs[0]).into(courseImg)

                flatCard.setOnClickListener{
                    val intent = Intent(context, CourseContentList::class.java)
                    intent.putExtra("courseId", course.id)
                    context.startActivity(intent)
                }

                progressBar.apply {
                    // Set Progress
                    progress = 20f
                    // or with animation
                    setProgressWithAnimation(20f, 4000) // =1s

                    // Set Progress Max
                    progressMax = 100f

                    // Set ProgressBar Color
                    //progressBarColor = Color.BLACK

                    // or with gradient
                    progressBarColorStart = Color.GREEN
                    progressBarColorEnd = Color.GRAY
                    progressBarColorDirection = CircularProgressBar.GradientDirection.TOP_TO_BOTTOM

                    // Set background ProgressBar Color
                    backgroundProgressBarColor = Color.GRAY
                    // or with gradient
                    backgroundProgressBarColorStart = Color.parseColor("#b6bbd8")
                    backgroundProgressBarColorEnd = Color.parseColor("#b6bbd8")
                    backgroundProgressBarColorDirection = CircularProgressBar.GradientDirection.TOP_TO_BOTTOM

                    // Set Width
                    progressBarWidth = 7f // in DP
                    backgroundProgressBarWidth = 8f // in DP

                    // Other
                    roundBorder = true
                    startAngle = 0f
                    progressDirection = CircularProgressBar.ProgressDirection.TO_RIGHT
                }
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCoursesViewHolder {
        return MyCoursesViewHolder(
            MyCoursesAdapterLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: MyCoursesViewHolder, position: Int) {
        val course = differ.currentList[position]
        holder.bind(course)
    }
}