package app.smartattend.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.smartattend.R
import app.smartattend.model.Class

class ClassAdapter(private val classes: ArrayList<Class> ) : RecyclerView.Adapter<ClassAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvId: TextView = itemView.findViewById(R.id.tv_class_id)
        val tvProgram: TextView = itemView.findViewById(R.id.tv_program)
        init{
            itemView.setOnClickListener{

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_class, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClassAdapter.ViewHolder, position: Int) {
        val classs: Class = classes[position]
        holder.apply {
            tvId.text = classs.id
            tvProgram.text = classs.program
        }
    }

    override fun getItemCount(): Int {
        return classes.size
    }
}