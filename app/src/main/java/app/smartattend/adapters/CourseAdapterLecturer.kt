package app.smartattend.adapters

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import app.smartattend.R
import app.smartattend.admin.ui.ClassFragmentDirections
import app.smartattend.admin.ui.CoursesFragmentDirections
import app.smartattend.model.Course
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class CourseAdapterLecturer(options : FirebaseRecyclerOptions<Course>): FirebaseRecyclerAdapter<Course, CourseAdapterLecturer.ViewHolder>(options){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvCode: TextView = itemView.findViewById(R.id.tv_course_code)
        val tvTitle: TextView = itemView.findViewById(R.id.tv_course_title)
        val tvLec: TextView = itemView.findViewById(R.id.tv_course_lecturer)
        init{
            itemView.setOnClickListener{
//                val action = ClassFragmentDirections.actionClassFragmentToCourseReportFragment(tvCode.text.toString())
                val action = CoursesFragmentDirections.actionCoursesFragment3ToCourseReportFragment2(tvCode.text.toString())
                it.findNavController().navigate(action)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return  ViewHolder(inflater.inflate(R.layout.list_item_course, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Course) {
        holder.apply {
            tvCode.text = model.code
            tvTitle.text = model.title
            tvLec.text = model.lecturer
        }
    }


}