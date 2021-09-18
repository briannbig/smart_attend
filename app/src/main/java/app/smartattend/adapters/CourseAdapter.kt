package app.smartattend.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.smartattend.R
import app.smartattend.model.Course

class CourseAdapter(private val courses: ArrayList<Course> ) : RecyclerView.Adapter<CourseAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvCode: TextView = itemView.findViewById(R.id.tv_course_code)
        val tvTitle: TextView = itemView.findViewById(R.id.tv_course_title)
        val tvLec: TextView = itemView.findViewById(R.id.tv_course_lecturer)
        init{
            itemView.setOnClickListener{

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_course, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CourseAdapter.ViewHolder, position: Int) {
        val course: Course = courses[position]
        holder.apply {
            tvCode.text = course.code
            tvTitle.text = course.title
            tvLec.text = course.title
        }
    }

    override fun getItemCount(): Int {
        return courses.size
    }
}