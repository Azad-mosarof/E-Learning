package com.example.e_learning.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_learning.activity.CourseContentList
import com.example.e_learning.data.Courses
import com.example.e_learning.data.User
import com.example.e_learning.databinding.TopCourseLayoutBinding
import com.example.e_learning.util.MyCourses
import com.example.e_learning.util.UserCollection
import com.example.e_learning.util.auth
import com.example.e_learning.util.fireStore

class TopCoursesAdapter(val context: Context): RecyclerView.Adapter<TopCoursesAdapter.TopCoursesViewHolder>() {

    inner class TopCoursesViewHolder(private val binding: TopCourseLayoutBinding):
        RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(course: Courses){
            binding.apply {
                courseName.text = course.c_name
                Glide.with(itemView).load(course.imgs[0]).into(courseImg)
                features.text = course.features

                add.setOnClickListener{
                    fireStore.collection(UserCollection).document(auth.currentUser!!.uid).collection(MyCourses).add(course)
                        .addOnSuccessListener {
                            Toast.makeText(context, "Course successfully added to your course list", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener{
                            Toast.makeText(context, it.message.toString(), Toast.LENGTH_SHORT).show()
                        }
                }

                flatCard.setOnClickListener{
                    val intent = Intent(context, CourseContentList::class.java)
                    intent.putExtra("courseId", course.id)
                    context.startActivity(intent)
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