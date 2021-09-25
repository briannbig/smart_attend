package app.smartattend.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.smartattend.R
import app.smartattend.model.Student
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class StudentAdapter(options : FirebaseRecyclerOptions<Student>): FirebaseRecyclerAdapter<Student, StudentAdapter.ViewHolder>(options){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvRegNo: TextView = itemView.findViewById(R.id.tv_stud_reg)
        val tvName: TextView = itemView.findViewById(R.id.tv_stud_name)
        init{
            itemView.setOnClickListener{

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return  ViewHolder(inflater.inflate(R.layout.list_item_student, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Student) {
        holder.apply {
            tvRegNo.text = model.reg_no
            tvName.text = model.name
        }
    }


}